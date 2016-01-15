package cc.linkedme.dao.impl;

import cc.linkedme.dao.LMClientInstallDao;
import cc.linkedme.model.LMClientInstallEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by qipo on 15/9/1.
 */
public class LMClientInstallDaoImpl implements LMClientInstallDao {

    private JdbcTemplate jdbcTemplate;

    public static final String INSERT_REGISTER_INSTALL = "INSERT INTO client_install(identity_id, app_key, hardware_id, google_advertising_id, is_hardware_id_real, " +
            "ad_tracking_enabled, is_referrable, app_version, sdk, carrier, " +
            "brand, model, os, osversion, os_release, " +
            "screen_width, screen_height, screen_dpi, updation, bluetooth, " +
            "bluetooth_versoin, lat_val, has_nfc, has_telephone, wifi, " +
            "uri_scheme, retry_number, ios_bundle_id, ios_team_id, link_identifer, " +
            "universal_link, spotlight_identifier, debug, ip, timestamp)" + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static final String QUERY_ONE_REGISTER_INSTALL = "SELECT * FROM registerinstall WHERE identity_id=?";

    public void addClientInstall(LMClientInstallEntity ri) {
        Object[] values = {ri.getIdentityId(), ri.getAppKey(), ri.getHardwareId(), ri.getGoogleAdvertisingId(), ri.getIsHardwareIdReal(),
                ri.getAdTrackingEnabled(), ri.getIsReferrable(), ri.getAppVersion(), ri.getSdk(), ri.getCarrier(),
                ri.getBrand(), ri.getModel(), ri.getOs(), ri.getOsversion(), ri.getOsRelease(),
                ri.getScreenWidth(), ri.getScreenHeight(), ri.getScreenDpi(), ri.getUpdation(), ri.getBluetooth(),
                ri.getBluetoothVersoin(), ri.getLatVal(), ri.getHasNfc(), ri.getHasTelephone(), ri.getWifi(),
                ri.getUriScheme(), ri.getRetryNumber(), ri.getIosBundleId(), ri.getIosTeamId(), ri.getLinkIdentifer(),
                ri.getUniversalLink(), ri.getSpotlightIdentifier(), ri.getDebug(), ri.getIp(), ri.getTimestamp()};
        jdbcTemplate.update(INSERT_REGISTER_INSTALL, values);
    }

    public LMClientInstallEntity getOneClientInstall(String identityId) {
        Object[] values = {identityId};
        List<LMClientInstallEntity> allRowList = jdbcTemplate.query(QUERY_ONE_REGISTER_INSTALL, values, new BeanPropertyRowMapper(LMClientInstallEntity.class));
        if (allRowList.size() == 0) {
            return null;
        } else {
            return allRowList.get(0);
        }
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
