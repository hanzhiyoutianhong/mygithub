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

}
