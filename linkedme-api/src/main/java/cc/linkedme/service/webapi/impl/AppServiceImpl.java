package cc.linkedme.service.webapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.uuid.UuidCreator;
import cc.linkedme.dao.webapi.AppDao;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.service.webapi.AppService;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
        AppInfo appInfo = new AppInfo();
        appInfo.setApp_id(appId);
        appInfo.setApp_name(appParams.app_name);
        appInfo.setUser_id(appParams.user_id);
        if (appDao.insertApp(appInfo) > 0) {
            return appId;
        }
        throw new LMException("Create appInfo failed");
    }

    public List<AppInfo> getAppsByUserId(long userId) {
        List<AppInfo> appList = appDao.getAppsByUserId(userId);
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
