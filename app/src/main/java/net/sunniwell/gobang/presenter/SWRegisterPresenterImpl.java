package net.sunniwell.gobang.presenter;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.iswinterface.ISWOnRegisterInterface;
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

    public SWRegisterPresenterImpl(ISWOnRegisterInterface.ISWOnRegisterViewInterface callback) {
        mView = callback;
    }

    @Override
    public void register(BmobUser userInfo, final String userPassword, String smsCode) {
        log.d("register");
        final String userName = userInfo.getUsername();
        final String telNumber = userInfo.getMobilePhoneNumber();
        if (TextUtils.isEmpty(telNumber) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(smsCode)) {
            mView.onRegisterFailed(((Fragment) mView).getString(R.string.string_please_fill_in_complete_info));
            return;
        }
        userInfo.signOrLogin(smsCode, new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
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
