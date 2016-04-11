package cc.linkedme.data.model;

import net.sf.json.JSONObject;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by LinkedME01 on 16/4/7.
 */
@Entity
@Table(name = "button_info_0", schema = "button_0", catalog = "")
public class ButtonInfo {
    private String btnId;
    private String btnName;
    private long appId;
    private long consumerAppId;
    private String btnCategory;
    private String creationTime;
    private int checkStatus;
    private int onlineStatus;
    private int consumerOnlineStatus;

    private ConsumerAppInfo consumerAppInfo;

    @Id
    @Column(name = "btn_id")
    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    @Basic
    @Column(name = "btn_name")
    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }

    @Basic
    @Column(name = "app_id")
    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "consumer_app_id")
    public long getConsumerAppId() {
        return consumerAppId;
    }

    public void setConsumerAppId(long consumerAppId) {
        this.consumerAppId = consumerAppId;
    }

    @Basic
    @Column(name = "btn_category")
    public String getBtnCategory() {
        return btnCategory;
    }

    public void setBtnCategory(String btnCategory) {
        this.btnCategory = btnCategory;
    }

    @Basic
    @Column(name = "creation_time")
    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    @Basic
    @Column(name = "check_status")
    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    @Basic
    @Column(name = "online_status")
    public int getOnlineStatus() {
        return onlineStatus;
    }

    public int getConsumerOnlineStatus() {
        return consumerOnlineStatus;
    }

    public void setConsumerOnlineStatus(int consumerOnlineStatus) {
        this.consumerOnlineStatus = consumerOnlineStatus;
    }

    public void setOnlineStatus(int onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public ConsumerAppInfo getConsumerAppInfo() {
        return consumerAppInfo;
    }

    public void setConsumerAppInfo(ConsumerAppInfo consumerAppInfo) {
        this.consumerAppInfo = consumerAppInfo;
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("button_id", btnId);
        json.put("consumer_app_id", consumerAppId);
        if (consumerAppInfo != null) {
            json.put("consumer_app_name", consumerAppInfo.getAppName());
            json.put("consumer_app_logo_url", consumerAppInfo.getAppLogoUrl());
            json.put("consumer_app_status", consumerAppInfo.getStatus());
            json.put("online_date", consumerAppInfo.getOnlineTime());
            json.put("category", consumerAppInfo.getCategory());
            json.put("status", checkStatus == 1 ? "online" : "offline");
        }
        return json.toString();
    }

}
