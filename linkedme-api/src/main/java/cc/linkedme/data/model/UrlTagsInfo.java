package cc.linkedme.data.model;

/**
 * Created by Vontroy on 16/4/17.
 */
public class UrlTagsInfo {
    private long appId;
    private String tag_content;
    private String tag_type;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getTag_content() {
        return tag_content;
    }

    public void setTag_content(String tag_content) {
        this.tag_content = tag_content;
    }

    public String getTag_type() {
        return tag_type;
    }

    public void setTag_type(String tag_type) {
        this.tag_type = tag_type;
    }
}
