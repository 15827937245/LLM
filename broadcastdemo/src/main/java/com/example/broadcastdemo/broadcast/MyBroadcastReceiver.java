package com.example.broadcastdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    public String TAG = "llm_MyBroadcastReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        if (activeNetworkInfo!=null&&activeNetworkInfo.isAvailable()){
            Toast.makeText(context,"网络可用",Toast.LENGTH_SHORT).show();
            Log.i(TAG, "onReceive: 网络可用");
        }else {
            Log.i(TAG, "onReceive: 网络不可用");
        }
    }
}
