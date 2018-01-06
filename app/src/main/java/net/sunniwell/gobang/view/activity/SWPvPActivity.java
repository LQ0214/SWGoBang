package net.sunniwell.gobang.view.activity;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.presenter.SWPvpGoBangPresenterImpl;

/**
 * 联网模式Activity
 */

public class SWPvPActivity extends BasePlayActivity {
    @Override
    protected void initData() {
        super.initData();
        // 设置对战模式的presenter
        mGoBangView.setGoBangPresenter(new SWPvpGoBangPresenterImpl(mGoBangView));
        mOtherTextView.setText(R.string.string_word_playname);
    }
}
