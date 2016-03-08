package cc.linkedme.commons.counter.task;

import java.util.concurrent.*;

import cc.linkedme.commons.log.StatLog;

public class CountTaskExecutor {
    private static final ThreadFactory threadFactory = Executors.defaultThreadFactory();
    private static final RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.DiscardOldestPolicy();

    public static ThreadPoolExecutor db = new ThreadPoolExecutor(80, 100, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(100),
            threadFactory, rejectedExecutionHandler);
    static {
        StatLog.registerExecutor("pool_count_db", db);
    }
}
