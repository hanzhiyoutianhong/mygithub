package cc.linkedme.data.model;

/**
 * Created by LinkedME01 on 16/3/8.
 */
public class DeepLink {
    private long deeplink_id;
    private String deeplink; // base62(deeplink_id)
    private String deeplink_md5;
    private String linkedme_key;
    private String identity_id;
    private String tags;
    private String alias;
    private String channel;
    private String feature;
    private String stage;
    private String campaign; // TODO
    private String params;
    private String source;
    private String sdk_version;

    public DeepLink() {

    }

    public DeepLink(long deeplink_id, String deeplink_md5, String linkedme_key, String identity_id, String tags, String alias,
            String channel, String feature, String stage, String campaign, String params, String source, String sdk_version) {
        this.deeplink_id = deeplink_id;
        this.deeplink_md5 = deeplink_md5;
        this.identity_id = identity_id;
        this.linkedme_key = linkedme_key;
        this.tags = tags;
        this.alias = alias;
        this.channel = channel;
        this.feature = feature;
        this.stage = stage;
        this.campaign = campaign;
        this.params = params;
        this.source = source;
        this.sdk_version = sdk_version;
    }

    public long getDeeplink_id() {
        return deeplink_id;
    }

    public void setDeeplink_id(long deeplink_id) {
        this.deeplink_id = deeplink_id;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public String getLinkedme_key() {
        return linkedme_key;
    }

    public void setLinkedme_key(String linkedme_key) {
        this.linkedme_key = linkedme_key;
    }

    public String getDeeplink_md5() {
        return deeplink_md5;
    }

    public void setDeeplink_md5(String deeplink_md5) {
        this.deeplink_md5 = deeplink_md5;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSdk_version() {
        return sdk_version;
    }

    public void setSdk_version(String sdk_version) {
        this.sdk_version = sdk_version;
    }
}
