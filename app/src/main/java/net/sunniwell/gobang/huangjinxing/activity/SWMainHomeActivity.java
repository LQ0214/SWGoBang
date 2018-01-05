package net.sunniwell.gobang.huangjinxing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.jar.log.SWLogger;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Xing on 2018/1/4.
 */

public class SWMainHomeActivity extends Activity {
    private SWLogger log = SWLogger.getLogger(SWMainHomeActivity.class.getSimpleName());
    private Timer mTimer;
    private boolean mIsExit;

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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        int action = event.getAction();
        log.d("hjx    ==SWMainHomeActivity==>>>  action = " + "   keyCode = " + keyCode);
        if (action == KeyEvent.ACTION_DOWN) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击退出
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
            mTimer = null;
        }
        super.onDestroy();
    }
}
