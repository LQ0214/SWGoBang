package net.sunniwell.gobang.presenter;

import android.content.Context;

import net.sunniwell.gobang.SWApplication;
import net.sunniwell.gobang.iswinterface.ISWOnSignAboutInterface;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.BmobUser;

/**
 * Created by lin on 2018/1/7.
 */

public class SWSignOutPresenterImpl implements ISWOnSignAboutInterface.ISWOnSignOutPresenterInter {

    private static final SWLogger log = SWLogger.getLogger(SWSignOutPresenterImpl.class.getSimpleName());

    private ISWOnSignAboutInterface.ISWOnSignOutViewInterface mView;

    public SWSignOutPresenterImpl(ISWOnSignAboutInterface.ISWOnSignOutViewInterface view) {
        mView = view;
    }

    /**
     * 登出
     */
    @Override
    public void signOut() {
        Context context = SWApplication.getContext();
        if (context != null) {
            BmobUser.logOut();
            mView.onSignOutSucceed();
        } else {
            new Throwable("context = " + context);
            mView.onSignOutFailed("");
        }
    }
}
