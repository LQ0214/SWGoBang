package net.sunniwell.gobang.view.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.presenter.ASWGoBangPresenterImpl;
import net.sunniwell.gobang.presenter.SWPveGoBangPresenterImpl;
import net.sunniwell.gobang.presenter.SWPvpGoBangPresenterImpl;
import net.sunniwell.gobang.utils.SWGoBangConstant;
import net.sunniwell.gobang.view.ISWGoBangView;
import net.sunniwell.jar.log.SWLogger;

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

    private ASWGoBangPresenterImpl mGoBangPresenter;
    private ISWEventCompletedListener mEventCompletedListener;
    private int mUserId;

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

    public void bindUserId() {
        if (mGoBangPresenter != null) {
            String userId = mGoBangPresenter.getUser().getMobilePhoneNumber();
            if (!TextUtils.isEmpty(userId) && !userId.equals("0"))
                mUserId = Integer.valueOf(userId);
        }
    }

    public void setGoBangPresenter(ASWGoBangPresenterImpl mGoBangPresenter) {
        this.mGoBangPresenter = mGoBangPresenter;
        bindUserId();
    }

    /**
     * 初始化内容
     */
    private void initView() {
        // 初始化画笔
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.color_black));
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
        // 绘制小黑点
        drawBlackPoint(canvas);
        // 画棋子
        drawPiece(canvas);

    }

    /**
     * 绘制小黑点
     *
     * @param canvas
     */
    private void drawBlackPoint(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_black));
        paint.setAntiAlias(true);
        paint.setDither(true);
        canvas.drawCircle(this.getMeasuredWidth() / 2, this.getMeasuredHeight() / 2, 5, paint);
        canvas.drawCircle(this.getMeasuredWidth() / 2 - mLineHeight * 4, this.getMeasuredHeight() / 2 - mLineHeight * 4, 5, paint);
        canvas.drawCircle(this.getMeasuredWidth() / 2 + mLineHeight * 4, this.getMeasuredHeight() / 2 - mLineHeight * 4, 5, paint);
        canvas.drawCircle(this.getMeasuredWidth() / 2 - mLineHeight * 4, this.getMeasuredHeight() / 2 + mLineHeight * 4, 5, paint);
        canvas.drawCircle(this.getMeasuredWidth() / 2 + mLineHeight * 4, this.getMeasuredHeight() / 2 + mLineHeight * 4, 5, paint);
    }

    /**
     * 画棋子
     *
     * @param canvas
     */
    private void drawPiece(Canvas canvas) {
        log.d("hjx    =====>>>   黑棋数量  size = " + mGoBangPresenter.getBlackArray().size());
        log.d("hjx    =====>>>   白棋数量  size = " + mGoBangPresenter.getWhiteArray().size());
        if (mGoBangPresenter.getBlackArray() != null)
            for (Point point : mGoBangPresenter.getBlackArray()) {
                canvas.drawBitmap(mBlackPiece, (point.x + (1 - PIECE_SCALE) / 2) * mLineHeight, (point.y + (1 - PIECE_SCALE) / 2) * mLineHeight, mPaint);
            }
        if (mGoBangPresenter.getWhiteArray() != null)
            for (Point point : mGoBangPresenter.getWhiteArray()) {
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
            if (mGoBangPresenter.getBlackArray().contains(point) || mGoBangPresenter.getWhiteArray().contains(point)) {
                return true;
            }

            if (mGoBangPresenter.isMyTurun()) {
                // 我方已落子，轮到对方
                mGoBangPresenter.handleChessPosition(point);
                // 重绘
                invalidate();
                if (mGoBangPresenter instanceof SWPveGoBangPresenterImpl)
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
            mGoBangPresenter.undo(mUserId, mGoBangPresenter.getBlackArray(), mGoBangPresenter.getWhiteArray());
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
        // 对方落子完成，轮到我方
        if (point != null) {
            mGoBangPresenter.handleChessPosition(point);
            log.d("hjx    ====>>>  对方落子成功    point = " + point);
            invalidate();
        }
    }

    /**
     * 对方落子失败回调
     */
    @Override
    public void playFailed() {

    }
}