package net.sunniwell.gobang.liqiang.presenter;

import net.sunniwell.gobang.liqiang.model.ISWChessLogicModel;
import net.sunniwell.gobang.liqiang.model.SWPveLogicModel;
import net.sunniwell.gobang.liqiang.view.ISWGoBangView;

/**
 * Created by Administrator on 2018/1/4.
 */

public class SWPveGoBangPresenterImpl extends ASWGoBangPresenterImpl {
    public SWPveGoBangPresenterImpl(ISWGoBangView view) {
        super(view);
    }

    @Override
    ISWChessLogicModel createChessLogicModel() {
        return new SWPveLogicModel();
    }
}
