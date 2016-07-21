package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by LinkedME01 on 16/3/28.
 */

public class InitUberButtonParams {

    public String device_id;
    public long identity_id;
    public String linkedme_key;
    public long session_id;
    public int retry_times;
    public boolean is_debug;
    public String sdk_version;
    public String btn_id;
    public double pickup_lat;
    public double pickup_lng;
    public String pickup_label;
    public String device_fingerprint_id;
    public double dropoff_lat;
    public double dropoff_lng;
    public String dropoff_label;
    public String source;
    public long timestamp;
    public String sign;

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

    public String getLinkedme_key() {
        return linkedme_key;
    }

    public void setLinkedme_key(String linkedme_key) {
        this.linkedme_key = linkedme_key;
    }

    public long getSession_id() {
        return session_id;
    }

    public void setSession_id(long session_id) {
        this.session_id = session_id;
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

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }

    public String getBtn_id() {
        return btn_id;
    }

    public void setBtn_id(String btn_id) {
        this.btn_id = btn_id;
    }

    public double getPickup_lat() {
        return pickup_lat;
    }

    public void setPickup_lat(double pickup_lat) {
        this.pickup_lat = pickup_lat;
    }

    public double getPickup_lng() {
        return pickup_lng;
    }

    public void setPickup_lng(double pickup_lng) {
        this.pickup_lng = pickup_lng;
    }

    public String getPickup_label() {
        return pickup_label;
    }

    public void setPickup_label(String pickup_label) {
        this.pickup_label = pickup_label;
    }

    public String getDevice_fingerprint_id() {
        return device_fingerprint_id;
    }

    public void setDevice_fingerprint_id(String device_fingerprint_id) {
        this.device_fingerprint_id = device_fingerprint_id;
    }

    public double getDropoff_lat() {
        return dropoff_lat;
    }

    public void setDropoff_lat(double dropoff_lat) {
        this.dropoff_lat = dropoff_lat;
    }

    public double getDropoff_lng() {
        return dropoff_lng;
    }

    public void setDropoff_lng(double dropoff_lng) {
        this.dropoff_lng = dropoff_lng;
    }

    public String getDropoff_label() {
        return dropoff_label;
    }

    public void setDropoff_label(String dropoff_label) {
        this.dropoff_label = dropoff_label;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
