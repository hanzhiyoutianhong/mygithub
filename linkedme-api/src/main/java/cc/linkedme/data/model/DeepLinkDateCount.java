package cc.linkedme.data.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Created by LinkedME01 on 16/4/17.
 */
@Entity
@Table(name = "url_count_0", schema = "count_0", catalog = "")
public class DeepLinkDateCount {
    private int appId;
    private long deeplinkId;
    private String date;
    private Long click;
    private Long open;
    private Long install;

    @Basic
    @Column(name = "app_id")
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "deeplink_id")
    public long getDeeplinkId() {
        return deeplinkId;
    }

    public void setDeeplinkId(long deeplinkId) {
        this.deeplinkId = deeplinkId;
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
    @Column(name = "click")
    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    @Basic
    @Column(name = "open")
    public Long getOpen() {
        return open;
    }

    public void setOpen(Long open) {
        this.open = open;
    }

    @Basic
    @Column(name = "install")
    public Long getInstall() {
        return install;
    }

    public void setInstall(Long install) {
        this.install = install;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeepLinkDateCount that = (DeepLinkDateCount) o;

        if (appId != that.appId) return false;
        if (deeplinkId != that.deeplinkId) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (click != null ? !click.equals(that.click) : that.click != null) return false;
        if (open != null ? !open.equals(that.open) : that.open != null) return false;
        if (install != null ? !install.equals(that.install) : that.install != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = appId;
        result = 31 * result + (int) (deeplinkId ^ (deeplinkId >>> 32));
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (click != null ? click.hashCode() : 0);
        result = 31 * result + (open != null ? open.hashCode() : 0);
        result = 31 * result + (install != null ? install.hashCode() : 0);
        return result;
    }
}
