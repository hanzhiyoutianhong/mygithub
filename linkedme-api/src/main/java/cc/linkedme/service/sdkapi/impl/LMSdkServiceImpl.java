package cc.linkedme.service.sdkapi.impl;

import javax.annotation.Resource;

import cc.linkedme.commons.counter.component.CountComponent;
import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.ArrayUtil;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.commons.util.DeepLinkUtil;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.DeepLinkCount;
import cc.linkedme.data.model.params.CloseParams;
import cc.linkedme.data.model.params.InstallParams;
import cc.linkedme.data.model.params.OpenParams;
import cc.linkedme.data.model.params.PreInstallParams;
import cc.linkedme.data.model.params.UrlParams;
import cc.linkedme.exception.LMException;
import cc.linkedme.exception.LMExceptionFactor;
import cc.linkedme.mcq.ClientMsgPusher;
import cc.linkedme.mcq.DeepLinkMsgPusher;
import cc.linkedme.service.DeepLinkService;
import cc.linkedme.service.sdkapi.LMSdkService;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.web.bind.ServletRequestUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private CountComponent deepLinkCountComponent;

    private static ThreadPoolExecutor deepLinkCountThreadPool = new ThreadPoolExecutor(20, 20, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(300), new ThreadPoolExecutor.DiscardOldestPolicy());

    public final static float UNIVERSE_LINK_IOS_VERSION = 8;

    public String install(InstallParams installParams) {
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setDeviceId(installParams.device_id);
        clientInfo.setLinkedmeKey(installParams.linkedme_key);
        clientInfo.setDeviceType(installParams.device_type);
        clientInfo.setDeviceModel(installParams.device_model);
        clientInfo.setDeviceBrand(installParams.device_brand);
        clientInfo.setHasBlutooth(installParams.has_bluetooth);
        clientInfo.setHasNfc(installParams.has_nfc);
        clientInfo.setHasSim(installParams.has_sim);
        clientInfo.setOs(installParams.os);
        clientInfo.setOsVersion(installParams.os_version);
        clientInfo.setScreenDpi(installParams.screen_dpi);
        clientInfo.setScreenHeight(installParams.screen_height);
        clientInfo.setScreenWidth(installParams.screen_width);
        clientInfo.setIsWifi(installParams.is_wifi);
        clientInfo.setIsReferable(installParams.is_referable);
        clientInfo.setLatVal(installParams.lat_val);
        clientInfo.setCarrier(installParams.carrier);
        clientInfo.setAppVersion(installParams.app_version);
        clientInfo.setSdkUpdate(installParams.sdk_update);
        clientInfo.setIosTeamId(installParams.ios_team_id);
        clientInfo.setIosBundleId(installParams.ios_bundle_id);

        // 根据linkedme_key获取appid和secret
        JedisPort appKeyClient = clientShardingSupport.getClient(installParams.linkedme_key);
        String appIdAndSecret = appKeyClient.get(installParams.linkedme_key);
        long appId = 0;
        String secret = null;
        if (!Strings.isNullOrEmpty(appIdAndSecret)) {
            String[] tmp = appIdAndSecret.split(",");
            appId = Long.parseLong(tmp[0]);
            secret = tmp[1];
        }

        String deviceId = installParams.device_id;
        JedisPort clientRedisClient = clientShardingSupport.getClient(deviceId);
        String identityIdStr = clientRedisClient.get(deviceId);
        long identityId;
        long deepLinkId = 0;
        DeepLink deepLink = null;
        if (Strings.isNullOrEmpty(identityIdStr)) { // 之前不存在identityId
            // device_fingerprint_id 与 browse_fingerprint_id匹配逻辑
            String[] identityIdAndDeepLinkId = matchDfpIdAndBfpId(installParams);
            if (identityIdAndDeepLinkId.length == 2) { // 匹配成功
                identityId = Long.parseLong(identityIdAndDeepLinkId[0]);
                deepLinkId = Long.parseLong(identityIdAndDeepLinkId[1]);
                deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);

            } else {
                // 匹配不成功, 生成identity_id
                identityId = uuidCreator.nextId(1); // 1表示发号器的identity_id业务
            }
            // 记录<device_id, identity_id>和<identity_id, device_id>
            clientRedisClient.set(deviceId, identityId);

            JedisPort identityRedisClient = clientShardingSupport.getClient(identityId);
            identityRedisClient.set(identityId + ".di", deviceId);
        } else { // 之前存在identityId
            identityId = Long.parseLong(identityIdStr);
            JedisPort identityRedisClient = clientShardingSupport.getClient(identityId);
            String deepLinkIdStr = identityRedisClient.get(identityIdStr + ".dpi");
            if (Strings.isNullOrEmpty(deepLinkIdStr)) { // 之前存在identityId,
                                                        // 但是没有identityId与deepLink的键值对
                // device_fingerprint_id 与 browse_fingerprint_id匹配逻辑
                // 如果匹配上了,更新<device_id, identity_id>记录，并把<device_id, identity_id>放在历史库;
                String[] identityIdAndDeepLinkId = matchDfpIdAndBfpId(installParams);
                if (identityIdAndDeepLinkId.length == 2) { // device_fingerprint_id 与
                                                           // browse_fingerprint_id匹配成功
                    long rightIdentityId = Long.parseLong(identityIdAndDeepLinkId[0]);
                    long dlId = Long.parseLong(identityIdAndDeepLinkId[1]);
                    deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);
                    clientRedisClient.set(deviceId + ".old", identityId);
                    clientRedisClient.set(deviceId, rightIdentityId);
                }
            } else { // 之前存在identityId, 并有identityId与deepLink的键值对
                deepLinkId = Long.parseLong(deepLinkIdStr);
                deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);
            }
        }

        String params = getParamsFromDeepLink(deepLink);
        long fromDeepLinkId = deepLinkId; // 用于统计一个deeplink带来的下载量
        if (Strings.isNullOrEmpty(params)) {
            fromDeepLinkId = 0;
        }
        // 写mcq
        clientInfo.setIdentityId(identityId);
        clientMsgPusher.addClient(clientInfo, fromDeepLinkId);

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("session_id", System.currentTimeMillis());
        resultJson.append("identity_id", String.valueOf(identityId));
        resultJson.append("device_fingerprint_id", installParams.device_fingerprint_id);
        resultJson.append("browser_fingerprint_id", "");
        resultJson.append("link", "");
        resultJson.append("params", params);
        resultJson.append("is_first_session", true);
        resultJson.append("clicked_linkedme_link", !Strings.isNullOrEmpty(params));
        return resultJson.flip().toString();
    }

    private String[] matchDfpIdAndBfpId(InstallParams installParams) {
        Joiner joiner = Joiner.on("&").skipNulls();
        // TODO 生成指纹的时候加上appkey信息,去掉屏幕的三个信息
        String deviceFingerprintId = createFingerprintId(installParams.os, installParams.os_version, installParams.screen_dpi,
                installParams.screen_height, installParams.screen_width);
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

    private static String createFingerprintId(String os, String os_version, int screen_dpi, int screen_height, int screen_width) {
        Joiner joiner = Joiner.on("&").skipNulls();
        String deviceParamsStr = joiner.join(os, os_version, screen_dpi, screen_height, screen_width);
        return MD5Utils.md5(deviceParamsStr);
    }

    private static String getClickIdFromUri(String deepLinkUrl) {
        String clickId = "";
        if (deepLinkUrl.startsWith("http") || deepLinkUrl.startsWith("https")) {
            clickId = DeepLinkUtil.getDeepLinkFromUrl(deepLinkUrl);
        } else {
            try {
                URI uri = new URI(deepLinkUrl);
                String paramStr = uri.getQuery();
                String[] params = paramStr.split("&");
                for (String param : params) {
                    if (param.split("=")[0].equals("click_id")) {
                        clickId = param.split("=")[1];
                        break;
                    }
                }
            } catch (Exception e) {
                throw new LMException(LMExceptionFactor.LM_ILLEGAL_PARAMETER_VALUE, deepLinkUrl);
            }
        }
        return clickId;
    }

    public String open(OpenParams openParams) {
        String deepLinkUrl = null;
        // 根据linkedme_key获取appid和secret
        JedisPort appKeyClient = clientShardingSupport.getClient(openParams.linkedme_key);
        String appIdAndSecret = appKeyClient.get(openParams.linkedme_key);
        long appId = 0;
        String secret = null;
        if (!Strings.isNullOrEmpty(appIdAndSecret)) {
            String[] tmp = appIdAndSecret.split(",");
            appId = Long.parseLong(tmp[0]);
            secret = tmp[1];
        }

        if ("Android".equals(openParams.os)) {
            deepLinkUrl = openParams.external_intent_uri;
        } else if ("iOS".equals(openParams.os)) {
            String[] osVersionArr = openParams.os_version.split("\\.");
            String osMajorVersion = osVersionArr[0];
            if (Integer.parseInt(osMajorVersion) >= UNIVERSE_LINK_IOS_VERSION) {
                deepLinkUrl = openParams.universal_link_url;
            }

            if (Strings.isNullOrEmpty(deepLinkUrl)) {
                deepLinkUrl = openParams.extra_uri_data;
            }

        } else {
            return null;
        }
        String clickId = getClickIdFromUri(deepLinkUrl);
        long deepLinkId = Base62.decode(clickId);
        DeepLink deepLink = deepLinkService.getDeepLinkInfo(deepLinkId, appId);
        if (deepLink == null) {
            return null;
        }
        String params = getParamsFromDeepLink(deepLink);

        // count
        String deviceType = null;
        if (!Strings.isNullOrEmpty(openParams.os)) {
            deviceType = openParams.os.trim().toLowerCase();
        }
        if ("android".equals(deviceType)) {
            deviceType = "adr";
        }
        String countType = deviceType + "_open";
        if (!DeepLinkCount.isValidCountType(countType)) {
            // TODO 对deviceType做判断
            countType = "other_" + "_open";
        }
        final String type = countType;
        deepLinkCountThreadPool.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    // TODO 对deeplink_id的有效性做判断
                    deepLinkCountComponent.incr(deepLinkId, type, 1);
                } catch (Exception e) {
                    ApiLogger.warn("LMSdkServiceImpl.open deepLinkCountThreadPool count failed", e);
                }
                return null;
            }
        });

        return params;
    }

    private static String getParamsFromDeepLink(DeepLink deepLink) {
        if (deepLink == null) {
            return null;
        }
        String param = deepLink.getParams();
        if (Strings.isNullOrEmpty(param)) {
            return null;
        }
        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.fromObject(param);
        } catch (Exception e) {
            ApiLogger.warn("LMSdkServiceImpl.getParamsFromDeepLink failed, param json is invalid", e);
            return null;
        }

        String[] tags = new String[0];
        String[] channel = new String[0];
        String[] feature = new String[0];
        String[] stage = new String[0];
        if (!Strings.isNullOrEmpty(deepLink.getTags())) {
            tags = deepLink.getTags().split(",");
        }
        if (!Strings.isNullOrEmpty(deepLink.getChannel())) {
            channel = deepLink.getChannel().split(",");
        }
        if (!Strings.isNullOrEmpty(deepLink.getFeature())) {
            feature = deepLink.getFeature().split(",");
        }
        if (!Strings.isNullOrEmpty(deepLink.getStage())) {
            stage = deepLink.getStage().split(",");
        }

        JSONArray tagsJson = JSONArray.fromObject(tags);
        JSONArray channelJson = JSONArray.fromObject(channel);
        JSONArray featureJson = JSONArray.fromObject(feature);
        JSONArray stageJson = JSONArray.fromObject(stage);

        jsonObject.put("tags", tagsJson);
        jsonObject.put("channel", channelJson);
        jsonObject.put("feature", featureJson);
        jsonObject.put("stage", stageJson);
        return jsonObject.toString();
    }

    public String url(UrlParams urlParams) {
        Joiner joiner = Joiner.on("&").skipNulls();
        Joiner joiner2 = Joiner.on(",").skipNulls();
        // linkedme_key & tags & alias & channel & feature & stage & params
        String urlParamsStr = joiner.join(urlParams.linkedme_key, joiner2.join(urlParams.tags), urlParams.alias,
                joiner2.join(urlParams.channel), joiner2.join(urlParams.feature), joiner2.join(urlParams.stage), urlParams.params);
        String deepLinkMd5 = MD5Utils.md5(urlParamsStr);
        // 从redis里查找md5是否存在
        // 如果存在,找出对应的deeplink_id,base62进行编码,
        // 根据linkedmeKey从redis里查找出appId,生成短链,返回 //http://lkme.cc/abc/qwerk
        // 如果不存在,发号deeplink_id,在redis里保存md5和deeplink_id的键值对
        // 然后把消息写入队列, 返回短链
        JedisPort redisClient = deepLinkShardingSupport.getClient(deepLinkMd5);
        String id = redisClient.get(deepLinkMd5);

        long appId = urlParams.app_id; // web创建url传appid, sdk创建url不传appid
        if (appId <= 0) {
            String value = clientShardingSupport.getClient(urlParams.linkedme_key).get(urlParams.linkedme_key);
            if (!Strings.isNullOrEmpty(value)) {
                appId = Long.parseLong(value.split(",")[0]); // 根据linkedme_key去库里查询
            }
        }
        if (id != null) {
            String link = Base62.encode(Long.parseLong(id));
            return Constants.DEEPLINK_HTTPS_PREFIX + Base62.encode(appId) + "/" + link;
        }

        long deepLinkId = uuidCreator.nextId(0); // 0表示发号器的deepLink业务
        DeepLink link = new DeepLink(deepLinkId, deepLinkMd5, urlParams.app_id, urlParams.linkedme_key, urlParams.identity_id,
                ArrayUtil.strArrToString(urlParams.tags), urlParams.alias, ArrayUtil.strArrToString(urlParams.channel),
                ArrayUtil.strArrToString(urlParams.feature), ArrayUtil.strArrToString(urlParams.stage),
                ArrayUtil.strArrToString(urlParams.campaign), urlParams.params.toString(), urlParams.source, urlParams.sdk_version);
        link.setLink_label(urlParams.link_label);
        link.setIos_use_default(urlParams.ios_use_default);
        link.setIos_custom_url(urlParams.ios_custom_url);
        link.setAndroid_use_default(urlParams.android_use_default);
        link.setAndroid_custom_url(urlParams.android_custom_url);
        link.setDesktop_use_default(urlParams.desktop_use_default);
        link.setDesktop_custom_url(urlParams.desktop_custom_url);


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        link.setCreateTime(df.format(new Date())); // 设置deeplink的创建时间
        link.setAppId(appId);
        // 写redis
        redisClient.set(deepLinkMd5, deepLinkId);
        // set mc
        deepLinkService.addDeepLinkToCache(link);
        // 写消息队列
        deepLinkMsgPusher.addDeepLink(link);
        String result = Constants.DEEPLINK_HTTPS_PREFIX + Base62.encode(appId) + "/" + Base62.encode(deepLinkId);

        return result;
    }

    public void close(CloseParams closeParams) {
        ApiLogger.info(closeParams.device_fingerprint_id + ", " + closeParams.linkedme_key + " close");// 记录日志
    }

    public String preInstall(PreInstallParams preInstallParams) {
        String result = null;
        long identityId = 0;
        if (preInstallParams.identity_id <= 0) {
            identityId = uuidCreator.nextId(1); // 浏览器的cookie里没有identityId,新分配
            String browseFingerprintId = createFingerprintId(preInstallParams.os, preInstallParams.os_version, preInstallParams.screen_dpi,
                    preInstallParams.screen_height, preInstallParams.screen_width);

            String identityIdAndDeepLinkId = identityId + "," + preInstallParams.deeplink_id;
            JedisPort browseFingerprintIdRedisClient = clientShardingSupport.getClient(browseFingerprintId);
            browseFingerprintIdRedisClient.set(browseFingerprintId, identityIdAndDeepLinkId);

            return String.valueOf(identityId);
        } else {
            // 浏览器里有identityId,不需要重新生成,从库里查找
            JedisPort identityRedisClient = clientShardingSupport.getClient(identityId);
            String deviceId = identityRedisClient.get(identityId + "di");
            if (Strings.isNullOrEmpty(deviceId)) {
                // 说明库里没有该identityId,存储browse_fingerprint_id和deeplinkid键值对
                String browseFingerprintId = createFingerprintId(preInstallParams.os, preInstallParams.os_version,
                        preInstallParams.screen_dpi, preInstallParams.screen_height, preInstallParams.screen_width);
                String identityIdAndDeepLinkId = preInstallParams.identity_id + "," + preInstallParams.deeplink_id;
                JedisPort browseFingerprintIdRedisClient = clientShardingSupport.getClient(browseFingerprintId);
                browseFingerprintIdRedisClient.set(browseFingerprintId, identityIdAndDeepLinkId);
            } else {
                boolean res = identityRedisClient.set(String.valueOf(identityId), preInstallParams.deeplink_id);
                identityRedisClient.expire(String.valueOf(identityId), 2 * 60 * 60); // 设置过期时间
            }
            return "";
        }

    }


}
