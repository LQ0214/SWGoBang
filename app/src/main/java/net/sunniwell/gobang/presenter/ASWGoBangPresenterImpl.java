package net.sunniwell.gobang.presenter;

import android.graphics.Point;

import net.sunniwell.gobang.model.ASWChessLogicModel;
import net.sunniwell.gobang.view.ISWGoBangView;
import net.sunniwell.jar.log.SWLogger;

import java.util.List;

/**
 * 棋盘逻辑处理中间层实现类
 */
public abstract class ASWGoBangPresenterImpl implements ASWChessLogicModel.ISWPlayPiece, ISWGoBangPresentInterface {
    private SWLogger log = SWLogger.getLogger(ASWGoBangPresenterImpl.class.getSimpleName());
    private ISWGoBangView mChessbroadView;
    private ASWChessLogicModel mChessLogicModel;

    /**
     * 抽象方法，创建子model类
     *
     * @return
     */
    abstract ASWChessLogicModel createChessLogicModel();

    public ASWGoBangPresenterImpl(ISWGoBangView view) {
        this.mChessLogicModel = createChessLogicModel();
        this.mChessbroadView = view;
    }

    @Override
    public void playFailed() {
        mChessbroadView.playFailed();
    }

    @Override
    public void playSucceed(Point point) {
        mChessbroadView.playSucceed(point);
    }

    @Override
    public void restart(int id) {
        if (mChessLogicModel.restart(id)) {
            mChessbroadView.restartCompleted(id);
        }
    }

    @Override
    public void undo(int id) {
        if (mChessLogicModel.undo(id)) {
            mChessbroadView.undoCompleted(id);
        }
    }

    @Override
    public void giveup(int id) {
        log.d("hjx   ===>>>  View调了认输接口     ");
        if (mChessLogicModel.giveup(id)) {
            mChessbroadView.giveupCompleted(id);
        }
    }

    @Override
    public void drawPiece(int id) {
        if (mChessLogicModel.drawPiece(id)) {
            mChessbroadView.drawPieveCompleted(id);
        }
    }

    @Override
    public void isGameOverMethod(int id, int x, int y) {
        if (mChessLogicModel.isGameOverMethod(id, x, y)) {
            mChessbroadView.gameOverCompleted(id);
        }
    }

    @Override
    public void playPiece(int x, int y, int depth) {
        mChessLogicModel.setPlayPieceListener(this);
        mChessLogicModel.playPiece(x, y, depth);
    }
}