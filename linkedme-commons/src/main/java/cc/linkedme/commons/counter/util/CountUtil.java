package cc.linkedme.commons.counter.util;

import cc.linkedme.commons.log.ApiLogger;

import java.util.concurrent.atomic.AtomicBoolean;

public class CountUtil {
    /**
     * count debug
     */
    public static AtomicBoolean countDebug = new AtomicBoolean(false);
    public static boolean isDebugEnabled() {
        return ApiLogger.isDebugEnabled() && countDebug.get();
    }
}
