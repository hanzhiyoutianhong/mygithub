package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by LinkedME01 on 16/3/28.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsRecordIdParams {

    public long identity_id;
    public boolean is_valid_identityid;
    public String browser_fingerprint_id;
    public long deeplink_id;

    public long getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(long identity_id) {
        this.identity_id = identity_id;
    }

    public boolean is_valid_identityid() {
        return is_valid_identityid;
    }

    public void setIs_valid_identityid(boolean is_valid_identityid) {
        this.is_valid_identityid = is_valid_identityid;
    }

    public String getBrowser_fingerprint_id() {
        return browser_fingerprint_id;
    }

    public void setBrowser_fingerprint_id(String browser_fingerprint_id) {
        this.browser_fingerprint_id = browser_fingerprint_id;
    }

    public long getDeeplink_id() {
        return deeplink_id;
    }

    public void setDeeplink_id(long deeplink_id) {
        this.deeplink_id = deeplink_id;
    }
}
