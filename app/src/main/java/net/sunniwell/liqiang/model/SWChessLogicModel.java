package net.sunniwell.liqiang.model;

import android.graphics.Point;

import java.util.List;

/**
 * 棋盘逻辑处理实现类
 */
public abstract class SWChessLogicModel implements ISWChessLogicModel {
    @Override
    public boolean isGameOverMethod(List<Point> whitePoints, List<Point> blackPoints) {
        return false;
    }

    @Override
    public boolean isFiveConnect(List<Point> points) {
        return false;
    }

    /**
     * 抽象算法
     */
    protected abstract void playMode();
}
