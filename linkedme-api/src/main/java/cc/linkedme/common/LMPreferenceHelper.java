package cc.linkedme.common;

import cc.linkedme.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qipo on 15/8/31.
 */
public class LMPreferenceHelper {

    protected static final Logger LM_INFO_LOG = LoggerFactory.getLogger("LMInfoFile");

    //common
    private String appID;
    private String appVersion;
    private String os;
    private String osVersion;
    private String urlScheme;
    private String debug;
    private String identifyID;
    private String isReferrable;

    //register install
    private String hardwareID;
    private String isHardwareIDReal;
    private String carrier;
    private String brand;
    private String model;
    private String screenWidth;
    private String screenHeight;
    private String update;
    private String installTme;


    //register open
    private String deviceFingerprintID;
    private String clickID;
    private String clickIDTime;

    //register custom url
    private String type;
    private String tags;
    private String channel;
    private String feature;
    private String stage;
    private String alias;
    private String sdk;

    //register close
    private String sessionID;


    public static LMClientOpenEntity initLMClientOpenEntity(String identityId, String appKey, String deviceFingerprintId, String appVersion, boolean adTrackingEnabled,
                                                            String googleAdvertsId, boolean isReferrable, String os, String osVersion, String osRelease,
                                                            String update, String sdk, String uriScheme, String spotlightIdentifier, String universalLinkUrl,
                                                            String linkIdentifier, String iosBundleId, boolean lat_val, boolean debug, String sessionId,
                                                            String ip, int retryNumber, int timestamp) {

        LMClientOpenEntity co = new LMClientOpenEntity();

        co.setIdentityId(identityId);
        co.setAppKey(appKey);
        co.setDeviceFingerprintId(deviceFingerprintId);
        co.setAppVersion(appVersion);
        int temp = adTrackingEnabled ? 1 : 0;
        co.setAdTrackingEnabled((byte) temp);
        co.setGoogleAdvertsId(googleAdvertsId);
        temp = isReferrable ? 1 : 0;
        co.setIsReferrable((byte) temp);
        co.setOs(os);
        co.setOsVersion(osVersion);
        co.setOsRelease(osRelease);
        co.setUpdation(update);
        co.setSdk(sdk);
        co.setUriScheme(uriScheme);
        co.setSpotlightIdentifier(spotlightIdentifier);
        co.setUniversalLinkUrl(universalLinkUrl);
        co.setLinkIdentifier(linkIdentifier);
        co.setIosBundleId(iosBundleId);
        temp = lat_val ? 1 : 0;
        co.setLatVal((byte) temp);
        temp = debug ? 1 : 0;
        co.setDebug((byte) temp);
        co.setSessionId(sessionId);
        co.setIp(ip);
        co.setRetryNumber(retryNumber);
        co.setTimestamp(timestamp);

        return co;
    }


    public static LMClientInstallEntity initLMClientInstallEntity(String identityId, String appKey, String hardwareId, String googleAdvertisingId, boolean isHardwareIdReal,
                                                                  boolean adTrackingEnabled, boolean isReferrable, String appVersion, String sdk, String carrier,
                                                                  String brand, String model, String os, String osVersion, String osRelease,
                                                                  int screenWidth, int screenHeight, int screenDpi, String update, boolean bluetooth,
                                                                  String bluetoothVersion, boolean latVal, boolean hasNfc, boolean hasTelephone, boolean wifi,
                                                                  String uriScheme, int retryNumber, String iosTeamId, String iosBundleId, String linkIdentifier,
                                                                  String universalLink, String spotlightIdentifier, boolean debug, String ip, int timestamp) {
        LMClientInstallEntity ci = new LMClientInstallEntity();
        ci.setIdentityId(identityId);
        ci.setAppKey(appKey);
        ci.setHardwareId(hardwareId);
        ci.setGoogleAdvertisingId(googleAdvertisingId);
        int temp = isHardwareIdReal ? 1 : 0;
        ci.setIsHardwareIdReal((byte) temp);
        temp = adTrackingEnabled ? 1 : 0;
        ci.setAdTrackingEnabled((byte) temp);
        temp = isReferrable ? 1 : 0;
        ci.setIsReferrable((byte) temp);
        ci.setAppVersion(appVersion);
        ci.setSdk(sdk);
        ci.setCarrier(carrier);
        ci.setBrand(brand);
        ci.setModel(model);
        ci.setOs(os);
        ci.setOsversion(osVersion);
        ci.setOsRelease(osRelease);
        ci.setScreenWidth(screenWidth);
        ci.setScreenHeight(screenHeight);
        ci.setScreenDpi(screenDpi);
        ci.setUpdation(update);
        temp = bluetooth ? 1 : 0;
        ci.setBluetooth((byte) temp);
        ci.setBluetoothVersoin(bluetoothVersion);
        temp = latVal ? 1 : 0;
        ci.setLatVal((byte) temp);
        temp = hasNfc ? 1 : 0;
        ci.setHasNfc((byte) temp);
        temp = hasTelephone ? 1 : 0;
        ci.setHasTelephone((byte) temp);
        temp = wifi ? 1 : 0;
        ci.setWifi((byte) temp);
        ci.setUriScheme(uriScheme);
        ci.setRetryNumber(retryNumber);
        ci.setIosBundleId(iosBundleId);
        ci.setIosTeamId(iosTeamId);
        ci.setLinkIdentifer(linkIdentifier);
        ci.setUniversalLink(universalLink);
        ci.setSpotlightIdentifier(spotlightIdentifier);
        temp = debug ? 1 : 0;
        ci.setDebug((byte) temp);
        ci.setIp(ip);
        ci.setTimestamp(timestamp);

        return ci;
    }

    /**
     * init LMLinkEntity
     */

    public static LMLinkEntity initLMLinkEntity(String identityId, String appKey, String type, String tags, String channel,
                                                String feature, String stage, String alias, String sdk, String data,
                                                String source, String deepLinkPath, long linkIdentifier, String linkClickId, int clicks,
                                                int install, int rejectInstall, int open, int weibo, int weChat, int retryNumber,
                                                String ip, int timestamp) {
        LMLinkEntity le = new LMLinkEntity();

        le.setIdentityId(identityId);
        le.setAppKey(appKey);
        le.setType(type);
        le.setType(tags);
        le.setChannel(channel);
        le.setFeature(feature);
        le.setStage(stage);
        le.setAlias(alias);
        le.setSdk(sdk);
        le.setData(data);
        le.setSource(source);
        le.setDeeplinkpath(deepLinkPath);
        le.setLinkIdentifier(String.valueOf(linkIdentifier));
        le.setLinkClickId(linkClickId);
        le.setClicks(clicks);
        le.setInstall(install);
        le.setRejectInstall(rejectInstall);
        le.setOpen(open);
        le.setWeibo(weibo);
        le.setWechat(weChat);
        le.setRetryNumber(retryNumber);
        le.setIp(ip);
        le.setTimestamp(timestamp);

        return le;
    }

    /**
     * get and set function
     */

    public String getClickIDTime() {
        return clickIDTime;
    }

    public void setClickIDTime(String clickIDTime) {
        this.clickIDTime = clickIDTime;
    }

    public String getInstallTme() {
        return installTme;
    }

    public void setInstallTme(String installTme) {
        this.installTme = installTme;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getUrlScheme() {
        return urlScheme;
    }

    public void setUrlScheme(String urlScheme) {
        this.urlScheme = urlScheme;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getIdentifyID() {
        return identifyID;
    }

    public void setIdentifyID(String identifyID) {
        this.identifyID = identifyID;
    }

    public String getHardwareID() {
        return hardwareID;
    }

    public void setHardwareID(String hardwareID) {
        this.hardwareID = hardwareID;
    }

    public String getIsHardwareIDReal() {
        return isHardwareIDReal;
    }

    public void setIsHardwareIDReal(String isHardwareIDReal) {
        this.isHardwareIDReal = isHardwareIDReal;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(String screenWidth) {
        this.screenWidth = screenWidth;
    }

    public String getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(String screenHeight) {
        this.screenHeight = screenHeight;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getDeviceFingerprintID() {
        return deviceFingerprintID;
    }

    public void setDeviceFingerprintID(String deviceFingerprintID) {
        this.deviceFingerprintID = deviceFingerprintID;
    }

    public String getIsReferrable() {
        return isReferrable;
    }

    public void setIsReferrable(String isReferrable) {
        this.isReferrable = isReferrable;
    }

    public String getClickID() {
        return clickID;
    }

    public void setClickID(String clinkID) {
        this.clickID = clinkID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }
}
