package net.sunniwell.gobang.iswinterface;

import android.content.Context;

import cn.bmob.v3.BmobUser;

/**
 * Created by lin on 2018/1/4.
 */

public interface ISWOnRegisterInterface {

    /**
     * 注册动作
     */
    interface ISWOnRegisterPresenterInterface {
        void register(BmobUser userInfo, String password, String smsCode);
    }


    /**
     * 注册结果回调
     */
    interface ISWOnRegisterViewInterface {
        void onRegisterSucceed();

        void onRegisterFailed(String reason);
    }

    interface ISWOnRegisterModelInterface {
        void saveUserInfo2SharePreferences(Context context, String userName, String userPassword);
    }
}
