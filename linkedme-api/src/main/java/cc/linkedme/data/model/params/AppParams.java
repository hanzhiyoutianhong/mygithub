package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by LinkedME01 on 16/3/18.
 */

@XmlRootElement
public class AppParams{

    public long app_id;
    public long user_id;
    public String app_name;

    public String lkme_live_key;
    public String lkme_live_secret;
    public String lkme_test_key;
    public String lkme_test_secret;

    public String ios_app_prefix;
    public String ios_uri_scheme;
    public String ios_search_option;
    public String ios_store_url;
    public String ios_custom_url;
    public String ios_buddle_id;
    public String ios_team_id;
    public boolean has_ios;
    public boolean enable_ulink;

    public String android_uri_scheme;
    public String android_search_option;
    public String google_play_search;
    public String android_custom_url;
    public String android_package_name;
    public String android_prefix;
    public boolean has_android;
    public boolean enable_applinks;

    public String sha256_fingerprints;

    public int iosAndroidFlag;

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

    public String getLkme_live_key() {
        return lkme_live_key;
    }

    public void setLkme_live_key(String lkme_live_key) {
        this.lkme_live_key = lkme_live_key;
    }

    public String getLkme_live_secret() {
        return lkme_live_secret;
    }

    public void setLkme_live_secret(String lkme_live_secret) {
        this.lkme_live_secret = lkme_live_secret;
    }

    public String getLkme_test_key() {
        return lkme_test_key;
    }

    public void setLkme_test_key(String lkme_test_key) {
        this.lkme_test_key = lkme_test_key;
    }

    public String getLkme_test_secret() {
        return lkme_test_secret;
    }

    public void setLkme_test_secret(String lkme_test_secret) {
        this.lkme_test_secret = lkme_test_secret;
    }

    public String getIos_app_prefix() {
        return ios_app_prefix;
    }

    public void setIos_app_prefix(String ios_app_prefix) {
        this.ios_app_prefix = ios_app_prefix;
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

    public String getIos_buddle_id() {
        return ios_buddle_id;
    }

    public void setIos_buddle_id(String ios_buddle_id) {
        this.ios_buddle_id = ios_buddle_id;
    }

    public String getIos_team_id() {
        return ios_team_id;
    }

    public void setIos_team_id(String ios_team_id) {
        this.ios_team_id = ios_team_id;
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

    public String getGoogle_play_search() {
        return google_play_search;
    }

    public void setGoogle_play_search(String google_play_search) {
        this.google_play_search = google_play_search;
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

    public String getAndroid_prefix() {
        return android_prefix;
    }

    public void setAndroid_prefix(String android_prefix) {
        this.android_prefix = android_prefix;
    }

    public String getSha256_fingerprints() {
        return sha256_fingerprints;
    }

    public void setSha256_fingerprints(String sha256_fingerprints) {
        this.sha256_fingerprints = sha256_fingerprints;
    }

    public int getIosAndroidFlag() {
        return iosAndroidFlag;
    }

    public void setIosAndroidFlag(int iosAndroidFlag) {
        this.iosAndroidFlag = iosAndroidFlag;
    }

}
