package net.sunniwell.gobang.presenter;

import net.sunniwell.gobang.iswinterface.ISWOnSignAboutInterface;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 处理登录业务
 * Created by lin on 2018/1/4.
 */

public class SWSignInPresenterImpl implements ISWOnSignAboutInterface.ISWOnSignInPresenterInterface {

    private static final SWLogger log = SWLogger.getLogger("SWSignInPresenterImpl");
    private ISWOnSignAboutInterface.ISWOnSignInViewInterface mSignInView;

    public SWSignInPresenterImpl(ISWOnSignAboutInterface.ISWOnSignInViewInterface view) {
        mSignInView = view;
    }

    @Override
    public void signIn(final String telNum, final String userPassword) {
        log.d("signIn");
        //手机密码登录
        BmobUser.loginByAccount(telNum, userPassword, new LogInListener<BmobUser>() {
            @Override
            public void done(BmobUser user, BmobException e) {
                if (e == null) {
                    if (mSignInView != null) {
                        mSignInView.onSignInSucceed();
                    } else {
                        new Throwable("error: the mCallback is null");
                    }
                } else {
                    if (mSignInView != null) {
                        mSignInView.onSignInFailed(e.getMessage());
                    } else {
                        new Throwable("error: the mCallback is null");
                    }
                }
            }
        });
        //账号密码登录
//        userInfo.login(new SaveListener<BmobUser>() {
//            @Override
//            public void done(BmobUser bmobUser, BmobException e) {
//                if (e == null) {
//                    SWApplication.saveUserInfo2SharePreferences(SWApplication.getContext(), bmobUser.getMobilePhoneNumber(), userName, userPassword);
//                    if (mView != null) {
//                        mView.onSignInSucceed();
//                    } else {
//                        new Throwable("error: the mCallback is null");
//                    }
//                } else {
//                    if (mView != null) {
//                        mView.onSignInFailed(e.getMessage());
//                    } else {
//                        new Throwable("error: the mCallback is null");
//                    }
//                }
//            }
//        });
    }

    @Override
    public BmobUser getUserInfo() {
        return BmobUser.getCurrentUser();
    }
}
