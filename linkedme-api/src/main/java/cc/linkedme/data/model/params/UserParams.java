package cc.linkedme.data.model.params;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserParams {
    public String email;
    public String pwd;
    public String name;
    public String company;
    public String role_id;
    public String last_logout_time;
    public String old_pwd;
    public String new_pwd;
    public String token;


    /**
     * construction function
     */
    public UserParams() {}

    public UserParams( String email, String token ) {
        this.email = email;
        this.token = token;
    }

    public UserParams( String email, String last_logout_time, String token ) {
        this.email = email;
        this.last_logout_time = last_logout_time;
        this.token = token;
    }

    public UserParams( String email, String old_pwd, String new_pwd, String token ) {
        this.email = email;
        this.old_pwd = old_pwd;
        this.new_pwd = new_pwd;
        this.token = token;
    }

    public UserParams( String email, String pwd, String name, String company, String token ) {
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.company = company;
        this.token = token;
    }

    /**
     * get and set functions
     */
    public String getEmail(){
        return email;
    }

    public void setEmail( String email ){
        this.email = email;
    }

    public String getPwd(){
        return pwd;
    }

    public void setPwd( String passwd ){
        this.pwd = passwd;
    }

    public String getName(){
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany( String company ) {
        this.company = company;
    }

    public String getLast_logout_time() {
        return last_logout_time;
    }

    public void setLast_logout_time( String last_logout_time ) {
        this.last_logout_time = last_logout_time;
    }

    public String getOld_pwd() {
        return old_pwd;
    }

    public void setOld_pwd( String old_pwd ) {
        this.old_pwd = old_pwd;
    }

    public String getNew_pwd() {
        return new_pwd;
    }

    public void setNew_pwd( String new_pwd ) {
        this.new_pwd = new_pwd;
    }

    public String getToken(){
        return token;
    }

    public void setToken( String token ){
        this.token = token;
    }
}
