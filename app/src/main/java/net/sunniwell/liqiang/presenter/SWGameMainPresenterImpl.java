package net.sunniwell.liqiang.presenter;

import net.sunniwell.liqiang.model.ISWGameMainModel;
import net.sunniwell.liqiang.view.ISWGameMainView;

/**
 * 游戏主界面中间层实现类
 */
public class SWGameMainPresenterImpl implements ISWGameMainPresenter {
    private ISWGameMainView mFightView;
    private ISWGameMainModel mFightModel;

    public SWGameMainPresenterImpl(ISWGameMainView view, ISWGameMainModel user) {
        this.mFightView = view;
        this.mFightModel = user;
    }

    @Override
    public void restart() {
        if (mFightView != null)
            mFightView.restart();
    }

    @Override
    public void undo() {
        if (mFightView != null && mFightModel != null)
            mFightView.undo(mFightModel.getUserId());
    }

    @Override
    public void giveup() {
        if (mFightView != null && mFightModel != null)
            mFightView.giveup(mFightModel.getUserId());
    }

    @Override
    public void draw() {
        if (mFightView != null && mFightModel != null)
            mFightView.draw(mFightModel.getUserId());
    }
}