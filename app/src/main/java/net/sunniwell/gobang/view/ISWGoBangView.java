package net.sunniwell.gobang.view;

import android.graphics.Point;

import java.util.List;

/**
 * 棋盘view接口
 */
public interface ISWGoBangView {
    /**
     * 复赛
     */
    void restartCompleted();

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
    void drawCompleted(int id);

    /**
     * 判断是否胜利
     */
    boolean gameOverCompleted();

    /**
     * 是否五子连珠
     */
    boolean fiveConnectCompleted();
}
