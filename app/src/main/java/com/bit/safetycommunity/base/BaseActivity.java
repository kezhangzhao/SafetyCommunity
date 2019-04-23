package com.bit.safetycommunity.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.bit.safetycommunity.manager.ActivityManager;
import com.blankj.utilcode.util.LogUtils;
import com.kzz.safetycommunity.R;

import butterknife.ButterKnife;

import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;


public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mActivity;
    protected static Integer systemBarTextColor;//系统顶部导航栏的字体颜色
    protected static Integer systemBarBackgroundColor;//系统顶部导航栏的背景颜色
    protected static Integer systemBarBackgroundColor21;//系统顶部导航栏的背景颜色android5.0到6.0的也就是默认颜色了

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientation();
        ActivityManager.addActivity(this);
        if (getIntent() != null) {
            handleIntent(getIntent());
        }
        mActivity = this;
        setWindowBarColor();
        setWindowStatusBar(mActivity, systemBarBackgroundColor, systemBarTextColor);
        setContentView(getLayoutResourceId());
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * 处理传递过来的Intent
     *
     * @param intent intent
     */
    protected void handleIntent(Intent intent) {
    }

    /**
     * 布局layout id
     */
    protected abstract int getLayoutResourceId();

    /**
     * 初始化操作
     */
    protected abstract void init();

    /**
     * 设置竖屏
     */
    protected void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//锁定竖屏
    }

    /**
     * 设置系统顶部任务栏背景颜色和字体颜色
     */
    protected void setWindowBarColor() {
        systemBarTextColor = SYSTEM_UI_FLAG_VISIBLE;
        systemBarBackgroundColor = R.color.title_bg;
        systemBarBackgroundColor21 = R.color.navigation_bar_bg;
    }

    //add ke start 2018.5.2

    /**
     * 将颜色配置到系统顶部任务栏
     *
     * @param activity        当前窗口
     * @param backgroundColor 背景颜色
     * @param textColor       字体颜色
     */
    public void setWindowStatusBar(Activity activity, Integer backgroundColor, Integer textColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (backgroundColor != null)
                setWindowStatusBarColor(activity, backgroundColor);
            if (textColor != null)
                setWindowStatusBarTextColor(activity, textColor);
        } else {
            if (systemBarBackgroundColor21 != null)
                setWindowStatusBarColor(activity, systemBarBackgroundColor21);
        }
    }

    /**
     * 设置标题栏颜色
     *
     * @param activity        当前窗口
     * @param backgroundColor 颜色
     */
    public void setWindowStatusBarColor(Activity activity, int backgroundColor) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(backgroundColor));

                //底部导航栏
//                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置系统标题栏的字体颜色
     *
     * @param activity   当前窗口
     * @param visibility 深色 SYSTEM_UI_FLAG_LIGHT_STATUS_BAR；白色 SYSTEM_UI_FLAG_VISIBLE(其他颜色不行。。)
     */
    public static void setWindowStatusBarTextColor(Activity activity, int visibility) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(visibility);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.removeActivity(this);
        ActivityManager.removeActivityTmp(this);
        ActivityManager.removeActivityTmp2(this);
        LogUtils.e("onDestroy:", this.getClass().getName());
    }

}
