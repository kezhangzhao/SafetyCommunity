package com.bit.safetycommunity.manager;

import android.app.Activity;
import android.content.Context;

import com.bit.safetycommunity.base.BaseActivity;
import com.blankj.utilcode.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity 管理类
 */
public class ActivityManager {

    private static List<BaseActivity> activities = new ArrayList<>();
    private static List<BaseActivity> activitiesTmp = new ArrayList<>();//临时栈
    private static List<BaseActivity> activitiesTmp2 = new ArrayList<>();//临时栈第二个。

    public static void addActivity(BaseActivity activity) {
        activities.add(activity);
    }

    public static void removeActivity(BaseActivity activity) {
        activities.remove(activity);
    }

    /**
     * 移除同个activity 并销毁
     *
     * @param activityName activity名字
     */
    public static void removeAllSameActivity(String activityName) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getClass().getSimpleName().equals(activityName)) {
                activities.get(i).finish();
            }
        }
    }

    /**
     * 移除此activity上面的所有activity，并回到当前activity
     *
     * @param activityName activity名字
     */
    public static void finishUpActivity(String activityName) {
        boolean isUp = false;
        for (int i = 0; i < activities.size(); i++) {
            if (isUp) {
                activities.get(i).finish();
            } else if (activities.get(i).getClass().getSimpleName().equals(activityName)) {
                isUp = true;
            }
        }
    }

    /**
     * 在栈中是否存在这个activity
     *
     * @param activityName activity名字
     */
    public static boolean isExistActivity(String activityName) {
        boolean isExist = false;
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getClass().getSimpleName().equals(activityName)) {
                isExist = true;
            }
        }
        return isExist;
    }

    public static void finishAll() {
        for (BaseActivity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void addActivityTmp(BaseActivity activity) {
        activitiesTmp.add(activity);
    }

    public static void removeActivityTmp(BaseActivity activity) {
        activitiesTmp.remove(activity);
    }

    public static void finishAllTmp() {
        for (BaseActivity activity : activitiesTmp) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    public static void addActivityTmp2(BaseActivity activity) {
        activitiesTmp2.add(activity);
    }

    public static void removeActivityTmp2(BaseActivity activity) {
        activitiesTmp2.remove(activity);
    }

    public static void finishAllTmp2() {
        for (BaseActivity activity : activitiesTmp2) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 获取顶端的activity
     *
     * @return activity
     */
    public static BaseActivity getTopActivity() {
        if (activities != null && activities.size() > 0) {
            return activities.get(activities.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 获取当前的activity或者appContext
     *
     * @return activity
     */
    public static Context getTopActivityOrApp() {
        if (isAppForeground()) {
            Activity topActivity = getTopActivity();
            return topActivity == null ? Utils.getApp() : topActivity;
        } else {
            return Utils.getApp();
        }
    }

    /**
     * 判断当前包名的app是否运行在手机前台
     *
     * @return app是否运行在手机前台
     */
    private static boolean isAppForeground() {
        android.app.ActivityManager am =
                (android.app.ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<android.app.ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (android.app.ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(Utils.getApp().getPackageName());
            }
        }
        return false;
    }

    public static List<BaseActivity> getActivityList() {
        return activities;
    }
}
