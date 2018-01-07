package net.sunniwell.gobang.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import net.sunniwell.gobang.SWApplication;
import net.sunniwell.gobang.iswinterface.ISWOnSignAboutInterface;
import net.sunniwell.gobang.view.activity.SWSignInActivity;
import net.sunniwell.jar.log.SWLogger;

/**
 * Created by lin on 2018/1/7.
 */

public class SWSignOutPresenterImpl implements ISWOnSignAboutInterface.ISWOnSignOutPresenterInter {

    private static final SWLogger log = SWLogger.getLogger("SWSignOutPresenterImpl");

    private ISWOnSignAboutInterface.ISWOnSignOutViewInterface mView;

    public SWSignOutPresenterImpl(ISWOnSignAboutInterface.ISWOnSignOutViewInterface view) {
        mView = view;
    }

    @Override
    public void signOut() {
        Context context = SWApplication.getContext();
        if (context != null) {
            SharedPreferences sharePreferences = context.getSharedPreferences(SWSignInActivity.SHARE_PREFERENCES_USER_INFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharePreferences.edit();
            editor.clear();
            editor.commit();
            mView.onSignOutSucceed();
        } else {
            new Throwable("context = " + context);
            mView.onSignOutFailed("");
        }
    }
}
