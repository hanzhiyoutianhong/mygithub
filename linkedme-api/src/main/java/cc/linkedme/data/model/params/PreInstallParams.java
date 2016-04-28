package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by LinkedME01 on 16/3/28.
 */

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreInstallParams {
    public int app_id;
    public long identity_id;
    public long deeplink_id;
    public String os;
    public String os_version;
    public int screen_dpi;
    public int screen_height;
    public int screen_width;
    public String clientIP;
    public long timestamp;
    public String sign;

    public long getIdentity_id() {
        return identity_id;
    }

    public void setIdentity_id(long identity_id) {
        this.identity_id = identity_id;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public int getScreen_dpi() {
        return screen_dpi;
    }

    public void setScreen_dpi(int screen_dpi) {
        this.screen_dpi = screen_dpi;
    }

    public int getScreen_height() {
        return screen_height;
    }

    public void setScreen_height(int screen_height) {
        this.screen_height = screen_height;
    }

    public int getScreen_width() {
        return screen_width;
    }

    public void setScreen_width(int screen_width) {
        this.screen_width = screen_width;
    }


    public long getDeeplink_id() {
        return deeplink_id;
    }

    public void setDeeplink_id(long deeplink_id) {
        this.deeplink_id = deeplink_id;
    }


    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public int getApp_id() {
        return app_id;
    }

    public void setApp_id(int app_id) {
        this.app_id = app_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
