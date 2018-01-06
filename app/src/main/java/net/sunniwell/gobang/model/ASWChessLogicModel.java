package net.sunniwell.gobang.model;

import android.graphics.Point;

import net.sunniwell.gobang.utils.SWGoBangConstant;
import net.sunniwell.jar.log.SWLogger;

/**
 * 棋盘逻辑处理实现类
 */
public abstract class ASWChessLogicModel implements ISWChessLogicModel {
    private static final SWLogger log = SWLogger.getLogger("ASWChessLogicModel");
    protected int mRawAndColumnCount;
    protected int[][] mPosition;
    protected ISWPlayPiece mPlayPiece;

    public interface ISWPlayPiece {
        void playSucceed(Point point);

        void playFailed();
    }

    public ASWChessLogicModel() {
        this(SWGoBangConstant.LINE_NUM);
    }

    public ASWChessLogicModel(int count) {
        mRawAndColumnCount = count;
        mPosition = new int[mRawAndColumnCount][mRawAndColumnCount];
    }

    public void setPlayPieceListener(ISWPlayPiece playPiece) {
        mPlayPiece = playPiece;
    }

    public abstract Point playPiece(int x, int y, int depth);

    @Override
    public boolean isGameOverMethod(int id, int x, int y) {
        if (x >= mRawAndColumnCount || x < 0 || y >= mRawAndColumnCount || y < 0) {
            new IllegalArgumentException("x = " + x + ", y = " + y);
        }
        mPosition[x][y] = id;
        boolean flag = checkXDirection(id, x, y) || checkYDirection(id, x, y) || checkXYDirection(id, x, y) || checkYXDirection(id, x, y);
        log.d("linhongbo: isGameOverMethod flag = " + flag + ",id  = " + id);
        return flag;
    }

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
                log.d("linhongbo: count = " + count + " isBackwardStop = " + isBackwardStop + " isForwardStop = " + isForwardStop);
                if (count == 5) {
                    return true;
                } else if (isBackwardStop && isForwardStop) {
                    return false;
                }
            }
        }
        return false;
    }

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

            if (count == 5) {
                return true;
            } else if (isBackwardStop && isForwardStop) {
                return false;
            }
        }
        return false;
    }

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

            if (count == 5) {
                return true;
            } else if (isBackwardStop && isForwardStop) {
                return false;
            }
        }
        return false;
    }

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

            if (count == 5) {
                return true;
            } else if (isBackwardStop && isForwardStop) {
                return false;
            }
        }
        return false;
    }

    private boolean checkPiece(int id, int x, int y, boolean isStop) {
        if (isStop) {
            return false;
        }
        if (x >= 0 && y >= 0 && x < mRawAndColumnCount && y < mRawAndColumnCount && mPosition[x][y] == id) {
            return true;
        }
        return false;
    }
}