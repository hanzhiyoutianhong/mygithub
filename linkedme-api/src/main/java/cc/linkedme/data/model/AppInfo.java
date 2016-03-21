package cc.linkedme.data.model;

import cc.linkedme.commons.json.JsonBuilder;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppInfo {
    private long appId;
    private long userId;
    private String appName;
    private String appLiveKey;
    private String appTestKey;

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

    public String getAppTestKey() {
        return appTestKey;
    }

    public void setAppTestKey(String appTestKey) {
        this.appTestKey = appTestKey;
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







    public String toJson() {
        JsonBuilder jb = new JsonBuilder();
        jb.append("app_id", appId);
        jb.append("user_id", userId);
        jb.append("app_name", appName);

        if( appLiveKey != null )
        {
            jb.append( "app_live_key", appLiveKey );
        }

        if( appTestKey != null )
        {
            jb.append( "app_test_key", appTestKey );
        }

        if( iosUriScheme != null )
        {
            jb.append( "ios_uri_scheme", iosUriScheme );
        }

        if( iosNotUrl != null )
        {
            jb.append( "ios_not_url", iosNotUrl );
        }

        if( iosStoreUrl != null )
        {
            jb.append( "ios_store_url", iosStoreUrl );
        }

        if( iosCustomUrl != null )
        {
            jb.append( "ios_custom_url", iosCustomUrl );
        }

        if( iosBundleId != null )
        {
            jb.append( "ios_bundle_id", iosBundleId );
        }

        if( iosPrefix != null )
        {
            jb.append( "ios_prefix", iosPrefix );
        }

        if( iosTeamId != null )
        {
            jb.append( "ios_team_id", iosTeamId );
        }

        if( androidUriScheme != null )
        {
            jb.append( "android_uri_scheme", androidUriScheme );
        }

        if( androidNotUrl != null )
        {
            jb.append( "android_not_url", androidNotUrl );
        }

        if( googlePlayUrl != null )
        {
            jb.append( "google_play_url", googlePlayUrl );
        }

        if( androidCustomUrl != null )
        {
            jb.append( "android_custom_url", androidCustomUrl );
        }

        if( androidPackageName != null )
        {
            jb.append( "android_package_name", androidPackageName );
        }

        if( androidPrefix != null )
        {
            jb.append( "android_prefix", androidPrefix );
        }

        int has_ios = ( iosAndroidFlag & 8 ) >> 3;
        int enable_ulink = ( iosAndroidFlag & 4 ) >> 2;
        int has_android = ( iosAndroidFlag & 2 ) >> 1;
        int enable_applinks = iosAndroidFlag & 1;
        jb.append( "has_ios", has_ios );
        jb.append( "enable_ulink", enable_ulink );
        jb.append( "has_android", has_android );
        jb.append( "enable_applinks", enable_applinks );

        if( desktopUrl != null )
        {
            jb.append( "desktop_url", desktopUrl );
        }

        return jb.flip().toString();
    }

}

