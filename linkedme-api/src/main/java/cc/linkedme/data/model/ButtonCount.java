package cc.linkedme.data.model;


/**
 * Created by LinkedME01 on 16/4/12.
 */
public class ButtonCount {
    private long appId;
    private String btnId;
    private long consumerId;
    private String date;
    private int viewCount;
    private int clickCount;
    private int openAppCount;
    private int openWebCount;
    private int openOtherCount;
    private int orderCount;
    private float income;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    public long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }


    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }


    public int getOpenAppCount() {
        return openAppCount;
    }

    public void setOpenAppCount(int openAppCount) {
        this.openAppCount = openAppCount;
    }

    public int getOpenWebCount() {
        return openWebCount;
    }

    public void setOpenWebCount(int openWebCount) {
        this.openWebCount = openWebCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }


    public float getIncome() {
        return income;
    }

    public void setIncome(float income) {
        this.income = income;
    }


    public int getOpenOtherCount() {
        return openOtherCount;
    }

    public void setOpenOtherCount(int openOtherCount) {
        this.openOtherCount = openOtherCount;
    }

}
