package net.sunniwell.gobang.presenter;

import android.graphics.Point;

import net.sunniwell.gobang.SWApplication;
import net.sunniwell.gobang.model.ASWChessLogicModel;
import net.sunniwell.gobang.view.ISWGoBangView;
import net.sunniwell.jar.log.SWLogger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * 棋盘逻辑处理中间层实现类
 */
public abstract class ASWGoBangPresenterImpl implements ASWChessLogicModel.ISWPlayPiece, ISWGoBangPresentInterface {
    private SWLogger log = SWLogger.getLogger(ASWGoBangPresenterImpl.class.getSimpleName());
    private ISWGoBangView mChessbroadView;
    private ASWChessLogicModel mChessLogicModel;
    private Chess mChessType;
    /**
     * 是否轮到我方
     */
    protected boolean mIsMyTurn;
    /**
     * 黑色棋子list
     */
    private List<Point> mBlackArray;
    /**
     * 白色棋子list
     */
    private List<Point> mWhiteArray;

    public enum Chess {
        EMPTY(0), BLACK(1), WHITE(2);

        Chess(int i) {
        }
    }

    /**
     * 抽象方法，创建子model类
     *
     * @return
     */
    abstract ASWChessLogicModel createChessLogicModel();

    public ASWGoBangPresenterImpl(ISWGoBangView view) {
        this.mChessLogicModel = createChessLogicModel();
        this.mChessbroadView = view;
        mBlackArray = new ArrayList<Point>();
        mWhiteArray = new ArrayList<Point>();
        mChessType = Chess.BLACK;
        mIsMyTurn = true;
    }

    @Override
    public void playFailed() {
        mChessbroadView.playFailed();
    }

    @Override
    public void playSucceed(Point point) {
        mIsMyTurn = true;
        mChessbroadView.playSucceed(point);
    }

    @Override
    public void restart(int id) {
        if (mChessLogicModel.restart(id)) {
            mIsOver = false;
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

    private boolean mIsOver = false;

    @Override
    public void isGameOverMethod(int id, int x, int y) {
        if (mChessLogicModel.isGameOverMethod(id, x, y)) {
            mIsOver = true;
            mChessbroadView.gameOverCompleted(id);
        }
    }

    @Override
    public void playPiece(int x, int y, int depth) {
        if(mIsOver){
            return;
        }
        mIsMyTurn = false;
        mChessLogicModel.setPlayPieceListener(this);
        mChessLogicModel.playPiece(x, y, depth);
    }

    public BmobUser getUser() {
        return SWApplication.getUserInfoFromSharePreferences();
    }

    public void setChessType(Chess chessType) {
        mChessType = chessType;
    }

    public Chess getChess() {
        return mChessType;
    }

    public boolean isWhiteChessType() {
        if (mChessType == Chess.WHITE)
            return true;
        else return false;
    }

    public void handleChessPosition(Point point) {
        log.d("into gobang point.x = " + point.x + ",point.y = " + point.y + ",getChess().ordinal() = " + getChess().ordinal());
        //判断五子连珠
        if(mIsOver){
            return;
        }
        isGameOverMethod(getChess().ordinal(), point.x, point.y);
        if (isWhiteChessType()) {
            mWhiteArray.add(point);
            setChessType(Chess.BLACK);
        } else {
            mBlackArray.add(point);
            setChessType(Chess.WHITE);
        }
    }

    public List<Point> getBlackArray() {
        return mBlackArray;
    }

    public List<Point> getWhiteArray() {
        return mWhiteArray;
    }

    public boolean isMyTurun() {
        return mIsMyTurn;
    }
}