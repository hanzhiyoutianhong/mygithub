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

    private Byte deviceType;

    @Basic
    @javax.persistence.Column(name = "device_type")
    public Byte getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Byte deviceType) {
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

    private Byte hasBlutooth;

    @Basic
    @javax.persistence.Column(name = "has_blutooth")
    public Byte getHasBlutooth() {
        return hasBlutooth;
    }

    public void setHasBlutooth(Byte hasBlutooth) {
        this.hasBlutooth = hasBlutooth;
    }

    private Byte hasNfc;

    @Basic
    @javax.persistence.Column(name = "has_nfc")
    public Byte getHasNfc() {
        return hasNfc;
    }

    public void setHasNfc(Byte hasNfc) {
        this.hasNfc = hasNfc;
    }

    private Byte hasSim;

    @Basic
    @javax.persistence.Column(name = "has_sim")
    public Byte getHasSim() {
        return hasSim;
    }

    public void setHasSim(Byte hasSim) {
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

    private Integer screenDpi;

    @Basic
    @javax.persistence.Column(name = "screen_dpi")
    public Integer getScreenDpi() {
        return screenDpi;
    }

    public void setScreenDpi(Integer screenDpi) {
        this.screenDpi = screenDpi;
    }

    private Integer screenHeight;

    @Basic
    @javax.persistence.Column(name = "screen_height")
    public Integer getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(Integer screenHeight) {
        this.screenHeight = screenHeight;
    }

    private Integer screenWidth;

    @Basic
    @javax.persistence.Column(name = "screen_width")
    public Integer getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(Integer screenWidth) {
        this.screenWidth = screenWidth;
    }

    private Byte isWifi;

    @Basic
    @javax.persistence.Column(name = "is_wifi")
    public Byte getIsWifi() {
        return isWifi;
    }

    public void setIsWifi(Byte isWifi) {
        this.isWifi = isWifi;
    }

    private Byte isReferable;

    @Basic
    @javax.persistence.Column(name = "is_referable")
    public Byte getIsReferable() {
        return isReferable;
    }

    public void setIsReferable(Byte isReferable) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientInfo that = (ClientInfo) o;

        if (identityId != that.identityId) return false;
        if (linkedmeKey != null ? !linkedmeKey.equals(that.linkedmeKey) : that.linkedmeKey != null) return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (deviceModel != null ? !deviceModel.equals(that.deviceModel) : that.deviceModel != null) return false;
        if (deviceBrand != null ? !deviceBrand.equals(that.deviceBrand) : that.deviceBrand != null) return false;
        if (hasBlutooth != null ? !hasBlutooth.equals(that.hasBlutooth) : that.hasBlutooth != null) return false;
        if (hasNfc != null ? !hasNfc.equals(that.hasNfc) : that.hasNfc != null) return false;
        if (hasSim != null ? !hasSim.equals(that.hasSim) : that.hasSim != null) return false;
        if (os != null ? !os.equals(that.os) : that.os != null) return false;
        if (osVersion != null ? !osVersion.equals(that.osVersion) : that.osVersion != null) return false;
        if (screenDpi != null ? !screenDpi.equals(that.screenDpi) : that.screenDpi != null) return false;
        if (screenHeight != null ? !screenHeight.equals(that.screenHeight) : that.screenHeight != null) return false;
        if (screenWidth != null ? !screenWidth.equals(that.screenWidth) : that.screenWidth != null) return false;
        if (isWifi != null ? !isWifi.equals(that.isWifi) : that.isWifi != null) return false;
        if (isReferable != null ? !isReferable.equals(that.isReferable) : that.isReferable != null) return false;
        if (latVal != null ? !latVal.equals(that.latVal) : that.latVal != null) return false;
        if (carrier != null ? !carrier.equals(that.carrier) : that.carrier != null) return false;
        if (appVersion != null ? !appVersion.equals(that.appVersion) : that.appVersion != null) return false;
        if (sdkUpdate != null ? !sdkUpdate.equals(that.sdkUpdate) : that.sdkUpdate != null) return false;
        if (iosTeamId != null ? !iosTeamId.equals(that.iosTeamId) : that.iosTeamId != null) return false;
        if (iosBundleId != null ? !iosBundleId.equals(that.iosBundleId) : that.iosBundleId != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (identityId ^ (identityId >>> 32));
        result = 31 * result + (linkedmeKey != null ? linkedmeKey.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (deviceModel != null ? deviceModel.hashCode() : 0);
        result = 31 * result + (deviceBrand != null ? deviceBrand.hashCode() : 0);
        result = 31 * result + (hasBlutooth != null ? hasBlutooth.hashCode() : 0);
        result = 31 * result + (hasNfc != null ? hasNfc.hashCode() : 0);
        result = 31 * result + (hasSim != null ? hasSim.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + (screenDpi != null ? screenDpi.hashCode() : 0);
        result = 31 * result + (screenHeight != null ? screenHeight.hashCode() : 0);
        result = 31 * result + (screenWidth != null ? screenWidth.hashCode() : 0);
        result = 31 * result + (isWifi != null ? isWifi.hashCode() : 0);
        result = 31 * result + (isReferable != null ? isReferable.hashCode() : 0);
        result = 31 * result + (latVal != null ? latVal.hashCode() : 0);
        result = 31 * result + (carrier != null ? carrier.hashCode() : 0);
        result = 31 * result + (appVersion != null ? appVersion.hashCode() : 0);
        result = 31 * result + (sdkUpdate != null ? sdkUpdate.hashCode() : 0);
        result = 31 * result + (iosTeamId != null ? iosTeamId.hashCode() : 0);
        result = 31 * result + (iosBundleId != null ? iosBundleId.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
