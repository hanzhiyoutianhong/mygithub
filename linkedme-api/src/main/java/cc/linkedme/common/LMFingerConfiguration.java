package cc.linkedme.common;

/**
 * Created by qipo on 15/9/19.
 */
public class LMFingerConfiguration {

    public String appID;
    public String IP;
    public String os;
    public String osVersion;
    public String deviceModel;
    public String screenWidth;
    public String screenHeight;


    /**
     * construction function
     */

    public LMFingerConfiguration() {}

    public LMFingerConfiguration(String appID, String IP, String os, String osVersion, String deviceModel, String screenWidth,
            String scrrenHeight) {
        this.appID = appID;
        this.IP = IP;
        this.os = os;
        this.osVersion = osVersion;
        this.deviceModel = deviceModel;
        this.screenWidth = screenWidth;
        this.screenHeight = scrrenHeight;
    }

    /**
     * get and set function
     */

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScrrenHeight() {
        return screenHeight;
    }

    public void setScrrenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }
}
