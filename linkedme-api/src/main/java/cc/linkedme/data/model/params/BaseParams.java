package cc.linkedme.data.model.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class BaseParams {

    public long identity_id;
    public String device_fingerprint_id;
    public String sdk_version;
    public int retry_times;
    public String linkedme_key;
    public long timestamp;
    public String sign;
    public boolean is_debug;

    public BaseParams() {}

    public BaseParams(long identity_id, String device_fingerprint_id, String sdk_version, int retry_times, String linkedme_key,
            long timestamp, String sign, boolean is_debug) {
        this.identity_id = identity_id;
        this.device_fingerprint_id = device_fingerprint_id;
        this.sdk_version = sdk_version;
        this.retry_times = retry_times;
        this.linkedme_key = linkedme_key;
        this.timestamp = timestamp;
        this.sign = sign;
        this.is_debug = is_debug;
    }
}
