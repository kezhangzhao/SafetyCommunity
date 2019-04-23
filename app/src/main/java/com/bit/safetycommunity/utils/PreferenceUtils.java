package com.bit.safetycommunity.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import com.bit.safetycommunity.app.SafetyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * author : zhangzhao.ke
 * time   : 2018/08/30
 * desc   : SharedPreferences工具类，存储
 */

public class PreferenceUtils {

    private static final String SP_NAME = SafetyApplication.getInstance().getPackageName();

    private SharedPreferences sp;

    public PreferenceUtils(Context ctx) {
        if (ctx == null) {
            throw new NullPointerException("初始化" + PreferenceUtils.class.getName() + "类时Context实例不能为空");
        }
        sp = ctx.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }


    private static volatile PreferenceUtils instance;

    public static synchronized PreferenceUtils get() {
        if (instance == null) {
            synchronized (PreferenceUtils.class) {
                if (instance == null) {
                    instance = new PreferenceUtils(SafetyApplication.getInstance().getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        return getString(key, "");
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void putDouble(String key, double value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, String.valueOf(value));
        editor.apply();
    }

    public double getDouble(String key) {
        return getDouble(key, 0d);
    }

    public double getDouble(String key, double defaultValue) {
        String value = sp.getString(key, String.valueOf(defaultValue));
        return parse(value);
    }

    private double parse(String text) {
        if (isNumber(text)) {
            return Double.parseDouble(text);
        }
        return 0;
    }

    private boolean isNumber(String text) {
        if (TextUtils.isEmpty(text)) return false;
        try {
            Double.parseDouble(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }

    public long getLong(String key, long defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public Set<String> putStringSet(String key, Set<String> set) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet(key, set);
        editor.apply();
        return set;
    }

    public Set<String> getStringSet(String key) {
        return getStringSet(key, new HashSet<String>());
    }

    public Set<String> getStringSet(String key, Set<String> set) {
        return sp.getStringSet(key, set);
    }

    /**
     * 使用SharedPreference保存对象
     *
     * @param key        储存对象的key
     * @param saveObject 储存的对象
     */
    public void putObject(String key, Object saveObject) {
        if (saveObject != null) {
            SharedPreferences.Editor editor = sp.edit();
            String string = Object2String(saveObject);
            editor.putString(key, string);
            editor.apply();
        }
    }

    /**
     * 获取SharedPreference保存的对象
     *
     * @param key 储存对象的key
     * @return object 返回根据key得到的对象
     */
    public Object getObject(String key) {
        String string = get().getString(key);
        if (string != null) {
            return String2Object(string);
        } else {
            return null;
        }
    }


    /**
     * writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
     * 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
     *
     * @param object 待加密的转换为String的对象
     * @return String   加密后的String
     */
    private static String Object2String(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 使用Base64解密String，返回Object对象
     *
     * @param objectString 待解密的String
     * @return object      解密后的object
     */
    private static Object String2Object(String objectString) {
        byte[] mobileBytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public void remove(String key) {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
