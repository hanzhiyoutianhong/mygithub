package cc.linkedme.data.model.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMBaseParams {
    public String identityId;
    public String deviceFingerprintId;
    public String linkedmeKey;
    public String sdkVersion;
    public int retryTimes;
    public boolean isDebug;

    /**
     * construction function
     */

    public LMBaseParams() {}

    public LMBaseParams(String linkedmeKey, String identityId, String deviceFingerprintId, String sdkVersion, int retryTimes, boolean isDebug) {
        this.linkedmeKey = linkedmeKey;
        this.identityId = identityId;
        this.deviceFingerprintId = deviceFingerprintId;
        this.sdkVersion = sdkVersion;
        this.retryTimes = retryTimes;
        this.isDebug = isDebug;
    }
}
