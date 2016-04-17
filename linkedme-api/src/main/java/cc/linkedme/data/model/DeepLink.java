package cc.linkedme.data.model;

import cc.linkedme.commons.serialization.KryoSerializationUtil;
import cc.linkedme.commons.util.Base62;
import cc.linkedme.commons.util.Constants;
import cc.linkedme.commons.util.Util;
import com.alibaba.fastjson.JSONObject;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import net.sf.json.JSONArray;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by LinkedME01 on 16/3/19.
 */

public class DeepLink implements Serializable{

    private long deeplinkId;
    private String deeplinkMd5;
    private long appId;
    private String linkedmeKey;
    private long identityId;
    private String createTime;
    private String tags;
    private String alias;
    private String channel;
    private String feature;
    private String stage;
    private String campaign;
    private String params;
    private String source;
    private String sdkVersion;
    private Timestamp updateTime;
    private int state;

    private String link_label;// 自定义短链名称，和域名 https://lkme.cc/链在一起构成短链主体,目前不支持，该值先忽略
    private boolean ios_use_default;
    private String ios_custom_url;
    private boolean android_use_default;
    private String  android_custom_url;
    private boolean  desktop_use_default;
    private String  desktop_custom_url;

    private DeepLinkCount deepLinkCount;

    public DeepLink() {}

    public DeepLink(long deeplinkId, String deeplinkMd5, long appId, String linkedmeKey, long identityId, String tags, String alias,
                                   String channel, String feature, String stage, String campaign, String params, String source, String sdkVersion) {
        this.deeplinkId = deeplinkId;
        this.deeplinkMd5 = deeplinkMd5;
        this.identityId = identityId;
        this.appId = appId;
        this.linkedmeKey = linkedmeKey;
        this.tags = tags;
        this.alias = alias;
        this.channel = channel;
        this.feature = feature;
        this.stage = stage;
        this.campaign = campaign;
        this.params = params;
        this.source = source;
        this.sdkVersion = sdkVersion;
    }

    @Id
    @Column(name = "deeplink_id")
    public long getDeeplinkId() {
        return deeplinkId;
    }

    public void setDeeplinkId(long deeplinkId) {
        this.deeplinkId = deeplinkId;
    }

    @Basic
    @Column(name = "deeplink_md5")
    public String getDeeplinkMd5() {
        return deeplinkMd5;
    }

    public void setDeeplinkMd5(String deeplinkMd5) {
        this.deeplinkMd5 = deeplinkMd5;
    }

    @Basic
    @Column(name = "linkedme_key")
    public String getLinkedmeKey() {
        return linkedmeKey;
    }

    public void setLinkedmeKey(String linkedmeKey) {
        this.linkedmeKey = linkedmeKey;
    }

    @Basic
    @Column(name = "identity_id")
    public long getIdentityId() {
        return identityId;
    }

    public void setIdentityId(long identityId) {
        this.identityId = identityId;
    }

    @Basic
    @Column(name = "create_time")
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "tags")
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Basic
    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "channel")
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Basic
    @Column(name = "feature")
    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Basic
    @Column(name = "stage")
    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @Basic
    @Column(name = "campaign")
    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    @Basic
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "sdk_version")
    public String getSdkVersion() {
        return sdkVersion;
    }

    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "state")
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }


    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeepLink that = (DeepLink) o;

        if (deeplinkId != that.deeplinkId) return false;
        if (deeplinkMd5 != null ? !deeplinkMd5.equals(that.deeplinkMd5) : that.deeplinkMd5 != null) return false;
        if (linkedmeKey != null ? !linkedmeKey.equals(that.linkedmeKey) : that.linkedmeKey != null) return false;
        if (identityId != that.identityId) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (channel != null ? !channel.equals(that.channel) : that.channel != null) return false;
        if (feature != null ? !feature.equals(that.feature) : that.feature != null) return false;
        if (stage != null ? !stage.equals(that.stage) : that.stage != null) return false;
        if (campaign != null ? !campaign.equals(that.campaign) : that.campaign != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (sdkVersion != null ? !sdkVersion.equals(that.sdkVersion) : that.sdkVersion != null) return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (deeplinkId ^ (deeplinkId >>> 32));
        result = 31 * result + (deeplinkMd5 != null ? deeplinkMd5.hashCode() : 0);
        result = 31 * result + (linkedmeKey != null ? linkedmeKey.hashCode() : 0);
        result = 31 * result + (identityId != 0 ? ((Long)identityId).hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (feature != null ? feature.hashCode() : 0);
        result = 31 * result + (stage != null ? stage.hashCode() : 0);
        result = 31 * result + (campaign != null ? campaign.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (sdkVersion != null ? sdkVersion.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + state;
        return result;
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

    public DeepLinkCount getDeepLinkCount() {
        return deepLinkCount;
    }

    public void setDeepLinkCount(DeepLinkCount deepLinkCount) {
        this.deepLinkCount = deepLinkCount;
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("deeplink_id", deeplinkId);
        jsonObject.put("deeplink_url", Constants.DEEPLINK_HTTPS_PREFIX + Base62.encode(appId) + "/" + Base62.encode(deeplinkId));
        if (!Strings.isNullOrEmpty(feature)) {
            JSONArray featureJson = JSONArray.fromObject(getFeature().split(","));
            jsonObject.put("feature", featureJson);
        }
        if (!Strings.isNullOrEmpty(campaign)) {
            JSONArray campaignJson = JSONArray.fromObject(campaign.split(","));
            jsonObject.put("campaign", campaignJson);
        }
        if (!Strings.isNullOrEmpty(stage)) {
            JSONArray stageJson = JSONArray.fromObject(stage.split(","));
            jsonObject.put("campaign", stageJson);
        }
        if (!Strings.isNullOrEmpty(channel)) {
            JSONArray channelJson = JSONArray.fromObject(channel.split(","));
            jsonObject.put("channel", channelJson);
        }
        if (!Strings.isNullOrEmpty(tags)) {
            JSONArray tagsJson = JSONArray.fromObject(tags.split(","));
            jsonObject.put("tags", tagsJson);
        }
        jsonObject.put("unique", false);
        jsonObject.put("creation_time", createTime);
        jsonObject.put("source", source);

        if(deepLinkCount != null) {
            JSONObject iosCount = new JSONObject();
            iosCount.put("ios_click", deepLinkCount.getIos_click());
            iosCount.put("ios_install", deepLinkCount.getIos_install());
            iosCount.put("ios_open", deepLinkCount.getIos_open());

            JSONObject adrCount = new JSONObject();
            adrCount.put("adr_click", deepLinkCount.getAdr_click());
            adrCount.put("adr_install", deepLinkCount.getAdr_install());
            adrCount.put("adr_open", deepLinkCount.getAdr_open());

            JSONObject pcCount = new JSONObject();
            pcCount.put("pc_click", deepLinkCount.getPc_click());
            pcCount.put("pc_ios_scan", deepLinkCount.getPc_ios_scan());
            pcCount.put("pc_adr_scan", deepLinkCount.getPc_adr_scan());

            jsonObject.put("ios", iosCount);
            jsonObject.put("android", adrCount);
            jsonObject.put("desktop", pcCount);
        }
        return jsonObject;
    }

    public String toJson() {
        return toJsonObject().toString();
    }

    public static void main(String[] args) {
        String[] channel = new String[]{"aa", "bb"};
        JSONArray jarr = JSONArray.fromObject(channel);
        System.out.println(jarr.toString());


        DeepLink deepLink = new DeepLink();
        deepLink.linkedmeKey = Util.getUUID();
        deepLink.appId = 11234;
        deepLink.alias = "adf,adfa";
        deepLink.deeplinkId = 1234123412341L;
        deepLink.deeplinkMd5 = "asdfasdfasdfasdfasdfasdf";
        deepLink.identityId = 234534523453451354L;
        deepLink.createTime = "2016-04-13 09:20:20";
        deepLink.tags = "asd,asdf,adsf";
        deepLink.channel = "weibo,weixin";
        deepLink.feature = "adsf,adfa";
        deepLink.stage = "adf,adf";
        deepLink.params = "adsfadsfasdfasdfa;sldfkasdjfal;skdfjals;dkfjasd;lfkasdjf;lasdfas";
        deepLink.source = "ios";
        deepLink.sdkVersion = "1.0.1";
        deepLink.state = 12;
        deepLink.link_label = "asdfasdf";
        deepLink.ios_use_default = false;
        deepLink.ios_custom_url = "asdfadsf";
        deepLink.android_custom_url = "asdfasdf";
        deepLink.android_use_default = false;
        deepLink.desktop_custom_url = "asdfasdf";
        deepLink.desktop_use_default = false;
        DeepLinkCount deepLinkCount = new DeepLinkCount();
        deepLinkCount.setAdr_open(12);
        deepLinkCount.setAdr_install(23);
        deepLink.deepLinkCount = deepLinkCount;

        long start = System.currentTimeMillis();
        byte[] b = KryoSerializationUtil.serializeObj(deepLink);
        long end = System.currentTimeMillis();
        long serializeCost = System.currentTimeMillis() - start;
        System.out.println(b.length);
        System.out.println(serializeCost);
        DeepLink deepLink1 = KryoSerializationUtil.deserializeObj(b, DeepLink.class);
        long desCost = System.currentTimeMillis() - end;
        DeepLinkCount deepLinkCount1 = deepLink1.deepLinkCount;

        String json = deepLink1.toJson();
        System.out.println(deepLink1.toJson());
        System.out.println(desCost);
        System.out.print(deepLinkCount1.getAdr_install());
    }
}
