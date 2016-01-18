package cc.linkedme.commons.utils;

/**
 * Created by qipo on 15/9/2.
 */
public class TimeUtils {

    /**
     * get seconds
     */

    public static String getTimestampString() {
        String result;
        Long timestampLong = System.currentTimeMillis() / 1000;
        result = String.valueOf(timestampLong);
        return result;
    }

    public static int getTimestamp() {
        Long timestampLong = System.currentTimeMillis() / 1000;
        return timestampLong.intValue();
    }

    public static Long getCurrentTimes() {
        return System.currentTimeMillis() / 1000;
    }

    public static Long getCurrentTimesMills() {
        return System.currentTimeMillis();
    }

    /**
     * get millis seconds
     */

    public static String getCurrentTimeMills() {
        String result;
        Long timestampLong = System.currentTimeMillis();
        result = String.valueOf(timestampLong);
        return result;
    }

}
