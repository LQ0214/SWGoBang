package net.sunniwell.gobang.model;

import android.graphics.Point;

import java.util.List;

/**
 * 棋盘逻辑处理实现类
 */
public abstract class ASWChessLogicModel extends SWBaseModel implements ISWChessLogicModel {
    public interface ISWPlayPiece {
        void playSucceed(Point point);
        void playFailed();
    }

    public ISWPlayPiece mPlayPiece;

    public void setPlayPieceListener(ISWPlayPiece playPiece) {
        mPlayPiece = playPiece;
    }

    public abstract Point playPiece(int x, int y, int depth);
}