package net.sunniwell.gobang.liqiang.presenter;

import android.graphics.Point;

import java.util.List;

/**
 * 棋盘逻辑处理中间层接口
 */
public interface ISWChessLogicPresenter {

    /**
     * 判断是否胜利
     */
    boolean isGameOverMethod(List<Point> whitePoints, List<Point> blackPoints);

    /**
     * 是否五子连珠
     *
     * @param points
     * @return
     */
    boolean isFiveConnect(List<Point> points);
}