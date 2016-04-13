package cc.linkedme.data.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by LinkedME01 on 16/4/12.
 */
@Entity
@Table(name = "button_count_0", schema = "count_0", catalog = "")
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

    @Id
    @Column(name = "app_id")
    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "btn_id")
    public String getBtnId() {
        return btnId;
    }

    public void setBtnId(String btnId) {
        this.btnId = btnId;
    }

    @Basic
    @Column(name = "consumer_id")
    public long getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(long consumerId) {
        this.consumerId = consumerId;
    }

    @Basic
    @Column(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "view_count")
    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @Basic
    @Column(name = "click_count")
    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    @Basic
    @Column(name = "open_app_count")
    public int getOpenAppCount() {
        return openAppCount;
    }

    public void setOpenAppCount(int openAppCount) {
        this.openAppCount = openAppCount;
    }

    @Basic
    @Column(name = "open_web_count")
    public int getOpenWebCount() {
        return openWebCount;
    }

    public void setOpenWebCount(int openWebCount) {
        this.openWebCount = openWebCount;
    }

    @Basic
    @Column(name = "order_count")
    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    @Basic
    @Column(name = "income")
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
