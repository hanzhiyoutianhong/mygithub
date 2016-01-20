package cc.linkedme.common.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMBaseParams {

    public String linkedMeKey;
    public String sdk;
    public String retryNumber;
    public String debug;

    public LMBaseParams(String linkedMeKey, String sdk, String retryNumber, String debug) {
        this.linkedMeKey = linkedMeKey;
        this.sdk = sdk;
        this.retryNumber = retryNumber;
        this.debug = debug;
    }
}
