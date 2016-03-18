package cc.linkedme.data.model.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMOpenParams extends LMBaseParams {

    public boolean isReferable;
    public String appVersion;
    public String osVersion;
    public int sdkUpdate;
    public String os;
    public String latVal;

    /**
     * construction function
     */

    public LMOpenParams(String deviceFingerprintId, String identityId, boolean isReferable, String appVersion, String osVersion, int sdkUpdate,
            String os, boolean isDebug, String latVal, String sdkVersion, int retryTimes, String linkedmeKey) {

        super(linkedmeKey, identityId, deviceFingerprintId, sdkVersion, retryTimes, isDebug);
        this.isReferable = isReferable;
        this.appVersion = appVersion;
        this.osVersion = osVersion;
        this.sdkUpdate = sdkUpdate;
        this.latVal = latVal;
    }
}
