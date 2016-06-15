package cc.linkedme.data.model.params;

/**
 * Created by linkedme05 on 6/15/16.
 */
public class WebInitParams {

    private String LMKey;
    private String identityId;
    private String clientIP;

    public String getLMKey() {
        return LMKey;
    }

    public void setLMKey(String LMKey) {
        this.LMKey = LMKey;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }



}
