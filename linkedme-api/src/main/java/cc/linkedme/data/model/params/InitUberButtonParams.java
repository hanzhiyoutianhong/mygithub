package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by LinkedME01 on 16/3/28.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class InitUberButtonParams {

    public long identity_id;
    public String linkedme_key;
    public long session_id;
    public int retry_times;
    public boolean is_debug;
    public String sdk_version;
    public String btn_id;
    public float pickup_lat;
    public float pickup_lng;
    public String pickup_label;
    public float dropoff_lat;
    public float dropoff_lng;
    public String dropoff_label;

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

    public float getPickup_lat() {
        return pickup_lat;
    }

    public void setPickup_lat(float pickup_lat) {
        this.pickup_lat = pickup_lat;
    }

    public float getPickup_lng() {
        return pickup_lng;
    }

    public void setPickup_lng(float pickup_lng) {
        this.pickup_lng = pickup_lng;
    }

    public String getPickup_label() {
        return pickup_label;
    }

    public void setPickup_label(String pickup_label) {
        this.pickup_label = pickup_label;
    }

    public float getDropoff_lat() {
        return dropoff_lat;
    }

    public void setDropoff_lat(float dropoff_lat) {
        this.dropoff_lat = dropoff_lat;
    }

    public float getDropoff_lng() {
        return dropoff_lng;
    }

    public void setDropoff_lng(float dropoff_lng) {
        this.dropoff_lng = dropoff_lng;
    }

    public String getDropoff_label() {
        return dropoff_label;
    }

    public void setDropoff_label(String dropoff_label) {
        this.dropoff_label = dropoff_label;
    }
}
