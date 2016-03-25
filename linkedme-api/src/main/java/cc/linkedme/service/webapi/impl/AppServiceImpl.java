package cc.linkedme.service.webapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.util.MD5Utils;
import cc.linkedme.commons.utils.RandomUtils;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.dao.webapi.AppDao;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.service.webapi.AppService;
import org.apache.commons.collections.CollectionUtils;
import sun.security.provider.MD5;

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

    public long createApp(AppParams appParams) {
        long appId = uuidCreator.nextId(2); //2表示发号器的app业务
        AppInfo app_live_Info = new AppInfo();
        AppInfo app_test_Info = new AppInfo();
        String live_md5_key = appParams.app_name + "live" + appParams.user_id + new Random(appId);
        String test_md5_key = appParams.app_name + "test" + appParams.user_id + new Random(appId);

        app_live_Info.setApp_key( new MD5Utils().md5( live_md5_key ) );
        app_live_Info.setApp_id(appId);
        app_live_Info.setType( "live" );
        app_live_Info.setUser_id(appParams.user_id);
        app_live_Info.setApp_name(appParams.app_name);

        app_test_Info.setApp_key( new MD5Utils().md5( test_md5_key ) );
        app_test_Info.setApp_id(appId);
        app_test_Info.setType( "test" );
        app_test_Info.setUser_id(appParams.user_id);
        app_test_Info.setApp_name(appParams.app_name);


        if (appDao.insertApp(app_live_Info) > 0 && appDao.insertApp(app_test_Info) > 0 ) {
            return appId;
        }
        throw new LMException("Create appInfo failed");
    }

    public List<AppInfo> getAppsByUserId( AppParams appParams ) {
        List<AppInfo> appList = appDao.getAppsByUserId(appParams);
        if(CollectionUtils.isEmpty(appList)) {
            return new ArrayList<AppInfo>(0);
        }
        return appList;
    }

    public int deleteApp(AppParams appParams)
    {
        return appDao.delApp( appParams );
    }

    public AppInfo queryApp( AppParams appParams )
    {
        AppInfo appInfo = appDao.getAppsByAppId( appParams );


        return appInfo;
    }

    public int updateApp( AppParams appParams )
    {
        return appDao.updateApp( appParams );
    }

}
