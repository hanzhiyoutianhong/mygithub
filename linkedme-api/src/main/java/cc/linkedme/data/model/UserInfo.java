package cc.linkedme.data.model;

import java.util.Date;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserInfo {
    private int id;
    private String email;
    private String pwd;
    private String name;
    private String company;
    private short role_id;
    private String register_time;
    private String last_login_time;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

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

    public String getName() {
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

    public short getRole_id() {
        return role_id;
    }

    public void setRole_id( short role_id ) {
        this.role_id = role_id;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time( String register_time ) {
        this.register_time = register_time;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time( String last_login_time ) {
        this.last_login_time = last_login_time;
    }
}
