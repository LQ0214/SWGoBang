package net.sunniwell.liqiang.view;

/**
 * 棋盘view接口
 */
public interface ISWChessbroadView {
    /**
     * 复赛
     */
    void restart();

    /**
     * 悔棋
     */
    void undo(int id);

    /**
     * 认输
     */
    void giveup(int id);

    /**
     * 求和
     */
    void draw(int id);
}
