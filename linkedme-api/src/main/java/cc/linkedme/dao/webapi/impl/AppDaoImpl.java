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
import cc.linkedme.data.model.UserInfo;
import cc.linkedme.data.model.params.AppParams;
import cc.linkedme.data.model.params.UserParams;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppDaoImpl extends BaseDao implements AppDao {
    private static final String ADD_APP = "ADD_APP";
    private static final String GET_APPS_BY_USERID = "GET_APPS_BY_USERID";
    private static final String DEL_APP_BY_USERID_AND_APPID = "DEL_APP_BY_USERID_AND_APPID";
    private static final String GET_APP_BY_APPID = "GET_APP_BY_APPID";
    private static final String UPDATE_APP_BY_APPID = "UPDATE_APP_BY_APPID";

    public int insertApp(AppInfo appInfo) {
        int result = 0;
        if (appInfo == null) {
            ApiLogger.error("AppDaoImpl.insertApp appInfo is null, add failed");
            return result;
        }

        long userId = appInfo.getUser_id();

        //需要添加重名判断

        TableChannel tableChannel = tableContainer.getTableChannel("appInfo", ADD_APP, userId, userId);

        Object[] values = {appInfo.getApp_id(), appInfo.getApp_name(), appInfo.getUser_id(), appInfo.getApp_key(), appInfo.getApp_secret(), appInfo.getType()};

        try {
            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), values);
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuilder(128).append("Duplicate insert deepLink, appName=").append(appInfo.getApp_name()), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return result;
    }


    public List<AppInfo> getAppsByUserId(AppParams appParams) {
        TableChannel tableChannel = tableContainer.getTableChannel("appInfo", GET_APPS_BY_USERID, appParams.user_id, appParams.user_id);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<AppInfo> appInfos = new ArrayList<AppInfo>();
        jdbcTemplate.query(tableChannel.getSql(), new Object[] {appParams.user_id}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                AppInfo app = new AppInfo();
                app.setApp_id(resultSet.getLong("id"));
                app.setType(resultSet.getString("type"));
                app.setUser_id(appParams.user_id);
                app.setApp_name(resultSet.getString("app_name"));
                app.setApp_key(resultSet.getString("app_key"));
                app.setApp_secret(resultSet.getString("app_secret"));
                app.setIos_uri_scheme(resultSet.getString("ios_uri_scheme"));
                app.setIos_search_option(resultSet.getString("ios_search_option"));
                app.setIos_store_url(resultSet.getString("ios_store_url"));
                app.setIos_custom_url(resultSet.getString("ios_custom_url"));
                app.setIos_bundle_id(resultSet.getString("ios_bundle_id"));
                app.setIos_app_prefix(resultSet.getString("ios_app_prefix"));
                app.setAndroid_uri_scheme(resultSet.getString("android_uri_scheme"));
                app.setAndroid_search_option(resultSet.getString("android_search_option"));
                app.setGoogle_paly_url(resultSet.getString("google_play_url"));
                app.setAndroid_custom_url(resultSet.getString("android_custom_url"));
                app.setAndroid_package_name(resultSet.getString("android_package_name"));
                app.setAndroid_sha256_fingerprints(resultSet.getString("android_sha256_fingerprints"));
                app.setIos_android_flag(resultSet.getInt("ios_android_flag"));
                app.setQr_code(resultSet.getString("qr_code"));
                appInfos.add(app);
                return null;
            }
        });
        return appInfos;
    }

    public int delApp(AppParams appParams) {
        int result = 0;
        TableChannel tableChannel =
                tableContainer.getTableChannel("appInfo", DEL_APP_BY_USERID_AND_APPID, appParams.user_id, appParams.user_id);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        try {
            result += jdbcTemplate.update(tableChannel.getSql(), new Object[] {appParams.user_id, appParams.app_id});
        } catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return result;
    }

    public AppInfo getAppsByAppId(final AppParams appParams) {
        TableChannel tableChannel = tableContainer.getTableChannel("appInfo", GET_APP_BY_APPID, appParams.user_id, appParams.user_id);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        final List<AppInfo> appInfos = new ArrayList<AppInfo>();
        jdbcTemplate.query(tableChannel.getSql(), new Object[] {appParams.app_id, appParams.type}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                AppInfo app = new AppInfo();
                app.setApp_id(appParams.app_id);
                app.setType( appParams.type );
                app.setUser_id(resultSet.getLong("user_id"));
                app.setApp_name(resultSet.getString("app_name"));
                app.setApp_key(resultSet.getString("app_key"));
                app.setApp_secret(resultSet.getString("app_secret"));
                app.setIos_uri_scheme(resultSet.getString("ios_uri_scheme"));
                app.setIos_search_option(resultSet.getString("ios_search_option"));
                app.setIos_store_url(resultSet.getString("ios_store_url"));
                app.setIos_custom_url(resultSet.getString("ios_custom_url"));
                app.setIos_bundle_id(resultSet.getString("ios_bundle_id"));
                app.setIos_app_prefix(resultSet.getString("ios_app_prefix"));
                app.setAndroid_uri_scheme(resultSet.getString("android_uri_scheme"));
                app.setAndroid_search_option(resultSet.getString("android_search_option"));
                app.setGoogle_paly_url(resultSet.getString("google_play_url"));
                app.setAndroid_custom_url(resultSet.getString("android_custom_url"));
                app.setAndroid_package_name(resultSet.getString("android_package_name"));
                app.setAndroid_sha256_fingerprints(resultSet.getString("android_sha256_fingerprints"));
                app.setIos_android_flag(resultSet.getInt("ios_android_flag"));
                app.setQr_code(resultSet.getString("qr_code"));

                appInfos.add(app);
                return null;
            }
        });

        if (!appInfos.isEmpty())
            return appInfos.get(0);
        else
            return null;
    }

    public int updateApp(final AppParams appParams) {
        int res = 0;
        TableChannel tableChannel = tableContainer.getTableChannel("appInfo", UPDATE_APP_BY_APPID, appParams.user_id, appParams.user_id);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        String last_update_time = DateFormat.getDateTimeInstance().format(new Date());

        Object[] values = {appParams.app_name, appParams.ios_uri_scheme, appParams.ios_search_option, appParams.ios_store_url,
                appParams.ios_custom_url, appParams.ios_bundle_id, appParams.ios_app_prefix, appParams.android_uri_scheme,
                appParams.android_search_option, appParams.google_play_url, appParams.android_custom_url, appParams.android_package_name,
                appParams.ios_android_flag, appParams.android_sha256_fingerprints, appParams.qr_code, last_update_time, appParams.app_id, appParams.type};

        try {
            res += jdbcTemplate.update(tableChannel.getSql(), values);
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                ApiLogger.warn(new StringBuffer(128).append("Duplicate update app, appId=").append(appParams.app_id), e);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }
        return res;

    }

}
