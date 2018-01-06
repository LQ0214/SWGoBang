package net.sunniwell.gobang.presenter;

import android.graphics.Point;

import net.sunniwell.gobang.model.ASWChessLogicModel;
import net.sunniwell.gobang.model.SWPveLogicModel;
import net.sunniwell.gobang.view.ISWGoBangView;

/**
 * Created by Administrator on 2018/1/4.
 */

public class SWPveGoBangPresenterImpl extends ASWGoBangPresenterImpl {

    public SWPveGoBangPresenterImpl(ISWGoBangView view) {
        super(view);
        mIsMyTurn = true;
    }

    @Override
    ASWChessLogicModel createChessLogicModel() {
        return new SWPveLogicModel();
    }

    @Override
    public void playSucceed(Point point) {
        super.playSucceed(point);
        mIsMyTurn = true;
    }

    @Override
    public void handleChessPosition(Point point) {
        super.handleChessPosition(point);
        mIsMyTurn = false;
    }
}
