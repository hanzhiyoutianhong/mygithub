/**
 *
 */
package cc.linkedme.commons.util;

import java.util.concurrent.atomic.AtomicBoolean;

import cc.linkedme.commons.log.ApiLogger;

public class CommonUtil {

    /**
     * count debug
     */
    public static AtomicBoolean commonDebug = new AtomicBoolean(false);

    public static boolean isDebugEnabled() {
        return ApiLogger.isDebugEnabled() && commonDebug.get();
    }

}
