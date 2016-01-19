package cc.linkedme.service;

/**
 * Created by LinkedME00 on 16/1/15.
 */
public interface LMSdkService {

    /**
     * @param linkedMeKey
     * @param hardwareId
     * @param isHardwareIdReal
     * @param adTrackingEnabled
     * @param brand
     * @param carrier
     * @param iOSBundleId
     * @param isReferable
     * @param os
     * @param osVersion
     * @param appVersion
     * @param sdk
     * @param update
     * @param uriScheme
     * @param iOSTeamId
     * @param universalLinkUrl
     * @param spotlightIdentifier
     * @param latVal
     * @param wifi
     * @param hasNFC
     * @param hasTelephone
     * @param bluetooth
     * @param screenDpi
     * @param screenHeight
     * @param screenWidth
     * @param retryNumber
     * @param ip
     * @param sessionId
     * @param debug
     * @return
     */

    String install(String linkedMeKey, String hardwareId, String isHardwareIdReal, String adTrackingEnabled, String brand,
                   String carrier, String iOSBundleId, String isReferable, String os, String osVersion,
                   String appVersion, String sdk, String update, String uriScheme, String iOSTeamId,
                   String universalLinkUrl, String spotlightIdentifier, String latVal, String wifi, String hasNFC,
                   String hasTelephone, String bluetooth, String screenDpi, String screenHeight, String screenWidth,
                   String retryNumber, String ip, String sessionId, String debug);

    /**
     * @param linkedMeKey
     * @param identifyId
     * @param deviceFingerprintId
     * @param adTrackingEnabled
     * @param linkIdentifier
     * @param isReferable
     * @param os
     * @param osVersion
     * @param appVersion
     * @param sdk
     * @param update
     * @param uriScheme
     * @param iOSBundleId
     * @param iOSTeamId
     * @param spotlightIdentifier
     * @param universalLinkUrl
     * @param latVal
     * @param retryNumber
     * @param debug
     * @return
     */

    String open(String linkedMeKey, String identifyId, String deviceFingerprintId, String adTrackingEnabled, String linkIdentifier,
                String isReferable, String os, String osVersion, String appVersion, String sdk,
                String update, String uriScheme, String iOSBundleId, String iOSTeamId, String spotlightIdentifier,
                String universalLinkUrl, String latVal, String retryNumber, String debug);

    /**
     * @param linkedMeKey
     * @param identifyId
     * @param deviceFingerprintId
     * @param sdk
     * @param sessionId
     * @param retryNumber
     * @return
     */

    String close(String linkedMeKey, String identifyId, String deviceFingerprintId, String sdk, String sessionId,
                 String retryNumber);

    /**
     * @param linkedMeKey
     * @param identifyId
     * @param deviceFingerprintId
     * @param type
     * @param tags
     * @param channel
     * @param feature
     * @param stage
     * @param alias
     * @param sdk
     * @param data
     * @param update
     * @param source
     * @param deepLinkPath
     * @param duration
     * @param sessionId
     * @param retryNumber
     * @param debug
     * @return
     */

    String url(String linkedMeKey, String identifyId, String deviceFingerprintId, String type, String tags,
               String channel, String feature, String stage, String alias, String sdk,
               String data, String update, String source, String deepLinkPath, String duration,
               String sessionId, String retryNumber, String debug);

    /**
     * @param linkClickId
     * @return
     */

    String preInstall(String linkClickId);
}
