package cc.linkedme.data.model.params;

import net.sf.json.JSONObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by LinkedME01 on 16/3/23.
 */


public class UrlParams extends BaseParams {
    public long deeplink_id;
    public long[] deeplink_ids;
    public long app_id;
    public int user_id;
    public String alias;
    public String[] tags;
    public String[] channel;
    public String[] feature;
    public String[] stage;
    public String[] campaign;
    public JSONObject params;
    public String deepLinkMd5;
    public String source;
    public String session_id;

    public String link_label;// 自定义短链名称，和域名 https://lkme.cc/链在一起构成短链主体,目前不支持，该值先忽略
    public boolean ios_use_default;
    public String ios_custom_url;
    public boolean android_use_default;
    public String android_custom_url;
    public boolean desktop_use_default;
    public String desktop_custom_url;


    public long getApp_id() {
        return app_id;
    }

    public void setApp_id(long app_id) {
        this.app_id = app_id;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String[] getChannel() {
        return channel;
    }

    public void setChannel(String[] channel) {
        this.channel = channel;
    }

    public String[] getFeature() {
        return feature;
    }

    public void setFeature(String[] feature) {
        this.feature = feature;
    }

    public String[] getStage() {
        return stage;
    }

    public void setStage(String[] stage) {
        this.stage = stage;
    }

    public String[] getCampaign() {
        return campaign;
    }

    public void setCampaign(String[] campaign) {
        this.campaign = campaign;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getLink_label() {
        return link_label;
    }

    public void setLink_label(String link_label) {
        this.link_label = link_label;
    }

    public boolean isIos_use_default() {
        return ios_use_default;
    }

    public void setIos_use_default(boolean ios_use_default) {
        this.ios_use_default = ios_use_default;
    }

    public String getIos_custom_url() {
        return ios_custom_url;
    }

    public void setIos_custom_url(String ios_custom_url) {
        this.ios_custom_url = ios_custom_url;
    }

    public boolean isAndroid_use_default() {
        return android_use_default;
    }

    public void setAndroid_use_default(boolean android_use_default) {
        this.android_use_default = android_use_default;
    }

    public String getAndroid_custom_url() {
        return android_custom_url;
    }

    public void setAndroid_custom_url(String android_custom_url) {
        this.android_custom_url = android_custom_url;
    }

    public boolean isDesktop_use_default() {
        return desktop_use_default;
    }

    public void setDesktop_use_default(boolean desktop_use_default) {
        this.desktop_use_default = desktop_use_default;
    }

    public String getDesktop_custom_url() {
        return desktop_custom_url;
    }

    public void setDesktop_custom_url(String desktop_custom_url) {
        this.desktop_custom_url = desktop_custom_url;
    }

    public JSONObject getParams() {
        return params;
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public long getDeeplink_id() {
        return deeplink_id;
    }

    public void setDeeplink_id(long deeplink_id) {
        this.deeplink_id = deeplink_id;
    }

    public long[] getDeeplink_ids() {
        return deeplink_ids;
    }

    public void setDeeplink_ids(long[] deeplink_ids) {
        this.deeplink_ids = deeplink_ids;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDeepLinkMd5() {
        return deepLinkMd5;
    }

    public void setDeepLinkMd5(String deepLinkMd5) {
        this.deepLinkMd5 = deepLinkMd5;
    }
}
