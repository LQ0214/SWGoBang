package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.iswinterface.ISWOnSignAboutInterface;
import net.sunniwell.gobang.presenter.SWSignOutPresenterImpl;
import net.sunniwell.jar.log.SWLogger;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.BmobUser;

/**
 * Created by Xing on 2018/1/4.
 */

public class SWMainHomeActivity extends Activity implements View.OnClickListener, ISWOnSignAboutInterface.ISWOnSignOutViewInterface {
    private SWLogger log = SWLogger.getLogger(SWMainHomeActivity.class.getSimpleName());
    private Timer mTimer;
    private boolean mIsExit;
    private Button mBtn_pvp, mBtn_pve, mBtn_setting, mBtn_about;
    private TextView mWelcomeTextView;
    private SWSignOutPresenterImpl mSignOutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        mTimer = new Timer();
        mIsExit = false;
        mSignOutPresenter = new SWSignOutPresenterImpl(this);
        mBtn_pvp = (Button) findViewById(R.id.main_home_pvp);
        mBtn_pve = (Button) findViewById(R.id.main_home_pve);
        mBtn_setting = (Button) findViewById(R.id.main_home_setting);
        mBtn_about = (Button) findViewById(R.id.main_home_about);
        mBtn_about = (Button) findViewById(R.id.main_home_about);
        mWelcomeTextView = (TextView) findViewById(R.id.main_home_welcome);

        mBtn_pvp.setOnClickListener(this);
        mBtn_pve.setOnClickListener(this);
        mBtn_setting.setOnClickListener(this);
        mBtn_about.setOnClickListener(this);

        String userName = BmobUser.getCurrentUser().getUsername();
        log.d("SWGoBangLog:  userName = " + userName);
        if (!TextUtils.isEmpty(userName))
            mWelcomeTextView.setText(getResources().getString(R.string.main_home_welcome, userName));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int action = event.getAction();
        log.d("SWGoBangLog:  action = " + "   keyCode = " + keyCode);
        if (action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
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
            Toast.makeText(SWMainHomeActivity.this, getResources().getString(R.string.main_home_exit), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_home_pvp:
                Intent pvpIntent = new Intent(this, SWPvPActivity.class);
                startActivity(pvpIntent);
                break;
            case R.id.main_home_pve:
                Intent pveIntent = new Intent(this, SWPveActivity.class);
                startActivity(pveIntent);
                break;
            case R.id.main_home_setting:
                if (mSignOutPresenter != null) {
                    mSignOutPresenter.signOut();
                }
                break;
            case R.id.main_home_about:
                Intent aboutIntent = new Intent(this, SWAboutAcitvity.class);
                startActivity(aboutIntent);
                break;
        }
    }

    @Override
    public void onSignOutSucceed() {
        Intent intent = new Intent(this, SWSignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSignOutFailed(String reason) {
    }
}
