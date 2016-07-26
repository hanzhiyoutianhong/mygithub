package cc.linkedme.data.model;

/**
 * Created by LinkedME07 on 16/7/17.
 */
public class DeviceInfo {


    private long appId;
    private String deviceId;
    private String deviceName;
    private int platform;
    private String description;
    private String createTime;
    private String lastUpateTime;

    public DeviceInfo() {}

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpateTime() {
        return lastUpateTime;
    }

    public void setLastUpateTime(String lastUpateTime) {
        this.lastUpateTime = lastUpateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeviceInfo that = (DeviceInfo) o;

        if (appId != that.appId) return false;
        if (platform != that.platform) return false;
        if (deviceId != null ? !deviceId.equals(that.deviceId) : that.deviceId != null) return false;
        if (deviceName != null ? !deviceName.equals(that.deviceName) : that.deviceName != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return lastUpateTime != null ? lastUpateTime.equals(that.lastUpateTime) : that.lastUpateTime == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (appId ^ (appId >>> 32));
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
        result = 31 * result + platform;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (lastUpateTime != null ? lastUpateTime.hashCode() : 0);
        return result;
    }
}
