package net.sunniwell.gobang.liqiang.presenter;

import android.graphics.Point;

import net.sunniwell.gobang.liqiang.model.ISWChessLogicModel;
import net.sunniwell.gobang.liqiang.view.ISWGoBangView;

import java.util.List;

/**
 * 棋盘逻辑处理中间层实现类
 */
public class SWGoBangPresenterImpl implements ISWGoBangPresenter {
    private ISWGoBangView mChessbroadView;
    private ISWChessLogicModel mChessLogicModel;

    public SWGoBangPresenterImpl(ISWGoBangView view, ISWChessLogicModel user) {
        this.mChessbroadView = view;
        this.mChessLogicModel = user;
    }

    @Override
    public void restart() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void giveup() {

    }

    @Override
    public void draw() {

    }

    @Override
    public boolean isGameOverMethod(List<Point> whitePoints, List<Point> blackPoints) {
        if (mChessLogicModel != null && mChessbroadView != null)
            return mChessLogicModel.isGameOverMethod(whitePoints, blackPoints);
        else
            return false;
    }

    @Override
    public boolean isFiveConnect(List<Point> points) {
        if (mChessLogicModel != null)
            return mChessLogicModel.isFiveConnect(points);
        else
            return false;
    }
}