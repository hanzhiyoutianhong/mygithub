package cc.linkedme.task;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;

public class TaskTrigger implements InitializingBean {
    private static final long DELAY_TIME = 60 * 1000;
    private static final long EXEC_PERIOD_TIME = 2 * 60 * 1000;
    private static ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(1, new DiscardOldestPolicy());

    @Resource
    private DeviceTask deviceTask;

    public void schedualTask() {
        exec.scheduleAtFixedRate(deviceTask, DELAY_TIME, EXEC_PERIOD_TIME, TimeUnit.MILLISECONDS);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        schedualTask();
    }

}