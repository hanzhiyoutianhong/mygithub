package cc.linkedme.data.model;

import cc.linkedme.commons.json.JsonBuilder;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppInfo {
    private long app_id;
    private long user_id;
    private String app_name;
    private String type;

    private String lkme_key;
    private String lkme_secret;

    private String ios_uri_scheme;
    private String ios_search_option;
    private String ios_store_url;
    private String ios_custom_url;
    private String ios_bundle_id;
    private String ios_app_prefix;

    private String android_uri_scheme;
    private String android_search_option;
    private String google_play_url;
    private String android_custom_url;
    private String android_package_name;
    private String android_sha256_fingerprints;
    private String qr_code;

    private String register_time;
    private String last_update_time;

    private int iosAndroidFlag;

    public long getApp_id() {
        return app_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLkme_key() {
        return lkme_key;
    }

    public void setLkme_key(String lkme_key) {
        this.lkme_key = lkme_key;
    }

    public String getLkme_secret() {
        return lkme_secret;
    }

    public void setLkme_secret(String lkme_secret) {
        this.lkme_secret = lkme_secret;
    }

    public String getIos_uri_scheme() {
        return ios_uri_scheme;
    }

    public void setIos_uri_scheme(String ios_uri_scheme) {
        this.ios_uri_scheme = ios_uri_scheme;
    }

    public String getIos_search_option() {
        return ios_search_option;
    }

    public void setIos_search_option(String ios_search_option) {
        this.ios_search_option = ios_search_option;
    }

    public String getIos_store_url() {
        return ios_store_url;
    }

    public void setIos_store_url(String ios_store_url) {
        this.ios_store_url = ios_store_url;
    }

    public String getIos_custom_url() {
        return ios_custom_url;
    }

    public void setIos_custom_url(String ios_custom_url) {
        this.ios_custom_url = ios_custom_url;
    }

    public String getIos_bundle_id() {
        return ios_bundle_id;
    }

    public void setIos_bundle_id(String ios_bundle_id) {
        this.ios_bundle_id = ios_bundle_id;
    }

    public String getIos_app_prefix() {
        return ios_app_prefix;
    }

    public void setIos_app_prefix(String ios_app_prefix) {
        this.ios_app_prefix = ios_app_prefix;
    }

    public String getAndroid_uri_scheme() {
        return android_uri_scheme;
    }

    public void setAndroid_uri_scheme(String android_uri_scheme) {
        this.android_uri_scheme = android_uri_scheme;
    }

    public String getAndroid_search_option() {
        return android_search_option;
    }

    public void setAndroid_search_option(String android_search_option) {
        this.android_search_option = android_search_option;
    }

    public String getGoogle_play_url() {
        return google_play_url;
    }

    public void setGoogle_play_search(String google_play_url) {
        this.google_play_url = google_play_url;
    }

    public String getAndroid_custom_url() {
        return android_custom_url;
    }

    public void setAndroid_custom_url(String android_custom_url) {
        this.android_custom_url = android_custom_url;
    }

    public String getAndroid_package_name() {
        return android_package_name;
    }

    public void setAndroid_package_name(String android_package_name) {
        this.android_package_name = android_package_name;
    }

    public String getAndroid_sha256_fingerprints() {
        return android_sha256_fingerprints;
    }

    public void setAndroid_sha256_fingerprints(String android_sha256_fingerprints) {
        this.android_sha256_fingerprints = android_sha256_fingerprints;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public int getIosAndroidFlag() {
        return iosAndroidFlag;
    }

    public void setIosAndroidFlag(int iosAndroidFlag) {
        this.iosAndroidFlag = iosAndroidFlag;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getLast_update_time() {
        return last_update_time;
    }

    public void setLast_update_time(String last_update_time) {
        this.last_update_time = last_update_time;
    }

    public String toJson()
    {
        boolean has_ios = ( ( iosAndroidFlag & 8 ) >> 3 == 1 ) ? true : false;
        boolean ios_enable_ulink = ( ( iosAndroidFlag & 4 ) >> 2 == 1 ) ? true : false;
        boolean has_android = ( ( iosAndroidFlag & 2 ) >> 1 == 1 ) ? true : false;
        boolean android_enable_applinks = ( iosAndroidFlag & 1 ) == 1 ? true : false;

        JSONObject ios = new JSONObject();
        ios.put( "has_ios", has_ios );
        ios.put( "ios_uri_scheme", ios_uri_scheme );
        ios.put( "ios_search_option", ios_search_option );
        ios.put( "ios_store_url", ios_store_url );
        ios.put( "ios_custom_url", ios_custom_url );
        ios.put( "ios_enable_ulink", ios_enable_ulink );
        ios.put( "ios_bundle_id", ios_bundle_id );
        ios.put( "ios_app_prefix", ios_app_prefix );

        JSONObject android = new JSONObject();
        android.put( "has_android", has_android );
        android.put( "android_uri_scheme", android_uri_scheme );
        android.put( "android_search_option", android_search_option );
        android.put( "google_play_url", google_play_url );
        android.put( "android_custom_url", android_custom_url );
        android.put( "android_package_name", android_package_name );
        android.put( "android_enable_applinks", android_enable_applinks );
        android.put( "android_sha256_fingerprints", android_sha256_fingerprints );

        JSONObject desktop = new JSONObject();
        desktop.put( "qr_code", qr_code );


        JSONObject link_setting = new JSONObject();
        link_setting.put( "ios", ios );
        link_setting.put( "android", android );
        link_setting.put( "desktop", desktop );

        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "app_name",app_name );
        jsonObject.put( "type", type );
        jsonObject.put( "lkme_key", lkme_key );
        jsonObject.put( "lkme_secret", lkme_secret );
        jsonObject.put( "link_setting", link_setting );

        return jsonObject.toString();
    }

}

