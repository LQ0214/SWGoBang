package net.sunniwell.gobang.presenter;

import android.graphics.Point;

import net.sunniwell.gobang.model.ISWChessLogicModel;
import net.sunniwell.gobang.view.ISWGoBangView;
import net.sunniwell.jar.log.SWLogger;

import java.util.List;

/**
 * 棋盘逻辑处理中间层实现类
 */
public abstract class ASWGoBangPresenterImpl {
    private SWLogger log  = SWLogger.getLogger(ASWGoBangPresenterImpl.class.getSimpleName());
    private ISWGoBangView mChessbroadView;
    private ISWChessLogicModel mChessLogicModel;

    public ASWGoBangPresenterImpl(ISWGoBangView view) {
        this.mChessLogicModel = createChessLogicModel();
        this.mChessbroadView = view;
    }


    public void restart(int id) {
        if (mChessLogicModel.restart(id)) {
            mChessbroadView.restartCompleted(id);
        }
    }

    public void undo(int id) {
        if (mChessLogicModel.undo(id)) {
            mChessbroadView.undoCompleted(id);
        }
    }

    public void giveup(int id) {
        log.d("hjx   ===>>>  View调了认输接口    isTrue = "+mChessLogicModel.giveup(id));
        if (mChessLogicModel.giveup(id)) {
            mChessbroadView.giveupCompleted(id);
        }

    }

    public void drawPiece(int id) {
        if (mChessLogicModel.drawPiece(id)) {
            mChessbroadView.drawPieveCompleted(id);
        }
    }

    public void isGameOverMethod(int id, List<Point> whitePoints, List<Point> blackPoints) {
        if (mChessLogicModel.isGameOverMethod(id, whitePoints, blackPoints)) {
            mChessbroadView.gameOverCompleted(id);
        }
    }

    public void isFiveConnect(List<Point> points) {
        if (mChessLogicModel.isFiveConnect(points)) {
            mChessbroadView.fiveConnectCompleted();
        }
    }

    abstract ISWChessLogicModel createChessLogicModel();
}