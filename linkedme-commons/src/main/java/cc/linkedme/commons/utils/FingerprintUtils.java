package cc.linkedme.commons.utils;

/**
 * Created by qipo on 15/9/18.
 */
public class FingerprintUtils {


    public static String browserFingerprintId(String appId, String ip, String os, String osVersion, String brand, String deviceModel) {
        StringBuffer buffer = new StringBuffer();
            buffer.append(appId);
            buffer.append(ip);
            buffer.append(os);
            buffer.append(osVersion);
            buffer.append(brand);
            buffer.append(deviceModel);

        return MD5Utils.MD5Sixteen(buffer.toString());
    }

    public static String deviceFingerprintId(String appId, String ip, String os, String osVersion, String brand, String deviceModel) {
        StringBuffer buffer = new StringBuffer();
            buffer.append(appId);
            buffer.append(ip);
            buffer.append(os);
            buffer.append(osVersion);
            buffer.append(brand);
            buffer.append(deviceModel);

        return MD5Utils.MD5Sixteen(buffer.toString());
    }


}
