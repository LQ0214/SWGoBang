package net.sunniwell.gobang.iswinterface;

import cn.bmob.v3.BmobUser;

/**
 * Created by lin on 2018/1/4.
 */

public interface ISWOnSignAboutInterface {

    /**
     * 登录动作
     */
    interface ISWOnSignInPresenterInterface {
        void signIn(String userName, String userPassword);

        BmobUser getUserInfo();
    }

    interface ISWOnSignOutPresenterInter {
        void signOut();
    }


    /**
     * 登录业务回调接口
     */
    interface ISWOnSignInViewInterface {
        void onSignInSucceed();

        void onSignInFailed(String reason);
    }

    /**
     * 注销业务回调接口
     */
    interface ISWOnSignOutViewInterface {
        void onSignOutSucceed();

        void onSignOutFailed(String reason);
    }
}
