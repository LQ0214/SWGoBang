package net.sunniwell.gobang.model;

import android.content.Context;
import android.content.SharedPreferences;

import net.sunniwell.gobang.bean.User;
import net.sunniwell.gobang.view.activity.SWSignInActivity;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.BmobUser;

/**
 * Created by lin on 2018/1/5.
 */

public class SWBaseModel {
    private static final SWLogger log = SWLogger.getLogger("SWRegisterModel");

    /**
     * 保存用户登录信息到sharePreferences
     *
     * @param context
     * @param userName
     * @param userPassword
     */
    public void saveUserInfo2SharePreferences(Context context, String userId, String userName, String userPassword) {
        if (context != null) {
            SharedPreferences sharePreferences = context.getSharedPreferences(SWSignInActivity.SHARE_PREFERENCES_USER_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharePreferences.edit();
            editor.clear();
            editor.putString(SWSignInActivity.SHARE_PREFERENCES_USER_ID, userId);
            editor.putString(SWSignInActivity.SHARE_PREFERENCES_USER_NAME, userName);
            editor.putString(SWSignInActivity.SHARE_PREFERENCES_USER_PASSWORD, userPassword);
            editor.commit();
            log.d("saveUserInfo2SharePreferences succeed.");
        } else {
            new Throwable("context = " + context);
        }
    }

    public BmobUser getUserInfoFromSharePreferences(Context context) {
        if (context != null) {
            SharedPreferences sharePreferences = context.getSharedPreferences(SWSignInActivity.SHARE_PREFERENCES_USER_INFO, Context.MODE_PRIVATE);
            log.d("getUserInfoFromSharePreferences succeed.");
            String userId = sharePreferences.getString(SWSignInActivity.SHARE_PREFERENCES_USER_ID, "");
            String userName = sharePreferences.getString(SWSignInActivity.SHARE_PREFERENCES_USER_NAME, "");
            String password = sharePreferences.getString(SWSignInActivity.SHARE_PREFERENCES_USER_PASSWORD, "");
            BmobUser user = new User(userId, userName, password);
            return user;
        } else {
            new Throwable("context = " + context);
            return null;
        }
    }
}