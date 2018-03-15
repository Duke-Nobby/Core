package com.zkcb.doctorstation.models;

/**
 * TODO: 用户实体类
 * Author: Yong Liu
 * Time : 2018/3/13 16:43
 * E-Mail : liuy@zkcbmed.com
 */

public class User {
    private String username;
    private String password;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
