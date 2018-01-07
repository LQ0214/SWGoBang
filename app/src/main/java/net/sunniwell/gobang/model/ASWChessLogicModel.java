package net.sunniwell.gobang.model;

import android.graphics.Point;

import net.sunniwell.gobang.utils.SWGoBangConstant;
import net.sunniwell.jar.log.SWLogger;

import java.util.List;

/**
 * 棋盘逻辑处理实现类
 */
public abstract class ASWChessLogicModel implements ISWChessLogicModel {
    private static final SWLogger log = SWLogger.getLogger("ASWChessLogicModel");
    protected int mRawAndColumnCount;   // 行列数，默认为15
    protected int[][] mPosition;    // 存储棋盘信息的二维数组
    protected ISWPlayPiece mPlayPiece;

    public interface ISWPlayPiece {
        /**
         * 落子成功回调
         *
         * @param point
         */
        void playSucceed(Point point);

        /**
         * 落子失败回调
         */
        void playFailed();
    }

    public ASWChessLogicModel() {
        this(SWGoBangConstant.LINE_NUM);
    }

    public ASWChessLogicModel(int count) {
        mRawAndColumnCount = count;
        mPosition = new int[mRawAndColumnCount][mRawAndColumnCount];
    }

    /**
     * 外部调用接口，供P设置回调对象
     *
     * @param playPiece
     */
    public void setPlayPieceListener(ISWPlayPiece playPiece) {
        mPlayPiece = playPiece;
    }

    /**
     * 通知对方落子位置
     *
     * @param x     已经确定下子的x，即二维数组的x下标
     * @param y     已经确定下子的y，即二维数组的y下标
     * @param depth 深度，人机对战时，让机器往后考虑的不熟，根据目前的算法，depth为>1时，会导致计算结果太久。
     * @return
     */
    public abstract Point playPiece(int x, int y, int depth);

    /**
     * 判断是否五子连珠的接口
     *
     * @param id 当前用户的id，用于判断当前是哪一方赢了
     * @param x  已经确定下子的x，即二维数组的x下标
     * @param y  已经确定下子的y，即二维数组的y下标
     * @return 游戏是否结束
     */
    @Override
    public boolean isGameOverMethod(int id, int x, int y) {
        if (x >= mRawAndColumnCount || x < 0 || y >= mRawAndColumnCount || y < 0) {
            new IllegalArgumentException("x = " + x + ", y = " + y);
        }
        mPosition[x][y] = id;
        // 用于判断每个方向是否五子连珠；
        boolean isGameOver = checkXDirection(id, x, y) || checkYDirection(id, x, y) || checkXYDirection(id, x, y) || checkYXDirection(id, x, y);
        log.d("SWGoBangLog: isGameOver  = " + isGameOver + ",id  = " + id);
        return isGameOver;
    }

    /**
     * 检查当前落子在X轴是否形成五子连珠
     *
     * @param id 用户id
     * @param x  已经确定下子的x，即二维数组的x下标
     * @param y  已经确定下子的y，即二维数组的y下标
     * @return
     */
    private boolean checkXDirection(int id, int x, int y) {
        int count = 0;
        boolean isBackwardStop = false;
        boolean isForwardStop = false;
        for (int backward = x, forward = x; backward >= 0 && forward < mRawAndColumnCount; ) {
            for (int step = 0; step < 5; step++) {
                if (step == 0) {
                    count++;
                } else {
                    if (checkPiece(id, x - step, y, isBackwardStop)) {
                        count++;
                    } else {
                        isBackwardStop = true;
                    }

                    if (checkPiece(id, x + step, y, isForwardStop)) {
                        count++;
                    } else {
                        isForwardStop = true;
                    }
                }
                log.d("SWGoBangLog: count = " + count + " isBackwardStop = " + isBackwardStop + " isForwardStop = " + isForwardStop);
                if (count == 5) {
                    return true;
                } else if (isBackwardStop && isForwardStop) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 检查当前落子在Y轴是否形成五子连珠
     *
     * @param id 用户id
     * @param x  已经确定下子的x，即二维数组的x下标
     * @param y  已经确定下子的y，即二维数组的y下标
     * @return
     */
    private boolean checkYDirection(int id, int x, int y) {
        int count = 0;
        boolean isBackwardStop = false;
        boolean isForwardStop = false;
        for (int step = 0; step < 5; step++) {
            if (step == 0) {
                count++;
            } else {
                if (checkPiece(id, x, y - step, isBackwardStop)) {
                    count++;
                } else {
                    isBackwardStop = true;
                }
                if (checkPiece(id, x, y + step, isForwardStop)) {
                    count++;
                } else {
                    isForwardStop = true;
                }
            }
            log.d("SWGoBangLog: count = " + count + " isBackwardStop = " + isBackwardStop + " isForwardStop = " + isForwardStop);
            if (count == 5) {
                return true;
            } else if (isBackwardStop && isForwardStop) {
                return false;
            }
        }
        return false;
    }

    /**
     * 检查当前落子在/的方向是否形成五子连珠
     *
     * @param id 用户id
     * @param x  已经确定下子的x，即二维数组的x下标
     * @param y  已经确定下子的y，即二维数组的y下标
     * @return
     */
    private boolean checkXYDirection(int id, int x, int y) {
        int count = 0;
        boolean isBackwardStop = false;
        boolean isForwardStop = false;
        for (int step = 0; step < 5; step++) {
            if (step == 0) {
                count++;
            } else {
                if (checkPiece(id, x - step, y - step, isBackwardStop)) {
                    count++;
                } else {
                    isBackwardStop = true;
                }

                if (checkPiece(id, x + step, y + step, isForwardStop)) {
                    count++;
                } else {
                    isForwardStop = true;
                }
            }
            log.d("SWGoBangLog: count = " + count + " isBackwardStop = " + isBackwardStop + " isForwardStop = " + isForwardStop);
            if (count == 5) {
                return true;
            } else if (isBackwardStop && isForwardStop) {
                return false;
            }
        }
        return false;
    }

    /**
     * 检查当前落子在\的方向是否形成五子连珠
     *
     * @param id 用户id
     * @param x  已经确定下子的x，即二维数组的x下标
     * @param y  已经确定下子的y，即二维数组的y下标
     * @return
     */
    private boolean checkYXDirection(int id, int x, int y) {
        int count = 0;
        boolean isBackwardStop = false;
        boolean isForwardStop = false;
        for (int step = 0; step < 5; step++) {
            if (step == 0) {
                count++;
            } else {
                if (checkPiece(id, x - step, y + step, isBackwardStop)) {
                    count++;
                } else {
                    isBackwardStop = true;
                }

                if (checkPiece(id, x + step, y - step, isForwardStop)) {
                    count++;
                } else {
                    isForwardStop = true;
                }
            }
            log.d("SWGoBangLog: count = " + count + " isBackwardStop = " + isBackwardStop + " isForwardStop = " + isForwardStop);
            if (count == 5) {
                return true;
            } else if (isBackwardStop && isForwardStop) {
                return false;
            }
        }
        return false;
    }

    /**
     * @param id     用户id
     * @param x      已经确定下子的x，即二维数组的x下标
     * @param y      已经确定下子的y，即二维数组的y下标
     * @param isStop 该方向是否处于停止搜索的状态
     * @return
     */
    private boolean checkPiece(int id, int x, int y, boolean isStop) {
        if (isStop) {
            return false;
        }
        if (x >= 0 && y >= 0 && x < mRawAndColumnCount && y < mRawAndColumnCount && mPosition[x][y] == id) {
            return true;
        }
        return false;
    }

    /**
     * 悔棋操作，清空二维数组的数据
     *
     * @param id
     * @param blackArray
     * @param whiteArray
     * @return
     */
    @Override
    public boolean undo(int id, List<Point> blackArray, List<Point> whiteArray) {
        Point point;
        if (blackArray.size() > 0 && whiteArray.size() > 0) {
            point = blackArray.get(blackArray.size() - 1);
            blackArray.remove(blackArray.size() - 1);
            mPosition[point.x][point.y] = 0;

            point = whiteArray.get(whiteArray.size() - 1);
            whiteArray.remove(whiteArray.size() - 1);
            mPosition[point.x][point.y] = 0;
        }
        printTable();
        return true;
    }

    /**
     * 打印二维数组的状态
     */
    private void printTable() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mRawAndColumnCount; i++) {
            for (int j = 0; j < mRawAndColumnCount; j++) {
                stringBuilder.append(mPosition[j][i] + " ");
            }
            log.d("SWGoBangLog： " + stringBuilder.toString() + " mPosition= ");
            stringBuilder.delete(0, stringBuilder.length());
        }
    }
}