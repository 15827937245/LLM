package com.example.broadcastdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.broadcastdemo.broadcast.MyBroadcastReceiver;
import com.example.broadcastdemo.broadcast.MyBroadcastReceiverOne;
import com.example.broadcastdemo.broadcast.MyBroadcastReceiverThree;
import com.example.broadcastdemo.broadcast.MyBroadcastReceiverTwo;

public class MainActivity extends AppCompatActivity {
    private String TAG = "llm";
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
        MyBroadcastReceiverOne myBroadcastReceiverOne = new MyBroadcastReceiverOne();
        MyBroadcastReceiverTwo myBroadcastReceiverTwo = new MyBroadcastReceiverTwo();
        MyBroadcastReceiverThree myBroadcastReceiverThree = new MyBroadcastReceiverThree();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(100);
        filter.addAction("test");
        registerReceiver(myBroadcastReceiverOne,filter);
        filter.setPriority(200);
        registerReceiver(myBroadcastReceiverTwo,filter);
        filter.setPriority(300);
        registerReceiver(myBroadcastReceiverThree,filter);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        //registerReceiver(myBroadcastReceiver,filter);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(myBroadcastReceiver,filter);


    }

    public void sendBroadcast(View view){
        Intent intent = new Intent();
        intent.setAction("test");
        sendOrderedBroadcast(intent,null);
    }

    public void sendNormalBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("test");
        intent.putExtra("msg","我是普通广播");
        sendBroadcast(intent);
    }


    public void sendLocalBroadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("test");
        intent.putExtra("msg","我是本地广播");
        localBroadcastManager.sendBroadcast(intent);
    }
}