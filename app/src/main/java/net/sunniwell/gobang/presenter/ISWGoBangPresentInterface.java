package net.sunniwell.gobang.presenter;

import android.graphics.Point;

import java.util.List;

/**
 * Created by Xing on 2018/1/5.
 */

public interface ISWGoBangPresentInterface {
    void restart(int id);

    void undo(int id);

    void giveup(int id);

    void drawPiece(int id);

    void isGameOverMethod(int id, List<Point> whitePoints, List<Point> blackPoints);

    void isFiveConnect(List<Point> points);

    void playPiece(int x, int y, int depth);
}
