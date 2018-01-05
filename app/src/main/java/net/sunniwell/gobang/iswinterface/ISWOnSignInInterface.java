package net.sunniwell.gobang.iswinterface;

/**
 * Created by lin on 2018/1/4.
 */

public interface ISWOnSignInInterface {

    /**
     * 登录动作
     */
    interface ISWOnSignInPresenterInterface{
        void signIn(String userName, String userPassword);
    }


    /**
     * 登录业务回调接口
     */
    interface ISWOnSignInViewInterface {
        void onSignInSucceed();

        void onSignInFailed(String reason);
    }
}
