package com.bit.safetycommunity.activity;

import android.Manifest;

import com.bit.safetycommunity.app.AppInfo;
import com.bit.safetycommunity.app.SafetyApplication;
import com.bit.safetycommunity.base.BaseActivity;
import com.bit.safetycommunity.bean.LoginBean;
import com.bit.safetycommunity.beanpar.LoginPwdPar;
import com.bit.safetycommunity.contant.ConfigPF;
import com.bit.safetycommunity.manager.UserManager;
import com.bit.safetycommunity.net.ApiRequest;
import com.bit.safetycommunity.net.DefineCallback;
import com.bit.safetycommunity.net.HttpEntity;
import com.bit.safetycommunity.net.HttpURL;
import com.bit.safetycommunity.utils.PermissionsUtils;
import com.bit.safetycommunity.utils.PreferenceUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kzz.safetycommunity.R;
import com.yanzhenjie.kalle.simple.SimpleResponse;

public class MainActivity extends BaseActivity {


    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        //申请权限
        PermissionsUtils.getInstance().requestPermissions(mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.REORDER_TASKS);
        login("15112270850", "123456");
    }

    /**
     * 登陆
     */
    private void login(final String mobile, final String pwd) {

        LoginPwdPar par = new LoginPwdPar();
        par.setMobile(mobile);
        par.setPwd(pwd);
        ApiRequest.getInstance().post(HttpURL.SIGN_IN, par, new DefineCallback<LoginBean>() {
            @Override
            public void onResponse(SimpleResponse<LoginBean, HttpEntity> response) {
                if (response.isSucceed()) {
                    LoginBean bean = response.succeed();
                    if (bean != null) {
//                        if (JPushInterface.isPushStopped(mActivity))
//                            JPushInterface.resumePush(mActivity);
//                        saveUserInfo(bean);
                        AppInfo appInfo = new AppInfo(pwd, mobile);
                        UserManager.getInstance().saveAppInfo(appInfo);
                        UserManager.getInstance().saveUserToken(bean.getToken());
                        UserManager.getInstance().saveUserId(bean.getId());
                        UserManager.getInstance().saveCompanyId(bean.getCompanyId());
                        SafetyApplication.getInstance().getKalleConfig()
                                .setHeader("BIT-TOKEN", bean.getToken())
                                .setHeader("BIT-UID", bean.getId())
                                .setHeader("COMPANY-ID", bean.getCompanyId());
//                        LogUtils.e("KZZ-PUSH-ID-LOGN", JPushInterface.getRegistrationID(mActivity));
                        PreferenceUtils.get().putBoolean(ConfigPF.USER_IS_LOGIN_KEY, true);
                    }
                } else {
                    ToastUtils.showShort(response.failed().getErrorMsg());
                }
            }
        });
    }
}
