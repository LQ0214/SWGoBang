package net.sunniwell.gobang.liqiang.model;

import net.sunniwell.gobang.liqiang.bean.User;

/**
 * 游戏主界面逻辑处理实现类
 */
public class SWGameMainModel implements ISWGameMainModel {
    private User mUser;

    public SWGameMainModel(User user) {
        this.mUser = user;
    }

    @Override
    public void setUserId(int id) {
        mUser.setUserId(id);
    }

    @Override
    public int getUserId() {
        return mUser.getUserId();
    }

    @Override
    public void restart() {

    }

    @Override
    public void undo(int id) {

    }

    @Override
    public void giveup(int id) {

    }

    @Override
    public void draw(int id) {

    }
}