package cc.linkedme.model;

/**
 * Created by qipo on 15/10/1.
 */
public class LMAppAccountEntity {
    private int email;
    private String emailexsit;
    private String username;
    private String password;
    private String registertimestamp;
    private String logintimestamp;

    public int getEmail() {
        return email;
    }

    public void setEmail(int email) {
        this.email = email;
    }

    public String getEmailexsit() {
        return emailexsit;
    }

    public void setEmailexsit(String emailexsit) {
        this.emailexsit = emailexsit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegistertimestamp() {
        return registertimestamp;
    }

    public void setRegistertimestamp(String registertimestamp) {
        this.registertimestamp = registertimestamp;
    }

    public String getLogintimestamp() {
        return logintimestamp;
    }

    public void setLogintimestamp(String logintimestamp) {
        this.logintimestamp = logintimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LMAppAccountEntity that = (LMAppAccountEntity) o;

        if (email != that.email) return false;
        if (emailexsit != null ? !emailexsit.equals(that.emailexsit) : that.emailexsit != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (registertimestamp != null ? !registertimestamp.equals(that.registertimestamp) : that.registertimestamp != null) return false;
        if (logintimestamp != null ? !logintimestamp.equals(that.logintimestamp) : that.logintimestamp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = email;
        result = 31 * result + (emailexsit != null ? emailexsit.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (registertimestamp != null ? registertimestamp.hashCode() : 0);
        result = 31 * result + (logintimestamp != null ? logintimestamp.hashCode() : 0);
        return result;
    }
}
