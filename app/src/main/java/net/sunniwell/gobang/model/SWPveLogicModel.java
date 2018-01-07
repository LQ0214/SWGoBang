package net.sunniwell.gobang.model;

import android.graphics.Point;

import net.sunniwell.gobang.utils.SWGoBangConstant;
import net.sunniwell.jar.log.SWLogger;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * 人机对战逻辑处理类
 */
public class SWPveLogicModel extends ASWChessLogicModel {
    private static final SWLogger log = SWLogger.getLogger("SWPveLogicModel");

    @Override
    public boolean restart(int id) {
        return true;
    }

    @Override
    public boolean giveup(int id) {
        return false;
    }

    @Override
    public boolean drawPiece(int id) {
        return false;
    }

    @Override
    public Point playPiece(int x, int y, int depth) {
        log.d("playPiece x = " + x + " y = " + y);
        mPosition[x][y] = 1;
        int best = Integer.MIN_VALUE;
        List<Point> usefulPositionList = getUsefulPositionList();
        List<Point> bestPoints = new ArrayList<Point>();
        for (int i = 0; i < usefulPositionList.size(); i++) {
            Point point = usefulPositionList.get(i);
            mPosition[point.x][point.y] = 2;
            int tmp = minNotAlphaBeta(point.x, point.y, depth - 1);
            if (tmp == best) {
                bestPoints.add(point);
            } else if (tmp > best) {
                best = tmp;
                bestPoints.clear();
                bestPoints.add(point);
            }
            mPosition[point.x][point.y] = 0;
        }
        Point point = bestPoints.get(new SecureRandom().nextInt(bestPoints.size()));
        if (point != null) {
            mPosition[point.x][point.y] = 2;
            mPlayPiece.playSucceed(point);
        } else {
            mPlayPiece.playFailed();
        }
        printTable();
        return point;
    }


    /**
     * 该点周围有棋子
     *
     * @param x
     * @param y
     * @return
     */
    private boolean hasPieceAround(int x, int y) {
        for (int i = (x - 3) > 0 ? (x - 3) : 0;
             i <= x + 3 && i < mRawAndColumnCount; i++) {
            for (int j = (y - 3) > 0 ? (y - 3) : 0; j <= (y + 3) && j < mRawAndColumnCount; j++) {
                // 修复第一次下棋左上角不能下子的情况
//                if (i != 0 || j != 0) {
                    if (mPosition[i][j] != 0) {
                        return true;
//                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取有意义的落子位置
     * 1、该点没有棋子
     * 2、该点的周围有其他棋子
     *
     * @return
     */
    private List<Point> getUsefulPositionList() {
        List<Point> usefulPositionList = new ArrayList<Point>();
        for (int i = 0; i < mRawAndColumnCount; i++) {
            for (int j = 0; j < mRawAndColumnCount; j++) {
                if (mPosition[i][j] == 0 && hasPieceAround(i, j)) {
                    Point point = new Point(i, j);
                    usefulPositionList.add(point);
                }
            }
        }
        return usefulPositionList;
    }

    private int maxNotAlphaBeta(int x, int y, int depth) {

        int finalScore = calculateScoreMinMaxNotAlphaBeta();
        if (depth <= 0) {
            return finalScore;
        }
        List<Point> usefulPoints = getUsefulPositionList();
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < usefulPoints.size(); i++) {
            Point point = usefulPoints.get(i);
            mPosition[point.x][point.y] = 2;
            int tmp = minNotAlphaBeta(point.x, point.y, depth);
            if (tmp > best) {
                best = tmp;
            }
            mPosition[point.x][point.y] = 0;
        }
        return best;
    }

    private int minNotAlphaBeta(int x, int y, int depth) {
        int finalScore = calculateScoreMinMaxNotAlphaBeta();
        if (depth <= 0) {
            return finalScore;
        }
        List<Point> usefulPoints = getUsefulPositionList();
        int best = Integer.MAX_VALUE;
        for (int i = 0; i < usefulPoints.size(); i++) {
            Point point = usefulPoints.get(i);
            mPosition[point.x][point.y] = 1;
            int tmp = maxNotAlphaBeta(point.x, point.y, depth);
            if (tmp < best) {
                best = tmp;
            }
            mPosition[point.x][point.y] = 0;
        }
        return best;
    }

    private int calculateScoreMinMaxNotAlphaBeta() {
        int scoreComputer = 0;
        int scoreHuman = 0;
        List<Point> allPositions = new ArrayList<Point>(mRawAndColumnCount * mRawAndColumnCount);
        for (int i = 0; i < mRawAndColumnCount; i++) {
            for (int j = 0; j < mRawAndColumnCount; j++) {
                Point point = new Point(i, j);
                allPositions.add(point);
            }
            scoreComputer += countScore(allPositions, 2);
            scoreHuman += countScore(allPositions, 1);
            allPositions.clear();
        }

        for (int j = 0; j < mRawAndColumnCount; j++) {
            for (int i = 0; i < mRawAndColumnCount; i++) {
                Point point = new Point(i, j);
                allPositions.add(point);
            }
            scoreComputer += countScore(allPositions, 2);
            scoreHuman += countScore(allPositions, 1);
            allPositions.clear();
        }

        for (int i = 0; i < mRawAndColumnCount; i++) {
            int x, y;
            for (x = i, y = 0; x < mRawAndColumnCount && y < mRawAndColumnCount; ++x, ++y) {
                Point point = new Point(y, x);
                allPositions.add(point);
            }
            scoreComputer += countScore(allPositions, 2);
            scoreHuman += countScore(allPositions, 1);
            allPositions.clear();
        }

        for (int j = 1; j < mRawAndColumnCount; j++) {
            int x, y;
            for (x = 0, y = j; x < mRawAndColumnCount && y < mRawAndColumnCount; ++x, ++y) {
                Point point = new Point(y, x);
                allPositions.add(point);
            }
            scoreComputer += countScore(allPositions, 2);
            scoreHuman += countScore(allPositions, 1);
            allPositions.clear();
        }


        for (int i = 0; i < mRawAndColumnCount; i++) {
            int x, y;
            for (y = i, x = 0; y >= 0 && x < mRawAndColumnCount; ++x, --y) {
                Point point = new Point(y, x);
                allPositions.add(point);
            }
            scoreComputer += countScore(allPositions, 2);
            scoreHuman += countScore(allPositions, 1);
            allPositions.clear();
        }

        for (int j = 1; j < mRawAndColumnCount; j++) {
            int x, y;
            for (y = j, x = mRawAndColumnCount - 1; y < mRawAndColumnCount && x >= 0; ++y, --x) {
                Point point = new Point(x, y);
                allPositions.add(point);
            }
            scoreComputer += countScore(allPositions, 2);
            scoreHuman += countScore(allPositions, 1);
            allPositions.clear();
        }
        return scoreComputer - scoreHuman;
    }

    private int scoreTable(int number, int emptyBlock) {
        if (number >= 5) {
            return 100000;
        } else if (number == 4) {
            if (emptyBlock == 2) {
                return 10000;
            } else if (emptyBlock == 1) {
                return 1000;
            }
        } else if (number == 3) {
            if (emptyBlock == 2) {
                return 1000;
            } else if (emptyBlock == 1) {
                return 100;
            }
        } else if (number == 2) {
            if (emptyBlock == 2) {
                return 100;
            } else if (emptyBlock == 1) {
                return 10;
            }
        } else if (number == 1) {
            if (emptyBlock == 2) {
                return 10;
            }
        }
        return 0;
    }

    private int countScore(List<Point> allPosition, int turn) {
        int tmpScore = 0;
        int emptyBlock = 0;
        int number = 0;
        Point point = allPosition.get(0);
        if (mPosition[point.x][point.y] == 0) {
            ++emptyBlock;
        } else if (mPosition[point.x][point.y] == turn) {
            ++number;
        }
        int i = 1;
        while (i < allPosition.size()) {
            point = allPosition.get(i);
            if (mPosition[point.x][point.y] == turn) {
                ++number;
            } else if (mPosition[point.x][point.y] == 0) {
                if (number == 0) {
                    emptyBlock = 1;
                } else {
                    tmpScore += scoreTable(number, emptyBlock + 1);
                    emptyBlock = 1;
                    number = 0;
                }
            } else {
                tmpScore += scoreTable(number, emptyBlock);
                emptyBlock = 0;
                number = 0;
            }
            ++i;
        }
        tmpScore += scoreTable(number, emptyBlock);
        return tmpScore;
    }

    private void printTable(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mRawAndColumnCount; i++) {
            for (int j = 0; j < mRawAndColumnCount; j++) {
                stringBuilder.append(mPosition[j][i]+" ");
            }
            log.d("hjx    ====>>>   "+ stringBuilder.toString() + " mPosition= " + mPosition.hashCode());
            stringBuilder.delete(0,stringBuilder.length());
        }
    }
}