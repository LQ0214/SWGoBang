package net.sunniwell.gobang.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Xing on 2018/1/7.
 */

public class SWAboutDialogView extends Dialog {
    private int mViewId;
    private View mContentView;
    private Context mContext;

    public SWAboutDialogView(@NonNull Context context) {
        super(context);
    }

    public SWAboutDialogView(@NonNull Context context, @StyleRes int theme, int ViewId) {
        super(context, theme);
        mContext = context;
        mViewId = ViewId;
        initView();
    }

    private void initView() {
        mContentView = LayoutInflater.from(mContext).inflate(mViewId, null);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(400, 250);
        setContentView(mContentView, lp);
    }


}
