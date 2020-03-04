package com.example.sm4forandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 向共享文件夹下写数据的工具类 写的时候根据不同的帐号对应写入数据，防止清除缓存时造成数据丢失（存储图案解锁密码）
 *SharedPreferences是Android平台上一个轻量级的存储类，主要是保存一些常用的配置比如窗口状态，
 一般在Activity中 重载窗口状态onSaveInstanceState保存一般使用SharedPreferences完成，
 它提供了Android平台常规的Long长 整形、Int整形、String字符串型的保存。
 */
public class SharedPreferencesHelper {

    public static final String PRE_PATH_NAME = ".demo";

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context context;

    public static SharedPreferencesHelper getDefaultHelper(Context context) {
        return new SharedPreferencesHelper(context, PRE_PATH_NAME);
    }

    public static SharedPreferencesHelper getDefaultHelper(Context context, String fileName) {
       return new SharedPreferencesHelper(context, fileName);
    }

    private SharedPreferencesHelper(Context c, String name) {
        context = c;
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void removeAll() {
        editor = sp.edit();
        editor.clear();
       editor.commit();
    }

    public void removeByKey(String key) {
        editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    public void putValue(String key, String value) {
        editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(String key) {
        return sp.getString(key, "");
    }

    public String getValue(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void putValue(String key, long value) {
        editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public boolean getBooleanValue(String key) {
        return sp.getBoolean(key, false);
    }

    public void putValue(String key, boolean value) {
        editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public long getLongValue(String key) {
        return sp.getLong(key, 0);
    }

    public void putValue(String key, int value) {
        editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getIntValue(String key) {
        return sp.getInt(key, 0);
    }

    public int getIntValue(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }


}