package cc.linkedme.service.sdkapi.impl;

import cc.linkedme.commons.memcache.MemCacheTemplate;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.data.model.params.JsRecordIdParams;
import cc.linkedme.service.sdkapi.JsService;

import javax.annotation.Resource;

/**
 * Created by LinkedME01 on 16/5/19.
 */
public class JsServiceImpl implements JsService {
    @Resource
    private ShardingSupportHash<JedisPort> clientShardingSupport;

    @Resource
    private MemCacheTemplate<String> browserFingerprintIdForYYBMemCache;

    @Override
    public void recordId(JsRecordIdParams jsRecordIdParams) {
        String identityId = String.valueOf(jsRecordIdParams.identity_id);
        if (jsRecordIdParams.is_valid_identityid) {
            JedisPort identityRedisClient = clientShardingSupport.getClient(identityId);
            boolean res = identityRedisClient.set(identityId + ".dpi", jsRecordIdParams.deeplink_id);
            if (res) {
                identityRedisClient.expire(identityId + ".dpi", 2 * 60 * 60);
            }

            if (jsRecordIdParams.is_pc_scan) {
                res = identityRedisClient.set(identityId + ".scan", "1");
                if (res) {
                    identityRedisClient.expire(identityId + ".scan", 2 * 60 * 60);
                }
            }
        }
        String browserFingerprintId = jsRecordIdParams.browser_fingerprint_id;
        JedisPort browseFingerprintIdRedisClient = clientShardingSupport.getClient(browserFingerprintId);
        browseFingerprintIdRedisClient.hset(browserFingerprintId, "iid", identityId);
        browseFingerprintIdRedisClient.hset(browserFingerprintId, "did", jsRecordIdParams.deeplink_id);
        if (jsRecordIdParams.is_pc_scan) {
            browseFingerprintIdRedisClient.hset(browserFingerprintId, "scan", "1");
        }
        browseFingerprintIdRedisClient.expire(browserFingerprintId, 2 * 60 * 60); // 设置过期时间

    }


    @Override
    public void recordIdForYYB(JsRecordIdParams jsRecordIdParams) {
        String browserFingerprintId = jsRecordIdParams.browser_fingerprint_id;
        browserFingerprintIdForYYBMemCache.set(browserFingerprintId + ".yyb", String.valueOf(jsRecordIdParams.deeplink_id),
                Constants.EXPTIME_BROWSER_FINGERPRINT_ID_FOR_YYB);
    }
}
