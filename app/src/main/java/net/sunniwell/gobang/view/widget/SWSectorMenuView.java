package net.sunniwell.gobang.view.widget;

/**
 * Created by Xing on 2018/1/5.
 */

import android.R.anim;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import net.sunniwell.gobang.R;
import net.sunniwell.jar.log.SWLogger;


public class SWSectorMenuView extends RelativeLayout {

    private SWLogger log = SWLogger.getLogger(SWSectorMenuView.class.getSimpleName());
    private Context context;
    private int leftMargin = 0, bottomMargin = 0;
    private final int buttonWidth = 58;//图片宽高
    private final int r = 100;//半径
    private final int maxTimeSpent = 200;//最长动画耗时
    private final int minTimeSpent = 80;//最短动画耗时
    private int intervalTimeSpent;//每相邻2个的时间间隔
    private Button[] btns;
    private Button btn_menu;
    private RelativeLayout.LayoutParams params;
    private boolean isOpen = false;//是否菜单打开状态
    private float angle;//每个按钮之间的夹角

    public SWSectorMenuView(Context context) {
        super(context);
        this.context = context;
    }

    public SWSectorMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(context).inflate(R.layout.anim_buttons, this);

        initButtons(view);

    }

    private void initButtons(View view) {
        //4个按钮，具体视情况而定
        btns = new Button[4];
        btns[0] = (Button) view.findViewById(R.id.btn_giveup);
        btns[1] = (Button) view.findViewById(R.id.btn_undo);
        btns[2] = (Button) view.findViewById(R.id.btn_drawpiece);
        btns[3] = (Button) view.findViewById(R.id.btn_restart);
        btn_menu = (Button) view.findViewById(R.id.btn_menu);

        leftMargin = ((RelativeLayout.LayoutParams) (btn_menu.getLayoutParams())).leftMargin;
        bottomMargin = ((RelativeLayout.LayoutParams) (btn_menu.getLayoutParams())).bottomMargin;

        for (int i = 0; i < btns.length; i++) {
            btns[i].setLayoutParams(btn_menu.getLayoutParams());//初始化的时候按钮都重合
            btns[i].setTag(String.valueOf(i));
            btns[i].setOnTouchListener(touchListener);
        }

        intervalTimeSpent = (maxTimeSpent - minTimeSpent) / btns.length;//20
        angle = (float) Math.PI / (2 * (btns.length - 1));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        log.d("hjx   ===>>>  88888888888888888");
        final int bottomMargins = this.getMeasuredHeight() - buttonWidth - bottomMargin;
        btn_menu.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    isOpen = true;
                    dynamicExtendMove(bottomMargins);
                } else {
                    isOpen = false;
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
        for (int i = 0; i < btns.length; i++) {
            float x = (float) (r * Math.sin(i * angle));
            float y = (float) (r * Math.cos(i * angle));
            btns[i].startAnimation(animTranslate(x, -y, leftMargin + (int) x, bottomMargins - (int) y, btns[i], minTimeSpent + i * intervalTimeSpent));
        }
    }

    /**
     * 动态收缩移动效果
     *
     * @param bottomMargins
     */
    private void dynamicshrinkMove(int bottomMargins) {
        for (int i = 0; i < btns.length; i++) {
            float x = (float) (r * Math.sin(i * angle));
            float y = (float) (r * Math.cos(i * angle));
            btns[i].startAnimation(animTranslate(-x, y, leftMargin, bottomMargins - 4, btns[i], maxTimeSpent - i * intervalTimeSpent));
        }
    }

    private Animation animScale(float toX, float toY) {
        Animation animation = new ScaleAnimation(1.0f, toX, 1.0f, toY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(context, anim.accelerate_decelerate_interpolator);
        animation.setDuration(400);
        animation.setFillAfter(false);
        return animation;

    }

    private Animation animRotate(float toDegrees, float pivotXValue, float pivotYValue) {
        final Animation animation = new RotateAnimation(0, toDegrees, Animation.RELATIVE_TO_SELF, pivotXValue, Animation.RELATIVE_TO_SELF, pivotYValue);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation.setFillAfter(true);
            }
        });
        return animation;
    }


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
                params = new RelativeLayout.LayoutParams(0, 0);
                params.height = buttonWidth;
                params.width = buttonWidth;
                params.setMargins(lastX, lastY, 0, 0);
                button.setLayoutParams(params);
                button.clearAnimation();

            }
        });
        animation.setDuration(durationMillis);

        return animation;
    }


    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            log.d("hjx   ===>>  event.getAction() = "+event.getAction());
            if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
                for (int i = 0; i < btns.length; i++) {
                    if (isOpen) {
                        isOpen = false;
                        dynamicshrinkMove(SWSectorMenuView.this.getMeasuredHeight() - buttonWidth - bottomMargin);
                    }
                }
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                for (int i = 0; i < btns.length; i++) {
                    if (isOpen) {
                        isOpen = false;
                        dynamicshrinkMove(SWSectorMenuView.this.getMeasuredHeight() - buttonWidth - bottomMargin);
                    }
                }
                if (onButtonClickListener != null) {
                    onButtonClickListener.onButtonClick(v);
                }
            }
            return true;
        }
    };

    public boolean isOpen() {
        return isOpen;
    }

    private ISWOnButtonClickListener onButtonClickListener;

    public interface ISWOnButtonClickListener {
        void onButtonClick(View v);
    }

    public void setOnButtonClickListener(ISWOnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}

