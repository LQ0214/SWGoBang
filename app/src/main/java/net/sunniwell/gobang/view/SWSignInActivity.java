package net.sunniwell.gobang.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.view.fragment.SWRegisterFragment;
import net.sunniwell.gobang.view.fragment.SWSignInFragment;
import net.sunniwell.jar.log.SWLogger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lin on 2018/1/4.
 */

public class SWSignInActivity extends FragmentActivity {
    private static final SWLogger log = SWLogger.getLogger("SWSignInActivity");
    public static final String SHARE_PREFERENCES_USER_INFO = "userInfo";
    public static final String SHARE_PREFERENCES_USER_NAME = "userName";
    public static final String SHARE_PREFERENCES_USER_PASSWORD = "userPassword";

    private static Map<Class, Fragment> mFragmentMap = new HashMap<Class, Fragment>(2);

    static {
        SWRegisterFragment registerFragment = new SWRegisterFragment();
        mFragmentMap.put(SWRegisterFragment.class, registerFragment);
        SWSignInFragment signInFragment = new SWSignInFragment();
        mFragmentMap.put(SWSignInFragment.class, signInFragment);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        log.d("onCreate");
    }



    /**
     * 启动主activity,进入游戏页面
     */
    public static void startMainActivity() {
        log.d("startMainActivity");
        // TODO: 2018/1/5
    }
}
