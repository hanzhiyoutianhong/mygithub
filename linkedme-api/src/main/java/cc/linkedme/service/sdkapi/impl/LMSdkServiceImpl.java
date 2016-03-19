package cc.linkedme.service.sdkapi.impl;

import javax.annotation.Resource;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.dao.sdkapi.ClientDao;
import cc.linkedme.data.model.ClientInfo;
import cc.linkedme.data.model.DeepLink;
import cc.linkedme.data.model.params.LMUrlParams;
import cc.linkedme.exception.LMException;
import cc.linkedme.exception.LMExceptionFactor;
import cc.linkedme.mcq.DeepLinkMsgPusher;


import cc.linkedme.service.sdkapi.LMSdkService;
import com.google.common.base.Joiner;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public class LMSdkServiceImpl implements LMSdkService {
    @Resource
    public UuidCreator uuidCreator;

    @Resource
    public DeepLinkMsgPusher deepLinkMsgPusher;

    @Resource
    public ShardingSupportHash<JedisPort> deepLinkShardingSupport;

    @Resource
    public ClientDao clientDao;

    public int install(ClientInfo clientInfo) {

        int result = 0;
        try {
            result = clientDao.addClient(clientInfo);
        } catch (Exception e) {

        }

        return result;

    }

    public String url(LMUrlParams lmUrlParams) {
        Joiner joiner = Joiner.on("&").skipNulls();
        String urlParamsStr = joiner.join(lmUrlParams.linkedMEKey, lmUrlParams.tags, lmUrlParams.alias, lmUrlParams.channel,
                lmUrlParams.feature, lmUrlParams.stage, lmUrlParams.params);
        String deepLinkMd5 = MD5Utils.md5(urlParamsStr);
        String appId = ""; // get appId
        // 从redis里查找md5是否存在
        // 如果存在,找出对应的deeplink_id,base62进行编码,
        // 根据linkedmeKey从redis里查找出appId,生成短链,返回 //http://lkme.cc/abc/qwerk
        // 如果不存在,发号deeplink_id,在redis里保存md5和deeplink_id的键值对
        // 然后把消息写入队列, 返回短链
        JedisPort redisClient = deepLinkShardingSupport.getClient(deepLinkMd5);
        String id = redisClient.get(deepLinkMd5);
        if (id != null) {
            String linkId = Base62.encode(Long.parseLong(id));
            return Constants.DEEPLINK_HTTP_PREFIX + appId + "/" + linkId;
        }

        long deepLinkId = uuidCreator.nextId(0);
        DeepLink link = new DeepLink(deepLinkId, deepLinkMd5, lmUrlParams.linkedMEKey, lmUrlParams.identityId, lmUrlParams.tags,
                lmUrlParams.alias, lmUrlParams.channel, lmUrlParams.feature, lmUrlParams.stage, lmUrlParams.campaign, lmUrlParams.params,
                lmUrlParams.source, lmUrlParams.sdkVersion);
        // 写mc和redis
        redisClient.set(deepLinkMd5, deepLinkId);
        // set mc
        deepLinkMsgPusher.addDeepLink(link);
        String result = Constants.DEEPLINK_HTTP_PREFIX + appId + "/" + Base62.encode(deepLinkId);

        return result; // linkedme_key & tags & alias & channel & feature & stage & params
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
