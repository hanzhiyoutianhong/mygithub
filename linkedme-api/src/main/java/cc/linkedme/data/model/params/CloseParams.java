package cc.linkedme.data.model.params;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by LinkedME01 on 16/3/23.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloseParams {
    public long identity_id;
    public String device_fingerprint_id;
    public String linkedme_key;
    public String sdk_version;
    public int retry_times;
    public boolean is_debug;
    public String session_id;
    public long timestamp;
    public String sign;

    public long getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(long identity_id) {
        this.identity_id = identity_id;
    }

    public String getDevice_fingerprint_id() {
        return device_fingerprint_id;
    }

    public void setDevice_fingerprint_id(String device_fingerprint_id) {
        this.device_fingerprint_id = device_fingerprint_id;
    }

    public String getLinkedme_key() {
        return linkedme_key;
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

    public void setLinkedme_key(String linkedme_key) {
        this.linkedme_key = linkedme_key;
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

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

}
