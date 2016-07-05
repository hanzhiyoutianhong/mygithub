package cc.linkedme.data.model;

import net.sf.json.JSONObject;

/**
 * Created by LinkedME01 on 16/4/7.
 */
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


    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    public String getBtnName() {
        return btnName;
    }

    public void setBtnName(String btnName) {
        this.btnName = btnName;
    }


    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }


    public long getConsumerAppId() {
        return consumerAppId;
    }

    public void setConsumerAppId(long consumerAppId) {
        this.consumerAppId = consumerAppId;
    }


    public String getBtnCategory() {
        return btnCategory;
    }

    public void setBtnCategory(String btnCategory) {
        this.btnCategory = btnCategory;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }


    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

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
        json.put("button_name", btnName);
        json.put("consumer_app_id", consumerAppId);
        if (consumerAppInfo != null) {
            json.put("consumer_app_name", consumerAppInfo.getAppName());
            json.put("consumer_app_logo_url", consumerAppInfo.getAppLogoUrl());
            json.put("consumer_app_status", consumerAppInfo.getStatus());
            json.put("online_date", consumerAppInfo.getOnlineTime());
            json.put("category", consumerAppInfo.getCategory());
            json.put("status", checkStatus);
        }
        return json.toString();
    }

}
