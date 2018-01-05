package net.sunniwell.gobang.model;

import android.graphics.Point;

import java.util.List;

/**
 * 人机对战逻辑处理类
 */
public class SWPveLogicModel extends ASWChessLogicModel {

    @Override
    public boolean restart(int id) {
        return false;
    }

    @Override
    public boolean undo(int id) {
        return false;
    }

    @Override
    public boolean giveup(int id) {
        return false;
    }

    @Override
    public boolean drawPiece(int id) {
        return false;
    }

    @Override
    public boolean isGameOverMethod(int id, List<Point> whitePoints, List<Point> blackPoints) {
        return false;
    }

    @Override
    public boolean isFiveConnect(List<Point> points) {
        return false;
    }

    @Override
    public Point playPiece(int id, List<Point> whitePoints, List<Point> blackPoints) {
        //TODO 人机算法 计算棋子落子的point 成功回调 mPlayPiece.playSucceed 失败回调mPlayPiece.playFailed
        Point point = new Point();
        return point;
    }
}