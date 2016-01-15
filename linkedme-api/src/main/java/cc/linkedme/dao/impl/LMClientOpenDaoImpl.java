package cc.linkedme.dao.impl;

import cc.linkedme.dao.LMClientOpenDao;
import cc.linkedme.model.LMClientOpenEntity;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by qipo on 15/9/1.
 */

public class LMClientOpenDaoImpl implements LMClientOpenDao {

    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_CLIENT_OPEN = "INSERT INTO client_open(identity_id, app_key, device_fingerprint_id, app_version, ad_tracking_enabled, " +
            "google_adverts_Id, is_referrable, os, os_version, os_release, " +
            "updation, sdk, uri_scheme, spotlight_identifier, universal_link_url, " +
            "link_identifier, ios_bundle_id, lat_val, debug, session_id, " +
            "ip, retry_number, timestamp)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public void addClientOpen(LMClientOpenEntity co) {
        Object[] values = {co.getIdentityId(), co.getAppKey(), co.getDeviceFingerprintId(), co.getAppVersion(), co.getAdTrackingEnabled(),
                co.getGoogleAdvertsId(), co.getIsReferrable(), co.getOs(), co.getOsVersion(), co.getOsRelease(),
                co.getUpdation(), co.getSdk(), co.getUriScheme(), co.getSpotlightIdentifier(), co.getUniversalLinkUrl(),
                co.getLinkIdentifier(), co.getIosBundleId(), co.getLatVal(), co.getDebug(), co.getSessionId(),
                co.getIp(), co.getRetryNumber(), co.getTimestamp()};
        jdbcTemplate.update(INSERT_CLIENT_OPEN, values);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
