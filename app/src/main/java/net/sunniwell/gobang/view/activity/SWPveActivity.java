package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.presenter.SWPveGoBangPresenterImpl;
import net.sunniwell.gobang.view.widget.SWGoBangView;
import net.sunniwell.gobang.view.widget.SWSectorMenuView;
import net.sunniwell.jar.log.SWLogger;

/**
 * Created by Xing on 2018/1/4.
 * 人机对战模式Activity
 */

public class SWPveActivity extends Activity implements SWGoBangView.ISWEventCompletedListener, SWSectorMenuView.ISWOnButtonClickListener {
    private SWLogger log = SWLogger.getLogger(SWPveActivity.class.getSimpleName());
    private SWGoBangView mGoBangView;
    private SWSectorMenuView mSectorMenuView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initView();
    }

    private void initView() {
        mGoBangView = (SWGoBangView) findViewById(R.id.play_game_board);
        // 设置对战模式的presenter
        mGoBangView.setGoBangPresenter(new SWPveGoBangPresenterImpl(mGoBangView));
        mGoBangView.setEventCompletedListener(this);
        mSectorMenuView = (SWSectorMenuView) findViewById(R.id.play_game_sector_menu);
        mSectorMenuView.setOnButtonClickListener(this);
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
                Toast.makeText(SWPveActivity.this, getResources().getString(R.string.game_board_give_up), Toast.LENGTH_LONG).show();
                mGoBangView.giveup();
                break;
            case R.id.btn_undo:
                Toast.makeText(SWPveActivity.this, getResources().getString(R.string.game_board_undo), Toast.LENGTH_LONG).show();
                mGoBangView.drawPiece();
                break;
            case R.id.btn_drawpiece:
                Toast.makeText(SWPveActivity.this, getResources().getString(R.string.game_board_draw_piece), Toast.LENGTH_LONG).show();
                mGoBangView.reStart();
                break;
            case R.id.btn_restart:
                Toast.makeText(SWPveActivity.this, getResources().getString(R.string.game_board_restart), Toast.LENGTH_LONG).show();
                mGoBangView.undo();
                break;
        }
    }
}
