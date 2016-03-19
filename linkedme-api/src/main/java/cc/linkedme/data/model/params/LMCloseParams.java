package cc.linkedme.data.model.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMCloseParams extends LMBaseParams {

    public String sessionId;

    /**
     * construction function
     */

    public LMCloseParams(String deviceFingerprintId, long identityId, String sessionId, String sdkVersion, int retryTimes,
            String linkedmeKey) {
        super(linkedmeKey, identityId, deviceFingerprintId, sdkVersion, retryTimes, false);

        this.sessionId = sessionId;

    }

}
