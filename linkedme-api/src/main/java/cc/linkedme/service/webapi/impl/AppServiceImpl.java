package cc.linkedme.service.webapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.memcache.MemCacheTemplate;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.serialization.KryoSerializationUtil;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.dao.webapi.AppDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.UrlTagsInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.data.model.params.UrlParams;
import cc.linkedme.service.webapi.AppService;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppServiceImpl implements AppService {
    @Resource
    UuidCreator uuidCreator;

    @Resource
    private AppDao appDao;

    @Resource
    private AppDao urlTagDao;

    @Resource
    private MemCacheTemplate<byte[]> appInfoMemCache;

    @Resource
    private ShardingSupportHash<JedisPort> linkedmeKeyShardingSupport;

    public long createApp(AppParams appParams) {
        AppInfo appInfo = new AppInfo();
        Random random = new Random(appParams.user_id);
        String linkedmeKey = MD5Utils.md5(appParams.app_name + "live" + appParams.user_id + random.nextInt());
        String secret = MD5Utils.md5(appParams.user_id + "live" + appParams.app_name + random.nextInt());

        appInfo.setApp_key(linkedmeKey);
        appInfo.setApp_secret(secret);
        appInfo.setType("live");
        appInfo.setUser_id(appParams.user_id);
        appInfo.setApp_name(appParams.app_name);
        long appId = appDao.insertApp(appInfo);
        if (appId > 0) {
            JedisPort linkedmeKeyClient = linkedmeKeyShardingSupport.getClient(linkedmeKey);
            linkedmeKeyClient.hset(linkedmeKey, "appid", appId);
            linkedmeKeyClient.hset(linkedmeKey, "secret", secret);

            // 把appInfo写入mc,点击短链时需要查询appInfo
            setAppInfoToCache(appInfo);
            return appId;
        }
        throw new LMException(LMExceptionFactor.LM_SYS_ERROR, "Create appInfo failed");
    }

    @Override
    public boolean setAppInfoToCache(AppInfo appInfo) {
        byte[] b = KryoSerializationUtil.serializeObj(appInfo);
        boolean res = appInfoMemCache.set(String.valueOf(appInfo.getApp_id()), b);
        return res;
    }

    public List<AppInfo> getAppsByUserId(AppParams appParams) {
        List<AppInfo> appList = appDao.getAppsByUserId(appParams);
        if (CollectionUtils.isEmpty(appList)) {
            return new ArrayList<AppInfo>(0);
        }
        return appList;
    }

    public int deleteApp(AppParams appParams) {
        return appDao.delApp(appParams);
    }

    public AppInfo queryApp(AppParams appParams) {
        AppInfo appInfo = appDao.getAppsByAppId(appParams.app_id);
        return appInfo;
    }

    public AppInfo getAppById(long appId) {
        AppInfo appInfo;
        // 先从mc取,没有命中再从DB取
        byte[] appInfoByteArr = appInfoMemCache.get(String.valueOf(appId));
        if (appInfoByteArr != null && appInfoByteArr.length > 0) {
            appInfo = KryoSerializationUtil.deserializeObj(appInfoByteArr, AppInfo.class);
            if (appInfo != null) {
                return appInfo;
            }
        }

        appInfo = appDao.getAppsByAppId(appId);
        if (appInfo != null && appInfo.getApp_id() > 0) {
            appInfoMemCache.set(String.valueOf(appId), KryoSerializationUtil.serializeObj(appInfo));
            return appInfo;
        }
        return null;
    }

    public int updateApp(AppParams appParams) {
        return appDao.updateApp(appParams);
    }

    public List<UrlTagsInfo> getUrlTags(AppParams appParams) {
        return urlTagDao.getUrlTagsByAppId(appParams);
    }

    public boolean configUrlTags(UrlParams urlParams) {
        return urlTagDao.configUrlTags(urlParams);
    }

    @Override
    public String uploadImg(AppParams appParams, String imagePath) {
        return appDao.uploadImg(appParams, imagePath);
    }

}
