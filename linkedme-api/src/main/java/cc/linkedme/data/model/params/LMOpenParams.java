package cc.linkedme.data.model.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMOpenParams extends LMBaseParams {

    public boolean isReferable;
    public String appVersion;
    public String extra_uri_data;
    public String osVersion;
    public int sdkUpdate;
    public String os;
    public String latVal;

    /**
     * construction function
     */

    public LMOpenParams(String deviceFingerprintId, long identityId, boolean isReferable, String appVersion, String extra_uri_data, String osVersion, int sdkUpdate,
            String os, boolean isDebug, String latVal, String sdkVersion, int retryTimes, String linkedmeKey) {

        super(linkedmeKey, identityId, deviceFingerprintId, sdkVersion, retryTimes, isDebug);
        this.isReferable = isReferable;
        this.appVersion = appVersion;
        this.extra_uri_data = extra_uri_data;
        this.osVersion = osVersion;
        this.sdkUpdate = sdkUpdate;
        this.latVal = latVal;
    }
}
