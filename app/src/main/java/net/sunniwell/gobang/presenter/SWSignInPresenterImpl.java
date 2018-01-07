package net.sunniwell.gobang.presenter;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import net.sunniwell.gobang.R;
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

    private static final SWLogger log = SWLogger.getLogger(SWSignInPresenterImpl.class.getSimpleName());
    private ISWOnSignAboutInterface.ISWOnSignInViewInterface mSignInView;

    public SWSignInPresenterImpl(ISWOnSignAboutInterface.ISWOnSignInViewInterface view) {
        mSignInView = view;
    }

    /**
     * 登录
     *
     * @param account      账号
     * @param userPassword 密码
     */
    @Override
    public void signIn(final String account, final String userPassword) {
        if (TextUtils.isEmpty(account) || TextUtils.isEmpty(userPassword)) {
            mSignInView.onSignInFailed(((Fragment) mSignInView).getString(R.string.string_please_fill_in_complete_info));
            return;
        }
        log.d("SWGoBangLog ,signIn");
        //手机和用户名登录
        BmobUser.loginByAccount(account, userPassword, new LogInListener<BmobUser>() {
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
    }

    @Override
    public BmobUser getUserInfo() {
        return BmobUser.getCurrentUser();
    }
}
