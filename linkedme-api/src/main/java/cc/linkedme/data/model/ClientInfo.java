package cc.linkedme.data.model;

import java.sql.Timestamp;

/**
 * Created by LinkedME00 on 16/3/19.
 */
public class ClientInfo {
    private long identityId;


    public long getIdentityId() {
        return identityId;
    }

    public void setIdentityId(long identityId) {
        this.identityId = identityId;
    }

    private String linkedmeKey;

    public String getLinkedmeKey() {
        return linkedmeKey;
    }

    public void setLinkedmeKey(String linkedmeKey) {
        this.linkedmeKey = linkedmeKey;
    }

    private String deviceId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    private int deviceType;

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    private String deviceModel;

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    private String deviceBrand;

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    private boolean hasBlutooth;

    public boolean getHasBlutooth() {
        return hasBlutooth;
    }

    public void setHasBlutooth(boolean hasBlutooth) {
        this.hasBlutooth = hasBlutooth;
    }

    private boolean hasNfc;

    public boolean getHasNfc() {
        return hasNfc;
    }

    public void setHasNfc(boolean hasNfc) {
        this.hasNfc = hasNfc;
    }

    private boolean hasSim;

    public boolean getHasSim() {
        return hasSim;
    }

    public void setHasSim(boolean hasSim) {
        this.hasSim = hasSim;
    }

    private String os;

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    private String osVersion;

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    private int screenDpi;


    public int getScreenDpi() {
        return screenDpi;
    }

    public void setScreenDpi(int screenDpi) {
        this.screenDpi = screenDpi;
    }

    private int screenHeight;

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    private int screenWidth;


    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    private boolean isWifi;

    public boolean getIsWifi() {
        return isWifi;
    }

    public void setIsWifi(boolean isWifi) {
        this.isWifi = isWifi;
    }

    private boolean isReferable;

    public boolean getIsReferable() {
        return isReferable;
    }

    public void setIsReferable(boolean isReferable) {
        this.isReferable = isReferable;
    }

    private boolean latVal;

    public boolean getLatVal() {
        return latVal;
    }

    public void setLatVal(boolean latVal) {
        this.latVal = latVal;
    }

    private String carrier;

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    private String appVersion;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    private int sdkUpdate;


    public int getSdkUpdate() {
        return sdkUpdate;
    }

    public void setSdkUpdate(int sdkUpdate) {
        this.sdkUpdate = sdkUpdate;
    }

    private String iosTeamId;

    public String getIosTeamId() {
        return iosTeamId;
    }

    public void setIosTeamId(String iosTeamId) {
        this.iosTeamId = iosTeamId;
    }

    private String iosBundleId;

    public String getIosBundleId() {
        return iosBundleId;
    }

    public void setIosBundleId(String iosBundleId) {
        this.iosBundleId = iosBundleId;
    }

    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
