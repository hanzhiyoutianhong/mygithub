package cc.linkedme.common.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMCloseParams extends LMBaseParams {


    public String identifyId;
    public String deviceFingerprintId;
    public String sessionId;

    /**
     * construction function
     */

    public LMCloseParams(String linkedMeKey, String sdk, String retryNumber, String debug, String identifyId,
                         String deviceFingerprintId,  String sessionId) {
        super(linkedMeKey, sdk, retryNumber, debug);
        this.identifyId = identifyId;
        this.deviceFingerprintId = deviceFingerprintId;
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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
