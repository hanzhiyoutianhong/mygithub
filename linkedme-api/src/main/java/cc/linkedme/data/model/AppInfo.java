package cc.linkedme.data.model;

import cc.linkedme.commons.json.JsonBuilder;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

import java.io.Serializable;


/**
 * Created by LinkedME01 on 16/3/18.
 */
public class AppInfo implements Serializable {
    private long app_id;
    private long user_id;
    private String app_name;
    private String app_logo;
    private String type;
    private String creation_time;

    private String app_key;
    private String app_secret;

    private String ios_uri_scheme;
    private String ios_not_url;
    private String ios_store_url;
    private String ios_custom_url;
    private String ios_app_prefix;
    private String ios_search_option;
    private String ios_bundle_id;
    private String ios_team_id;

    private String android_uri_scheme;
    private String android_not_url;
    private String google_play_url;
    private String android_custom_url;
    private String android_search_option;
    private String android_package_name;
    private String android_sha256_fingerprints;
    private int ios_android_flag;

    private boolean use_default_landing_page;
    private String custom_landing_page;

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

    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getApp_secret() {
        return app_secret;
    }

    public void setApp_secret(String app_secret) {
        this.app_secret = app_secret;
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

    public void setGoogle_play_url(String google_play_url) {
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

    public int getIos_android_flag() {
        return ios_android_flag;
    }

    public void setIos_android_flag(int ios_android_flag) {
        this.ios_android_flag = ios_android_flag;
    }

    public String getIos_not_url() {
        return ios_not_url;
    }

    public void setIos_not_url(String ios_not_url) {
        this.ios_not_url = ios_not_url;
    }

    public String getAndroid_not_url() {
        return android_not_url;
    }

    public void setAndroid_not_url(String android_not_url) {
        this.android_not_url = android_not_url;
    }

    public String getIos_team_id() {
        return ios_team_id;
    }

    public void setIos_team_id(String ios_team_id) {
        this.ios_team_id = ios_team_id;
    }

    public boolean isUse_default_landing_page() {
        return use_default_landing_page;
    }

    public void setUse_default_landing_page(boolean use_default_landing_page) {
        this.use_default_landing_page = use_default_landing_page;
    }

    public String getCustom_landing_page() {
        return custom_landing_page;
    }

    public void setCustom_landing_page(String custom_landing_page) {
        this.custom_landing_page = custom_landing_page;
    }

    public boolean hasIos() {
        return ((ios_android_flag & 8) >> 3) == 1;
    }

    public boolean iosUniverseLinkEnable() {
        return ((ios_android_flag & 4) >> 2) == 1;
    }

    public boolean hasAndroid() {
        return ((ios_android_flag & 2) >> 1) == 1;
    }

    public boolean appLinksEnable() {
        return (ios_android_flag & 1) == 1;
    }

    public String toJson() {
        boolean is_yyb_available = ((ios_android_flag & 16) >> 4) == 1;
        boolean has_ios = ((ios_android_flag & 8) >> 3) == 1;
        boolean ios_enable_ulink = ((ios_android_flag & 4) >> 2) == 1;
        boolean has_android = ((ios_android_flag & 2) >> 1) == 1;
        boolean android_enable_applinks = (ios_android_flag & 1) == 1;

        JsonBuilder ios = new JsonBuilder();
        ios.append("has_ios", String.valueOf(has_ios));
        ios.append("ios_not_url", ios_not_url);
        ios.append("ios_uri_scheme", ios_uri_scheme);
        ios.append("ios_search_option", ios_search_option);
        ios.append("ios_store_url", ios_store_url);
        ios.append("ios_custom_url", ios_custom_url);
        ios.append("ios_enable_ulink", String.valueOf(ios_enable_ulink));
        ios.append("ios_bundle_id", ios_bundle_id);
        ios.append("ios_app_prefix", ios_app_prefix);

        JsonBuilder android = new JsonBuilder();
        android.append("has_android", String.valueOf(has_android));
        android.append("is_yyb_available", String.valueOf(is_yyb_available));
        android.append("android_not_url", android_not_url);
        android.append("android_uri_scheme", android_uri_scheme);
        android.append("android_search_option", android_search_option);
        android.append("google_play_url", google_play_url);
        android.append("android_custom_url", android_custom_url);
        android.append("android_package_name", android_package_name);
        android.append("android_enable_applinks", String.valueOf(android_enable_applinks));
        android.append("android_sha256_fingerprints", android_sha256_fingerprints);

        JsonBuilder desktop = new JsonBuilder();
        desktop.append("use_default_landing_page", String.valueOf(use_default_landing_page));
        desktop.append("custom_landing_page", custom_landing_page);

        JsonBuilder link_setting = new JsonBuilder();
        link_setting.append("ios", ios.flip());
        link_setting.append("android", android.flip());
        link_setting.append("desktop", desktop.flip());

        JsonBuilder resultJson = new JsonBuilder();
        resultJson.append("app_id", app_id);
        resultJson.append("app_identifier", Base62.encode(app_id));
        resultJson.append("app_name", app_name);
        resultJson.append("app_logo", Constants.DASHBOARD_API_URL + "/app/images/" + app_id + Constants.APP_LOGO_IMG_TYPE);
        resultJson.append("lkme_key", app_key);
        resultJson.append("lkme_secret", app_secret);
        resultJson.append("link_setting", link_setting.flip());
        resultJson.append("creation_time", creation_time);

        return resultJson.flip().toString();
    }

}
