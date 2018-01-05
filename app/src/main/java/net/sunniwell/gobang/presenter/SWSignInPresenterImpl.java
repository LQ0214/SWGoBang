package net.sunniwell.gobang.presenter;

import net.sunniwell.gobang.SWApplication;
import net.sunniwell.gobang.iswinterface.ISWOnSignInInterface;
import net.sunniwell.gobang.model.SWRegisterModel;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 处理登录业务
 * Created by lin on 2018/1/4.
 */

public class SWSignInPresenterImpl implements ISWOnSignInInterface.ISWOnSignInPresenterInterface {

    private static final SWLogger log = SWLogger.getLogger("SWSignInPresenterImpl");
    private ISWOnSignInInterface.ISWOnSignInViewInterface mView;
    private SWRegisterModel mModel = new SWRegisterModel();

    public SWSignInPresenterImpl(ISWOnSignInInterface.ISWOnSignInViewInterface view){
        mView = view;
    }

    @Override
    public void signIn(final String userName, final String userPassword) {
        log.d("signIn");
        BmobUser userInfo = new BmobUser();
        userInfo.setUsername(userName);
        userInfo.setPassword(userPassword);
        userInfo.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    mModel.saveUserInfo2SharePreferences(SWApplication.getContext(), userName, userPassword);
                    if (mView != null) {
                        mView.onSignInSucceed();
                    } else {
                        new Throwable("error: the mCallback is null");
                    }
                } else {
                    if (mView != null) {
                        mView.onSignInFailed(e.getMessage());
                    } else {
                        new Throwable("error: the mCallback is null");
                    }
                }
            }
        });

    }
}