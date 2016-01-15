package cc.linkedme.model;

/**
 * Created by qipo on 15/11/25.
 */

public class LMClientOpenEntity {
    private int id;
    private String identityId;
    private String appKey;
    private String deviceFingerprintId;
    private String appVersion;
    private Byte adTrackingEnabled;
    private Byte isReferrable;
    private String os;
    private String osVersion;
    private String updation;
    private String sdk;
    private String uriScheme;
    private String spotlightIdentifier;
    private String universalLinkUrl;
    private String linkIdentifier;
    private Byte latVal;
    private Byte debug;
    private String sessionId;
    private String ip;
    private Integer retryNumber;
    private Integer timestamp;
    private String googleAdvertsId;
    private String osRelease;
    private String iosBundleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getDeviceFingerprintId() {
        return deviceFingerprintId;
    }

    public void setDeviceFingerprintId(String deviceFingerprintId) {
        this.deviceFingerprintId = deviceFingerprintId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Byte getAdTrackingEnabled() {
        return adTrackingEnabled;
    }

    public void setAdTrackingEnabled(Byte adTrackingEnabled) {
        this.adTrackingEnabled = adTrackingEnabled;
    }

    public Byte getIsReferrable() {
        return isReferrable;
    }

    public void setIsReferrable(Byte isReferrable) {
        this.isReferrable = isReferrable;
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

    public String getUpdation() {
        return updation;
    }

    public void setUpdation(String updation) {
        this.updation = updation;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getUriScheme() {
        return uriScheme;
    }

    public void setUriScheme(String uriScheme) {
        this.uriScheme = uriScheme;
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

    public String getLinkIdentifier() {
        return linkIdentifier;
    }

    public void setLinkIdentifier(String linkIdentifier) {
        this.linkIdentifier = linkIdentifier;
    }

    public Byte getLatVal() {
        return latVal;
    }

    public void setLatVal(Byte latVal) {
        this.latVal = latVal;
    }

    public Byte getDebug() {
        return debug;
    }

    public void setDebug(Byte debug) {
        this.debug = debug;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getRetryNumber() {
        return retryNumber;
    }

    public void setRetryNumber(Integer retryNumber) {
        this.retryNumber = retryNumber;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    public String getGoogleAdvertsId() {
        return googleAdvertsId;
    }

    public void setGoogleAdvertsId(String googleAdvertsId) {
        this.googleAdvertsId = googleAdvertsId;
    }

    public String getOsRelease() {
        return osRelease;
    }

    public void setOsRelease(String osRelease) {
        this.osRelease = osRelease;
    }

    public String getIosBundleId() {
        return iosBundleId;
    }

    public void setIosBundleId(String iosBundleId) {
        this.iosBundleId = iosBundleId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LMClientOpenEntity that = (LMClientOpenEntity) o;

        if (id != that.id) return false;
        if (identityId != null ? !identityId.equals(that.identityId) : that.identityId != null) return false;
        if (appKey != null ? !appKey.equals(that.appKey) : that.appKey != null) return false;
        if (deviceFingerprintId != null ? !deviceFingerprintId.equals(that.deviceFingerprintId) : that.deviceFingerprintId != null)
            return false;
        if (appVersion != null ? !appVersion.equals(that.appVersion) : that.appVersion != null) return false;
        if (adTrackingEnabled != null ? !adTrackingEnabled.equals(that.adTrackingEnabled) : that.adTrackingEnabled != null)
            return false;
        if (isReferrable != null ? !isReferrable.equals(that.isReferrable) : that.isReferrable != null) return false;
        if (os != null ? !os.equals(that.os) : that.os != null) return false;
        if (osVersion != null ? !osVersion.equals(that.osVersion) : that.osVersion != null) return false;
        if (updation != null ? !updation.equals(that.updation) : that.updation != null) return false;
        if (sdk != null ? !sdk.equals(that.sdk) : that.sdk != null) return false;
        if (uriScheme != null ? !uriScheme.equals(that.uriScheme) : that.uriScheme != null) return false;
        if (spotlightIdentifier != null ? !spotlightIdentifier.equals(that.spotlightIdentifier) : that.spotlightIdentifier != null)
            return false;
        if (universalLinkUrl != null ? !universalLinkUrl.equals(that.universalLinkUrl) : that.universalLinkUrl != null)
            return false;
        if (linkIdentifier != null ? !linkIdentifier.equals(that.linkIdentifier) : that.linkIdentifier != null)
            return false;
        if (latVal != null ? !latVal.equals(that.latVal) : that.latVal != null) return false;
        if (debug != null ? !debug.equals(that.debug) : that.debug != null) return false;
        if (sessionId != null ? !sessionId.equals(that.sessionId) : that.sessionId != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (retryNumber != null ? !retryNumber.equals(that.retryNumber) : that.retryNumber != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (identityId != null ? identityId.hashCode() : 0);
        result = 31 * result + (appKey != null ? appKey.hashCode() : 0);
        result = 31 * result + (deviceFingerprintId != null ? deviceFingerprintId.hashCode() : 0);
        result = 31 * result + (appVersion != null ? appVersion.hashCode() : 0);
        result = 31 * result + (adTrackingEnabled != null ? adTrackingEnabled.hashCode() : 0);
        result = 31 * result + (isReferrable != null ? isReferrable.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
        result = 31 * result + (updation != null ? updation.hashCode() : 0);
        result = 31 * result + (sdk != null ? sdk.hashCode() : 0);
        result = 31 * result + (uriScheme != null ? uriScheme.hashCode() : 0);
        result = 31 * result + (spotlightIdentifier != null ? spotlightIdentifier.hashCode() : 0);
        result = 31 * result + (universalLinkUrl != null ? universalLinkUrl.hashCode() : 0);
        result = 31 * result + (linkIdentifier != null ? linkIdentifier.hashCode() : 0);
        result = 31 * result + (latVal != null ? latVal.hashCode() : 0);
        result = 31 * result + (debug != null ? debug.hashCode() : 0);
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (retryNumber != null ? retryNumber.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }


}
