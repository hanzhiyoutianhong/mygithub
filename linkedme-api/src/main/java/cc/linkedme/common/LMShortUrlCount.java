package cc.linkedme.common;

import cc.linkedme.commons.utils.ShortUrlUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by qipo on 15/9/6.
 */
public class LMShortUrlCount {
    public static Map<String, String> shortUrlCountMap = new HashMap<String, String>();

    /**
     * construction function
     */

    public LMShortUrlCount() {
    }

    /**
     * get and set function
     */

    public static Map<String, String> getShortUrlCountMap() {
        return shortUrlCountMap;
    }

    public static void setShortrlCountMap(Map<String, String> shortUrlCountMap) {
        LMShortUrlCount.shortUrlCountMap = shortUrlCountMap;
    }

    /**
     * get the short url count of clickID
     *
     * @param ckickID
     * @return
     */

    public static String getShortUrlIdentify(String ckickID) {
        String result;

        if (shortUrlCountMap.containsKey(ckickID)) {
            String shortUrlIdentify = shortUrlCountMap.get(ckickID);
            long decimal = ShortUrlUtils.turnSixTwoToTen(shortUrlIdentify) + 1;
            String sixTwoValue = ShortUrlUtils.turnTenToSixTwo(decimal);
            shortUrlCountMap.put(ckickID, sixTwoValue);
            result = sixTwoValue;
        } else {
            int shortUrlCount = 0;
            String shortUrlIdentify = ShortUrlUtils.turnTenToSixTwo(shortUrlCount);
            shortUrlCountMap.put(ckickID, shortUrlIdentify);
            result = shortUrlIdentify;
        }

        return result;
    }

    /**
     * get the app count
     *
     * @return
     */

    public static int shortUrlCount() {
        int result;

        Set<String> set = shortUrlCountMap.keySet();
        if (set.isEmpty()) {
            result = 0;
        } else {
            result = set.size();
        }

        return result;
    }

}
