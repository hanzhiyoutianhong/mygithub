package cc.linkedme.dao.webapi;

import cc.linkedme.data.model.DeviceInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by LinkedME07 on 16/7/17.
 */
public interface DeviceDao {

    int addDevice(DeviceInfo deviceInfo);

    int delDevice(long appId, String deviceId);

    int updateDevice(DeviceInfo deviceInfo);

    DeviceInfo getDevice(long appId, String deviceId);

    List<DeviceInfo> listDevices(long appId);

    Map<String, List<Long>> getDeviceIdAndAppIdKVMap();
}
