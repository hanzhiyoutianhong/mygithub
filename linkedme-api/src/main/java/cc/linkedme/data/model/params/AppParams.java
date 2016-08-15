package cc.linkedme.data.model.params;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import net.sf.json.JSONObject;


/**
 * Created by LinkedME01 on 16/3/18.
 */
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppParams {

    public long app_id;
    public long user_id;
    public String app_name;
    public String type;
    public String[] value;
    public String lkme_key;
    public String lkme_secret;
    public JSONObject link_setting;

    public boolean has_ios;
    public String ios_uri_scheme;
    public String ios_not_url;
    public String ios_search_option;
    public String ios_store_url;
    public String ios_custom_url;
    public boolean ios_enable_ulink;
    public String ios_bundle_id;
    public String ios_app_prefix;

    public boolean has_android;
    public String android_uri_scheme;
    public String android_not_url;
    public String android_search_option;
    public String google_play_url;
    public String android_custom_url;
    public String android_package_name;
    public boolean android_enable_applinks;
    public String android_sha256_fingerprints;

    public boolean use_default_landing_page = true;
    public boolean is_yyb_available;
    public String custom_landing_page;

    public int ios_android_flag;

    public String img_data;
    public String img_encoding;

    public String app_logo;

    public String live_test_flag;

    public String trackId;

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

    public String[] getValue() {
        return value;
    }

    public void setValue(String[] value) {
        this.value = value;
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

    public JSONObject getLink_setting() {
        return link_setting;
    }

    public void setLink_setting(JSONObject link_setting) {
        this.link_setting = link_setting;
    }

    public boolean isHas_ios() {
        return has_ios;
    }

    public void setHas_ios(boolean has_ios) {
        this.has_ios = has_ios;
    }

    public String getIos_uri_scheme() {
        return ios_uri_scheme;
    }

    public void setIos_uri_scheme(String ios_uri_scheme) {
        this.ios_uri_scheme = ios_uri_scheme;
    }

    public String getIos_not_url() {
        return ios_not_url;
    }

    public void setIos_not_url(String ios_not_url) {
        this.ios_not_url = ios_not_url;
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

    public boolean isIos_enable_ulink() {
        return ios_enable_ulink;
    }

    public void setIos_enable_ulink(boolean ios_enable_ulink) {
        this.ios_enable_ulink = ios_enable_ulink;
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

    public boolean isHas_android() {
        return has_android;
    }

    public void setHas_android(boolean has_android) {
        this.has_android = has_android;
    }

    public String getAndroid_uri_scheme() {
        return android_uri_scheme;
    }

    public void setAndroid_uri_scheme(String android_uri_scheme) {
        this.android_uri_scheme = android_uri_scheme;
    }

    public String getAndroid_not_url() {
        return android_not_url;
    }

    public void setAndroid_not_url(String android_not_url) {
        this.android_not_url = android_not_url;
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

    public boolean isAndroid_enable_applinks() {
        return android_enable_applinks;
    }

    public void setAndroid_enable_applinks(boolean android_enable_applinks) {
        this.android_enable_applinks = android_enable_applinks;
    }

    public String getAndroid_sha256_fingerprints() {
        return android_sha256_fingerprints;
    }

    public void setAndroid_sha256_fingerprints(String android_sha256_fingerprints) {
        this.android_sha256_fingerprints = android_sha256_fingerprints;
    }

    public boolean isUse_default_landing_page() {
        return use_default_landing_page;
    }

    public void setUse_default_landing_page(boolean use_default_landing_page) {
        this.use_default_landing_page = use_default_landing_page;
    }

    public boolean is_yyb_available() {
        return is_yyb_available;
    }

    public void setIs_yyb_available(boolean is_yyb_available) {
        this.is_yyb_available = is_yyb_available;
    }

    public String getCustom_landing_page() {
        return custom_landing_page;
    }

    public void setCustom_landing_page(String custom_landing_page) {
        this.custom_landing_page = custom_landing_page;
    }

    public int getIos_android_flag() {
        return ios_android_flag;
    }

    public void setIos_android_flag(int ios_android_flag) {
        this.ios_android_flag = ios_android_flag;
    }

    public String getImg_data() {
        return img_data;
    }

    public void setImg_data(String img_data) {
        this.img_data = img_data;
    }

    public String getImg_encoding() {
        return img_encoding;
    }

    public void setImg_encoding(String img_encoding) {
        this.img_encoding = img_encoding;
    }

    public String getApp_logo() {
        return app_logo;
    }

    public void setApp_logo(String app_logo) {
        this.app_logo = app_logo;
    }

    public String getLive_test_flag() {
        return live_test_flag;
    }

    public void setLive_test_flag(String live_test_flag) {
        this.live_test_flag = live_test_flag;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

}
