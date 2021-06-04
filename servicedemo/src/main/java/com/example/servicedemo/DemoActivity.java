package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.example.servicedemo.R;
import com.example.servicedemo.service.ServiceDemo;

public class DemoActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        intent = new Intent(this, ServiceDemo.class);
    }

    public void startServiceMode(View view) {
        startService(intent);
    }

    public void bindServiceMode(View view) {
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("..", "onServiceConnected: 绑定成功！");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Service.BIND_AUTO_CREATE);
    }

    public void myStopService(View view) {
        stopService(intent);
    }
}