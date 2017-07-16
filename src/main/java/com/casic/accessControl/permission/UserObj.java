package com.casic.accessControl.permission;

import java.io.Serializable;

/**
 * Created by admin on 2015/10/9.
 */
public class UserObj implements Serializable {
    private String userName;
    private String password;
    private String appId;
    /*
    roles:?????????????
     */
    private String roles;

    /*
    authorities:?????????????
    */
    private String authorities;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
