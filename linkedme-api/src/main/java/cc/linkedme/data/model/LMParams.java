package cc.linkedme.data.model;

import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;

public class LMParams {

    @FormParam("linkedme_key")
    private String linkedmeKey;

    @FormParam("identity_id")
    private long identityId;

    @FormParam("session_id")
    private long sessionId;

    @FormParam("sdk_version")
    private String sdkVersion;

    @FormParam("retry_times")
    private int retryTimes;

    @FormParam("timestamp")
    private long timestamp;

    @FormParam("sign")
    private String sign;
    
    @HeaderParam("x-forwarded-for")
    private String remoteIp;

    public String getLinkedmeKey() {
        return linkedmeKey;
    }

    public void setLinkedmeKey(String linkedmeKey) {
        this.linkedmeKey = linkedmeKey;
    }

    public long getIdentityId() {
        return identityId;
    }

    public void setIdentityId(long identityId) {
        this.identityId = identityId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }


}
