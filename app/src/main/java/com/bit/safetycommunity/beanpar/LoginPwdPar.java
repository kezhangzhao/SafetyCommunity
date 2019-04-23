package com.bit.safetycommunity.beanpar;

/**
 * 密码登陆
 */
public class LoginPwdPar {

    private String capUuid;//验证码id
    private String captcha;//验证码
    private String mobile;
    private String pwd;

    public String getCapUuid() {
        return capUuid;
    }

    public void setCapUuid(String capUuid) {
        this.capUuid = capUuid;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
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
