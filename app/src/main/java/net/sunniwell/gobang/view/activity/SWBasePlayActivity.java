package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.view.widget.SWGoBangView;
import net.sunniwell.gobang.view.widget.SWSectorMenuView;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2018/1/6.
 */

public abstract class SWBasePlayActivity extends Activity implements SWGoBangView.ISWEventCompletedListener, SWSectorMenuView.ISWOnButtonClickListener {
    protected SWLogger log = SWLogger.getLogger(SWPveActivity.class.getSimpleName());
    protected SWGoBangView mGoBangView;
    protected SWSectorMenuView mSectorMenuView;
    protected TextView mMyTextView;
    protected TextView mOtherTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initView();
        initData();
    }

    private void initView() {
        mGoBangView = (SWGoBangView) findViewById(R.id.play_game_board);
        mGoBangView.setEventCompletedListener(this);
        mSectorMenuView = (SWSectorMenuView) findViewById(R.id.play_game_sector_menu);
        mSectorMenuView.setOnButtonClickListener(this);
        mMyTextView = (TextView) findViewById(R.id.play_game_my_id);
        mOtherTextView = (TextView) findViewById(R.id.play_game_other_id);
    }


    protected void initData() {
        String userName = BmobUser.getCurrentUser().getUsername();
        if (!TextUtils.isEmpty(userName))
            mMyTextView.setText(userName);
        mOtherTextView.setText(R.string.string_word_othername);
    }

    @Override
    public void restartCompleted() {
        initView();
        initData();
        mGoBangView.invalidate();
    }

    @Override
    public void undoCompleted() {
        mGoBangView.invalidate();
    }

    @Override
    public void giveupCompleted() {

    }

    @Override
    public void drawPieceCompleted() {

    }

    @Override
    public void gameOverCompleted(boolean isWhiteSuccess) {
        log.d("SWGoBangLog:" + isWhiteSuccess);
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        final View view = LayoutInflater.from(this).inflate(R.layout.view_gobang_finish, null);
        TextView whoWinTip = (TextView) view.findViewById(R.id.id_who_win);
        if(isWhiteSuccess){
            whoWinTip.setText(R.string.string_white_win);
        } else {
            whoWinTip.setText(R.string.string_black_win);
        }
        Button back = (Button) view.findViewById(R.id.id_gobang_finish_return);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoBangView.setEnabled(false);
                windowManager.removeViewImmediate(view);
            }
        });
        WindowManager.LayoutParams wlp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        wlp.dimAmount = 0.0f;
        wlp.format = PixelFormat.TRANSPARENT;
        wlp.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN;
        wlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.gravity = Gravity.CENTER;
        windowManager.addView(view, wlp);
    }

    @Override
    public void fiveConnectCompleted() {

    }

    @Override
    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.btn_giveup:
                mGoBangView.giveup();
                break;
            case R.id.btn_undo:
                mGoBangView.undo();
                break;
            case R.id.btn_drawpiece:
                mGoBangView.drawPiece();
                break;
            case R.id.btn_restart:
                mGoBangView.reStart();
                break;
        }
    }
}
