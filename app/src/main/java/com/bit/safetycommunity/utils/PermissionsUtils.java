package com.bit.safetycommunity.utils;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.Disposable;

/**
 * author : zhangzhao.ke
 * time   : 2019/04/23
 * desc   :申请相关权限工具类
 */

public class PermissionsUtils {

    private RxPermissions rxPermissions;
    private static PermissionsUtils instance;

    public static PermissionsUtils getInstance() {
        if (instance == null) {
            synchronized (PermissionsUtils.class) {
                if (instance == null) {
                    instance = new PermissionsUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 申请相关权限
     *
     * @param activity   当前activity
     * @param permission 申请权限数组
     * 可能会发生异常：java.lang.IllegalStateException: Activity has been destroyed
     */
    public void requestPermissions(Activity activity, String... permission) {
        try {
            if (activity == null || activity.isDestroyed() || activity.isFinishing())
                return;
            rxPermissions = new RxPermissions(activity);
            Disposable disposable = rxPermissions.request(permission)
                    .subscribe(aBoolean -> {

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
