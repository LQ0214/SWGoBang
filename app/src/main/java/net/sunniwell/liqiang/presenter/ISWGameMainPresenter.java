package net.sunniwell.liqiang.presenter;

/**
 * 游戏主界面中间层接口
 */
public interface ISWGameMainPresenter {
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
}
