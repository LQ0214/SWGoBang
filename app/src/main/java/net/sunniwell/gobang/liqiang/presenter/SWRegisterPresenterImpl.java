package net.sunniwell.gobang.liqiang.presenter;

import net.sunniwell.gobang.SWApplication;
import net.sunniwell.gobang.linhongbo.ISWInterface.ISWOnRegisterInterface;
import net.sunniwell.gobang.liqiang.model.SWRegisterModel;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 处理注册业务
 * Created by lin on 2018/1/4.
 */

public class SWRegisterPresenterImpl implements ISWOnRegisterInterface.ISWOnRegisterPresenterInterface {
    private static final SWLogger log = SWLogger.getLogger("SWRegisterPresenterImpl");
    private ISWOnRegisterInterface.ISWOnRegisterViewInterface mView;
    private SWRegisterModel mModel = new SWRegisterModel();

    public SWRegisterPresenterImpl(ISWOnRegisterInterface.ISWOnRegisterViewInterface callback) {
        mView = callback;
    }

    @Override
    public void register(BmobUser userInfo, final String userPassword) {
        log.d("register");
        final String userName = userInfo.getUsername();
        userInfo.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    mModel.saveUserInfo2SharePreferences(SWApplication.getContext(), userName, userPassword);
                    if (mView != null) {
                        mView.onRegisterSucceed();
                    } else {
                        new Throwable("error: the mCallback is null");
                    }
                } else {
                    if (mView != null) {
                        mView.onRegisterFailed(e.getMessage());
                    } else {
                        new Throwable("error: the mCallback is null");
                    }
                }
            }
        });
    }
}
