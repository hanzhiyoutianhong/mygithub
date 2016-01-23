package cc.linkedme.common.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMBaseParams {

    public String linkedMeKey;
    public String sdk;
    public String retryNumber;
    public String debug;

    /**
     * construction function
     */

    public LMBaseParams(String linkedMeKey, String sdk, String retryNumber, String debug) {
        this.linkedMeKey = linkedMeKey;
        this.sdk = sdk;
        this.retryNumber = retryNumber;
        this.debug = debug;
    }

    /**
     * get and set function
     */

    public String getLinkedMeKey() {
        return linkedMeKey;
    }

    public void setLinkedMeKey(String linkedMeKey) {
        this.linkedMeKey = linkedMeKey;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getRetryNumber() {
        return retryNumber;
    }

    public void setRetryNumber(String retryNumber) {
        this.retryNumber = retryNumber;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }
}
