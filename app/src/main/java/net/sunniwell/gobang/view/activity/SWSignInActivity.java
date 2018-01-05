package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.utils.FragmentUtil;
import net.sunniwell.gobang.view.fragment.SWRegisterFragment;
import net.sunniwell.gobang.view.fragment.SWSignInFragment;
import net.sunniwell.jar.log.SWLogger;

/**
 * Created by lin on 2018/1/4.
 */

public class SWSignInActivity extends FragmentActivity {
    private static final SWLogger log = SWLogger.getLogger("SWSignInActivity");
    public static final String SHARE_PREFERENCES_USER_INFO = "userInfo";
    public static final String SHARE_PREFERENCES_USER_NAME = "userName";
    public static final String SHARE_PREFERENCES_USER_PASSWORD = "userPassword";
    private static Activity mActivity;

    private FragmentManager mFragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_main);
        log.d("onCreate");
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentUtil.show(mFragmentManager, SWSignInFragment.class.getSimpleName());
    }

    /**
     * 启动主activity,进入游戏页面
     */
    public static void startMainActivity() {
        log.d("startMainActivity");
        if(mActivity != null){
            Intent intent = new Intent(mActivity, SWMainHomeActivity.class);
            mActivity.startActivity(intent);
            mActivity.finish();
        } else {
            new Throwable("mContext = null");
        }
    }

    private void initData() {
        mActivity = this;
        SWRegisterFragment registerFragment = new SWRegisterFragment();
        FragmentUtil.add(mFragmentManager, R.id.id_sign_in_root, registerFragment, SWRegisterFragment.class.getSimpleName());
        SWSignInFragment signInFragment = new SWSignInFragment();
        FragmentUtil.add(mFragmentManager, R.id.id_sign_in_root, signInFragment, SWSignInFragment.class.getSimpleName());
    }
}
