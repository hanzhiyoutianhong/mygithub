package cc.linkedme.dao.webapi.impl;

import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.webapi.ConsumerAppDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.ConsumerAppInfo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LinkedME01 on 16/4/8.
 */

public class ConsumerAppDaoImpl extends BaseDao implements ConsumerAppDao {
    private static final String ADD_CONSUMER_APP = "ADD_CONSUMER_APP";
    private static final String GET_CONSUMER_APP_INFO = "GET_CONSUMER_APP_INFO";
    private static final String GET_CONSUMER_APPS = "GET_CONSUMER_APPS";
    private static final String GET_ALL_CONSUMER_APPS = "GET_ALL_CONSUMER_APPS";

    @Resource
    org.springframework.jdbc.core.JdbcTemplate consumerJdbcTemplate;

    @Override
    public int insertApp(ConsumerAppInfo consumerAppInfo) {
        TableChannel tableChannel = tableContainer.getTableChannel("consumerAppInfo", ADD_CONSUMER_APP, 0L, 0L);

        return 0;
    }

    @Override
    public ConsumerAppInfo getConsumerAppInfo(long consumerAppId) {
        TableChannel tableChannel = tableContainer.getTableChannel("consumerAppInfo", GET_CONSUMER_APP_INFO, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        ConsumerAppInfo consumerAppInfo = new ConsumerAppInfo();
        jdbcTemplate.query(tableChannel.getSql(), new Object[] {consumerAppId}, new RowMapper() {
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                consumerAppInfo.setAppName(rs.getString("app_name"));
                consumerAppInfo.setAppLogoUrl(rs.getString("app_logo_url"));
                consumerAppInfo.setCategory(rs.getString("category"));
                consumerAppInfo.setOnlineTime(rs.getString("online_time"));
                consumerAppInfo.setIosCode(rs.getString("ios_code"));
                consumerAppInfo.setAndroidCode(rs.getString("android_code"));
                consumerAppInfo.setAndroidConfig(rs.getString("android_config"));
                consumerAppInfo.setSchemeUrl(rs.getString("scheme_url"));
                consumerAppInfo.setCustomUrl(rs.getString("custom_url"));
                consumerAppInfo.setDefaultUrl(rs.getString("default_url"));
                consumerAppInfo.setBundleId(rs.getString("bundle_id"));
                consumerAppInfo.setPackageName(rs.getString("package_name"));
                consumerAppInfo.setClientId(rs.getString("client_id"));
                consumerAppInfo.setServerToken(rs.getString("server_token"));
                consumerAppInfo.setStatus(rs.getInt("status"));
                return null;
            }
        });
        return consumerAppInfo;
    }

    @Override
    public Map<Long, ConsumerAppInfo> getConsumerAppList(List<Long> appIds) {
        Map<Long, ConsumerAppInfo> consumerApps = new HashMap<>(appIds.size());
        TableChannel tableChannel = tableContainer.getTableChannel("consumerAppInfo", GET_CONSUMER_APPS, 0L, 0L);
        String sql = tableChannel.getSql();
        Map namedParameters = Collections.singletonMap("listOfValues", appIds);
        NamedParameterJdbcTemplate namedparameterJdbcTemplate = new NamedParameterJdbcTemplate(consumerJdbcTemplate);
        namedparameterJdbcTemplate.query(sql, namedParameters, new RowMapper() {
            public ConsumerAppInfo mapRow(ResultSet rs, int i) throws SQLException {
                ConsumerAppInfo appInfo = new ConsumerAppInfo();
                appInfo.setAppId(rs.getInt("app_id"));
                appInfo.setAppName(rs.getString("app_name"));
                appInfo.setAppLogoUrl(rs.getString("app_logo_url"));
                appInfo.setCategory(rs.getString("category"));
                appInfo.setOnlineTime(rs.getString("online_time"));
                appInfo.setIosCode(rs.getString("ios_code"));
                appInfo.setAndroidCode(rs.getString("android_code"));
                appInfo.setAndroidConfig(rs.getString("android_config"));
                appInfo.setSchemeUrl(rs.getString("scheme_url"));
                appInfo.setCustomUrl(rs.getString("custom_url"));
                appInfo.setDefaultUrl(rs.getString("default_url"));
                appInfo.setBundleId(rs.getString("bundle_id"));
                appInfo.setPackageName(rs.getString("package_name"));
                appInfo.setClientId(rs.getString("client_id"));
                appInfo.setServerToken(rs.getString("server_token"));
                appInfo.setStatus(rs.getInt("status"));
                consumerApps.put((long) appInfo.getAppId(), appInfo);
                return null;
            }
        });
        return consumerApps;
    }

    @Override
    public List<ConsumerAppInfo> getAllConsumerApps() {
        TableChannel tableChannel = tableContainer.getTableChannel("consumerAppInfo", GET_ALL_CONSUMER_APPS, 0L, 0L);
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();
        List<ConsumerAppInfo> consumerApps = new ArrayList<ConsumerAppInfo>();
        jdbcTemplate.query(tableChannel.getSql(), new Object[] {}, new RowMapper() {
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                ConsumerAppInfo consumerAppInfo = new ConsumerAppInfo();
                consumerAppInfo.setAppId(rs.getLong("app_id"));
                consumerAppInfo.setAppName(rs.getString("app_name"));
                consumerAppInfo.setAppLogoUrl(rs.getString("app_logo_url"));
                consumerAppInfo.setCategory(rs.getString("category"));
                consumerAppInfo.setOnlineTime(rs.getString("online_time"));
                consumerAppInfo.setIosCode(rs.getString("ios_code"));
                consumerAppInfo.setAndroidCode(rs.getString("android_code"));
                consumerAppInfo.setAndroidConfig(rs.getString("android_config"));
                consumerAppInfo.setSchemeUrl(rs.getString("scheme_url"));
                consumerAppInfo.setCustomUrl(rs.getString("custom_url"));
                consumerAppInfo.setDefaultUrl(rs.getString("default_url"));
                consumerAppInfo.setBundleId(rs.getString("bundle_id"));
                consumerAppInfo.setPackageName(rs.getString("package_name"));
                consumerAppInfo.setClientId(rs.getString("client_id"));
                consumerAppInfo.setServerToken(rs.getString("server_token"));
                consumerAppInfo.setStatus(rs.getInt("status"));
                consumerApps.add(consumerAppInfo);
                return null;
            }
        });
        return consumerApps;
    }

}
