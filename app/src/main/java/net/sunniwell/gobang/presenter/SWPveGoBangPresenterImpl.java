package net.sunniwell.gobang.presenter;

import net.sunniwell.gobang.model.ISWChessLogicModel;
import net.sunniwell.gobang.model.SWPveLogicModel;

/**
 * Created by Administrator on 2018/1/4.
 */

public class SWPveGoBangPresenterImpl extends ASWGoBangPresenterImpl {

    @Override
    ISWChessLogicModel createChessLogicModel() {
        return new SWPveLogicModel();
    }
}