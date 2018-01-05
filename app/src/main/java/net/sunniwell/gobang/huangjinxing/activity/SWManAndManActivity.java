package net.sunniwell.gobang.huangjinxing.activity;

import android.app.Activity;
import android.os.Bundle;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.huangjinxing.custom.SWGoBangView;
import net.sunniwell.gobang.huangjinxing.utils.SWGoBangConstant;
import net.sunniwell.gobang.liqiang.presenter.SWPvpGoBangPresenterImpl;
import net.sunniwell.jar.log.SWLogger;

/**
 * 联网模式Activity
 */

public class SWManAndManActivity extends Activity{
    private SWLogger log = SWLogger.getLogger(SWManAndManActivity.class.getSimpleName());
    private SWGoBangView mGoBangView;
    private SWPvpGoBangPresenterImpl mPvpGoBangPresenterImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);
        initView();
    }

    private void initView() {
        mGoBangView = (SWGoBangView) findViewById(R.id.pvp_piece_board);
        mGoBangView.setBattleMode(SWGoBangConstant.P_NET_BATTLE_MODE);
    }
}
