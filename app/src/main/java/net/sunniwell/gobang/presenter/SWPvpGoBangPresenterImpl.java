package net.sunniwell.gobang.presenter;

import net.sunniwell.gobang.model.ASWChessLogicModel;
import net.sunniwell.gobang.model.ISWChessLogicModel;
import net.sunniwell.gobang.model.SWPvpLogicModel;
import net.sunniwell.gobang.view.ISWGoBangView;

/**
 * Created by Administrator on 2018/1/4.
 */

public class SWPvpGoBangPresenterImpl extends ASWGoBangPresenterImpl {

    public SWPvpGoBangPresenterImpl(ISWGoBangView view) {
        super(view);
    }

    @Override
    ASWChessLogicModel createChessLogicModel() {
        return new SWPvpLogicModel();
    }
}
