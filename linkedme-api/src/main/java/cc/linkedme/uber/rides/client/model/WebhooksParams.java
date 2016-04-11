package cc.linkedme.uber.rides.client.model;

import net.sf.json.JSONObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by LinkedME01 on 16/4/5.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class WebhooksParams {
    public String event_id;
    public String event_time;
    public String event_type;
    public JSONObject meta;
    public String resource_href;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_time() {
        return event_time;
    }

    public void setEvent_time(String event_time) {
        this.event_time = event_time;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public JSONObject getMeta() {
        return meta;
    }

    public void setMeta(JSONObject meta) {
        this.meta = meta;
    }

    public String getResource_href() {
        return resource_href;
    }

    public void setResource_href(String resource_href) {
        this.resource_href = resource_href;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("event_id", event_id);
        json.put("event_time", event_time);
        json.put("event_type", event_type);
        json.put("meta", meta);
        json.put("resource_href", resource_href);
        return json.toString();
    }

}
