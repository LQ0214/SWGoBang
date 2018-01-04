package net.sunniwell.gobang;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by lin on 2018/1/4.
 */

public class SWApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        mContext = this;
        Bmob.initialize(getApplicationContext(), "6c9122e9c11067cf76c694646027dc91");
    }

    private void unInit(){
        mContext = null;
    }

    public static Context getContext(){
        return mContext;
    }
}
