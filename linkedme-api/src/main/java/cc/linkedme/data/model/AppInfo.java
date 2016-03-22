package cc.linkedme.data.model;

import cc.linkedme.commons.json.JsonBuilder;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppInfo {
    private long appId;
    private long userId;
    private String appName;
    private String appLiveKey;
    private String appLiveSecret;
    private String appTestKey;
    private String appTestSecret;

    private String iosUriScheme;
    private String iosNotUrl;
    private String iosStoreUrl;
    private String iosCustomUrl;
    private String iosBundleId;
    private String iosPrefix;
    private String iosTeamId;

    private String androidUriScheme;
    private String androidNotUrl;
    private String googlePlayUrl;
    private String androidCustomUrl;
    private String androidPackageName;
    private String androidPrefix;
    private int iosAndroidFlag;

    private String desktopUrl;

    public void setAppId( long appId )
    {
        this.appId = appId;
    }

    public long getAppId()
    {
        return appId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppLiveKey() {
        return appLiveKey;
    }

    public void setAppLiveKey(String appLiveKey) {
        this.appLiveKey = appLiveKey;
    }

    public void setAppLiveSecret( String appLiveSecret )
    {
        this.appLiveSecret = appLiveSecret;
    }

    public String getAppLiveSecret()
    {
        return appLiveSecret;
    }

    public String getAppTestKey() {
        return appTestKey;
    }

    public void setAppTestKey(String appTestKey) {
        this.appTestKey = appTestKey;
    }

    public void setAppTestSecret( String appTestSecret )
    {
        this.appTestSecret = appTestSecret;
    }

    public String getAppTestSecret()
    {
        return appTestSecret;
    }

    public void setIosUriScheme( String iosUriScheme )
    {
        this.iosUriScheme = iosUriScheme;
    }

    public String getIosUriScheme()
    {
        return iosUriScheme;
    }

    public String getIosNotUrl() {
        return iosNotUrl;
    }

    public void setIosNotUrl(String iosNotUrl) {
        this.iosNotUrl = iosNotUrl;
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

    public String getIosBundleId() {
        return iosBundleId;
    }

    public void setIosBundleId(String iosBundleId) {
        this.iosBundleId = iosBundleId;
    }

    public void setIosPrefix( String iosPrefix )
    {
        this.iosPrefix = iosPrefix;
    }

    public String getIosPrefix()
    {
        return iosPrefix;
    }

    public String getIosTeamId() {
        return iosTeamId;
    }

    public void setIosTeamId(String iosTeamId) {
        this.iosTeamId = iosTeamId;
    }

    public void setAndroidUriScheme( String androidUriScheme )
    {
        this.androidUriScheme = androidUriScheme;
    }

    public String getAndroidUriScheme()
    {
        return androidUriScheme;
    }

    public String getAndroidNotUrl() {
        return androidNotUrl;
    }

    public void setAndroidNotUrl(String androidNotUrl) {
        this.androidNotUrl = androidNotUrl;
    }

    public void setGooglePlayUrl( String googlePlayUrl )
    {
        this.googlePlayUrl = googlePlayUrl;
    }

    public String getGooglePlayUrl()
    {
        return googlePlayUrl;
    }

    public String getAndroidCustomUrl() {
        return androidCustomUrl;
    }

    public void setAndroidCustomUrl(String androidCustomUrl) {
        this.androidCustomUrl = androidCustomUrl;
    }

    public void setAndroidPackageName( String androidPackageName )
    {
        this.androidPackageName = androidPackageName;
    }

    public String getAndroidPackageName()
    {
        return androidPackageName;
    }

    public void setAndroidPrefix( String androidPrefix )
    {
        this.androidPrefix = androidPrefix;
    }

    public String getAndroidPrefix()
    {
        return androidPrefix;
    }

    public void setIosAndroidFlag( int iosAndroidFlag )
    {
        this.iosAndroidFlag = iosAndroidFlag;
    }

    public int getIosAndroidFlag()
    {
        return iosAndroidFlag;
    }

    public String getDesktopUrl() {
        return desktopUrl;
    }

    public void setDesktopUrl(String desktopUrl) {
        this.desktopUrl = desktopUrl;
    }

    public String toJson()
    {
        int has_ios = ( iosAndroidFlag & 8 ) >> 3;
        int enable_ulink = ( iosAndroidFlag & 4 ) >> 2;
        int has_android = ( iosAndroidFlag & 2 ) >> 1;
        int enable_applinks = iosAndroidFlag & 1;

        JSONObject ios = new JSONObject();
        ios.put( "has_ios", has_ios );
        ios.put( "uri_scheme", iosUriScheme );
        ios.put( "search_option", iosNotUrl );
        ios.put( "apple_store_url", iosStoreUrl );
        ios.put( "custom_url", iosCustomUrl );
        ios.put( "enable_ulink", enable_ulink );
        ios.put( "bundle_id", iosBundleId );
        ios.put( "app_prefix", iosPrefix );

        JSONObject android = new JSONObject();
        android.put( "has_android", has_android );
        android.put( "uri_scheme", androidUriScheme );
        android.put( "search_option", androidNotUrl );
        android.put( "google_play_url", googlePlayUrl );
        android.put( "custom_apk_url", androidCustomUrl );
        android.put( "package_name", androidPackageName );
        android.put( "enable_applinks", enable_applinks );
        android.put( "sha256_fingerprints", androidPrefix );

        JSONObject desktop = new JSONObject();
        desktop.put( "qc_code", desktopUrl );


        JSONObject link_setting = new JSONObject();
        link_setting.put( "ios", ios );
        link_setting.put( "android", android );
        link_setting.put( "desktop", desktop );



        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "app_name",appName );
        jsonObject.put( "lkme_test_key", appTestKey );
        jsonObject.put( "lkme_test_secret", appTestSecret );
        jsonObject.put( "lkme_live_key", appLiveKey );
        jsonObject.put( "lkme_live_secret", appLiveSecret );
        jsonObject.put( "link_setting", link_setting );

        return jsonObject.toString();
    }

}

