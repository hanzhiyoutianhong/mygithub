package cc.linkedme.task;

import javax.annotation.Resource;

import cc.linkedme.commons.log.ApiLogger;
import cc.linkedme.service.webapi.impl.DeviceServiceImpl;

/**
 * Created by LinkedME01 on 16/6/23.
 */

public class DeviceTask implements Runnable {

    @Resource
    DeviceServiceImpl deviceService;

    @Override
    public void run() {
        ApiLogger.info("device task start...");
        deviceService.setWhiteDeviceMap();
    }
}
