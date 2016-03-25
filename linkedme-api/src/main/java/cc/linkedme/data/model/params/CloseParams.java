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
}
