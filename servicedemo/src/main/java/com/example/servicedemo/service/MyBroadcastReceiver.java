package com.example.servicedemo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.servicedemo.MainActivity;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private String TAG = "llm-MyBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: 发送消息");
       // Toast.makeText(MainActivity.class,intent.getStringExtra("msg"),Toast.LENGTH_SHORT);
        Log.i(TAG, "onReceive: "+intent.getStringExtra("msg"));
    }
}
