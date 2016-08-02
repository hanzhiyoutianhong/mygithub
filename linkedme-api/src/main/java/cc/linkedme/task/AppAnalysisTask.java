package cc.linkedme.task;

import javax.annotation.Resource;

import cc.linkedme.service.sdkapi.AppAnalysisService;
import cc.linkedme.service.webapi.impl.DeviceServiceImpl;

/**
 * Created by LinkedME01 on 16/6/23.
 */

public class AppAnalysisTask implements Runnable {

    public static final String MLINKS = "https://s.mlinks.cc/apple-app-site-association";
    public static final String FDSSO = "https://fds.so/apple-app-site-association";
    public static final String LINKEDME = "https://lkme.cc/apple-app-site-association";

    @Resource
    AppAnalysisService appAnalysisService;

    @Override
    public void run() {
        appAnalysisService.addAppBundle(MLINKS,"magicwindow");
        appAnalysisService.addAppBundle(FDSSO,"deepshare");
        appAnalysisService.addAppBundle(LINKEDME,"linkedme");
    }
}
