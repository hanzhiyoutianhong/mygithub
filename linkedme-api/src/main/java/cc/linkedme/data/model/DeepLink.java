package cc.linkedme.data.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by LinkedME01 on 16/3/19.
 */
@Entity
@Table(name = "deeplink_info_1603", schema = "deeplink_0", catalog = "")
public class DeepLink {

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
}
