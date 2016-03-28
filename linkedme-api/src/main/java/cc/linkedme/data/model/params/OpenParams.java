package cc.linkedme.data.model.params;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by LinkedME01 on 16/3/28.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenParams {


    public String device_fingerprint_id;
    public long identity_id;
    public String linkedme_key;
    public boolean is_referable;
    public String app_version;
    public String extra_uri_data;
    public String os_version;
    public String sdk_version;
    public int sdk_update;
    public String os;
    public String lat_val;
    public String last_source;
    public int retry_times;
    public boolean is_debug;
    public String sign;


    public String getDevice_fingerprint_id() {
        return device_fingerprint_id;
    }

    public void setDevice_fingerprint_id(String device_fingerprint_id) {
        this.device_fingerprint_id = device_fingerprint_id;
    }

    public long getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(long identity_id) {
        this.identity_id = identity_id;
    }

    public String getLinkedme_key() {
        return linkedme_key;
    }

    public void setLinkedme_key(String linkedme_key) {
        this.linkedme_key = linkedme_key;
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

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

}
