package com.example.broadcastdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadcastReceiverThree extends BroadcastReceiver {
    private String TAG = "llm-MyBroadcastReceiverThree";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: 我是广播接收者3，我收到消息");
    }
}
