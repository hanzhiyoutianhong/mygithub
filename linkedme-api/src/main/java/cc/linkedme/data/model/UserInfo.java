package cc.linkedme.data.model;

import cc.linkedme.commons.json.JsonBuilder;

import java.util.Date;

/**
 * Created by Vontroy on 16/3/19.
 */
public class UserInfo {
    private int id;
    private String email;
    private String pwd;
    private String name;
    private String phone_number;
    private String company;
    private short role_id;
    private String ios_bundle_id;
    private String bundle_id_online_date;
    private String register_time;
    private String last_login_time;
    private String token;

    public String toJson() {
        JsonBuilder json = new JsonBuilder();
        json.append("user_id", id);
        json.append("email", email);
        json.append("name", name);
        json.append("company", company);
        json.append("role_id", role_id);
        json.append("register_time", register_time);
        json.append("last_login_time", last_login_time);
        return json.flip().toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public short getRole_id() {
        return role_id;
    }

    public void setRole_id(short role_id) {
        this.role_id = role_id;
    }

    public String getIos_bundle_id() {
        return ios_bundle_id;
    }

    public String getBundle_id_online_date() {
        return bundle_id_online_date;
    }

    public void setBundle_id_online_date(String bundle_id_online_date) {
        this.bundle_id_online_date = bundle_id_online_date;
    }

    public void setIos_bundle_id(String ios_bundle_id) {
        this.ios_bundle_id = ios_bundle_id;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
