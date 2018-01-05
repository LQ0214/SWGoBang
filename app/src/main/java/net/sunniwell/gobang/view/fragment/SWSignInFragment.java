package net.sunniwell.gobang.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.iswinterface.ISWOnSignInInterface;
import net.sunniwell.gobang.presenter.SWSignInPresenterImpl;
import net.sunniwell.gobang.view.activity.SWSignInActivity;
import net.sunniwell.jar.log.SWLogger;

/**
 * Created by lin on 2018/1/4.
 */

public class SWSignInFragment extends Fragment implements ISWOnSignInInterface.ISWOnSignInViewInterface, View.OnClickListener {

    private static final SWLogger log = SWLogger.getLogger("SWSignInFragment");
    private SWSignInPresenterImpl mPresenter = new SWSignInPresenterImpl(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, null);
        return view;
    }

    @Override
    public void onSignInSucceed() {
        log.d("onSignInSucceed");
        Toast.makeText(getActivity(), R.string.string_sign_in_sign_in_succeed, Toast.LENGTH_SHORT).show();
        SWSignInActivity.startMainActivity();
    }

    @Override
    public void onSignInFailed(String reason) {
        log.d("onSignInFailed, reason = " + reason);
        Toast.makeText(getActivity(), reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // TODO: 2018/1/4 换为登录的id
            case 1:
                String userName = "";
                String userPassword = "";
                mPresenter.signIn(userName, userPassword);
                break;
            // TODO: 2018/1/5 忘记密码的id
            case 2:
                break;
            default:
                break;
        }
    }
}
