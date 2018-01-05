package net.sunniwell.gobang.presenter;

import android.graphics.Point;

import net.sunniwell.gobang.model.ISWChessLogicModel;
import net.sunniwell.gobang.view.ISWGoBangView;

import java.util.List;

/**
 * 棋盘逻辑处理中间层实现类
 */
public abstract class ASWGoBangPresenterImpl implements ISWGoBangPresenter {
    private ISWGoBangView mChessbroadView;
    private ISWChessLogicModel mChessLogicModel;

    public ASWGoBangPresenterImpl() {
        this.mChessLogicModel = createChessLogicModel();
    }

    public void setGoBangViewListener(ISWGoBangView view) {
        this.mChessbroadView = view;
    }

    abstract ISWChessLogicModel createChessLogicModel();

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
        if (mChessLogicModel != null && mChessbroadView != null) {
            mChessbroadView.fiveConnectCompleted();
            return mChessLogicModel.isGameOverMethod(whitePoints, blackPoints);
        } else {
            return false;
        }
    }

    @Override
    public boolean isFiveConnect(List<Point> points) {
        if (mChessLogicModel != null)
            return mChessLogicModel.isFiveConnect(points);
        else
            return false;
    }
}