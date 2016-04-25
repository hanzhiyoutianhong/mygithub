package cc.linkedme.dao.sdkapi.impl;

import cc.linkedme.commons.exception.LMException;
import cc.linkedme.commons.exception.LMExceptionFactor;
import cc.linkedme.dao.BaseDao;
import cc.linkedme.dao.sdkapi.AppListDao;
import cc.linkedme.data.dao.strategy.TableChannel;
import cc.linkedme.data.dao.util.JdbcTemplate;
import cc.linkedme.data.model.AppListInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Vontroy on 16/4/24.
 */
public class AppListDaoImpl extends BaseDao implements AppListDao {
    public static final String INSERT_APP_LIST = "INSERT_APP_LIST";
    public static final String QUERY_APP_LIST = "QUERY_APP_LIST";

    public int addAppList(AppListInfo appListInfo) {
        int result = 0;

        long identity_id = appListInfo.getIdentityId();
        String device_fingerprint_id = appListInfo.getDeviceFingerprintId();
        String app_name = appListInfo.getAppName();
        String app_identifier = appListInfo.getAppIdentifier();
        String uri_scheme = appListInfo.getUriScheme();
        String public_source_dir = appListInfo.getPublicSourceDir();
        String source_dir = appListInfo.getSourceDir();
        String install_date = appListInfo.getInstallDate();
        String last_update_date = appListInfo.getLastUpdateDate();
        String version_code = appListInfo.getVersionCode();
        String version_name = appListInfo.getVersionName();
        String os = appListInfo.getOs();
        String sdk_version = appListInfo.getSdkVersion();
        int retry_times = appListInfo.getRetryTimes();
        String linkedme_key = appListInfo.getLinkedmeKey();
        String sign = appListInfo.getSign();

        TableChannel tableChannel = tableContainer.getTableChannel("appListInfo", INSERT_APP_LIST, identity_id, identity_id );
        try {
            result += tableChannel.getJdbcTemplate().update( tableChannel.getSql(),
                    new Object[]{ identity_id, device_fingerprint_id, app_name, app_identifier, uri_scheme, public_source_dir, source_dir, install_date, last_update_date, version_code, version_name, os, sdk_version, retry_times, linkedme_key, sign});
        }catch (DataAccessException e) {
            throw new LMException(LMExceptionFactor.LM_FAILURE_DB_OP);
        }

        return result;
    }

    public ArrayList<AppListInfo> queryAppList( long identityId ) {
        ArrayList<AppListInfo> appListInfos = new ArrayList<>();
        TableChannel tableChannel = tableContainer.getTableChannel("appListInfo", QUERY_APP_LIST, identityId, identityId );
        JdbcTemplate jdbcTemplate = tableChannel.getJdbcTemplate();

        jdbcTemplate.query(tableChannel.getSql(), new Object[] {identityId}, new RowMapper() {
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                do {
                    AppListInfo appList = new AppListInfo();
                    appList.setIdentityId( identityId );
                    appList.setLinkedmeKey(resultSet.getString("linkedme_key"));
                    appList.setDeviceFingerprintId(resultSet.getString("device_fingerprint_id"));
                    appList.setAppName(resultSet.getString("app_name"));
                    appList.setAppIdentifier(resultSet.getString("app_identifier"));
                    appList.setUriScheme(resultSet.getString("uri_scheme"));
                    appList.setPublicSourceDir(resultSet.getString("public_source_dir"));
                    appList.setSourceDir(resultSet.getString("source_dir"));
                    appList.setInstallDate(resultSet.getString("install_date"));
                    appList.setLastUpdateDate(resultSet.getString("last_update_date"));
                    appList.setVersionCode(resultSet.getString("version_code"));
                    appList.setVersionName(resultSet.getString("version_name"));
                    appList.setOs(resultSet.getString("os"));
                    appList.setSdkVersion(resultSet.getString("sdk_version"));
                    appList.setRetryTimes(resultSet.getInt("retry_times"));
                    appList.setSign(resultSet.getString("sign"));

                    appListInfos.add(appList);
                } while( resultSet.next() );
                return null;
            }
        });

        return appListInfos;
    }
}
