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
    public int insertApp(AppInfo appInfo) {
        int result = 0;
        if (appInfo == null) {
            ApiLogger.error("AppDaoImpl.insertApp appInfo is null, add failed");
            return result;
        }
        long appId = appInfo.getAppid();
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
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                AppInfo app = new AppInfo();
                app.setAppid(rs.getLong("id"));
                app.setAppName(rs.getString("app_name"));
                app.setUserId(userId);
                app.setUriScheme(rs.getString("uri_scheme"));
                app.setPathPrefix(rs.getString("path_prefix"));
                app.setAppLiveKey(rs.getString("app_live_key"));
                app.setAppTestKey(rs.getString("app_test_key"));
                app.setAndroidNotUrl(rs.getString("android_not_url"));
                app.setPackageName(rs.getString("package_name"));
                app.setAndroidCustomUrl(rs.getString("android_custom_url"));
                app.setIosStoreUrl(rs.getString("ios_store_url"));
                app.setIosCustomUrl(rs.getString("ios_custom_url"));
                app.setIosNotUrl(rs.getString("ios_not_url"));
                app.setIosBundleId(rs.getString("ios_bundle_id"));
                app.setIosTeamId(rs.getString("ios_team_id"));
                app.setDesktopUrl(rs.getString("desktop_url"));
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
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
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

}
