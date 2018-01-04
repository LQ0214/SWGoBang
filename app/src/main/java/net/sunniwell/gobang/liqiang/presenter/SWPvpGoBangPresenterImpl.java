package net.sunniwell.gobang.liqiang.presenter;

import net.sunniwell.gobang.liqiang.model.ISWChessLogicModel;
import net.sunniwell.gobang.liqiang.model.SWPvpLogicModel;
import net.sunniwell.gobang.liqiang.view.ISWGoBangView;

/**
 * Created by Administrator on 2018/1/4.
 */

public class SWPvpGoBangPresenterImpl extends ASWGoBangPresenterImpl {
    public SWPvpGoBangPresenterImpl(ISWGoBangView view) {
        super(view);
    }

    @Override
    ISWChessLogicModel createChessLogicModel() {
        return new SWPvpLogicModel();
    }
}
