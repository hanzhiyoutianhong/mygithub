package cc.linkedme.service.webapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.redis.JedisPort;
import cc.linkedme.commons.shard.ShardingSupportHash;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.dao.webapi.AppDao;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.AppParams;
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
    private ShardingSupportHash<JedisPort> clientShardingSupport;

    public long createApp(AppParams appParams) {
        //long appId = uuidCreator.nextId(2); // 2表示发号器的app业务,后续再调整appid的生成规则
        AppInfo app_live_Info = new AppInfo();
        Random random = new Random(appParams.user_id);
        String live_md5_key = MD5Utils.md5(appParams.app_name + "live" + appParams.user_id + random.nextInt());
        String live_md5_secret = MD5Utils.md5(appParams.user_id + "live" + appParams.app_name + random.nextInt());

        app_live_Info.setApp_key(live_md5_key);
        app_live_Info.setApp_secret(live_md5_secret);
        app_live_Info.setType("live");
        app_live_Info.setUser_id(appParams.user_id);
        app_live_Info.setApp_name(appParams.app_name);
        long appId = appDao.insertApp(app_live_Info);
        if (appId > 0) {
            JedisPort liveClient = clientShardingSupport.getClient(live_md5_key);
            liveClient.set(live_md5_key, appId + "," + live_md5_secret);
            return appId;
        }
        throw new LMException(LMExceptionFactor.LM_SYS_ERROR, "Create appInfo failed");
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
        AppInfo appInfo = appDao.getAppsByAppId(appId);
        return appInfo;
    }

    public int updateApp(AppParams appParams) {
        return appDao.updateApp(appParams);
    }

}
