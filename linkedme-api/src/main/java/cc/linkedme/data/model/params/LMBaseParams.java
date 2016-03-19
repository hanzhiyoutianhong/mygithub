package cc.linkedme.data.model.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMBaseParams {
    public long identityId;
    public String deviceFingerprintId;
    public String linkedMEKey;
    public String sdkVersion;
    public int retryTimes;
    public int debug;

    /**
     * construction function
     */

    public LMBaseParams(String linkedMEKey, long identityId, String deviceFingerprintId, String sdkVersion, int retryTimes, int debug) {
        this.linkedMEKey = linkedMEKey;
        this.identityId = identityId;
        this.deviceFingerprintId = deviceFingerprintId;
        this.sdkVersion = sdkVersion;
        this.retryTimes = retryTimes;
        this.debug = debug;
    }
}