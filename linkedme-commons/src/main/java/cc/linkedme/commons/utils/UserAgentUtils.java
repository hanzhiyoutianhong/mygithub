package cc.linkedme.commons.utils;

/**
 * Created by qipo on 15/10/10.
 */
public class UserAgentUtils {

    /**
     * construct function
     */

    public UserAgentUtils() {

    }

    public static boolean isEmail(String family) {
        return family.equals("Email");
    }

    public static boolean isDouban(String family) {
        return family.equals("Douban");
    }

    public static boolean isZhihu(String family) {
        return family.equals("Zhihu");
    }

    public static boolean isQQSpace(String family) {
        return family.equals("QQSpace");
    }

    public static boolean isQQ(String family) {
        return family.equals("QQ");
    }

    public static boolean isEvernote(String fmaily) {
        return fmaily.equals("Evernote");
    }

    public static boolean isWeibo(String family) {
        return family.equals("Weibo");
    }

    public static boolean isTencentWeibo(String family) {
        return family.equals("TencentWeibo");
    }

    public static boolean isQQInner(String family) {
        return family.equals("QQInner");
    }

    public static boolean isWeChat(String family) {
        return family.equals("WeChat");
    }

    public static boolean isChrome(String family) {
        return family.equals("Chrome");
    }

    public static boolean isFirefox(String family) {
        return family.equals("Firefox");
    }

    public static boolean isAndroid(String os) {
        return os.equals("Android");
    }

    public static boolean isIOS(String os) {
        return os.equals("iOS");
    }

    public static boolean isUniversalLink(String major) {
        int majorInt = Integer.valueOf(major);
        if (majorInt >= 9) {
            return true;
        } else {
            return false;
        }
    }

}


