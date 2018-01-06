package net.sunniwell.gobang;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import net.sunniwell.gobang.bean.User;
import net.sunniwell.gobang.view.activity.SWSignInActivity;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * Created by lin on 2018/1/4.
 */

public class SWApplication extends Application {

    private static Context mContext;
    private static SWLogger log = SWLogger.getLogger(SWApplication.class.getSimpleName());

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mContext = this;
        Bmob.initialize(getApplicationContext(), "6c9122e9c11067cf76c694646027dc91");
    }

    private void unInit() {
        mContext = null;
    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 保存用户登录信息到sharePreferences
     *
     * @param context
     * @param userName
     * @param userPassword
     */
    public static void saveUserInfo2SharePreferences(Context context, String userId, String userName, String userPassword) {
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

    public static BmobUser getUserInfoFromSharePreferences() {
        Context context = SWApplication.getContext();
        if (context != null) {
            SharedPreferences sharePreferences = context.getSharedPreferences(SWSignInActivity.SHARE_PREFERENCES_USER_INFO, Context.MODE_PRIVATE);
            log.d("getUserInfoFromSharePreferences succeed.");
            String userId = sharePreferences.getString(SWSignInActivity.SHARE_PREFERENCES_USER_ID, "");
            String userName = sharePreferences.getString(SWSignInActivity.SHARE_PREFERENCES_USER_NAME, "");
            String password = sharePreferences.getString(SWSignInActivity.SHARE_PREFERENCES_USER_PASSWORD, "");
            log.d("into gobang, userName = " + userName);
            BmobUser user = new User(userId, userName, password);
            return user;
        } else {
            new Throwable("context = " + context);
            return null;
        }
    }
}
