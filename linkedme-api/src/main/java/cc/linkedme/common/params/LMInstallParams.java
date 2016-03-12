package cc.linkedme.common.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMInstallParams extends LMBaseParams {
    public String hardwareId;
    public String googleAdvertisingId;
    public String isHardwareIdReal;
    public String adTrackingEnabled;
    public String brand;
    public String carrier;
    public String iOSBundleId;
    public String isReferable;
    public String os;
    public String osVersion;
    public String appVersion;
    public String update;
    public String uriScheme;
    public String iOSTeamId;
    public String universalLinkUrl;
    public String SpotlightIdentifier;
    public String latVal;
    public String wifi;
    public String hasNfc;
    public String hasTelephone;
    public String bluetooth;
    public String screenDpi;
    public String screenHeight;
    public String ScreenWidth;

    /**
     * construction function
     */

    public LMInstallParams(String linkedMeKey, String sdk, String debug, String retryNumber, String hardwareId, String googleAdvertisingId,
            String isHardwareIdReal, String adTrackingEnabled, String brand, String carrier, String iOSBundleId, String isReferable,
            String os, String osVersion, String appVersion, String update, String uriScheme, String iOSTeamId, String universalLinkUrl,
            String spotlightIdentifier, String latVal, String wifi, String hasNfc, String hasTelephone, String bluetooth, String screenDpi,
            String screenHeight, String screenWidth) {
        super(linkedMeKey, sdk, debug, retryNumber);
        this.hardwareId = hardwareId;
        this.googleAdvertisingId = googleAdvertisingId;
        this.isHardwareIdReal = isHardwareIdReal;
        this.adTrackingEnabled = adTrackingEnabled;
        this.brand = brand;
        this.carrier = carrier;
        this.iOSBundleId = iOSBundleId;
        this.isReferable = isReferable;
        this.os = os;
        this.osVersion = osVersion;
        this.appVersion = appVersion;
        this.update = update;
        this.uriScheme = uriScheme;
        this.iOSTeamId = iOSTeamId;
        this.universalLinkUrl = universalLinkUrl;
        this.SpotlightIdentifier = spotlightIdentifier;
        this.latVal = latVal;
        this.wifi = wifi;
        this.hasNfc = hasNfc;
        this.hasTelephone = hasTelephone;
        this.bluetooth = bluetooth;
        this.screenDpi = screenDpi;
        this.screenHeight = screenHeight;
        this.ScreenWidth = screenWidth;
    }

    /**
     * get and set function
     */
    public String getHardwareId() {
        return hardwareId;
    }

    public void setHardwareId(String hardwareId) {
        this.hardwareId = hardwareId;
    }

    public String getGoogleAdvertisingId() {
        return googleAdvertisingId;
    }

    public void setGoogleAdvertisingId(String googleAdvertisingId) {
        this.googleAdvertisingId = googleAdvertisingId;
    }

    public String getIsHardwareIdReal() {
        return isHardwareIdReal;
    }

    public void setIsHardwareIdReal(String isHardwareIdReal) {
        this.isHardwareIdReal = isHardwareIdReal;
    }

    public String getAdTrackingEnabled() {
        return adTrackingEnabled;
    }

    public void setAdTrackingEnabled(String adTrackingEnabled) {
        this.adTrackingEnabled = adTrackingEnabled;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getiOSBundleId() {
        return iOSBundleId;
    }

    public void setiOSBundleId(String iOSBundleId) {
        this.iOSBundleId = iOSBundleId;
    }

    public String getIsReferable() {
        return isReferable;
    }

    public void setIsReferable(String isReferable) {
        this.isReferable = isReferable;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getUriScheme() {
        return uriScheme;
    }

    public void setUriScheme(String uriScheme) {
        this.uriScheme = uriScheme;
    }

    public String getiOSTeamId() {
        return iOSTeamId;
    }

    public void setiOSTeamId(String iOSTeamId) {
        this.iOSTeamId = iOSTeamId;
    }

    public String getUniversalLinkUrl() {
        return universalLinkUrl;
    }

    public void setUniversalLinkUrl(String universalLinkUrl) {
        this.universalLinkUrl = universalLinkUrl;
    }

    public String getSpotlightIdentifier() {
        return SpotlightIdentifier;
    }

    public void setSpotlightIdentifier(String spotlightIdentifier) {
        SpotlightIdentifier = spotlightIdentifier;
    }

    public String getLatVal() {
        return latVal;
    }

    public void setLatVal(String latVal) {
        this.latVal = latVal;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public String getHasNfc() {
        return hasNfc;
    }

    public void setHasNfc(String hasNfc) {
        this.hasNfc = hasNfc;
    }

    public String getHasTelephone() {
        return hasTelephone;
    }

    public void setHasTelephone(String hasTelephone) {
        this.hasTelephone = hasTelephone;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getScreenDpi() {
        return screenDpi;
    }

    public void setScreenDpi(String screenDpi) {
        this.screenDpi = screenDpi;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getScreenWidth() {
        return ScreenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        ScreenWidth = screenWidth;
    }
}
