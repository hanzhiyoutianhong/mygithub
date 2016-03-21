package cc.linkedme.data.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by LinkedME00 on 16/3/19.
 */
@Entity
@javax.persistence.Table(name = "client_info_0", schema = "client_0", catalog = "")
public class ClientInfo {
    private long identityId;

    @Id
    @javax.persistence.Column(name = "identity_id")
    public long getIdentityId() {
        return identityId;
    }

    public void setIdentityId(long identityId) {
        this.identityId = identityId;
    }

    private String linkedmeKey;

    @Basic
    @javax.persistence.Column(name = "linkedme_key")
    public String getLinkedmeKey() {
        return linkedmeKey;
    }

    public void setLinkedmeKey(String linkedmeKey) {
        this.linkedmeKey = linkedmeKey;
    }

    private String deviceId;

    @Basic
    @javax.persistence.Column(name = "device_id")
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    private int deviceType;

    @Basic
    @javax.persistence.Column(name = "device_type")
    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    private String deviceModel;

    @Basic
    @javax.persistence.Column(name = "device_model")
    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    private String deviceBrand;

    @Basic
    @javax.persistence.Column(name = "device_brand")
    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    private boolean hasBlutooth;

    @Basic
    @javax.persistence.Column(name = "has_blutooth")
    public boolean getHasBlutooth() {
        return hasBlutooth;
    }

    public void setHasBlutooth(boolean hasBlutooth) {
        this.hasBlutooth = hasBlutooth;
    }

    private boolean hasNfc;

    @Basic
    @javax.persistence.Column(name = "has_nfc")
    public boolean getHasNfc() {
        return hasNfc;
    }

    public void setHasNfc(boolean hasNfc) {
        this.hasNfc = hasNfc;
    }

    private boolean hasSim;

    @Basic
    @javax.persistence.Column(name = "has_sim")
    public boolean getHasSim() {
        return hasSim;
    }

    public void setHasSim(boolean hasSim) {
        this.hasSim = hasSim;
    }

    private String os;

    @Basic
    @javax.persistence.Column(name = "os")
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    private String osVersion;

    @Basic
    @javax.persistence.Column(name = "os_version")
    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    private int screenDpi;

    @Basic
    @javax.persistence.Column(name = "screen_dpi")
    public int getScreenDpi() {
        return screenDpi;
    }

    public void setScreenDpi(int screenDpi) {
        this.screenDpi = screenDpi;
    }

    private int screenHeight;

    @Basic
    @javax.persistence.Column(name = "screen_height")
    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    private int screenWidth;

    @Basic
    @javax.persistence.Column(name = "screen_width")
    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    private boolean isWifi;

    @Basic
    @javax.persistence.Column(name = "is_wifi")
    public boolean getIsWifi() {
        return isWifi;
    }

    public void setIsWifi(boolean isWifi) {
        this.isWifi = isWifi;
    }

    private boolean isReferable;

    @Basic
    @javax.persistence.Column(name = "is_referable")
    public boolean getIsReferable() {
        return isReferable;
    }

    public void setIsReferable(boolean isReferable) {
        this.isReferable = isReferable;
    }

    private String latVal;

    @Basic
    @javax.persistence.Column(name = "lat_val")
    public String getLatVal() {
        return latVal;
    }

    public void setLatVal(String latVal) {
        this.latVal = latVal;
    }

    private String carrier;

    @Basic
    @javax.persistence.Column(name = "carrier")
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    private String appVersion;

    @Basic
    @javax.persistence.Column(name = "app_version")
    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    private String sdkUpdate;

    @Basic
    @javax.persistence.Column(name = "sdk_update")
    public String getSdkUpdate() {
        return sdkUpdate;
    }

    public void setSdkUpdate(String sdkUpdate) {
        this.sdkUpdate = sdkUpdate;
    }

    private String iosTeamId;

    @Basic
    @javax.persistence.Column(name = "ios_team_id")
    public String getIosTeamId() {
        return iosTeamId;
    }

    public void setIosTeamId(String iosTeamId) {
        this.iosTeamId = iosTeamId;
    }

    private String iosBundleId;

    @Basic
    @javax.persistence.Column(name = "ios_bundle_id")
    public String getIosBundleId() {
        return iosBundleId;
    }

    public void setIosBundleId(String iosBundleId) {
        this.iosBundleId = iosBundleId;
    }

    private Timestamp timestamp;

    @Basic
    @javax.persistence.Column(name = "timestamp")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
