package cc.linkedme.data.model;

import cc.linkedme.commons.json.JsonBuilder;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by LinkedME01 on 16/4/8.
 */
@Entity
@Table(name = "consumer_info_0", schema = "consumer_0", catalog = "")
public class ConsumerAppInfo {
    private long appId;
    private String appName;
    private String appLogoUrl;
    private String category;
    private String onlineTime;
    private String iosCode;
    private String androidCode;
    private String androidConfig;
    private String schemeUrl;
    private String customUrl;
    private String defaultUrl;
    private String bundleId;
    private String packageName;
    private String clientId;
    private String serverToken;
    private int status;

    @Id
    @Column(name = "app_id")
    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "app_name")
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Basic
    @Column(name = "app_logo_url")
    public String getAppLogoUrl() {
        return appLogoUrl;
    }

    public void setAppLogoUrl(String appLogoUrl) {
        this.appLogoUrl = appLogoUrl;
    }

    @Basic
    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "online_time")
    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    @Basic
    @Column(name = "ios_code")
    public String getIosCode() {
        return iosCode;
    }

    public void setIosCode(String iosCode) {
        this.iosCode = iosCode;
    }

    @Basic
    @Column(name = "android_code")
    public String getAndroidCode() {
        return androidCode;
    }

    public void setAndroidCode(String androidCode) {
        this.androidCode = androidCode;
    }

    @Basic
    @Column(name = "android_config")
    public String getAndroidConfig() {
        return androidConfig;
    }

    public void setAndroidConfig(String androidConfig) {
        this.androidConfig = androidConfig;
    }

    @Basic
    @Column(name = "scheme_url")
    public String getSchemeUrl() {
        return schemeUrl;
    }

    public void setSchemeUrl(String schemeUrl) {
        this.schemeUrl = schemeUrl;
    }

    @Basic
    @Column(name = "custom_url")
    public String getCustomUrl() {
        return customUrl;
    }

    public void setCustomUrl(String customUrl) {
        this.customUrl = customUrl;
    }

    @Basic
    @Column(name = "default_url")
    public String getDefaultUrl() {
        return defaultUrl;
    }

    public void setDefaultUrl(String defaultUrl) {
        this.defaultUrl = defaultUrl;
    }

    @Basic
    @Column(name = "bundle_id")
    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    @Basic
    @Column(name = "package_name")
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Basic
    @Column(name = "client_id")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Basic
    @Column(name = "server_token")
    public String getServerToken() {
        return serverToken;
    }

    public void setServerToken(String serverToken) {
        this.serverToken = serverToken;
    }

    @Basic
    @Column(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toJson() {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.append("consumer_app_id", appId);
        jsonBuilder.append("consumer_app_name", appName);
        jsonBuilder.append("consumer_app_logo_url", appLogoUrl);
        jsonBuilder.append("consumer_app_status", status);
        jsonBuilder.append("online_date", onlineTime);
        jsonBuilder.append("consumer_app_ios_code", iosCode);
        jsonBuilder.append("consumer_app_android_code", androidCode);
        jsonBuilder.append("consumer_app_android_config", androidConfig);
        return jsonBuilder.flip().toString();
    }

}
