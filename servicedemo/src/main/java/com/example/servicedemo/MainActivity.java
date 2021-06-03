package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.servicedemo.service.MyBroadcastReceiver;
import com.example.servicedemo.service.MyService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "llm_MainActivity";
    private Button mStartServiceBtn;;
    private ServiceConnection serviceConnection;
    private MyService mService;
    private MyBroadcastReceiver mMyBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: "+Thread.currentThread().getName());
        init();
    }

    private void init() {
        mStartServiceBtn = findViewById(R.id.start_service_btn);
        mStartServiceBtn.setOnClickListener((view)->{
            Intent  intent = new Intent(this, MyService.class);
            bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
        });

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(TAG, "onServiceConnected: 绑定成功！！");
              mService  = (MyService) ((MyService.LocalBinder) service).getService();

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i(TAG, "onServiceDisconnected: 绑定失败！！");
                mService = null;
            }
        };

        mMyBroadcastReceiver = new MyBroadcastReceiver();
        //创建过滤器，并指定action，使之用于接收同action的广播
        IntentFilter filter = new IntentFilter("MyBroadcastReceiver_action");
        //注册广播
        registerReceiver(mMyBroadcastReceiver,filter);
    }

    public void getCount(View view){
        if (mService!=null){
            Toast.makeText(this,"服务器运行时间为："+mService.getCount(),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"请先绑定服务：",Toast.LENGTH_SHORT).show();
        }

    }
    public void stopService(View view){
        if (mService!=null){
        unbindService(serviceConnection);
            Toast.makeText(this,"解除绑定成功",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this,"请先绑定",Toast.LENGTH_SHORT).show();

        }
    }
    public void sendBroadcast(View view){
        Intent intent = new Intent();
        intent.setAction("MyBroadcastReceiver_action");
        intent.putExtra("msg","MyService服务运行了：秒");
        sendBroadcast(intent);
    }
    public void jumpActivity(View view) {
        Intent intent = new Intent(this, DemoActivity.class);
        startActivity(intent);
    }
}