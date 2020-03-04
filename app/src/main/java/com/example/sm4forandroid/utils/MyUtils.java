package com.example.sm4forandroid.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

/*传出的工具类，用于数据存储
 */

public class MyUtils {

    /**
     * 静态gson工具了
     */
    public static final Gson kGson = new Gson();

    /**
     * 转换json字符串为相应的对象
     *
     * @param content
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromJson(String content, Class<T> type) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        try {
            return kGson.fromJson(content, type);
        } catch (Exception e) {
            return null;
        }
    }

    public static void savePwd(Context context, String pwd) {
        SharedPreferencesHelper helper = SharedPreferencesHelper.getDefaultHelper(context);
        helper.putValue("pwd", pwd);
    }

    public static String gePwd(Context context) {
        SharedPreferencesHelper helper = SharedPreferencesHelper.getDefaultHelper(context);
        return helper.getValue("pwd","");
    }
}