package cc.linkedme.data.model.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMBaseParams {
    public String identityId;
    public String deviceFingerPrintId;
    public String linkedmeKey;
    public String sdkVersion;
    public int retryTimes;
    public int debug;

    /**
     * construction function
     */

    public LMBaseParams(String identityId, String deviceFingerPrintId, String linkedmeKey, String sdkVersion, int retryTimes, int debug) {
        this.identityId = identityId;
        this.deviceFingerPrintId = deviceFingerPrintId;
        this.linkedmeKey = linkedmeKey;
        this.sdkVersion = sdkVersion;
        this.retryTimes = retryTimes;
        this.debug = debug;
    }
}
