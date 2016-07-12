package cc.linkedme.data.model;

import cc.linkedme.commons.json.JsonBuilder;

public class User {

    private int id;
    private String email;
    private String pwd;
    private String name;
    private String company;
    private short roleId;
    private String registerTime;
    private String lastLoginTime;
    private String token;

    public String toJson() {
        JsonBuilder json = new JsonBuilder();
        json.append("user_id", id);
        json.append("email", email);
        json.append("name", name);
        json.append("company", company);
        json.append("role_id", roleId);
        json.append("register_time", registerTime);
        json.append("last_login_time", lastLoginTime);
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public short getRoleId() {
        return roleId;
    }

    public void setRoleId(short roleId) {
        this.roleId = roleId;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
