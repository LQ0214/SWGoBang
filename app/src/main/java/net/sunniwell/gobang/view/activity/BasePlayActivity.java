package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
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
