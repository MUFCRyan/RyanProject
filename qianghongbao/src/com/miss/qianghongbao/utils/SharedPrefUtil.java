package com.miss.qianghongbao.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefUtil {
    private static SharedPreferences mSP;
    /**
     * 避免反复打开关闭SP
     * @param context
     * @return
     */
    private static SharedPreferences getSharedPref(Context context) {
        if(mSP == null) {
            mSP = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return mSP;
    }

    /**
     * 往SP里存boolean值
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        getSharedPref(context).edit().putBoolean(key, value).commit();
    }
    public static void putInt(Context context, String key,int value) {
    	getSharedPref(context).edit().putInt(key, value).commit();
    }
    public static int getInt(Context context, String key) {
    	return getSharedPref(context).getInt(key, 0);
    }
    
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getSharedPref(context).getBoolean(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        return getSharedPref(context).getString(key, defValue);
    }
    
    public static void putString(Context context, String key, String value) {
        getSharedPref(context).edit().putString(key, value).commit();
    }
    
    /**
     * 移除字符串
     * @param context
     * @param key
     */
    public static void removeString(Context context, String key) {
        getSharedPref(context).edit().remove(key).commit();
    }
    
    public static void removeBoolean(Context context, String key) {
        getSharedPref(context).edit().remove(key).commit();
    }
}
