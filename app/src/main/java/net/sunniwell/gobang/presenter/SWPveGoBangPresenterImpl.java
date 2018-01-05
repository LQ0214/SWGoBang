package net.sunniwell.gobang.presenter;

import net.sunniwell.gobang.model.ISWChessLogicModel;
import net.sunniwell.gobang.model.SWPveLogicModel;
import net.sunniwell.gobang.view.ISWGoBangView;

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
