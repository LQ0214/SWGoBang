package net.sunniwell.gobang.liqiang.model;

import android.graphics.Point;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 */

/**
 * 棋盘逻辑处理接口
 */
public interface ISWChessLogicModel {
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