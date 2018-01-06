package net.sunniwell.gobang.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/1/4.
 */

public class User extends BmobUser {
    private String mUserId;
    private String mUserName;
    private String mPassword;

    public User(String userId, String userName, String password) {
        mUserId = userId;
        mUserName = userName;
        mPassword = password;
    }

    public void setUserId(int userId) {
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getName() {
        return mUserName;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }
}