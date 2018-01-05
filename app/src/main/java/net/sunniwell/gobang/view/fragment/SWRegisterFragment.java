package net.sunniwell.gobang.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.iswinterface.ISWOnRegisterInterface;
import net.sunniwell.gobang.presenter.SWRegisterPresenterImpl;
import net.sunniwell.gobang.utils.FragmentUtil;
import net.sunniwell.gobang.view.activity.SWSignInActivity;
import net.sunniwell.jar.log.SWLogger;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by lin on 2018/1/4.
 */

public class SWRegisterFragment extends Fragment implements ISWOnRegisterInterface.ISWOnRegisterViewInterface, View.OnClickListener {
    private static final SWLogger log = SWLogger.getLogger("SWRegisterFragment");
    private SWRegisterPresenterImpl mPresenter = new SWRegisterPresenterImpl(this);
    private EditText mAccount, mPassword, mTelNumber, mSmsCode;
    private Button mBack, mConfirm, mSendSmsCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, null);
        mAccount = (EditText) view.findViewById(R.id.id_register_account);
        mPassword = (EditText) view.findViewById(R.id.id_register_password);
        mTelNumber = (EditText) view.findViewById(R.id.id_register_tel_number);
        mSmsCode = (EditText) view.findViewById(R.id.id_register_sms_code);
        mBack = (Button) view.findViewById(R.id.id_back);
        mConfirm = (Button) view.findViewById(R.id.id_register);
        mSendSmsCode = (Button) view.findViewById(R.id.id_send_sms_code);
        mBack.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        mSendSmsCode.setOnClickListener(this);
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
            case R.id.id_register:
                BmobUser userInfo = new BmobUser();
                String userName = mAccount.getText().toString();
                userInfo.setUsername(userName);
                String userPassword = mPassword.getText().toString();
                userInfo.setPassword(userPassword);
                String userTelNumber = mTelNumber.getText().toString();
                userInfo.setMobilePhoneNumber(userTelNumber);
                String userSmsCode = mSmsCode.getText().toString();
                mPresenter.register(userInfo, userPassword, userSmsCode);
                break;
            case R.id.id_back:
                FragmentUtil.hide(getFragmentManager(),this);
                FragmentUtil.show(getFragmentManager(), SWSignInFragment.class.getSimpleName());
                break;
            case R.id.id_send_sms_code:
                BmobSMS.requestSMSCode(mTelNumber.getText().toString(), "朝歌帐号注册", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            Toast.makeText(SWRegisterFragment.this.getActivity(), R.string.string_sms_code_send_succeed, Toast.LENGTH_SHORT).show();
                        } else {
                            new Throwable("sms code send error");
                            Toast.makeText(SWRegisterFragment.this.getActivity(), R.string.string_sms_code_send_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
