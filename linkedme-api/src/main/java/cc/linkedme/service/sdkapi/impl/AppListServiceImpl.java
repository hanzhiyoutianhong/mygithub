package cc.linkedme.service.sdkapi.impl;

import cc.linkedme.dao.sdkapi.AppListDao;
import cc.linkedme.data.model.AppListInfo;
import cc.linkedme.data.model.params.AppListParams;
import cc.linkedme.service.sdkapi.AppListService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vontroy on 16/4/24.
 */
public class AppListServiceImpl implements AppListService {

    @Resource
    private AppListDao appListDao;

    public boolean compareApps( AppListInfo existedApp, AppListInfo newApp ) {
        return existedApp.getAppName().equals(newApp.getAppName())
                && existedApp.getUriScheme().equals(newApp.getUriScheme())
                && existedApp.getPublicSourceDir().equals(newApp.getPublicSourceDir())
                && existedApp.getSourceDir().equals(newApp.getSourceDir())
                && existedApp.getInstallDate().equals(newApp.getInstallDate())
                && existedApp.getVersionCode().equals(newApp.getVersionCode())
                && existedApp.getVersionName().equals(newApp.getVersionName())
                && existedApp.getOs().equals(newApp.getOs());
    }

    public int addAppList( ArrayList<AppListInfo> appListInfos) {
        ArrayList<AppListInfo> existedAppList = queryAppList( appListInfos.get( 0 ).getIdentityId() );
        Map<String, AppListInfo> appListMap = new HashMap<String, AppListInfo>();
        for( int i = 0; i < existedAppList.size(); i++ ) {
            appListMap.put( existedAppList.get( i ).getAppIdentifier(), existedAppList.get( i ) );
        }
        int result = 0;
        for( int i = 0; i < appListInfos.size(); i++ ) {
            AppListInfo appListInfo = appListInfos.get( i );
            String appIdentifier = appListInfo.getAppIdentifier();
            if( appListMap.get( appIdentifier ) != null ) {
                if( !compareApps( appListMap.get( appIdentifier ), appListInfo ) )
                    result += appListDao.addAppList(appListInfo);
            }
            else
                result += appListDao.addAppList(appListInfo);
        }
        return result;
    }

    public ArrayList<AppListInfo> queryAppList( long identityId ) {
        ArrayList<AppListInfo> appListInfos = new ArrayList<>();
        appListInfos = appListDao.queryAppList( identityId );
        return appListInfos;
    }
}
