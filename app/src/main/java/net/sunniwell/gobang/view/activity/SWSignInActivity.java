package net.sunniwell.gobang.view.activity;

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
        FragmentUtil.show(mFragmentManager, SWRegisterFragment.class.getSimpleName());
    }

    /**
     * 启动主activity,进入游戏页面
     */
    public static void startMainActivity() {
        log.d("startMainActivity");
        // TODO: 2018/1/5
    }

    private void initData() {
        SWRegisterFragment registerFragment = new SWRegisterFragment();
        FragmentUtil.add(mFragmentManager, R.id.id_sign_in_root, registerFragment, SWRegisterFragment.class.getSimpleName());
        SWSignInFragment signInFragment = new SWSignInFragment();
        FragmentUtil.add(mFragmentManager, R.id.id_sign_in_root, signInFragment, SWSignInFragment.class.getSimpleName());
    }

}
