package net.sunniwell.gobang.view.activity;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
        // 设置对战模式的presenter
        mGoBangView.setGoBangPresenter(new SWPvpGoBangPresenterImpl(mGoBangView));
        mGoBangView.setEventCompletedListener(this);
        mSectorMenuView = (SWSectorMenuView) findViewById(R.id.play_game_sector_menu);
        mSectorMenuView.setOnButtonClickListener(this);
    }

    @Override
    public void restartCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    重新开始。。。");
        initView();
        mGoBangView.invalidate();
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
    public void drawPieceCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    和棋。。。");
    }

    @Override
    public void gameOverCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    游戏结束。。。");
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
        log.d("hjx  ===>>>  model回调过来成功    五子连珠。。。");
    }

    @Override
    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.btn_giveup:
                Toast.makeText(SWPvPActivity.this, getResources().getString(R.string.game_board_give_up), Toast.LENGTH_LONG).show();
                mGoBangView.giveup();
                break;
            case R.id.btn_undo:
                Toast.makeText(SWPvPActivity.this, getResources().getString(R.string.game_board_undo), Toast.LENGTH_LONG).show();
                mGoBangView.undo();
                break;
            case R.id.btn_drawpiece:
                Toast.makeText(SWPvPActivity.this, getResources().getString(R.string.game_board_draw_piece), Toast.LENGTH_LONG).show();
                mGoBangView.drawPiece();
                break;
            case R.id.btn_restart:
                Toast.makeText(SWPvPActivity.this, getResources().getString(R.string.game_board_restart), Toast.LENGTH_LONG).show();
                mGoBangView.reStart();
                break;
        }

    }
}
