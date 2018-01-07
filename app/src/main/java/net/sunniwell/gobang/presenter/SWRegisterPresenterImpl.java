package net.sunniwell.gobang.presenter;

import android.support.v4.app.Fragment;
import android.text.TextUtils;

import net.sunniwell.gobang.R;
import net.sunniwell.gobang.iswinterface.ISWOnRegisterInterface;
import net.sunniwell.jar.log.SWLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 处理注册业务
 * Created by lin on 2018/1/4.
 */

public class SWRegisterPresenterImpl implements ISWOnRegisterInterface.ISWOnRegisterPresenterInterface {
    private static final SWLogger log = SWLogger.getLogger(SWRegisterPresenterImpl.class.getSimpleName());
    private ISWOnRegisterInterface.ISWOnRegisterViewInterface mRegisterView;

    public SWRegisterPresenterImpl(ISWOnRegisterInterface.ISWOnRegisterViewInterface callback) {
        mRegisterView = callback;
    }

    /**
     * 注册
     *
     * @param userInfo     用户信息
     * @param userPassword 密码
     * @param smsCode      验证码
     */
    @Override
    public void register(BmobUser userInfo, final String userPassword, String smsCode) {
        log.d("SWGoBangLog ,register");
        final String userName = userInfo.getUsername();
        final String telNumber = userInfo.getMobilePhoneNumber();
        if (TextUtils.isEmpty(telNumber) || TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPassword) || TextUtils.isEmpty(smsCode)) {
            mRegisterView.onRegisterFailed(((Fragment) mRegisterView).getString(R.string.string_please_fill_in_complete_info));
            return;
        }
        if (!isTelNum(telNumber)) {
            log.d("SWGoBangLog ,register telNumber = " + telNumber);
            mRegisterView.onRegisterFailed(((Fragment) mRegisterView).getString(R.string.string_please_fill_in_correct_telnum));
            return;
        }
        userInfo.signOrLogin(smsCode, new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    if (mRegisterView != null) {
                        mRegisterView.onRegisterSucceed();
                    } else {
                        new Throwable("error: the mCallback is null");
                    }
                } else {
                    if (mRegisterView != null) {
                        mRegisterView.onRegisterFailed(e.getMessage());
                    } else {
                        new Throwable("error: the mCallback is null");
                    }
                }
            }
        });
    }

    /**
     * 验证手机号
     *
     * @param mobiles 手机号
     * @return 是否正确
     */
    public boolean isTelNum(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
