package cc.linkedme.common.params;

/**
 * Created by LinkedME00 on 16/1/20.
 */
public class LMOpenParams extends LMBaseParams {

    public String identifyId;
    public String deviceFingerprintId;
    public String adTrackingEnabled;
    public String linkIdentifier;
    public String isReferable;
    public String os;
    public String osVersion;
    public String appVersion;
    public String update;
    public String uriScheme;
    public String iOSBundleId;
    public String iOSTeamId;
    public String spotlightIdentifier;
    public String universalLinkUrl;
    public String latVal;

    /**
     * construction function
     */

    public LMOpenParams(String linkedMeKey, String sdk, String retryNumber, String debug, String identifyId,
                        String deviceFingerprintId, String adTrackingEnabled, String linkIdentifier, String isReferable, String os,
                        String osVersion, String appVersion, String update, String uriScheme, String iOSBundleId,
                        String iOSTeamId, String spotlightIdentifier, String universalLinkUrl, String latVal) {
        super(linkedMeKey, sdk, retryNumber, debug);
        this.identifyId = identifyId;
        this.deviceFingerprintId = deviceFingerprintId;
        this.adTrackingEnabled = adTrackingEnabled;
        this.linkIdentifier = linkIdentifier;
        this.isReferable = isReferable;
        this.os = os;
        this.osVersion = osVersion;
        this.appVersion = appVersion;
        this.update = update;
        this.uriScheme = uriScheme;
        this.iOSBundleId = iOSBundleId;
        this.iOSTeamId = iOSTeamId;
        this.spotlightIdentifier = spotlightIdentifier;
        this.universalLinkUrl = universalLinkUrl;
        this.latVal = latVal;
    }

    /**
     * get and set function
     */

    public String getIdentifyId() {
        return identifyId;
    }

    public void setIdentifyId(String identifyId) {
        this.identifyId = identifyId;
    }

    public String getDeviceFingerprintId() {
        return deviceFingerprintId;
    }

    public void setDeviceFingerprintId(String deviceFingerprintId) {
        this.deviceFingerprintId = deviceFingerprintId;
    }

    public String getAdTrackingEnabled() {
        return adTrackingEnabled;
    }

    public void setAdTrackingEnabled(String adTrackingEnabled) {
        this.adTrackingEnabled = adTrackingEnabled;
    }

    public String getLinkIdentifier() {
        return linkIdentifier;
    }

    public void setLinkIdentifier(String linkIdentifier) {
        this.linkIdentifier = linkIdentifier;
    }

    public String getIsReferable() {
        return isReferable;
    }

    public void setIsReferable(String isReferable) {
        this.isReferable = isReferable;
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

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getUriScheme() {
        return uriScheme;
    }

    public void setUriScheme(String uriScheme) {
        this.uriScheme = uriScheme;
    }

    public String getiOSBundleId() {
        return iOSBundleId;
    }

    public void setiOSBundleId(String iOSBundleId) {
        this.iOSBundleId = iOSBundleId;
    }

    public String getiOSTeamId() {
        return iOSTeamId;
    }

    public void setiOSTeamId(String iOSTeamId) {
        this.iOSTeamId = iOSTeamId;
    }

    public String getSpotlightIdentifier() {
        return spotlightIdentifier;
    }

    public void setSpotlightIdentifier(String spotlightIdentifier) {
        this.spotlightIdentifier = spotlightIdentifier;
    }

    public String getUniversalLinkUrl() {
        return universalLinkUrl;
    }

    public void setUniversalLinkUrl(String universalLinkUrl) {
        this.universalLinkUrl = universalLinkUrl;
    }

    public String getLatVal() {
        return latVal;
    }

    public void setLatVal(String latVal) {
        this.latVal = latVal;
    }
}
