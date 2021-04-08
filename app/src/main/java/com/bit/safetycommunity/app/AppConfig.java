package com.bit.safetycommunity.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.bit.safetycommunity.utils.FileUtils;
import com.yanzhenjie.kalle.util.IOUtils;

import java.io.File;

import androidx.annotation.Nullable;

/**
 * 一些配置信息
 */
public class AppConfig {

    private static AppConfig sInstance;

    public static AppConfig get() {
        if (sInstance == null)
            synchronized (AppConfig.class) {
                if (sInstance == null)
                    sInstance = new AppConfig();
            }
        return sInstance;
    }

    private final SharedPreferences mPreferences;
    /**
     * App root path.
     */
    public final String PATH_APP_ROOT;
    /**
     * Download.
     */
    public final String PATH_APP_DOWNLOAD;
    /**
     * Images.
     */
    public final String PATH_APP_IMAGE;
    /**
     * file.
     */
    public final String PATH_APP_FILE;
    /**
     * Cache root path.
     */
    public final String PATH_APP_CACHE;
    /**
     * Crash root path.
     */
    public final String PATH_APP_CRASH;

    public String getPATH_APP_SIGNATURE() {
        IOUtils.createFolder(PATH_APP_SIGNATURE);
        return PATH_APP_SIGNATURE;
    }

    /**
     * signature root path.
     */
    private final String PATH_APP_SIGNATURE;
    /**
     * body cache root path.
     */
    public final String PATH_APP_CACHE_BODY;

    private AppConfig() {
        this.mPreferences = SafetyApplication.getInstance().getSharedPreferences("ElevatorPlatform", Context.MODE_PRIVATE);
        this.PATH_APP_ROOT = FileUtils.getAppRootPath(SafetyApplication.getInstance()).getAbsolutePath() + File.separator + "ElevatorPlatform";
        this.PATH_APP_DOWNLOAD = PATH_APP_ROOT + File.separator + "Download";
        this.PATH_APP_IMAGE = PATH_APP_ROOT + File.separator + "Images";
        this.PATH_APP_FILE = PATH_APP_ROOT + File.separator + "File";
        this.PATH_APP_CACHE = PATH_APP_ROOT + File.separator + "Cache";
        this.PATH_APP_CRASH = PATH_APP_ROOT + File.separator + "Crash";
        this.PATH_APP_SIGNATURE = PATH_APP_ROOT + File.separator + "Signature";
        this.PATH_APP_CACHE_BODY = PATH_APP_CACHE + File.separator + "Elevator";
    }

    /**
     * Initialize file system for app.
     */
    public void initFileDir() {
        IOUtils.createFolder(PATH_APP_ROOT);
        IOUtils.createFolder(PATH_APP_DOWNLOAD);
        IOUtils.createFolder(PATH_APP_IMAGE);
        IOUtils.createFolder(PATH_APP_FILE);
        IOUtils.createFolder(PATH_APP_CACHE);
        IOUtils.createFolder(PATH_APP_CRASH);
        IOUtils.createFolder(PATH_APP_SIGNATURE);
        IOUtils.createFolder(PATH_APP_CACHE_BODY);
    }

    public void setStringApply(String key, String value) {
        mPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public boolean setStringCommit(String key, String value) {
        return mPreferences.edit()
                .putString(key, value)
                .commit();
    }

    public void setBooleanApply(String key, boolean value) {
        mPreferences.edit()
                .putBoolean(key, value)
                .apply();
    }

    public boolean setBooleanCommit(String key, boolean value) {
        return mPreferences.edit()
                .putBoolean(key, value)
                .commit();
    }

    public void setFloatApply(String key, float value) {
        mPreferences.edit()
                .putFloat(key, value)
                .apply();
    }

    public boolean setFloatCommit(String key, float value) {
        return mPreferences.edit()
                .putFloat(key, value)
                .commit();
    }

    public void setIntApply(String key, int value) {
        mPreferences.edit()
                .putInt(key, value)
                .apply();
    }

    public boolean setIntCommit(String key, int value) {
        return mPreferences.edit()
                .putInt(key, value)
                .commit();
    }

    public void setLongApply(String key, long value) {
        mPreferences.edit()
                .putLong(key, value)
                .apply();
    }

    public boolean setLongCommit(String key, long value) {
        return mPreferences.edit()
                .putLong(key, value)
                .commit();
    }

    public <P extends Parcelable> void setObjectApply(String key, P param) {
        if (param != null)
            mPreferences.edit()
                    .putString(key, JSON.toJSONString(param))
                    .apply();
        else
            mPreferences.edit()
                    .putString(key, "")
                    .apply();
    }

    public <P extends Parcelable> boolean setObjectCommit(String key, P param) {
        if (param != null)
            return mPreferences.edit()
                    .putString(key, JSON.toJSONString(param))
                    .commit();
        else
            return mPreferences.edit()
                    .putString(key, "")
                    .commit();
    }

    public String getString(String key, String defaultValue) {
        return mPreferences.getString(key, defaultValue);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return mPreferences.getBoolean(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return mPreferences.getFloat(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return mPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return mPreferences.getLong(key, defaultValue);
    }

    @Nullable
    public <P extends Parcelable> P getObject(String key, Class<P> pClass) {
        String jsonObject = getString(key, "");
        if (!TextUtils.isEmpty(jsonObject)) {
            try {
                return JSON.parseObject(jsonObject, pClass);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
