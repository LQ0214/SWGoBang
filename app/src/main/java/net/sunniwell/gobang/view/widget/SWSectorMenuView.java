package net.sunniwell.gobang.view.widget;

/**
 * Created by Xing on 2018/1/5.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import net.sunniwell.gobang.R;
import net.sunniwell.jar.log.SWLogger;


public class SWSectorMenuView extends RelativeLayout {

    private SWLogger log = SWLogger.getLogger(SWSectorMenuView.class.getSimpleName());
    private Context mContext;
    private int mLeftMargin = 0, mBottomMargin = 0;
    private final int mButtonWidth = 58;//图片宽高
    private final int mRadius = 100;//半径
    private final int mMaxTimeSpent = 200;//最长动画耗时
    private final int mMinTimeSpent = 80;//最短动画耗时
    private int mIntervalTimeSpent;//每相邻2个的时间间隔
    private Button[] mBtns;
    private Button mBtnMenu;
    private RelativeLayout.LayoutParams mParams;
    private boolean mIsOpen = false;//是否菜单打开状态
    private float mAngle;//每个按钮之间的夹角

    public SWSectorMenuView(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    public SWSectorMenuView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(mContext).inflate(R.layout.anim_buttons, this);

        initButtons(view);

    }

    private void initButtons(View view) {
        //4个按钮，具体视情况而定
        mBtns = new Button[2];
//        mBtns[0] = (Button) view.findViewById(R.id.btn_giveup);
        mBtns[0] = (Button) view.findViewById(R.id.btn_undo);
//        mBtns[2] = (Button) view.findViewById(R.id.btn_drawpiece);
        mBtns[1] = (Button) view.findViewById(R.id.btn_restart);
        mBtnMenu = (Button) view.findViewById(R.id.btn_menu);

        mLeftMargin = ((RelativeLayout.LayoutParams) (mBtnMenu.getLayoutParams())).leftMargin;
        mBottomMargin = ((RelativeLayout.LayoutParams) (mBtnMenu.getLayoutParams())).bottomMargin;

        for (int i = 0; i < mBtns.length; i++) {
            mBtns[i].setLayoutParams(mBtnMenu.getLayoutParams());//初始化的时候按钮都重合
            mBtns[i].setTag(String.valueOf(i));
            mBtns[i].setOnTouchListener(touchListener);
        }

        mIntervalTimeSpent = (mMaxTimeSpent - mMinTimeSpent) / mBtns.length;//20
        mAngle = (float) Math.PI / (2 * (mBtns.length - 1));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        final int bottomMargins = this.getMeasuredHeight() - mButtonWidth - mBottomMargin;
        mBtnMenu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mIsOpen) {
                    mIsOpen = true;
                    dynamicExtendMove(bottomMargins);
                } else {
                    mIsOpen = false;
                    dynamicshrinkMove(bottomMargins);
                }

            }
        });

    }

    /**
     * 动态扩展移动效果
     *
     * @param bottomMargins
     */
    private void dynamicExtendMove(int bottomMargins) {
        for (int i = 0; i < mBtns.length; i++) {
            float x = (float) (mRadius * Math.sin(i * mAngle));
            float y = (float) (mRadius * Math.cos(i * mAngle));
            mBtns[i].setVisibility(View.VISIBLE);
            mBtns[i].startAnimation(animTranslate(x, -y, mLeftMargin + (int) x, bottomMargins - (int) y, mBtns[i], mMinTimeSpent + i * mIntervalTimeSpent));
        }
    }

    /**
     * 动态收缩移动效果
     *
     * @param bottomMargins
     */
    private void dynamicshrinkMove(int bottomMargins) {
        for (int i = 0; i < mBtns.length; i++) {
            float x = (float) (mRadius * Math.sin(i * mAngle));
            float y = (float) (mRadius * Math.cos(i * mAngle));
            mBtns[i].startAnimation(animTranslate(-x, y, mLeftMargin, bottomMargins - 4, mBtns[i], mMaxTimeSpent - i * mIntervalTimeSpent));
            mBtns[i].setVisibility(View.GONE);
        }
    }


    /**
     * 对相应的View进行位移
     *
     * @param toX
     * @param toY
     * @param lastX
     * @param lastY
     * @param button
     * @param durationMillis
     * @return
     */
    private Animation animTranslate(float toX, float toY, final int lastX, final int lastY, final Button button, long durationMillis) {
        Animation animation = new TranslateAnimation(0, toX, 0, toY);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mParams = new RelativeLayout.LayoutParams(0, 0);
                mParams.height = mButtonWidth;
                mParams.width = mButtonWidth;
                mParams.setMargins(lastX, lastY, 0, 0);
                button.setLayoutParams(mParams);
                button.clearAnimation();

            }
        });
        animation.setDuration(durationMillis);

        return animation;
    }


    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            log.d("SWGoBangLog:    action = " + event.getAction());
            if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
                for (int i = 0; i < mBtns.length; i++) {
                    if (mIsOpen) {
                        mIsOpen = false;
                        dynamicshrinkMove(SWSectorMenuView.this.getMeasuredHeight() - mButtonWidth - mBottomMargin);
                    }
                }
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                for (int i = 0; i < mBtns.length; i++) {
                    if (mIsOpen) {
                        mIsOpen = false;
                        dynamicshrinkMove(SWSectorMenuView.this.getMeasuredHeight() - mButtonWidth - mBottomMargin);
                    }
                }
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick(v);
                }
            }
            return true;
        }
    };

    public boolean ismIsOpen() {
        return mIsOpen;
    }

    private ISWOnButtonClickListener onButtonClickListener;

    public interface ISWOnButtonClickListener {
        void onButtonClick(View v);
    }

    public void setOnButtonClickListener(ISWOnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}

