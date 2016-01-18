package cc.linkedme.service.impl;

import cc.linkedme.commons.utils.TimeUtils;
import cc.linkedme.service.LMSdkService;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public class LMSdkServiceImpl implements LMSdkService {
    public String install(String linkedMeKey, String hardwareId, String isHardwareIdReal, String adTrackingEnabled, String brand,
                          String carrier, String iOSBundleId, String isReferable, String os, String osVersion,
                          String appVersion, String sdk, String update, String uriScheme, String iOSTeamId,
                          String universalLinkUrl, String spotlightIdentifier, String latVal, String wifi, String hasNFC,
                          String hasTelephone, String bluetooth, String screenDpi, String screenHeight, String screenWidth,
                          String retryNumber, String ip, String sessionId, String debug) {
        String result = null;

        //hardware_id equals identify_id, and identify_id and link_click_id are in the redis

        //browser_fingerprint_id equals device_fingerprint_id

        //add the info into mysql

        //log

        return result;
    }

    public String open(String linkedMeKey, String identifyId, String deviceFingerprintId, String adTrackingEnabled, String linkIdentifier,
                       String isReferable, String os, String osVersion, String appVersion, String sdk,
                       String update, String uriScheme, String iOSBundleId, String iOSTeamId, String spotlightIdentifier,
                       String universalLinkUrl, String latVal, String retryNumber, String debug) {
        String result = null;

        //get the linkIdentifier by redis

        //add the info into mysql

        //log

        return result;
    }

    public String close(String linkedMeKey, String identifyId, String deviceFingerprintId, String sdk, String sessionId,
                        String retryNumber) {

        String result = null;

        int timestamp = TimeUtils.getTimestamp();

        //add this into mysql

        //log

        return result;
    }

    public String url(String linkedMeKey, String identifyId, String deviceFingerprintId, String type, String tags,
                      String channel, String feature, String stage, String alias, String sdk,
                      String data, String update, String source, String deepLinkPath, String duration,
                      String sessionId, String retryNumber, String debug) {
        String result = null;

        //generate a link by parameters

        //add this into the redis

        //log

        return result;
    }

    public String preInstall(String linkClickId) {
        String result = null;

        //set identify_id for browser,

        return result;
    }


}
