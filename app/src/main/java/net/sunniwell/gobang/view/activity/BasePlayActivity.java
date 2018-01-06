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
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.SWApplication;
import net.sunniwell.gobang.view.widget.SWGoBangView;
import net.sunniwell.gobang.view.widget.SWSectorMenuView;
import net.sunniwell.jar.log.SWLogger;

/**
 * Created by Administrator on 2018/1/6.
 */

public abstract class BasePlayActivity extends Activity implements SWGoBangView.ISWEventCompletedListener, SWSectorMenuView.ISWOnButtonClickListener {
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
        String userName = SWApplication.getUserInfoFromSharePreferences().getUsername();
        if (!TextUtils.isEmpty(userName))
            mMyTextView.setText(userName);
        mOtherTextView.setText(R.string.string_word_othername);
    }

    @Override
    public void restartCompleted() {
        initView();
        mGoBangView.invalidate();
    }

    @Override
    public void undoCompleted() {

    }

    @Override
    public void giveupCompleted() {

    }

    @Override
    public void drawPieceCompleted() {

    }

    @Override
    public void gameOverCompleted() {
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        final View view = LayoutInflater.from(this).inflate(R.layout.view_gobang_finish, null);
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
                Toast.makeText(BasePlayActivity.this, getResources().getString(R.string.game_board_give_up), Toast.LENGTH_LONG).show();
                mGoBangView.giveup();
                break;
            case R.id.btn_undo:
                Toast.makeText(BasePlayActivity.this, getResources().getString(R.string.game_board_undo), Toast.LENGTH_LONG).show();
                mGoBangView.drawPiece();
                break;
            case R.id.btn_drawpiece:
                Toast.makeText(BasePlayActivity.this, getResources().getString(R.string.game_board_draw_piece), Toast.LENGTH_LONG).show();
                mGoBangView.reStart();
                break;
            case R.id.btn_restart:
                Toast.makeText(BasePlayActivity.this, getResources().getString(R.string.game_board_restart), Toast.LENGTH_LONG).show();
                mGoBangView.undo();
                break;
        }
    }
}
