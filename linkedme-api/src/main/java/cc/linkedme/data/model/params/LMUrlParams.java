package cc.linkedme.data.model.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMUrlParams extends LMBaseParams {
    public long appid;
    public String tags;
    public String alias;
    public String channel;
    public String feature;
    public String stage;
    public String campaign;
    public String params;
    public String source;
    public String sessionId;

    /**
     * construction function
     */

    public LMUrlParams(String linkedmeKey, long appid, String identityId, String deviceFingerprintId, String sdkVersion, int retryTimes, int debug,
            String tags, String alias, String channel, String feature, String stage, String campaign, String params, String source,
            String sessionId) {
        super(linkedmeKey, identityId, deviceFingerprintId, sdkVersion, retryTimes, debug);
        this.appid = appid;
        this.tags = tags;
        this.alias = alias;
        this.channel = channel;
        this.feature = feature;
        this.stage = stage;
        this.campaign = campaign;
        this.params = params;
        this.source = source;
        this.sessionId = sessionId;
    }
}
