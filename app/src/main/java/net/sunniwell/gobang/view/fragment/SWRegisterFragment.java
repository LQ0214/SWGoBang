package net.sunniwell.gobang.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.iswinterface.ISWOnRegisterInterface;
import net.sunniwell.gobang.presenter.SWRegisterPresenterImpl;
import net.sunniwell.gobang.view.activity.SWSignInActivity;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.BmobUser;

/**
 * Created by lin on 2018/1/4.
 */

public class SWRegisterFragment extends Fragment implements ISWOnRegisterInterface.ISWOnRegisterViewInterface, View.OnClickListener {
    private static final SWLogger log = SWLogger.getLogger("SWRegisterFragment");
    private SWRegisterPresenterImpl mPresenter = new SWRegisterPresenterImpl(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, null);
        return view;
    }

    @Override
    public void onRegisterSucceed() {
        log.d("onRegisterSucceed");
        Toast.makeText(getActivity(), R.string.string_register_register_succeed, Toast.LENGTH_SHORT).show();
        SWSignInActivity.startMainActivity();
    }

    @Override
    public void onRegisterFailed(String reason) {
        log.d("onRegisterFailed, reason = " + reason);
        Toast.makeText(getActivity(), reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // TODO: 2018/1/4 换为注册的id
            case 1:
                BmobUser userInfo = new BmobUser();
                // TODO: 2018/1/4 设置注册的用户信息
                String userName = "";
                userInfo.setUsername(userName);
                String userPassword = "";
                userInfo.setPassword(userPassword);
                mPresenter.register(userInfo, userPassword);
                break;
            default:
                break;
        }
    }
}
