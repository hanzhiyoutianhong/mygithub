package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by LinkedME00 on 16/1/20.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstallParams {

    public long identity_id;
    public String linkedme_key;
    public String device_id;
    public String device_fingerprint_id;
    public int device_type;
    public String device_brand;
    public String device_model;
    public boolean has_bluetooth;
    public boolean has_nfc;
    public boolean has_sim;
    public String os;
    public String os_version;
    public int screen_dpi;
    public int screen_height;
    public int screen_width;
    public boolean is_wifi;
    public boolean is_referable;
    public boolean lat_val;
    public String carrier;
    public String app_version;
    public String extra_uri_data;
    public String external_intent_uri;
    public String spotlight_identifier;
    public String universal_link_url;
    public int sdk_update;
    public String sdk_version;
    public String ios_team_id;
    public String ios_bundle_id;
    public int retry_times;
    public boolean is_debug;
    public String clientIP;
    public long timestamp;
    public String sign;

    public String getLinkedme_key() {
        return linkedme_key;
    }

    public void setLinkedme_key(String linkedme_key) {
        this.linkedme_key = linkedme_key;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public long getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(long identity_id) {
        this.identity_id = identity_id;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public int getRetry_times() {
        return retry_times;
    }

    public void setRetry_times(int retry_times) {
        this.retry_times = retry_times;
    }

    public boolean is_debug() {
        return is_debug;
    }

    public void setIs_debug(boolean is_debug) {
        this.is_debug = is_debug;
    }

    public int getDevice_type() {
        return device_type;
    }

    public void setDevice_type(int device_type) {
        this.device_type = device_type;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public boolean isHas_nfc() {
        return has_nfc;
    }

    public void setHas_nfc(boolean has_nfc) {
        this.has_nfc = has_nfc;
    }

    public boolean isHas_bluetooth() {
        return has_bluetooth;
    }

    public void setHas_bluetooth(boolean has_bluetooth) {
        this.has_bluetooth = has_bluetooth;
    }

    public boolean isHas_sim() {
        return has_sim;
    }

    public void setHas_sim(boolean has_sim) {
        this.has_sim = has_sim;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public int getScreen_dpi() {
        return screen_dpi;
    }

    public void setScreen_dpi(int screen_dpi) {
        this.screen_dpi = screen_dpi;
    }

    public int getScreen_height() {
        return screen_height;
    }

    public void setScreen_height(int screen_height) {
        this.screen_height = screen_height;
    }

    public int getScreen_width() {
        return screen_width;
    }

    public void setScreen_width(int screen_width) {
        this.screen_width = screen_width;
    }

    public boolean is_wifi() {
        return is_wifi;
    }

    public void setIs_wifi(boolean is_wifi) {
        this.is_wifi = is_wifi;
    }

    public boolean is_referable() {
        return is_referable;
    }

    public void setIs_referable(boolean is_referable) {
        this.is_referable = is_referable;
    }

    public boolean getLat_val() {
        return lat_val;
    }

    public void setLat_val(boolean lat_val) {
        this.lat_val = lat_val;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public int getSdk_update() {
        return sdk_update;
    }

    public void setSdk_update(int sdk_update) {
        this.sdk_update = sdk_update;
    }

    public String getIos_bundle_id() {
        return ios_bundle_id;
    }

    public void setIos_bundle_id(String ios_bundle_id) {
        this.ios_bundle_id = ios_bundle_id;
    }

    public String getDevice_fingerprint_id() {
        return device_fingerprint_id;
    }

    public void setDevice_fingerprint_id(String device_fingerprint_id) {
        this.device_fingerprint_id = device_fingerprint_id;
    }

    public String getIos_team_id() {
        return ios_team_id;
    }

    public void setIos_team_id(String ios_team_id) {
        this.ios_team_id = ios_team_id;
    }

    public String getSpotlight_identifier() {
        return spotlight_identifier;
    }

    public void setSpotlight_identifier(String spotlight_identifier) {
        this.spotlight_identifier = spotlight_identifier;
    }

    public String getExtra_uri_data() {
        return extra_uri_data;
    }

    public void setExtra_uri_data(String extra_uri_data) {
        this.extra_uri_data = extra_uri_data;
    }

    public String getExternal_intent_uri() {
        return external_intent_uri;
    }

    public void setExternal_intent_uri(String external_intent_uri) {
        this.external_intent_uri = external_intent_uri;
    }


    public String getUniversal_link_url() {
        return universal_link_url;
    }

    public void setUniversal_link_url(String universal_link_url) {
        this.universal_link_url = universal_link_url;
    }


    public boolean isLat_val() {
        return lat_val;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
