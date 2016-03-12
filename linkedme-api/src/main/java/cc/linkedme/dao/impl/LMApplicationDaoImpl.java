package cc.linkedme.dao.impl;

import cc.linkedme.dao.LMApplicationDao;
import cc.linkedme.model.LMApplicationEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by qipo on 15/9/3.
 */
public class LMApplicationDaoImpl implements LMApplicationDao {

    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_APPLICATION =
            "INSERT INTO application(app_name, app_key_live, app_secret_live, app_key_test, app_secret_test, account_id, url_scheme, ios_store_url, ios_custom_url, ios_not_url, ios_bundle_id, ios_team_id, package_name, android_custom_url, android_not_url, desktop_url, timestamp)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String QUERY_ONE_APPLICATION_LIVE = "SELECT * FROM application WHERE app_key_live=?";
    public static final String QUERY_ALL_APPLICATION = "SELECT * FROM application";


    public void addApplication(LMApplicationEntity app) {
        Object[] values = {app.getAppName(), app.getAppKeyLive(), app.getAppSecretLive(), app.getAppKeyTest(), app.getAppSecretTest(),
                app.getAccountId(), app.getUrlScheme(), app.getIosStoreUrl(), app.getIosCustomUrl(), app.getIosNotUrl(),
                app.getIosBundleId(), app.getIosTeamId(), app.getPackageName(), app.getAdroidCustomUrl(), app.getAndroidNotUrl(),
                app.getDesktopUrl(), app.getTimestamp()};
        jdbcTemplate.update(INSERT_APPLICATION, values);
    }


    public List<LMApplicationEntity> getAllApplication() {
        List<LMApplicationEntity> allRowsList =
                jdbcTemplate.query(QUERY_ALL_APPLICATION, new BeanPropertyRowMapper(LMApplicationEntity.class));
        return allRowsList;
    }

    public LMApplicationEntity getOneApplicationLive(String app_key_live) {
        Object[] values = {app_key_live};
        List<LMApplicationEntity> allRowList =
                jdbcTemplate.query(QUERY_ONE_APPLICATION_LIVE, values, new BeanPropertyRowMapper(LMApplicationEntity.class));
        if (allRowList.size() == 0) {
            return null;
        } else {
            return allRowList.get(0);
        }
    }

    /**
     * get and set function
     */

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
