package net.sunniwell.gobang.model;

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
     * 重新开始
     */
    boolean restart(int id);

    /**
     * 悔棋
     */
    boolean undo(int id, List<Point> blackArray, List<Point> whiteArray);

    /**
     * 认输
     */
    boolean giveup(int id);

    /**
     * 和棋
     */
    boolean drawPiece(int id);

    /**
     * 判断是否胜利
     */
    boolean isGameOverMethod(int id, int x, int y);
}