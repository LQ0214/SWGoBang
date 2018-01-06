package net.sunniwell.gobang.presenter;

/**
 * Created by Xing on 2018/1/5.
 */

public interface ISWGoBangPresentInterface {
    void restart(int id);

    void undo(int id);

    void giveup(int id);

    void drawPiece(int id);

    void isGameOverMethod(int id, int x, int y);

    void playPiece(int x, int y, int depth);
}
