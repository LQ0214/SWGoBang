package net.sunniwell.gobang.presenter;

import android.graphics.Point;

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
    //是否轮到我方
    private boolean mIsMyTurn;
    //是否结束
    private boolean mIsOver = false;
    // 黑色棋子list
    private List<Point> mBlackArray;
    //白色棋子list
    private List<Point> mWhiteArray;

    /**
     * 棋子枚举类
     */
    private enum Chess {
        EMPTY(0), BLACK(1), WHITE(2);

        Chess(int i) {
        }
    }

    /**
     * 抽象方法，创建子model类
     *
     * @return ASWChessLogicModel
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

    /**
     * 落子失败方法
     */
    @Override
    public void playFailed() {
        mChessbroadView.playFailed();
    }

    /**
     * 落子成功方法
     *
     * @param point 点
     */
    @Override
    public void playSucceed(Point point) {
        mIsMyTurn = true;
        mChessbroadView.playSucceed(point);
    }

    /**
     * 重赛
     *
     * @param id userId
     */
    @Override
    public void restart(int id) {
        if (mChessLogicModel.restart(id)) {
            mIsOver = false;
            mChessbroadView.restartCompleted(id);
        }
    }

    /**
     * 悔棋
     *
     * @param id         userId
     * @param blackArray 黑棋列表
     * @param whiteArray 白棋列表
     */
    @Override
    public void undo(int id, List<Point> blackArray, List<Point> whiteArray) {
        if (mIsOver) {
            return;
        }
        if (mChessLogicModel.undo(id, blackArray, whiteArray)) {
            mChessbroadView.undoCompleted(id);
        }
    }

    /**
     * 认输
     *
     * @param id userId
     */
    @Override
    public void giveup(int id) {
        log.d("SWGoBangLog ,giveip id = " + id);
        if (mChessLogicModel.giveup(id)) {
            mChessbroadView.giveupCompleted(id);
        }
    }

    /**
     * 和棋
     *
     * @param id userId
     */
    @Override
    public void drawPiece(int id) {
        if (mChessLogicModel.drawPiece(id)) {
            mChessbroadView.drawPieveCompleted(id);
        }
    }

    /**
     * 判断是否五子连珠,游戏结束
     *
     * @param id userId
     * @param x  当前点x坐标
     * @param y  当前点y坐标
     */
    @Override
    public void isGameOverMethod(int id, int x, int y) {
        if (mChessLogicModel.isGameOverMethod(id, x, y)) {
            mIsOver = true;
            mChessbroadView.gameOverCompleted(id);
        }
    }

    /**
     * 算法
     *
     * @param x     当前点x坐标
     * @param y     当前点y坐标
     * @param depth 计算深度
     */
    @Override
    public void playPiece(int x, int y, int depth) {
        if (mIsOver) {
            return;
        }
        mIsMyTurn = false;
        mChessLogicModel.setPlayPieceListener(this);
        mChessLogicModel.playPiece(x, y, depth);
    }

    /**
     * 获取当前用户
     *
     * @return 用户对象
     */
    public BmobUser getUser() {
        return BmobUser.getCurrentUser();
    }

    public boolean isWhiteChessType() {
        return mChessType == Chess.WHITE;
    }

    /**
     * 处理棋子落点
     *
     * @param point point
     */
    public void handleChessPosition(Point point) {
        log.d("SWGoBangLog ,into gobang point.x = " + point.x + ",point.y = " + point.y + ",mChessType.ordinal() = " + mChessType.ordinal());

        if (mIsOver) {
            return;
        }
        isGameOverMethod(mChessType.ordinal(), point.x, point.y);
        if (isWhiteChessType()) {
            mWhiteArray.add(point);
            mChessType = Chess.BLACK;
        } else {
            mBlackArray.add(point);
            mChessType = Chess.WHITE;
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