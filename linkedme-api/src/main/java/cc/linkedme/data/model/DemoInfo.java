package cc.linkedme.data.model;

/**
 * Created by LinkedME01 on 16/5/4.
 */
public class DemoInfo {
    private long id;
    private String name;
    private String email;
    private String mobilePhone;
    private String companyProductName;
    private long fromChannel;
    private String createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCompanyProductName() {
        return companyProductName;
    }

    public void setCompanyProductName(String companyProductName) {
        this.companyProductName = companyProductName;
    }

    public long getFromChannel() {
        return fromChannel;
    }

    public void setFromChannel(long fromChannel) {
        this.fromChannel = fromChannel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
