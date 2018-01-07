package net.sunniwell.gobang.view;

import android.graphics.Point;

/**
 * 棋盘view接口
 */
public interface ISWGoBangView {
    /**
     * 复赛
     */
    void restartCompleted(int id);

    /**
     * 悔棋
     */
    void undoCompleted(int id);

    /**
     * 认输
     */
    void giveupCompleted(int id);

    /**
     * 求和
     */
    void drawPieveCompleted(int id);

    /**
     * 判断是否胜利
     */
    void gameOverCompleted(int id);

    /**
     * 落子成功
     */
    void playSucceed(Point point);

    /**
     * 落子失败
     */
    void playFailed();
}
