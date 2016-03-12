package cc.linkedme.common;

import cc.linkedme.commons.utils.ShortUrlUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by qipo on 15/8/31.
 */
public class LMAppCount {

    public static Map<String, String> appCountMap = new HashMap<String, String>();

    /**
     * construction function
     */

    public LMAppCount() {}

    /**
     * get and set function
     */

    public static Map<String, String> getAppCountMap() {
        return appCountMap;
    }

    public static void setAppCountMap(Map<String, String> appCountMap) {
        LMAppCount.appCountMap = appCountMap;
    }

    /**
     * get the short Url identify of app
     *
     * @param appID
     * @return
     */

    public static String getAppShortUrlIdentify(String appID) {
        String result;

        if (appCountMap.containsKey(appID)) {
            String shortUrlIdentify = appCountMap.get(appID);
            result = shortUrlIdentify;
        } else {
            int appCountCurrent = appCount();
            String shortUrlIdentify = ShortUrlUtils.turnTenToSixTwo(appCountCurrent);
            appCountMap.put(appID, shortUrlIdentify);
            result = shortUrlIdentify;
        }

        return result;
    }

    /**
     * get the app count
     *
     * @return
     */

    public static int appCount() {
        int result;

        Set<String> set = appCountMap.keySet();
        if (set.isEmpty()) {
            result = 0;
        } else {
            result = set.size();
        }

        return result;
    }

}
