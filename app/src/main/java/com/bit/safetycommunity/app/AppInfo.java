package com.bit.safetycommunity.app;

import java.io.Serializable;


public class AppInfo implements Serializable {

    private String pwd;
    private String mobile;

    public AppInfo() {
    }

    public AppInfo(String pwd, String mobile) {
        this.pwd = pwd;
        this.mobile = mobile;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
