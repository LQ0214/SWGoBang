package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.utils.FragmentUtil;
import net.sunniwell.gobang.view.fragment.SWRegisterFragment;
import net.sunniwell.gobang.view.fragment.SWSignInFragment;
import net.sunniwell.jar.log.SWLogger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lin on 2018/1/4.
 */

public class SWSignInActivity extends FragmentActivity {
    private static final SWLogger log = SWLogger.getLogger("SWSignInActivity");
    private static Activity mActivity;
    private Timer mTimer;

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private boolean mIsExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_main);
        log.d("SWGoBangLog:    onCreate");
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
        log.d("SWGoBangLog:  startMainActivity");
        if (mActivity != null) {
            Intent intent = new Intent(mActivity, SWMainHomeActivity.class);
            mActivity.startActivity(intent);
            mActivity.finish();
        } else {
            new Throwable("mContext = null");
        }
    }

    private void initData() {
        mActivity = this;
        mTimer = new Timer();
        mIsExit = false;
        SWRegisterFragment registerFragment = new SWRegisterFragment();
        FragmentUtil.add(mFragmentManager, R.id.id_sign_in_root, registerFragment, SWRegisterFragment.class.getSimpleName());
        SWSignInFragment signInFragment = new SWSignInFragment();
        FragmentUtil.add(mFragmentManager, R.id.id_sign_in_root, signInFragment, SWSignInFragment.class.getSimpleName());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int action = event.getAction();
        log.d("SWGoBangLog:   action = " + "   keyCode = " + keyCode);
        if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击返回键退出
     */
    private void exit() {
        if (!mIsExit) {
            mIsExit = true;
            Toast.makeText(this, getResources().getString(R.string.main_home_exit), Toast.LENGTH_SHORT).show();
            // 如果两秒内没连续点击2次，则认为不是退出行为
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mIsExit = false;
                }
            }, 2000);

        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void onDestroy() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onDestroy();
    }
}
