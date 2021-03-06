package com.epam.finalProject.entity;

public class User extends Entity {

    private String username;
    private String password;
    private int roleId;
    private int statusId;
    private UserInfo userInfo;
    private String lang;

    public String getLang() {
        System.out.println("Lang is:"+lang);
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String toString() {
        return "User{" + "user id=" + getId() +
                "username='" + username + '\'' +
                ", roleId=" + roleId + ", lang=" + lang +
                ", userInfo=" + userInfo +
                '}';
    }
}
