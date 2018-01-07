package net.sunniwell.gobang.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.SWApplication;
import net.sunniwell.gobang.iswinterface.ISWOnSignAboutInterface;
import net.sunniwell.gobang.presenter.SWSignInPresenterImpl;
import net.sunniwell.gobang.utils.FragmentUtil;
import net.sunniwell.gobang.view.activity.SWSignInActivity;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.BmobUser;

/**
 * Created by lin on 2018/1/4.
 */

public class SWSignInFragment extends Fragment implements ISWOnSignAboutInterface.ISWOnSignInViewInterface, View.OnClickListener {

    private static final SWLogger log = SWLogger.getLogger("SWSignInFragment");
    private SWSignInPresenterImpl mPresenter = new SWSignInPresenterImpl(this);
    private EditText mLoginEditText;
    private EditText mPasswordEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, null);
        view.findViewById(R.id.id_goregister).setOnClickListener(this);
        view.findViewById(R.id.id_login).setOnClickListener(this);
        view.findViewById(R.id.id_exit).setOnClickListener(this);
        mLoginEditText = (EditText) view.findViewById(R.id.id_login_account);
        mPasswordEditText = (EditText) view.findViewById(R.id.id_login_password);
        return view;
    }

    @Override
    public void onSignInSucceed() {
        log.d("onSignInSucceed");
        Toast.makeText(SWApplication.getContext(), R.string.string_sign_in_sign_in_succeed, Toast.LENGTH_SHORT).show();
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
            case R.id.id_login:
                mPresenter.signIn(mLoginEditText.getText().toString(), mPasswordEditText.getText().toString());
                break;
            case R.id.id_goregister:
                FragmentUtil.hide(getFragmentManager(), this);
                FragmentUtil.show(getFragmentManager(), SWRegisterFragment.class.getSimpleName());
                break;
            case R.id.id_exit:
                getActivity().finish();
                break;
            // TODO: 2018/1/5 忘记密码的id
            case 2:
                break;
            default:
                break;
        }
    }

    private void login() {
        BmobUser user = mPresenter.getUserInfo();
        if (user != null && !TextUtils.isEmpty(user.getUsername())) {
            log.d("into gobang,user.getName() = " + user.getUsername());
//            mPresenter.signIn(user.getName(), user.getPassword());
            SWSignInActivity.startMainActivity();
        }
    }
}
