package net.sunniwell.gobang.liqiang.model;

import android.content.Context;
import android.content.SharedPreferences;

import net.sunniwell.gobang.linhongbo.ISWInterface.ISWOnRegisterInterface;
import net.sunniwell.gobang.liqiang.view.SWSignInActivity;
import net.sunniwell.jar.log.SWLogger;

/**
 * Created by lin on 2018/1/5.
 */

public class SWRegisterModel implements ISWOnRegisterInterface.ISWOnRegisterModelInterface {
    private static final SWLogger log = SWLogger.getLogger("SWRegisterModel");
    /**
     * 保存用户登录信息到sharePreferences
     *
     * @param context
     * @param userName
     * @param userPassword
     */
    @Override
    public void saveUserInfo2SharePreferences(Context context, String userName, String userPassword) {
        if (context != null) {
            SharedPreferences sharePreferences = context.getSharedPreferences(SWSignInActivity.SHARE_PREFERENCES_USER_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharePreferences.edit();
            editor.clear();
            editor.putString(SWSignInActivity.SHARE_PREFERENCES_USER_NAME, userName);
            editor.putString(SWSignInActivity.SHARE_PREFERENCES_USER_PASSWORD, userPassword);
            editor.commit();
            log.d("saveUserInfo2SharePreferences succeed.");
        } else {
            new Throwable("context = " + context);
        }
    }
}
