package net.sunniwell.gobang.view.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.view.widget.SWAboutDialogView;

/**
 * Created by Xing on 2018/1/6.
 */

public class SWAboutAcitvity extends Activity implements View.OnClickListener {
    private Button mVersionBtn, mFuntionBtn, mAboutUs;
    private SWAboutDialogView mVersionDialog, mFuntionDialog, mUsDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_about);
        initView();
    }

    private void initView() {
        mVersionBtn = (Button) findViewById(R.id.about_version);
        mFuntionBtn = (Button) findViewById(R.id.about_functional_introduction);
        mAboutUs = (Button) findViewById(R.id.about_us);
        mVersionBtn.setOnClickListener(this);
        mFuntionBtn.setOnClickListener(this);
        mAboutUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_version:
                PackageInfo pkg = null;
                String appName = null;
                String versionName = null;
                try {
                    pkg = getPackageManager().getPackageInfo(getPackageName(), 0);
                    appName = pkg.applicationInfo.loadLabel(getPackageManager()).toString();
                    versionName = pkg.versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (mVersionDialog == null) {
                    mVersionDialog = new SWAboutDialogView(this, R.style.MyDialogStyle, R.layout.dialog_about_show_info);
                    mVersionDialog.setCanceledOnTouchOutside(true);
                    TextView app_name = (TextView) mVersionDialog.findViewById(R.id.about_version_app_name);
                    TextView version_num = (TextView) mVersionDialog.findViewById(R.id.about_version_num);
                    app_name.setText(appName);
                    version_num.setText(versionName);
                    mVersionDialog.show();
                } else {
                    mVersionDialog.show();
                }
                break;
            case R.id.about_functional_introduction:
                if (mFuntionDialog == null) {
                    mFuntionDialog = new SWAboutDialogView(this, R.style.MyDialogStyle, R.layout.dialog_about_connect_info);
                    mFuntionDialog.setCanceledOnTouchOutside(true);
                    TextView tv = (TextView) mFuntionDialog.findViewById(R.id.about_show_info);
                    tv.setText(getResources().getString(R.string.about_function));
                    mFuntionDialog.show();
                } else {
                    mFuntionDialog.show();
                }
                break;
            case R.id.about_us:
                if (mUsDialog == null) {
                    mUsDialog = new SWAboutDialogView(this, R.style.MyDialogStyle, R.layout.dialog_about_connect_info);
                    mUsDialog.setCanceledOnTouchOutside(true);
                    TextView tv = (TextView) mUsDialog.findViewById(R.id.about_show_info);
                    tv.setText(getResources().getString(R.string.about_us_info));
                    mUsDialog.show();
                } else {
                    mUsDialog.show();
                }
                break;
            default:
                break;
        }
    }
}
