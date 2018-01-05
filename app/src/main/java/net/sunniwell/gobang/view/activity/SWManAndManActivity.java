package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.presenter.SWPvpGoBangPresenterImpl;
import net.sunniwell.gobang.view.widget.SWGoBangView;
import net.sunniwell.gobang.utils.SWGoBangConstant;
import net.sunniwell.jar.log.SWLogger;

/**
 * 联网模式Activity
 */

public class SWManAndManActivity extends Activity implements SWGoBangView.ISWEventCompletedListener {
    private SWLogger log = SWLogger.getLogger(SWManAndManActivity.class.getSimpleName());
    private SWGoBangView mGoBangView;
    private Button btn_renshu, btn_heqi, btn_chongkai, btn_huiqi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initView();
    }

    private void initView() {
        mGoBangView = (SWGoBangView) findViewById(R.id.pvp_piece_board);
        // 设置对战模式
        mGoBangView.setBattleMode(SWGoBangConstant.P_NET_BATTLE_MODE);
        mGoBangView.setEventCompletedListener(this);


        btn_renshu = (Button) findViewById(R.id.btn_renshu);
        btn_renshu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.d("hjx   ===>> 点击认输按钮。。。。");
                Toast.makeText(SWManAndManActivity.this, getResources().getString(R.string.game_board_give_up), Toast.LENGTH_LONG).show();
                mGoBangView.giveup(789);
            }
        });
        btn_heqi = (Button) findViewById(R.id.btn_heqi);
        btn_heqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SWManAndManActivity.this, getResources().getString(R.string.game_board_draw_piece), Toast.LENGTH_LONG).show();
                mGoBangView.drawPiece(789);
            }
        });
        btn_chongkai = (Button) findViewById(R.id.btn_chongkai);
        btn_chongkai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SWManAndManActivity.this, getResources().getString(R.string.game_board_restart), Toast.LENGTH_LONG).show();
                mGoBangView.reStart(789);
            }
        });
        btn_huiqi = (Button) findViewById(R.id.btn_huiqi);
        btn_huiqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SWManAndManActivity.this, getResources().getString(R.string.game_board_undo), Toast.LENGTH_LONG).show();
                mGoBangView.undo(789);
            }
        });
    }

    @Override
    public void restartCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    restartCompleted。。。");

    }

    @Override
    public void undoCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    undoCompleted。。。");
    }

    @Override
    public void giveupCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    giveupCompleted。。。");
    }

    @Override
    public void drawPieveCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    drawPieveCompleted。。。");
    }

    @Override
    public void gameOverCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    gameOverCompleted。。。");
    }

    @Override
    public void fiveConnectCompleted() {
        log.d("hjx  ===>>>  model回调过来成功    fiveConnectCompleted。。。");
    }
}
