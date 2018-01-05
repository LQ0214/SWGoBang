package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.presenter.SWPvpGoBangPresenterImpl;
import net.sunniwell.gobang.view.widget.SWGoBangView;
import net.sunniwell.gobang.view.widget.SWSectorMenuView;
import net.sunniwell.jar.log.SWLogger;

/**
 * 联网模式Activity
 */

public class SWPvPActivity extends Activity implements SWGoBangView.ISWEventCompletedListener, SWSectorMenuView.ISWOnButtonClickListener {
    private SWLogger log = SWLogger.getLogger(SWPvPActivity.class.getSimpleName());
    private SWGoBangView mGoBangView;
    private SWSectorMenuView mSectorMenuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initView();
    }

    private void initView() {
        mGoBangView = (SWGoBangView) findViewById(R.id.play_game_board);
        // 设置对战模式
        mGoBangView.setGoBangPresenter(new SWPvpGoBangPresenterImpl(mGoBangView));
        mGoBangView.setEventCompletedListener(this);
        mGoBangView.setUserId(789);
        mSectorMenuView = (SWSectorMenuView) findViewById(R.id.play_game_sector_menu);
        mSectorMenuView.setOnButtonClickListener(this);

    }

    @Override
    public void restartCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    重新开始。。。");
    }

    @Override
    public void undoCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    悔棋。。。");
    }

    @Override
    public void giveupCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    认输。。。");
    }

    @Override
    public void drawPieveCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    和棋。。。");
    }

    @Override
    public void gameOverCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    游戏结束。。。");
    }

    @Override
    public void fiveConnectCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    五子连珠。。。");
    }

    @Override
    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.btn_giveup:
                Toast.makeText(SWPvPActivity.this, getResources().getString(R.string.game_board_give_up), Toast.LENGTH_LONG).show();
                mGoBangView.giveup(789);
                break;
            case R.id.btn_undo:
                Toast.makeText(SWPvPActivity.this, getResources().getString(R.string.game_board_undo), Toast.LENGTH_LONG).show();
                mGoBangView.drawPiece(789);
                break;
            case R.id.btn_drawpiece:
                Toast.makeText(SWPvPActivity.this, getResources().getString(R.string.game_board_draw_piece), Toast.LENGTH_LONG).show();
                mGoBangView.reStart(789);
                break;
            case R.id.btn_restart:
                Toast.makeText(SWPvPActivity.this, getResources().getString(R.string.game_board_restart), Toast.LENGTH_LONG).show();
                mGoBangView.undo(789);
                break;
        }

    }
}
