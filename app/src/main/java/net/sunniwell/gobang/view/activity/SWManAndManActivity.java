package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.os.Bundle;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.presenter.SWPvpGoBangPresenterImpl;
import net.sunniwell.gobang.view.widget.SWGoBangView;
import net.sunniwell.gobang.utils.SWGoBangConstant;
import net.sunniwell.jar.log.SWLogger;

/**
 * 联网模式Activity
 */

public class SWManAndManActivity extends Activity {
    private SWLogger log = SWLogger.getLogger(SWManAndManActivity.class.getSimpleName());
    private SWGoBangView mGoBangView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initView();
    }

    private void initView() {
        mGoBangView = (SWGoBangView) findViewById(R.id.pvp_piece_board);
        mGoBangView.setBattleMode(SWGoBangConstant.P_NET_BATTLE_MODE);
        mGoBangView.setPresenterImpl(new SWPvpGoBangPresenterImpl());
    }
}
