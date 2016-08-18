package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.sdkapi.AppAnalysisDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.DaoUtil;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.params.AppAnalysisParams;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinkedME07 on 16/7/27.
 */
public class AppAnalysisDaoImpl extends BaseDao implements AppAnalysisDao {

    private static final String ADD_APP_BUNDLE = "ADD_APP_BUNDLE";
    private static final String ONLINE_APPS = "ONLINE_APPS";
    private static final String UPDATE_STATUS = "UPDATE_STATUS";
    private static final String GET_APPS = "GET_APPS";
    private static final String GET_APPS_WITH_SDK = "GET_APPS_WITH_SDK";


    @Override
    public int addAppBundle(String appId, String appName, String appIcon, String genres, String company, String lastUpdateTime,
            int isOnline) {
        int result = 0;
        TableChannel tableChannel;
        try {
            tableChannel = tableContainer.getTableChannel("app_analysis", ADD_APP_BUNDLE, 0L, 0L);

            Object[] values = {appId + "_" + company, appId, company, appName, appIcon, genres, lastUpdateTime, isOnline};

            result += tableChannel.getJdbcTemplate().update(tableChannel.getSql(), values);
        } catch (DataAccessException e) {
            if (DaoUtil.isDuplicateInsert(e)) {
                return switchApp(appId, company, ONLINE_APPS);
            }
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to insert app_id: " + appId);
        }
        return result;
    }

    public List<String> getAppIds(Object[] params, String type) {

        TableChannel tableChannel = tableContainer.getTableChannel("app_analysis", type, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<String> oldApps = new ArrayList<>();

        jdbcTemplate.query(tableChannel.getSql(), params, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                oldApps.add(resultSet.getString("app_id"));
                return null;
            }
        });
        return oldApps;
    }

    public List<AppAnalysisParams> getApps(String company) {

        TableChannel tableChannel = tableContainer.getTableChannel("app_analysis", GET_APPS, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<AppAnalysisParams> oldApps = new ArrayList<>();

        jdbcTemplate.query(tableChannel.getSql(), new Object[] {company}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                AppAnalysisParams appAnalysisParams = new AppAnalysisParams();
                appAnalysisParams.setAppId(resultSet.getString("app_id"));
                appAnalysisParams.setStatus(resultSet.getString("status"));
                appAnalysisParams.setAppName(resultSet.getString("app_name"));
                appAnalysisParams.setAppIcon(resultSet.getString("app_icon"));
                appAnalysisParams.setIsOnline(resultSet.getString("is_online"));
                appAnalysisParams.setLastUpdateTime(resultSet.getString("last_update_time"));
                oldApps.add(appAnalysisParams);
                return null;
            }
        });
        return oldApps;
    }

    public List<AppAnalysisParams> getAppsWithSDK(String company, String startDate, String endDate) {

        TableChannel tableChannel = tableContainer.getTableChannel("app_analysis", GET_APPS_WITH_SDK, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<AppAnalysisParams> apps = new ArrayList<>();

        jdbcTemplate.query(tableChannel.getSql() + " and date_format(online_date,'%Y-%m-%d') <= '" + endDate + "'",
                new Object[] {company, startDate}, new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                        AppAnalysisParams appAnalysisParams = new AppAnalysisParams();
                        appAnalysisParams.setAppName(resultSet.getString("app_name"));
                        appAnalysisParams.setAppIcon(resultSet.getString("genres"));
                        apps.add(appAnalysisParams);
                        return null;
                    }
                });
        return apps;
    }

    public int count(String company, String startDate, String endDate, String type) {

        TableChannel tableChannel = tableContainer.getTableChannel("app_analysis", type, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        final List<Integer> countList = new ArrayList<>();

        jdbcTemplate.query(tableChannel.getSql() + " and date_format(online_date,'%Y-%m-%d') <= '" + endDate + "'",
                new Object[] {company, startDate}, new RowMapper() {
                    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                        countList.add(resultSet.getInt(1));
                        return null;
                    }
                });
        return countList.size() > 0 ? countList.get(0) : 0;
    }

    public int switchApp(String appId, String company, String type) {
        int result = 0;

        TableChannel tableChannel = tableContainer.getTableChannel("app_analysis", type, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        try {
            result += jdbcTemplate.update(tableChannel.getSql(), new Object[] {appId + "_" + company});
        } catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to update app_id" + appId);
        }
        return result;
    }

    public int updateStatus(String appId, String company, String status) {
        int result = 0;

        TableChannel tableChannel = tableContainer.getTableChannel("app_analysis", UPDATE_STATUS, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        try {
            result += jdbcTemplate.update(tableChannel.getSql(), new Object[] {status, appId + "_" + company});
        } catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP, "Failed to update status, app_id = " + appId);
        }
        return result;
    }

}
