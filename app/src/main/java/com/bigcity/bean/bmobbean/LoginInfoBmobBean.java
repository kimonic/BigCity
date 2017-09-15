package com.bigcity.bean.bmobbean;

import cn.bmob.v3.BmobObject;

/**
 * * ===============================================================
 * name:             LoginInfoBmobBean
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/15
 * description：    注册,登陆,请求信息bean,总共11个字段
 * history：
 * *==================================================================
 */

public class LoginInfoBmobBean extends BmobObject {


    /**用户名*/
    private String  userName;
    /**登陆密码*/
    private String  password;
    /**android 版本号*/
    private String  androidVersion;
    /**手机品牌*/
    private String   phoneBrand;
    /**性别*/
    private String  sex;
    /**年龄*/
    private String  age;
    /**手机号码*/
    private String  phoneNumber;
    /**邮箱*/
    private String  email;
    /**是否验证邮箱*/
    private String  emailCheck;
    /**是否验证手机*/
    private String   phoneCheck;
    /**用户登陆状态*/
    private String  state;
    /**注册时间*/
    private String registerTime;

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

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

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }



    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCheck() {
        return emailCheck;
    }

    public void setEmailCheck(String emailCheck) {
        this.emailCheck = emailCheck;
    }

    public String getPhoneCheck() {
        return phoneCheck;
    }

    public void setPhoneCheck(String phoneCheck) {
        this.phoneCheck = phoneCheck;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
