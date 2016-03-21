package cc.linkedme.service.sdkapi.impl;

import javax.annotation.Resource;

import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.commons.util.DeepLinkUtil;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.params.LMCloseParams;
import cc.linkedme.data.model.params.LMInstallParams;
import cc.linkedme.data.model.params.LMOpenParams;
import cc.linkedme.data.model.params.LMUrlParams;
import cc.linkedme.exception.LMException;
import cc.linkedme.exception.LMExceptionFactor;
import cc.linkedme.mcq.ClientMsgPusher;
import cc.linkedme.mcq.DeepLinkMsgPusher;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.sdkapi.LMSdkService;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public class LMSdkServiceImpl implements LMSdkService {
    @Resource
    private UuidCreator uuidCreator;

    @Resource
    private DeepLinkService deepLinkService;

    @Resource
    private DeepLinkMsgPusher deepLinkMsgPusher;

    @Resource
    private ClientMsgPusher clientMsgPusher;

    @Resource
    private ShardingSupportHash<JedisPort> deepLinkShardingSupport;

    @Resource
    private ShardingSupportHash<JedisPort> clientShardingSupport;

    @Resource
    public ClientDao clientDao;

    public String install(LMInstallParams lmInstallParams) {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setDeviceId(lmInstallParams.deviceId);
        clientInfo.setLinkedmeKey(lmInstallParams.linkedMEKey);
        clientInfo.setDeviceType(lmInstallParams.deviceType);
        clientInfo.setDeviceModel(lmInstallParams.deviceModel);
        clientInfo.setDeviceBrand(lmInstallParams.deviceBrand);
        clientInfo.setHasBlutooth(lmInstallParams.hasBluetooth);
        clientInfo.setHasNfc(lmInstallParams.hasNfc);
        clientInfo.setHasSim(lmInstallParams.hasSim);
        clientInfo.setOs(lmInstallParams.os);
        clientInfo.setOsVersion(lmInstallParams.osVersion);
        clientInfo.setScreenDpi(lmInstallParams.screenDpi);
        clientInfo.setScreenHeight(lmInstallParams.screenHeight);
        clientInfo.setScreenWidth(lmInstallParams.screenWidth);
        clientInfo.setIsWifi(lmInstallParams.isWifi);
        clientInfo.setIsReferable(lmInstallParams.isReferable);
        clientInfo.setLatVal(lmInstallParams.vatVal);
        clientInfo.setCarrier(lmInstallParams.carrier);
        clientInfo.setAppVersion(lmInstallParams.appVersion);
        clientInfo.setSdkUpdate(lmInstallParams.sdkUpdate);
        clientInfo.setIosTeamId(lmInstallParams.iOSTeamId);
        clientInfo.setIosBundleId(lmInstallParams.iOSBundleId);

        String params = null;
        String deviceId = lmInstallParams.deviceId;
        JedisPort clientRedisClient = clientShardingSupport.getClient(deviceId);
        String identityIdStr = clientRedisClient.get(deviceId);
        long identityId;
        long deepLinkId = 0;
        if (Strings.isNullOrEmpty(identityIdStr)) { // 之前不存在identityId
            // device_fingerprint_id 与 browse_fingerprint_id匹配逻辑
            String[] identityIdAndDeepLinkId = matchDfpIdAndBfpId(lmInstallParams);
            if (identityIdAndDeepLinkId.length == 2) { // 匹配成功
                identityId = Long.parseLong(identityIdAndDeepLinkId[0]);
                deepLinkId = Long.parseLong(identityIdAndDeepLinkId[1]);
                params = deepLinkService.getDeepLinkParam(deepLinkId);
            } else {
                // 匹配不成功, 生成identity_id
                identityId = uuidCreator.nextId(1); //1表示发号器的identity_id业务
            }
            // 记录<device_id, identity_id>和<identity_id, device_id>
            clientRedisClient.set(deviceId, identityId);
            clientRedisClient.set(String.valueOf(identityId), deviceId);
        } else { // 之前存在identityId
            identityId = Long.parseLong(identityIdStr);
            JedisPort identityRedisClient = clientShardingSupport.getClient(identityIdStr);
            String deepLinkIdStr = identityRedisClient.get(identityIdStr);
            if (Strings.isNullOrEmpty(deepLinkIdStr)) { // 之前存在identityId,
                                                        // 但是没有identityId与deepLink的键值对
                // device_fingerprint_id 与 browse_fingerprint_id匹配逻辑
                // 如果匹配上了,更新<device_id, identity_id>记录，并把<device_id, identity_id>放在历史库;
                String[] identityIdAndDeepLinkId = matchDfpIdAndBfpId(lmInstallParams);
                if (identityIdAndDeepLinkId.length == 2) { // device_fingerprint_id 与
                                                           // browse_fingerprint_id匹配成功
                    long rightIdentityId = Long.parseLong(identityIdAndDeepLinkId[0]);
                    long dlId = Long.parseLong(identityIdAndDeepLinkId[1]);
                    params = deepLinkService.getDeepLinkParam(deepLinkId);
                    clientRedisClient.set(deviceId + ".old", identityId);
                    clientRedisClient.set(deviceId, rightIdentityId);
                }
            } else { // 之前存在identityId, 并有identityId与deepLink的键值对
                deepLinkId = Long.parseLong(deepLinkIdStr);
                params = deepLinkService.getDeepLinkParam(deepLinkId); // get params from mc
            }
        }

        clientInfo.setIdentityId(identityId);
        long fromDeepLinkId = deepLinkId;   //用于统计一个deeplink带来的下载量
        if(Strings.isNullOrEmpty(params)) {
            fromDeepLinkId = 0;
        }
        //写mcq
        clientMsgPusher.addClient(clientInfo, fromDeepLinkId);

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("session_id", System.currentTimeMillis());
        resultJson.append("identity_id", identityId);
        resultJson.append("device_fingerprint_id", lmInstallParams.deviceFingerprintId);
        resultJson.append("browser_fingerprint_id", "");
        resultJson.append("link", "");
        resultJson.append("params", params);
        resultJson.append("is_first_session", true);
        resultJson.append("clicked_linkedme_link", !Strings.isNullOrEmpty(params));
        return resultJson.flip().toString();
    }

    private String[] matchDfpIdAndBfpId(LMInstallParams lmInstallParams) {
        Joiner joiner = Joiner.on("&").skipNulls();
        String deviceParamsStr = joiner.join(lmInstallParams.deviceBrand, lmInstallParams.deviceModel, lmInstallParams.os,
                lmInstallParams.osVersion, lmInstallParams.screenDpi, lmInstallParams.screenHeight, lmInstallParams.screenWidth);
        String deviceFingerprintId = MD5Utils.md5(deviceParamsStr);
        JedisPort clientRedisClient = clientShardingSupport.getClient(deviceFingerprintId);
        String identityIdAndDeepLinkId = clientRedisClient.get(deviceFingerprintId);
        if (Strings.isNullOrEmpty(identityIdAndDeepLinkId)) {
            return new String[0];
        }
        String[] result = identityIdAndDeepLinkId.split(",");
        if (result.length != 2) {
            return new String[0];
        }
        return result;
    }

    public String open(LMOpenParams lmOpenParams) {
        String deepLinkUrl = lmOpenParams.extra_uri_data;
        String deepLink = DeepLinkUtil.getDeepLinkFromUrl(deepLinkUrl);
        long deepLinkId = Base62.decode(deepLink);
        String param = deepLinkService.getDeepLinkParam(deepLinkId);
        return param;
    }

    public String url(LMUrlParams lmUrlParams) {
        Joiner joiner = Joiner.on("&").skipNulls();
        String urlParamsStr = joiner.join(lmUrlParams.linkedMEKey, lmUrlParams.tags, lmUrlParams.alias, lmUrlParams.channel,
                lmUrlParams.feature, lmUrlParams.stage, lmUrlParams.params);
        String deepLinkMd5 = MD5Utils.md5(urlParamsStr);
        // 从redis里查找md5是否存在
        // 如果存在,找出对应的deeplink_id,base62进行编码,
        // 根据linkedmeKey从redis里查找出appId,生成短链,返回 //http://lkme.cc/abc/qwerk
        // 如果不存在,发号deeplink_id,在redis里保存md5和deeplink_id的键值对
        // 然后把消息写入队列, 返回短链
        JedisPort redisClient = deepLinkShardingSupport.getClient(deepLinkMd5);
        String id = redisClient.get(deepLinkMd5);

        long appId = 0; // 根据linkedme_key去库里查询
        if (id != null) {
            String linkId = Base62.encode(Long.parseLong(id));
            return Constants.DEEPLINK_HTTP_PREFIX + appId + "/" + linkId;
        }

        long deepLinkId = uuidCreator.nextId(0);    //0表示发号器的deepLink业务
        DeepLink link = new DeepLink(deepLinkId, deepLinkMd5, lmUrlParams.appid, lmUrlParams.linkedMEKey, lmUrlParams.identityId,
                lmUrlParams.tags, lmUrlParams.alias, lmUrlParams.channel, lmUrlParams.feature, lmUrlParams.stage, lmUrlParams.campaign,
                lmUrlParams.params, lmUrlParams.source, lmUrlParams.sdkVersion);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        link.setCreateTime(df.format(new Date())); // 设置deeplink的创建时间
        // 写redis
        redisClient.set(deepLinkMd5, deepLinkId);
        // set mc
        deepLinkService.addDeepLinkToCache(link);
        // 写消息队列
        deepLinkMsgPusher.addDeepLink(link);
        String result = Constants.DEEPLINK_HTTP_PREFIX + Base62.encode(appId) + "/" + Base62.encode(deepLinkId);

        return result; // linkedme_key & tags & alias & channel & feature & stage & params
    }

    public void close(LMCloseParams lmCloseParams) {
        // 清空session
        ApiLogger.info("");// 记录日志
    }

    public String preInstall(String linkClickId) {
        String result = null;

        try {

            // set identify_id for browser,

        } catch (Exception e) {
            // error log
            ApiLogger.error("");
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, this.getClass().getName() + ".preInstall");
        }

        // info log
        ApiLogger.info("");


        return result;
    }


}