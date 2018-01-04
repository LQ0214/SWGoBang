package net.sunniwell.liqiang.presenter;

import android.graphics.Point;

import net.sunniwell.liqiang.model.ISWChessLogicModel;
import net.sunniwell.liqiang.view.ISWChessbroadView;

import java.util.List;

/**
 * 棋盘逻辑处理中间层实现类
 */
public class SWChessLogicPresenterImpl implements ISWChessLogicPresenter {
    private ISWChessbroadView mChessbroadView;
    private ISWChessLogicModel mChessLogicModel;

    public SWChessLogicPresenterImpl(ISWChessbroadView view, ISWChessLogicModel user) {
        this.mChessbroadView = view;
        this.mChessLogicModel = user;
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