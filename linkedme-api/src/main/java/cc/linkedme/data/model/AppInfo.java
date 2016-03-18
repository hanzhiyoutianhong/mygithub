package cc.linkedme.data.model;

import cc.linkedme.commons.json.JsonBuilder;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppInfo {
    private long appid;
    private String appName;
    private long userId;
    private String uriScheme;
    private String pathPrefix;
    private String appLiveKey;
    private String appTestKey;
    private String androidNotUrl;
    private String packageName;
    private String androidCustomUrl;
    private String iosStoreUrl;
    private String iosCustomUrl;
    private String iosNotUrl;
    private String iosBundleId;
    private String iosTeamId;
    private String desktopUrl;

    public String toJson() {
        JsonBuilder jb = new JsonBuilder();
        jb.append("appid", appid);
        jb.append("app_name", appName);
        jb.append("user_id", userId);

        if (uriScheme != null) {
            jb.append("uri_scheme", uriScheme);
        }
        if (pathPrefix != null) {

            jb.append("path_prefix", pathPrefix);
        }
        if (appLiveKey != null) {

            jb.append("app_live_key", appLiveKey);
        }
        if (appTestKey != null) {
            jb.append("app_test_key", appTestKey);
        }
        if (androidNotUrl != null) {

            jb.append("android_not_url", androidNotUrl);
        }
        if (packageName != null) {

            jb.append("package_name", packageName);
        }
        if (androidCustomUrl != null) {

            jb.append("android_custom_url", androidCustomUrl);
        }
        if (iosStoreUrl != null) {

            jb.append("ios_store_url", iosStoreUrl);
        }
        if (iosCustomUrl != null) {

            jb.append("ios_custom_url", iosCustomUrl);
        }
        if (iosNotUrl != null) {
            jb.append("ios_not_url", iosNotUrl);
        }
        if (iosBundleId != null) {

            jb.append("ios_bundle_id", iosBundleId);
        }
        if (iosTeamId != null) {

            jb.append("ios_team_id", iosTeamId);
        }
        if (desktopUrl != null) {

            jb.append("desktop_url", desktopUrl);
        }
        return jb.flip().toString();
    }

    public String getUriScheme() {
        return uriScheme;
    }

    public void setUriScheme(String uriScheme) {
        this.uriScheme = uriScheme;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public String getAppLiveKey() {
        return appLiveKey;
    }

    public void setAppLiveKey(String appLiveKey) {
        this.appLiveKey = appLiveKey;
    }

    public String getAppTestKey() {
        return appTestKey;
    }

    public void setAppTestKey(String appTestKey) {
        this.appTestKey = appTestKey;
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

    public String getAndroidCustomUrl() {
        return androidCustomUrl;
    }

    public void setAndroidCustomUrl(String androidCustomUrl) {
        this.androidCustomUrl = androidCustomUrl;
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

    public String getDesktopUrl() {
        return desktopUrl;
    }

    public void setDesktopUrl(String desktopUrl) {
        this.desktopUrl = desktopUrl;
    }

    public long getAppid() {
        return appid;
    }

    public void setAppid(long appid) {
        this.appid = appid;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

}
