package cc.linkedme.data.model.params;

import net.sf.json.JSONArray;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Vontroy on 16/4/23.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppListParams {
    public long identity_id;
    public String os;
    public String device_fingerprint_id;

    public JSONArray apps_data;

    public String sdk_version;
    public int retry_times;
    public String linkedme_key;
    public String sign;

    public long getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(long identity_id) {
        this.identity_id = identity_id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getDevice_fingerprint_id() {
        return device_fingerprint_id;
    }

    public void setDevice_fingerprint_id(String device_fingerprint_id) {
        this.device_fingerprint_id = device_fingerprint_id;
    }

    public JSONArray getApps_data() {
        return apps_data;
    }

    public void setApps_data(JSONArray apps_data) {
        this.apps_data = apps_data;
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

    public String getLinkedme_key() {
        return linkedme_key;
    }

    public void setLinkedme_key(String linkedme_key) {
        this.linkedme_key = linkedme_key;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
