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
    public String sdkUpdate;
    public String iOSTeamId;
    public String iOSBundleId;

    public LMInstallParams(String linkedMEKey, long identityId, String deviceFingerprintId, String sdkVersion, int retryTimes,
                           boolean isDebug, String deviceId, Byte deviceType, String deviceBrand, String deviceModel,
                           boolean hasBluetooth, boolean hasNfc, boolean hasSim, String os, String osVersion,
                           int screenDpi, int screenHeight, int screenWidth, boolean isWifi, boolean isReferable,
                           String vatVal, String carrier, String appVersion, String sdkUpdate, String iOSTeamId, String iOSBundleId) {
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
        this.sdkVersion = sdkVersion;
        this.sdkUpdate = sdkUpdate;
        this.iOSTeamId = iOSTeamId;
        this.iOSBundleId = iOSBundleId;
    }
}
