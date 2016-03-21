package cc.linkedme.dao.webapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.webapi.AppDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.AppInfo;
import cc.linkedme.data.model.params.AppParams;
import org.hibernate.annotations.Table;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppDaoImpl extends BaseDao implements AppDao {
    private static final String ADD_APP = "ADD_APP";
    private static final String GET_APPS_BY_USERID = "GET_APPS_BY_USERID";
    private static final String DEL_APP_BY_USERID_AND_APPNAME = "DEL_APP_BY_USERID_AND_APPNAME";
    private static final String GET_APP_BY_APPID = "GET_APP_BY_APPID";
    private static final String UPDATE_APP_BY_APPID = "UPDATE_APP_BY_APPID";

    public int insertApp(AppInfo appInfo) {
        int result = 0;
        if (appInfo == null) {
            ApiLogger.error("AppDaoImpl.insertApp appInfo is null, add failed");
            return result;
        }
        long appId = appInfo.getAppId();
        String appName = appInfo.getAppName();
        long userId = appInfo.getUserId();
        TableChannel tableChannel = tableContainer.getTableChannel("appInfo", ADD_APP, userId, userId); //根据userId hash
        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), new Object[] {appId, appName, userId});
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuilder(128).append("Duplicate insert deepLink, appName=").append(appName), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return result;
    }


    public List<AppInfo> getAppsByUserId(final long userId) {
        TableChannel tableChannel = tableContainer.getTableChannel("appInfo", GET_APPS_BY_USERID, userId, userId);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<AppInfo> appInfos = new ArrayList<AppInfo>();
        jdbcTemplate.query(tableChannel.getSql(), new Object[]{userId}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                AppInfo app = new AppInfo();
                app.setAppId( resultSet.getLong( "id" ) );
                app.setAppName( resultSet.getString( "app_name" ) );
                app.setAppLiveKey( resultSet.getString( "app_live_key" ) );
                app.setAppLiveSecret( resultSet.getString( "app_live_secret" ) );
                app.setAppTestKey( resultSet.getString( "app_test_key" ) );
                app.setAppTestSecret( resultSet.getString( "app_test_secret" ) );
                app.setIosUriScheme( resultSet.getString( "ios_uri_scheme" ) );
                app.setIosNotUrl( resultSet.getString( "ios_not_url" ) );
                app.setIosStoreUrl( resultSet.getString( "ios_store_url" ) );
                app.setIosCustomUrl( resultSet.getString( "ios_custom_url" ) );
                app.setIosBundleId( resultSet.getString( "ios_bundle_id" ) );
                app.setIosPrefix( resultSet.getString( "ios_prefix" ) );
                app.setIosTeamId( resultSet.getString( "ios_team_id" ) );
                app.setAndroidUriScheme( resultSet.getString( "android_uri_scheme" ) );
                app.setAndroidNotUrl( resultSet.getString( "android_not_url" ) );
                app.setGooglePlayUrl( resultSet.getString( "google_play_url" ) );
                app.setAndroidCustomUrl( resultSet.getString( "android_custom_url" ) );
                app.setAndroidPackageName( resultSet.getString( "android_package_name" ) );
                app.setAndroidPrefix( resultSet.getString( "android_prefix" ) );
                app.setIosAndroidFlag( resultSet.getInt( "ios_android_flag" ) );
                app.setDesktopUrl( resultSet.getString( "desktop_url" ) );
                appInfos.add(app);
                return null;
            }
        });
        return appInfos;
    }

    public int delApp(AppParams appParams)
    {
        int result = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("appInfo", DEL_APP_BY_USERID_AND_APPNAME, appParams.userId, appParams.userId );
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        try
        {
            jdbcTemplate.update( tableChannel.getSql(), new Object[]{appParams.userId, appParams.appName} );
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e))
            {
                ApiLogger.warn(new StringBuilder(128).append("Duplicate insert deepLink, appName=").append(appParams.appName), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return result;
    }

    public AppInfo getAppsByAppId(final AppParams appParams )
    {
        TableChannel tableChannel = tableContainer.getTableChannel("appInfo", GET_APP_BY_APPID, appParams.userId, appParams.userId );
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        final List<AppInfo> appInfos = new ArrayList<AppInfo>();
        jdbcTemplate.query(tableChannel.getSql(), new Object[]{appParams.appId}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException
            {
                AppInfo app = new AppInfo();
                app.setUserId( resultSet.getLong( "user_id" ) );
                app.setAppName( resultSet.getString( "app_name" ) );
                app.setAppLiveKey( resultSet.getString( "app_live_key" ) );
                app.setAppLiveSecret( resultSet.getString( "app_live_secret" ) );
                app.setAppTestKey( resultSet.getString( "app_test_key" ) );
                app.setAppTestSecret( resultSet.getString( "app_test_secret" ) );
                app.setIosUriScheme( resultSet.getString( "ios_uri_scheme" ) );
                app.setIosNotUrl( resultSet.getString( "ios_not_url" ) );
                app.setIosStoreUrl( resultSet.getString( "ios_store_url" ) );
                app.setIosCustomUrl( resultSet.getString( "ios_custom_url" ) );
                app.setIosBundleId( resultSet.getString( "ios_bundle_id" ) );
                app.setIosPrefix( resultSet.getString( "ios_prefix" ) );
                app.setIosTeamId( resultSet.getString( "ios_team_id" ) );
                app.setAndroidUriScheme( resultSet.getString( "android_uri_scheme" ) );
                app.setAndroidNotUrl( resultSet.getString( "android_not_url" ) );
                app.setGooglePlayUrl( resultSet.getString( "google_play_url" ) );
                app.setAndroidCustomUrl( resultSet.getString( "android_custom_url" ) );
                app.setAndroidPackageName( resultSet.getString( "android_package_name" ) );
                app.setAndroidPrefix( resultSet.getString( "android_prefix" ) );
                app.setIosAndroidFlag( resultSet.getInt( "ios_android_flag" ) );
                app.setDesktopUrl( resultSet.getString( "desktop_url" ) );

                appInfos.add( app );
                return null;
            }
        });

        if( !appInfos.isEmpty() )
            return appInfos.get( 0 );
        else
            return null;
    }

    public int updateApp( final AppParams appParams )
    {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel( "appInfo", UPDATE_APP_BY_APPID, appParams.userId, appParams.userId );
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        Object []values = {appParams.appName, appParams.appLiveKey, appParams.appLiveSecret, appParams.appTestKey, appParams.appTestSecret, appParams.iosUriScheme, appParams.iosNotUrl, appParams.iosStoreUrl, appParams.iosCustomUrl, appParams.iosBundleId, appParams.iosPrefix, appParams.iosTeamId, appParams.androidUriScheme, appParams.androidNotUrl, appParams.googlePlayUrl, appParams.androidCustomUrl, appParams.androidPackageName, appParams.androidPrefix, appParams.iosAndroidFlag, appParams.desktopUrl, appParams.appId};

        try
        {
            res += jdbcTemplate.update( tableChannel.getSql(), values );
        } catch( DataAccessException e ) {
            if( DaoUtil.isDuplicateInsert( e ) )
            {
                ApiLogger.warn( new StringBuffer(128).append("Duplicate update app, appId=").append( appParams.appId ), e );
            }
            throw new LMException( LMExceptionFactor.LM_FAILURE_DB_OP );
        }
        return res;

    }

}
