package cc.linkedme.service.webapi;

import cc.linkedme.data.model.DeviceInfo;

import java.util.List;

/**
 * Created by LinkedME07 on 16/7/17.
 */
public interface DeviceService {

    int addDevice(DeviceInfo deviceInfo);

    int delDevice(long appId, String[] deviceId);

    int updateDevice(DeviceInfo deviceInfo);

    DeviceInfo getDevice(long appId, String deviceId);

    List<DeviceInfo> listDevice(long appId);

    void setWhiteDeviceMap();
}
