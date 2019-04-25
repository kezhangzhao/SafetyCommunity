package com.bit.safetycommunity.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;

import com.bit.safetycommunity.app.SafetyApplication;
import com.kzz.safetycommunity.R;

import java.io.File;
import java.util.HashMap;
import java.util.List;


public class AppUtils {

    /**
     * 描述：判断网络是否有效.
     *
     * @return true, if is network available
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static String getDeviceId() {
        TelephonyManager tm =
                (TelephonyManager) SafetyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(SafetyApplication.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "DeviceId";
        }
        return tm != null ? tm.getDeviceId() : "DeviceId";
    }

    public static String getIMEI() {
        TelephonyManager tm =
                (TelephonyManager) SafetyApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(SafetyApplication.getInstance(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "IMEI";
            }
            return tm != null ? tm.getImei() : "IMEI";
        }
        return tm != null ? tm.getDeviceId() : "DeviceId";
    }

    /**
     * 读取baseurl
     *
     * @param url
     * @return
     */
    public static String getBasUrl(String url) {
        String head = "";
        int index = url.indexOf("://");
        if (index != -1) {
            head = url.substring(0, index + 3);
            url = url.substring(index + 3);
        }
        index = url.indexOf("/");
        if (index != -1) {
            url = url.substring(0, index + 1);
        }
        return head + url;
    }

    public static boolean install(Context context, String filePath) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        File file = new File(filePath);
        if (file.length() > 0 && file.exists() && file.isFile()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, "com.bit.elevator.fileProvider", file);
                i.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }

            context.startActivity(i);
            return true;
        }
        return false;
    }

    /**
     * 获取已安装的APP列表
     */
    public static HashMap<String, HashMap<String, Object>> getAppList(Context context) {
        HashMap<String, HashMap<String, Object>> items = new HashMap<>();

        // 得到PackageManager对象
        PackageManager pm = context.getPackageManager();
        // 得到系统安装的所有程序包的PackageInfo对象
        // List<ApplicationInfo> packs = pm.getInstalledApplications(0);
        List<PackageInfo> packs = pm.getInstalledPackages(0);

        for (PackageInfo pi : packs) {
            HashMap<String, Object> map = new HashMap<>();
            // 显示用户安装的应用程序，而不显示系统程序
            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0
                    && (pi.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == 0) {
                // 这将会显示所有安装的应用程序，包括系统应用程序
                map.put("icon", pi.applicationInfo.loadIcon(pm));// 图标
                map.put("appName", pi.applicationInfo.loadLabel(pm));// 应用程序名称
                map.put("packageName", pi.applicationInfo.packageName);// 应用程序包名
                map.put("versionName", pi.versionName);// 应用程序版本号

                items.put(pi.applicationInfo.packageName.toString(), map);
            }
        }
        return items;
    }

    /**
     * 获取内核版本号
     */
    public static int getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取外部版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return context.getString(R.string.main_check_update_app_err);
        }
    }

    /**
     * 获取应用包名
     */
    public static String getPackageName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            return info.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean checkVersionCode(Context context, String code) {
        boolean isUpdate;
        try {
            for (int i = 0; i < code.length(); i++) {
                if (!Character.isDigit(code.charAt(i))) {
                    return false;
                }
            }
            int versionCode = Integer.parseInt(code);
            isUpdate = versionCode > getVersionCode(context);
            return isUpdate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
