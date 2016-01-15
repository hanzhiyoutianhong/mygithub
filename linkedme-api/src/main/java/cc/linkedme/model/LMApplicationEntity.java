package cc.linkedme.model;

/**
 * Created by qipo on 15/11/24.
 */
public class LMApplicationEntity {
    private int id;
    private String appName;
    private String appKeyLive;
    private String appSecretLive;
    private String appKeyTest;
    private String appSecretTest;
    private int accountId;
    private String urlScheme;
    private String iosStoreUrl;
    private String iosCustomUrl;
    private String iosNotUrl;
    private String iosBundleId;
    private String iosTeamId;
    private String adroidCustomUrl;
    private String androidNotUrl;
    private String packageName;
    private String desktopUrl;
    private int timestamp;
    private String androidCustomUrl;
    private String appKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppKeyLive() {
        return appKeyLive;
    }

    public void setAppKeyLive(String appKeyLive) {
        this.appKeyLive = appKeyLive;
    }

    public String getAppSecretLive() {
        return appSecretLive;
    }

    public void setAppSecretLive(String appSecretLive) {
        this.appSecretLive = appSecretLive;
    }

    public String getAppKeyTest() {
        return appKeyTest;
    }

    public void setAppKeyTest(String appKeyTest) {
        this.appKeyTest = appKeyTest;
    }

    public String getAppSecretTest() {
        return appSecretTest;
    }

    public void setAppSecretTest(String appSecretTest) {
        this.appSecretTest = appSecretTest;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUrlScheme() {
        return urlScheme;
    }

    public void setUrlScheme(String urlScheme) {
        this.urlScheme = urlScheme;
    }

    public String getIosStoreUrl() {
        return iosStoreUrl;
    }

    public void setIosStoreUrl(String iosStoreUrl) {
        this.iosStoreUrl = iosStoreUrl;
    }

    public String getIosCustomUrl() {
        return iosCustomUrl;
    }

    public void setIosCustomUrl(String iosCustomUrl) {
        this.iosCustomUrl = iosCustomUrl;
    }

    public String getIosNotUrl() {
        return iosNotUrl;
    }

    public void setIosNotUrl(String iosNotUrl) {
        this.iosNotUrl = iosNotUrl;
    }

    public String getIosBundleId() {
        return iosBundleId;
    }

    public void setIosBundleId(String iosBundleId) {
        this.iosBundleId = iosBundleId;
    }

    public String getIosTeamId() {
        return iosTeamId;
    }

    public void setIosTeamId(String iosTeamId) {
        this.iosTeamId = iosTeamId;
    }

    public String getAdroidCustomUrl() {
        return adroidCustomUrl;
    }

    public void setAdroidCustomUrl(String adroidCustomUrl) {
        this.adroidCustomUrl = adroidCustomUrl;
    }

    public String getAndroidNotUrl() {
        return androidNotUrl;
    }

    public void setAndroidNotUrl(String androidNotUrl) {
        this.androidNotUrl = androidNotUrl;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDesktopUrl() {
        return desktopUrl;
    }

    public void setDesktopUrl(String desktopUrl) {
        this.desktopUrl = desktopUrl;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getAndroidCustomUrl() {
        return androidCustomUrl;
    }

    public void setAndroidCustomUrl(String androidCustomUrl) {
        this.androidCustomUrl = androidCustomUrl;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LMApplicationEntity that = (LMApplicationEntity) o;

        if (id != that.id) return false;
        if (accountId != that.accountId) return false;
        if (timestamp != that.timestamp) return false;
        if (appName != null ? !appName.equals(that.appName) : that.appName != null) return false;
        if (appKeyLive != null ? !appKeyLive.equals(that.appKeyLive) : that.appKeyLive != null) return false;
        if (appSecretLive != null ? !appSecretLive.equals(that.appSecretLive) : that.appSecretLive != null)
            return false;
        if (appKeyTest != null ? !appKeyTest.equals(that.appKeyTest) : that.appKeyTest != null) return false;
        if (appSecretTest != null ? !appSecretTest.equals(that.appSecretTest) : that.appSecretTest != null)
            return false;
        if (urlScheme != null ? !urlScheme.equals(that.urlScheme) : that.urlScheme != null) return false;
        if (iosStoreUrl != null ? !iosStoreUrl.equals(that.iosStoreUrl) : that.iosStoreUrl != null) return false;
        if (iosCustomUrl != null ? !iosCustomUrl.equals(that.iosCustomUrl) : that.iosCustomUrl != null) return false;
        if (iosNotUrl != null ? !iosNotUrl.equals(that.iosNotUrl) : that.iosNotUrl != null) return false;
        if (iosBundleId != null ? !iosBundleId.equals(that.iosBundleId) : that.iosBundleId != null) return false;
        if (iosTeamId != null ? !iosTeamId.equals(that.iosTeamId) : that.iosTeamId != null) return false;
        if (adroidCustomUrl != null ? !adroidCustomUrl.equals(that.adroidCustomUrl) : that.adroidCustomUrl != null)
            return false;
        if (androidNotUrl != null ? !androidNotUrl.equals(that.androidNotUrl) : that.androidNotUrl != null)
            return false;
        if (packageName != null ? !packageName.equals(that.packageName) : that.packageName != null) return false;
        if (desktopUrl != null ? !desktopUrl.equals(that.desktopUrl) : that.desktopUrl != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (appName != null ? appName.hashCode() : 0);
        result = 31 * result + (appKeyLive != null ? appKeyLive.hashCode() : 0);
        result = 31 * result + (appSecretLive != null ? appSecretLive.hashCode() : 0);
        result = 31 * result + (appKeyTest != null ? appKeyTest.hashCode() : 0);
        result = 31 * result + (appSecretTest != null ? appSecretTest.hashCode() : 0);
        result = 31 * result + accountId;
        result = 31 * result + (urlScheme != null ? urlScheme.hashCode() : 0);
        result = 31 * result + (iosStoreUrl != null ? iosStoreUrl.hashCode() : 0);
        result = 31 * result + (iosCustomUrl != null ? iosCustomUrl.hashCode() : 0);
        result = 31 * result + (iosNotUrl != null ? iosNotUrl.hashCode() : 0);
        result = 31 * result + (iosBundleId != null ? iosBundleId.hashCode() : 0);
        result = 31 * result + (iosTeamId != null ? iosTeamId.hashCode() : 0);
        result = 31 * result + (adroidCustomUrl != null ? adroidCustomUrl.hashCode() : 0);
        result = 31 * result + (androidNotUrl != null ? androidNotUrl.hashCode() : 0);
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (desktopUrl != null ? desktopUrl.hashCode() : 0);
        result = 31 * result + timestamp;
        return result;
    }

}
