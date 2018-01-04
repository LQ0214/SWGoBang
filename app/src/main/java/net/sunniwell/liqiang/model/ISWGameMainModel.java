package net.sunniwell.liqiang.model;

/**
 * 游戏主界面逻辑处理接口
 */
public interface ISWGameMainModel {
    void setUserId(int id);

    int getUserId();

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