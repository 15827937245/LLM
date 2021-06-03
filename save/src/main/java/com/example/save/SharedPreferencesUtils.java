package com.example.save;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils{
    public static void saveString(Context context,String key,String value,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }

    public static void saveInt(Context context,String key,int value,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    public static void saveBoolean(Context context,String key,boolean value,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static void saveFloat(Context context,String key,float value,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putFloat(key, value);
        edit.commit();
    }

    public static void saveLong(Context context,String key,long value,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(key, value);
        edit.commit();
    }


    public static String getString(Context context,String key,String defValue,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        return sharedPreferences.getString(key,defValue);
    }

    public static int getInt(Context context,String key,int defValue,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        return sharedPreferences.getInt(key,defValue);
    }

    public static boolean getBoolean(Context context,String key,boolean defValue,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        return sharedPreferences.getBoolean(key,defValue);
    }

    public static float getFloat(Context context,String key,float defValue,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        return sharedPreferences.getFloat(key,defValue);
    }

    public static long getLong(Context context,String key,long defValue,String name,int mode){
        SharedPreferences sharedPreferences = context.getSharedPreferences(name,mode);
        return sharedPreferences.getLong(key,defValue);
    }

}
