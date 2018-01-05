package net.sunniwell.gobang.presenter;

import android.graphics.Point;

import java.util.List;

/**
 * 棋盘逻辑处理中间层接口
 */
public interface ISWGoBangPresenter {
    /**
     * 复赛
     */
    void restart();

    /**
     * 悔棋
     */
    void undo();

    /**
     * 认输
     */
    void giveup();

    /**
     * 求和
     */
    void draw();


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
