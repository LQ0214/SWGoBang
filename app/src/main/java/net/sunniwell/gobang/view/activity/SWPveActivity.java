package net.sunniwell.gobang.view.activity;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.presenter.SWPveGoBangPresenterImpl;

/**
 * Created by Xing on 2018/1/4.
 * 人机对战模式Activity
 */

public class SWPveActivity extends BasePlayActivity {

    @Override
    protected void initData() {
        super.initData();
        // 设置对战模式的presenter
        mGoBangView.setGoBangPresenter(new SWPveGoBangPresenterImpl(mGoBangView));
        mOtherTextView.setText(R.string.string_word_othername);
    }
}
