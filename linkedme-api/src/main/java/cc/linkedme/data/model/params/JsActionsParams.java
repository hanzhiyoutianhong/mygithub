package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by LinkedME01 on 16/3/28.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsActionsParams {

    public String deepLinkId;
    public int lmTag;
    public String destination;


    /**
     * get and set function
     */

    public String getDeepLinkId() {
        return deepLinkId;
    }

    public void setDeepLinkId(String deepLinkId) {
        this.deepLinkId = deepLinkId;
    }

    public int getLmTag() {
        return lmTag;
    }

    public void setLmTag(int lmTag) {
        this.lmTag = lmTag;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
