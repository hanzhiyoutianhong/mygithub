package cc.linkedme.common.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMUrlParams extends LMBaseParams {


    public String identifyId;
    public String deviceFingerprintId;
    public String type;
    public String tags;
    public String channel;
    public String feature;
    public String stage;
    public String alias;
    public String data;
    public String update;
    public String source;
    public String deepLinkPath;
    public String duration;
    public String sessionId;

    /**
     * construction function
     */

    public LMUrlParams(String linkedMeKey, String sdk, String retryNumber, String debug, String identifyId,
                       String deviceFingerprintId, String type, String tags, String channel, String feature,
                       String stage, String alias, String data, String update, String source,
                       String deepLinkPath, String duration, String sessionId) {
        super(linkedMeKey, sdk, retryNumber, debug);
        this.identifyId = identifyId;
        this.deviceFingerprintId = deviceFingerprintId;
        this.type = type;
        this.tags = tags;
        this.channel = channel;
        this.feature = feature;
        this.stage = stage;
        this.alias = alias;
        this.data = data;
        this.update = update;
        this.source = source;
        this.deepLinkPath = deepLinkPath;
        this.duration = duration;
        this.sessionId = sessionId;
    }

    /**
     * get and set function
     */

    public String getIdentifyId() {
        return identifyId;
    }

    public void setIdentifyId(String identifyId) {
        this.identifyId = identifyId;
    }

    public String getDeviceFingerprintId() {
        return deviceFingerprintId;
    }

    public void setDeviceFingerprintId(String deviceFingerprintId) {
        this.deviceFingerprintId = deviceFingerprintId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDeepLinkPath() {
        return deepLinkPath;
    }

    public void setDeepLinkPath(String deepLinkPath) {
        this.deepLinkPath = deepLinkPath;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
