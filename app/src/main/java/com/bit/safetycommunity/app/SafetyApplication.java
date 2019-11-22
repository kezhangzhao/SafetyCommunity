package com.bit.safetycommunity.app;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.bit.safetycommunity.bean.ApkInfoBean;
import com.bit.safetycommunity.manager.UserManager;
import com.bit.safetycommunity.net.JsonConverter;
import com.bit.safetycommunity.utils.AppUtils;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CrashUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.kzz.safetycommunity.BuildConfig;
import com.yanzhenjie.kalle.Kalle;
import com.yanzhenjie.kalle.KalleConfig;
import com.yanzhenjie.kalle.connect.BroadcastNetwork;
import com.yanzhenjie.kalle.cookie.DBCookieStore;
import com.yanzhenjie.kalle.simple.cache.DiskCacheStore;
import com.yanzhenjie.kalle.urlconnect.URLConnectionFactory;
import com.yanzhenjie.kalle.util.MainExecutor;
import com.yanzhenjie.kalle.util.WorkExecutor;

import java.util.concurrent.TimeUnit;


public class SafetyApplication extends Application {

    private static SafetyApplication application;
    private ApkInfoBean apkInfo;//APK信息类
    // 是否向后台动态获取项目首页icon
    private String isDynamic;
    public KalleConfig.Builder newBuilder;
//    public static String registrationID;//极光推送注册码

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);//启用矢量图兼容

        //设置全局默认配置（优先级最低，会被其他设置覆盖）
//        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
//            @Override
//            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
//                //全局设置（优先级最低）
//                layout.setEnableOverScrollBounce(true);
//                layout.setEnableLoadMoreWhenContentNotFull(false);
//
//            }
//        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);//工具类初始化
        initLog();

//       Bugly SDK初始化
//        参数1：上下文对象
//        参数2：注册时申请的APPID
//        参数3：是否开启debug模式，true表示打开debug模式，false表示关闭调试模式

//        CrashReport.initCrashReport(getApplicationContext(), "51df1cc394", true);
//        SpeechUtility.createUtility(this, "appid=" + Config.XFYUN_APPID);
//        Bugly.init(getApplicationContext(), "1469375fd5", true);

//        BygglyUtil.activityInit(getApplicationContext());

//        JPushInterface.setDebugMode(BuildConfig.DEBUG);
//        JPushInterface.init(this);

        initKalle();

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //分包
        MultiDex.install(this);
    }

    /**
     * 获取网络配置信息对象，可以做一些公共操作
     */
    public KalleConfig.Builder getKalleConfig() {
        return newBuilder;
    }

    /**
     * Kalle框架配置信息初始化
     */
    public void initKalle() {
        AppConfig.get().initFileDir();
        newBuilder = KalleConfig.newBuilder();
        KalleConfig kalleConfig = newBuilder
                .addHeader("APP-ID", "5c08e295e4b08e5fd5cb298d")
                .addHeader("DEVICE-ID", "deviceId")
                .addHeader("DEVICE-TYPE", "IMEI")
                .addHeader("OS", "2")
                .addHeader("OS-VERSION", Build.VERSION.RELEASE)
                .addHeader("APP-VERSION", AppUtils.getVersionName(this))
//                .addHeader("PUSH-ID", JPushInterface.getRegistrationID(this))
                .addHeader("BIT-TOKEN", UserManager.getInstance().getUserToken())
                .addHeader("BIT-UID", UserManager.getInstance().getUserId())
                .addHeader("COMPANY-ID", UserManager.getInstance().getCompanyId())
                .workThreadExecutor(new WorkExecutor())
                .mainThreadExecutor(new MainExecutor())
                .connectionTimeout(3 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(5 * 1000, TimeUnit.MILLISECONDS)
                .connectFactory(URLConnectionFactory.newBuilder().build())
                .cookieStore(DBCookieStore.newBuilder(this).build())
                .cacheStore(DiskCacheStore.newBuilder(AppConfig.get().PATH_APP_CACHE).password("bitAndroid").build())
                .network(new BroadcastNetwork(this))
//                .addInterceptor(new LoginInterceptor())
//                .addInterceptor(new LoggerInterceptor("bitAndroid", BuildConfig.DEBUG))
                .converter(new JsonConverter(this))
                .build();
        Kalle.setConfig(kalleConfig);
//        LogUtils.e("KZZ-PUSH-ID-APP",JPushInterface.getRegistrationID(this));
    }


    public ApkInfoBean getApkInfo() {
        return apkInfo;
    }

    public void setApkInfo(ApkInfoBean apkInfo) {
        this.apkInfo = apkInfo;
    }

    public String getIsDynamic() {
        return isDynamic;
    }

    public void setIsDynamic(String isDynamic) {
        this.isDynamic = isDynamic;
    }

    /**
     * @return 全局application
     */
    public static SafetyApplication getInstance() {
        return application;
    }

    public void initLog() {
        final LogUtils.Config config = LogUtils.getConfig()
                .setLogSwitch(BuildConfig.DEBUG)// 设置 log 总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置 log 全局标签，默认为空
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setLogHeadSwitch(true)// 设置 log 头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印 log 时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("safety")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setSingleTagSwitch(true)// 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setConsoleFilter(LogUtils.V)// log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.V)// log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setStackDeep(1);// log 栈深度，默认为 1
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.d(config.toString());
            }
        }).start();
    }

    @SuppressLint("MissingPermission")
    private void initCrash() {
        CrashUtils.init((crashInfo, e) -> {
            e.printStackTrace();
            restartApp();
        });
    }

    private void restartApp() {
        Intent intent = new Intent();
        intent.setClassName("com.bit.elevator", "com.bit.elevator.LoginActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent restartIntent = PendingIntent.getActivity(this, 0, intent, 0);
        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (manager == null) return;
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + 1, restartIntent);
        ActivityUtils.finishAllActivities();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
