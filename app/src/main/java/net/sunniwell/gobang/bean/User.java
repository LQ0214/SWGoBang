package net.sunniwell.gobang.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/1/4.
 */

public class User extends BmobUser{
    private int mUserId;
    private String mUserName;
    private String mPassword;

    public User(int userId, String userName) {
        mUserId = userId;
        mUserName = userName;
    }

    public void setUserId(int userId) {
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getUserNam() {
        return mUserName;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }
}