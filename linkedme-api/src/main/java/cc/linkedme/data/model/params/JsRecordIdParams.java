package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by LinkedME01 on 16/3/28.
 */

public class JsRecordIdParams {

    public long identity_id;
    public boolean is_valid_identityid;
    public String browser_fingerprint_id;
    public long deeplink_id;
    public boolean is_pc_scan;
}
