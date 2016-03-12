package cc.linkedme.model;

import java.io.Serializable;

/**
 * Created by qipo on 15/11/25.
 */


public class LMLinkEntity implements Serializable {
    private static final long serialVersionUID = -3694350252230780080L;
    private int id;
    private String identityId;
    private String appKey;
    private String type;
    private String tags;
    private String channel;
    private String feature;
    private String state;
    private String stage;
    private String alias;
    private String sdk;
    private String data;
    private String source;
    private String deeplinkpath;
    private String linkIdentifier;
    private String linkClickId;
    private Integer clicks;
    private Integer install;
    private Integer rejectInstall;
    private Integer open;
    private Integer weibo;
    private Integer wechat;
    private Integer retryNumber;
    private String ip;
    private Integer timestamp;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getSdk() {
        return sdk;
    }

    public void setSdk(String sdk) {
        this.sdk = sdk;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDeeplinkpath() {
        return deeplinkpath;
    }

    public void setDeeplinkpath(String deeplinkpath) {
        this.deeplinkpath = deeplinkpath;
    }

    public String getLinkIdentifier() {
        return linkIdentifier;
    }

    public void setLinkIdentifier(String linkIdentifier) {
        this.linkIdentifier = linkIdentifier;
    }

    public String getLinkClickId() {
        return linkClickId;
    }

    public void setLinkClickId(String linkClickId) {
        this.linkClickId = linkClickId;
    }

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public Integer getInstall() {
        return install;
    }

    public void setInstall(Integer install) {
        this.install = install;
    }

    public Integer getRejectInstall() {
        return rejectInstall;
    }

    public void setRejectInstall(Integer rejectInstall) {
        this.rejectInstall = rejectInstall;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public Integer getWeibo() {
        return weibo;
    }

    public void setWeibo(Integer weibo) {
        this.weibo = weibo;
    }

    public Integer getWechat() {
        return wechat;
    }

    public void setWechat(Integer wechat) {
        this.wechat = wechat;
    }

    public Integer getRetryNumber() {
        return retryNumber;
    }

    public void setRetryNumber(Integer retryNumber) {
        this.retryNumber = retryNumber;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LMLinkEntity that = (LMLinkEntity) o;

        if (id != that.id) return false;
        if (identityId != null ? !identityId.equals(that.identityId) : that.identityId != null) return false;
        if (appKey != null ? !appKey.equals(that.appKey) : that.appKey != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        if (channel != null ? !channel.equals(that.channel) : that.channel != null) return false;
        if (feature != null ? !feature.equals(that.feature) : that.feature != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (stage != null ? !stage.equals(that.stage) : that.stage != null) return false;
        if (alias != null ? !alias.equals(that.alias) : that.alias != null) return false;
        if (sdk != null ? !sdk.equals(that.sdk) : that.sdk != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (deeplinkpath != null ? !deeplinkpath.equals(that.deeplinkpath) : that.deeplinkpath != null) return false;
        if (linkIdentifier != null ? !linkIdentifier.equals(that.linkIdentifier) : that.linkIdentifier != null) return false;
        if (linkClickId != null ? !linkClickId.equals(that.linkClickId) : that.linkClickId != null) return false;
        if (clicks != null ? !clicks.equals(that.clicks) : that.clicks != null) return false;
        if (install != null ? !install.equals(that.install) : that.install != null) return false;
        if (rejectInstall != null ? !rejectInstall.equals(that.rejectInstall) : that.rejectInstall != null) return false;
        if (open != null ? !open.equals(that.open) : that.open != null) return false;
        if (weibo != null ? !weibo.equals(that.weibo) : that.weibo != null) return false;
        if (wechat != null ? !wechat.equals(that.wechat) : that.wechat != null) return false;
        if (retryNumber != null ? !retryNumber.equals(that.retryNumber) : that.retryNumber != null) return false;
        if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (identityId != null ? identityId.hashCode() : 0);
        result = 31 * result + (appKey != null ? appKey.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (channel != null ? channel.hashCode() : 0);
        result = 31 * result + (feature != null ? feature.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (stage != null ? stage.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (sdk != null ? sdk.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (deeplinkpath != null ? deeplinkpath.hashCode() : 0);
        result = 31 * result + (linkIdentifier != null ? linkIdentifier.hashCode() : 0);
        result = 31 * result + (linkClickId != null ? linkClickId.hashCode() : 0);
        result = 31 * result + (clicks != null ? clicks.hashCode() : 0);
        result = 31 * result + (install != null ? install.hashCode() : 0);
        result = 31 * result + (rejectInstall != null ? rejectInstall.hashCode() : 0);
        result = 31 * result + (open != null ? open.hashCode() : 0);
        result = 31 * result + (weibo != null ? weibo.hashCode() : 0);
        result = 31 * result + (wechat != null ? wechat.hashCode() : 0);
        result = 31 * result + (retryNumber != null ? retryNumber.hashCode() : 0);
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
}
