package com.example.broadcastdemo.broadcast;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiverTwo extends BroadcastReceiver {
    private String TAG = "llm-MyBroadcastReceiverTwo";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: 我是广播接收者2，我收到消息");

    }
}
