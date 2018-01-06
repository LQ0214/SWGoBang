package net.sunniwell.gobang.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.presenter.ASWGoBangPresenterImpl;
import net.sunniwell.gobang.utils.SWGoBangConstant;
import net.sunniwell.gobang.view.ISWGoBangView;
import net.sunniwell.jar.log.SWLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xing on 2018/1/4.
 */

public class SWGoBangView extends View implements ISWGoBangView {
    private SWLogger log = SWLogger.getLogger(SWGoBangView.class.getSimpleName());

    private int mLineWidth;
    private float mLineHeight;
    private Paint mPaint;
    private Bitmap mBlackPiece;
    private Bitmap mWhitePiece;
    /**
     * 棋子的边长占高的比例
     */
    private static final float PIECE_SCALE = 3 * 1.0f / 4;
    private boolean mIsWhite;
    /**
     * 黑色棋子list
     */
    private List<Point> mBlackArray;
    /**
     * 白色棋子list
     */
    private List<Point> mWhiteArray;

    private ASWGoBangPresenterImpl mGoBangPresenter;
    private ISWEventCompletedListener mEventCompletedListener;
    private int mUserId;
    /**
     * 是否轮到我方
     */
    private boolean mIsMyTurn;


    public interface ISWEventCompletedListener {
        void restartCompleted();

        void undoCompleted();

        void giveupCompleted();

        void drawPieceCompleted();

        void gameOverCompleted();

        void fiveConnectCompleted();
    }

    public void setEventCompletedListener(ISWEventCompletedListener listener) {
        mEventCompletedListener = listener;
    }

    public SWGoBangView(Context context) {
        this(context, null);
    }

    public SWGoBangView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SWGoBangView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        log.d("hjx   ===>>>  初始化。。。");
        initView();
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int mUserId) {
        this.mUserId = mUserId;
    }

    public boolean isIsMyTurn() {
        return mIsMyTurn;
    }

    public void setIsMyTurn(boolean mIsMyTurn) {
        this.mIsMyTurn = mIsMyTurn;
    }

    public void setGoBangPresenter(ASWGoBangPresenterImpl mGoBangPresenter) {
        this.mGoBangPresenter = mGoBangPresenter;
    }

    /**
     * 初始化内容
     */
    private void initView() {
        setBackgroundColor(getResources().getColor(R.color.colorAccent));
        mBlackArray = new ArrayList<Point>();
        mWhiteArray = new ArrayList<Point>();
        // 默认我方先手
        setIsMyTurn(true);

        // 初始化画笔
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorPaint));
        // 抗锯齿
        mPaint.setAntiAlias(true);
        // 防抖动
        mPaint.setDither(true);
        //设置画笔的风格为空心
        mPaint.setStyle(Paint.Style.STROKE);

        // 黑子
        mBlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.chess_black);
        // 白子
        mWhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.chess_white);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        log.d("hjx   ===>>>  测量。。。");
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 宽和高取最小值作为正方形的边长
        int width = Math.min(widthSize, heightSize);

        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        log.d("hjx   ===>>>  Size改变。。。");
        mLineWidth = w;
        mLineHeight = mLineWidth * 1.0f / SWGoBangConstant.LINE_NUM;
        log.d("hjx   ===>>>   宽 = " + mLineWidth + "      高 = " + mLineHeight);

        int pieceWidth = (int) (mLineHeight * PIECE_SCALE);
        // 根据适应高度来修改棋子的大小
        mBlackPiece = Bitmap.createScaledBitmap(mBlackPiece, pieceWidth, pieceWidth, false);
        mWhitePiece = Bitmap.createScaledBitmap(mWhitePiece, pieceWidth, pieceWidth, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        log.d("hjx   ===>>>  开始画。。。");
        // 画线
        drawLine(canvas);
        // 画棋子
        drawPiece(canvas);

    }

    /**
     * 画棋子
     *
     * @param canvas
     */
    private void drawPiece(Canvas canvas) {
        log.d("hjx    =====>>>   黑棋数量  size = " + mBlackArray.size());
        log.d("hjx    =====>>>   白棋数量  size = " + mWhiteArray.size());
        for (Point point : mBlackArray) {
            canvas.drawBitmap(mBlackPiece, (point.x + (1 - PIECE_SCALE) / 2) * mLineHeight, (point.y + (1 - PIECE_SCALE) / 2) * mLineHeight, mPaint);
        }
        for (Point point : mWhiteArray) {
            canvas.drawBitmap(mWhitePiece, (point.x + (1 - PIECE_SCALE) / 2) * mLineHeight, (point.y + (1 - PIECE_SCALE) / 2) * mLineHeight, mPaint);
        }
    }

    /**
     * 画线来构成棋盘
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        int width = mLineWidth;
        float lineHeight = mLineHeight;
        // 画横线
        for (int i = 0; i < SWGoBangConstant.LINE_NUM; i++) {
            int startX = (int) (lineHeight / 2);
            int stopX = (int) (width - lineHeight / 2);
            int y = (int) ((0.5 + i) * lineHeight);
            canvas.drawLine(startX, y, stopX, y, mPaint);
        }
        // 画竖线
        for (int i = 0; i < SWGoBangConstant.LINE_NUM; i++) {
            int x = (int) ((0.5 + i) * lineHeight);
            int startY = (int) (lineHeight / 2);
            int stopY = (int) (width - lineHeight / 2);
            canvas.drawLine(x, startY, x, stopY, mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        log.d("hjx    ===>>>onTouchEvent    action = " + action);
        // 这里点击SWGoBangView的时候没有up事件，暂时先用down事件
        if (action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            // 根据坐标的值来取商直接保存落子坐标
            Point point = new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
            // 如果点击的位置上已经有落子，直接消费掉
            if (mBlackArray.contains(point) || mWhiteArray.contains(point)) {
                return true;
            }
            if (isIsMyTurn()) {
                if (mIsWhite) {
                    mWhiteArray.add(point);
                } else {
                    mBlackArray.add(point);
                }
                //TODO 根据黑白判断 五子连珠
                mGoBangPresenter.isGameOverMethod(getUserId(), mWhiteArray, mBlackArray);
                mIsWhite = !mIsWhite;
                // 重绘
                invalidate();
                //TODO maxNotAlphaBeta(int x, int y, int depth：算法往前先算多的步数)
                log.d("hjx   ==我方落子==>>>   point.x = " + point.x + "    point.y = " + point.y);
                // 我方已落子，轮到对方
                setIsMyTurn(false);
                mGoBangPresenter.playPiece(point.x, point.y, 1);
            } else {
                Toast.makeText(getContext(), "急个卵，还没轮到你呢~~", Toast.LENGTH_LONG).show();
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 向外提供的接口 ： 重新开始
     */
    public void reStart() {
        if (mGoBangPresenter != null) {
            mGoBangPresenter.restart(mUserId);
        }
    }

    /**
     * 向外提供的接口 ： 悔棋
     */
    public void undo() {
        if (mGoBangPresenter != null) {
            mGoBangPresenter.undo(mUserId);
        }
    }

    /**
     * 向外提供的接口 ： 认输
     */
    public void giveup() {
        if (mGoBangPresenter != null) {
            mGoBangPresenter.giveup(mUserId);
        }
    }

    /**
     * 向外提供的接口 ： 和棋
     */
    public void drawPiece() {
        if (mGoBangPresenter != null) {
            mGoBangPresenter.drawPiece(mUserId);
        }
    }

    @Override
    public void restartCompleted(int id) {
        mEventCompletedListener.restartCompleted();
    }

    @Override
    public void undoCompleted(int id) {
        mEventCompletedListener.undoCompleted();
    }

    @Override
    public void giveupCompleted(int id) {
        mEventCompletedListener.giveupCompleted();
    }

    @Override
    public void drawPieveCompleted(int id) {
        mEventCompletedListener.drawPieceCompleted();
    }

    @Override
    public void gameOverCompleted(int id) {
        mEventCompletedListener.gameOverCompleted();
    }

    @Override
    public void fiveConnectCompleted() {
        mEventCompletedListener.fiveConnectCompleted();
    }

    /**
     * 对方落子成功回调
     *
     * @param point
     */
    @Override
    public void playSucceed(Point point) {
        log.d("hjx    ====>>>  对方落子成功    point = " + point);
        // 对方落子完成，轮到我方
        setIsMyTurn(true);
        if (mIsWhite) {
            mWhiteArray.add(point);
        } else {
            mBlackArray.add(point);
        }
        mIsWhite = !mIsWhite;
        invalidate();
    }

    /**
     * 对方落子失败回调
     */
    @Override
    public void playFailed() {

    }
}