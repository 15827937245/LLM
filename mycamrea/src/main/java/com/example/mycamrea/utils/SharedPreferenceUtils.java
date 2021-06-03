package com.example.mycamrea.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtils {
    public static void saveBooleanValue(Context context,String key,boolean value,String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void saveIntegerValue(Context context,String key,int value,String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static boolean getBooleanValue(Context context,String key,String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
       return  sharedPreferences.getBoolean(key,false);
    }

    public static int getIntegerValue(Context context,String key,String name){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,Context.MODE_PRIVATE);
        return  sharedPreferences.getInt(key,3);
    }
}


