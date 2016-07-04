package cc.linkedme.data.model;

/**
 * Created by vontroy on 16-7-4.
 */
public class FingerPrintInfo {
    private String deviceId;
    private int deviceType;
    private long identityId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public long getIdentityId() {
        return identityId;
    }

    public void setIdentityId(long identityId) {
        this.identityId = identityId;
    }
}
