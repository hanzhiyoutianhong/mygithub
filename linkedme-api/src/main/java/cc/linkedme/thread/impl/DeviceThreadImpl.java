package cc.linkedme.thread.impl;

import cc.linkedme.thread.DeviceThread;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by LinkedME07 on 16/7/18.
 */

@Component
public class DeviceThreadImpl extends Thread implements DeviceThread {

    @Resource
    private  DeviceService ;
}
