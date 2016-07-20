package cc.linkedme.data.model.params;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by LinkedME01 on 16/3/28.
 */

public class OpenParams extends BaseParams{
    public long deeplink_id;
    public String deeplink_url;
    public boolean is_referable;
    public String app_version;
    public String extra_uri_data;
    public String external_intent_uri;
    public String spotlight_identifier;
    public String universal_link_url;
    public String os_version;
    public int sdk_update;
    public String os;
    public String device_model;
    public String lat_val;
    public String last_source;
    public String clientIP;

    public long getDeeplink_id() {
        return deeplink_id;
    }

    public void setDeeplink_id(long deeplink_id) {
        this.deeplink_id = deeplink_id;
    }

    public String getDeeplink_url() {
        return deeplink_url;
    }

    public void setDeeplink_url(String deeplink_url) {
        this.deeplink_url = deeplink_url;
    }

    public boolean is_referable() {
        return is_referable;
    }

    public void setIs_referable(boolean is_referable) {
        this.is_referable = is_referable;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
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

    public String getSpotlight_identifier() {
        return spotlight_identifier;
    }

    public void setSpotlight_identifier(String spotlight_identifier) {
        this.spotlight_identifier = spotlight_identifier;
    }

    public String getUniversal_link_url() {
        return universal_link_url;
    }

    public void setUniversal_link_url(String universal_link_url) {
        this.universal_link_url = universal_link_url;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public int getSdk_update() {
        return sdk_update;
    }

    public void setSdk_update(int sdk_update) {
        this.sdk_update = sdk_update;
    }

    public String getOs() {
        return os;
    }

    public String getDevice_model() {
        return device_model;
    }

    public void setDevice_model(String device_model) {
        this.device_model = device_model;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getLat_val() {
        return lat_val;
    }

    public void setLat_val(String lat_val) {
        this.lat_val = lat_val;
    }

    public String getLast_source() {
        return last_source;
    }

    public void setLast_source(String last_source) {
        this.last_source = last_source;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }
}
