package cc.linkedme.data.model.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMInstallParams extends LMBaseParams {

    public String deviceId;
    public Byte deviceType;
    public String deviceBrand;
    public String deviceModel;
    public boolean hasBluetooth;
    public boolean hasNfc;
    public boolean hasSim;
    public String os;
    public String osVersion;
    public int screenDpi;
    public int screenHeight;
    public int screenWidth;
    public boolean isWifi;
    public boolean isReferable;
    public String vatVal;
    public String carrier;
    public String appVersion;
    public String iOSTeamId;
    public String iOSBundleId;

    public LMInstallParams(String linkedMEKey, long identityId, String deviceFingerprintId, String sdkVersion, int retryTimes,
                           boolean isDebug, String deviceId, Byte deviceType, String deviceBrand, String deviceModel,
                           boolean hasBluetooth, boolean hasNfc, boolean hasSim, String os, String osVersion,
                           int screenDpi, int screenHeight, int screenWidth, boolean isWifi, boolean isReferable,
                           String vatVal, String carrier, String appVersion, String iOSTeamId, String iOSBundleId) {
        super(linkedMEKey, identityId, deviceFingerprintId, sdkVersion, retryTimes, isDebug);
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.deviceBrand = deviceBrand;
        this.deviceModel = deviceModel;
        this.hasBluetooth = hasBluetooth;
        this.hasNfc = hasNfc;
        this.hasSim = hasSim;
        this.os = os;
        this.osVersion = osVersion;
        this.screenDpi = screenDpi;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.isWifi = isWifi;
        this.isReferable = isReferable;
        this.vatVal = vatVal;
        this.carrier = carrier;
        this.appVersion = appVersion;
        this.iOSTeamId = iOSTeamId;
        this.iOSBundleId = iOSBundleId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public boolean isHasBluetooth() {
        return hasBluetooth;
    }

    public void setHasBluetooth(boolean hasBluetooth) {
        this.hasBluetooth = hasBluetooth;
    }

    public boolean isHasNfc() {
        return hasNfc;
    }

    public void setHasNfc(boolean hasNfc) {
        this.hasNfc = hasNfc;
    }

    public boolean isHasSim() {
        return hasSim;
    }

    public void setHasSim(boolean hasSim) {
        this.hasSim = hasSim;
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

    public int getScreenDpi() {
        return screenDpi;
    }

    public void setScreenDpi(int screenDpi) {
        this.screenDpi = screenDpi;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public void setWifi(boolean wifi) {
        isWifi = wifi;
    }

    public boolean isReferable() {
        return isReferable;
    }

    public void setReferable(boolean referable) {
        isReferable = referable;
    }

    public String getVatVal() {
        return vatVal;
    }

    public void setVatVal(String vatVal) {
        this.vatVal = vatVal;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getiOSTeamId() {
        return iOSTeamId;
    }

    public void setiOSTeamId(String iOSTeamId) {
        this.iOSTeamId = iOSTeamId;
    }

    public String getiOSBundleId() {
        return iOSBundleId;
    }

    public void setiOSBundleId(String iOSBundleId) {
        this.iOSBundleId = iOSBundleId;
    }
}
