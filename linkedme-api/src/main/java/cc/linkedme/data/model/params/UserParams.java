package cc.linkedme.data.model.params;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserParams {
    public String email;
    public String pwd;
    public String name;
    public String company;
    public int role_id;
    public String last_logout_time;
    public String old_pwd;
    public String new_pwd;
    public String token;
    public String register_time;
    public String current_login_time;


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

}
