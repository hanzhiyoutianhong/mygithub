package cc.linkedme.service.webapi.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Resource;

import cc.linkedme.commons.log.ApiLogger;
import org.apache.commons.collections.CollectionUtils;

import cc.linkedme.dao.webapi.DeviceDao;
import cc.linkedme.data.model.DeviceInfo;
import cc.linkedme.service.webapi.DeviceService;

/**
 * Created by LinkedME07 on 16/7/17.
 */

public class DeviceServiceImpl implements DeviceService {

    public static AtomicReference<Map<String, List<Long>>> whiteDeviceMap = new AtomicReference<>(new HashMap<>());

    @Resource
    private DeviceDao deviceDao;

    @Override
    public int addDevice(DeviceInfo deviceInfo) {

        int result = deviceDao.addDevice(deviceInfo);
        return result;
    }

    @Override
    public int delDevice(long appId, String[] deviceIds) {
        int result = 0;
        for (String deviceId : deviceIds) {
            result += deviceDao.delDevice(appId, deviceId);
        }
        return result;
    }

    @Override
    public int updateDevice(DeviceInfo deviceInfo) {
        int result = deviceDao.updateDevice(deviceInfo);
        return result;
    }

    @Override
    public DeviceInfo getDevice(long appId, String deviceId) {
        DeviceInfo deviceInfo = deviceDao.getDevice(appId, deviceId);
        if (deviceInfo != null) {
            return deviceInfo;
        }
        return null;
    }

    @Override
    public List<DeviceInfo> listDevice(long appId) {
        List<DeviceInfo> deviceInfos = deviceDao.listDevices(appId);
        if (CollectionUtils.isEmpty(deviceInfos)) {
            return new ArrayList<DeviceInfo>(0);
        }
        return deviceInfos;
    }


    public void setWhiteDeviceMap() {
        Map<String, List<Long>> deviceIdAndAppIdMap = deviceDao.getDeviceIdAndAppIdKVMap();
        ApiLogger.debug("deviceIdAndAppIdMap: " + deviceIdAndAppIdMap);
        whiteDeviceMap.set(deviceIdAndAppIdMap);
    }
}
