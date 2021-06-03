package com.example.servicedemo.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.servicedemo.R;

public class MyService extends Service {
    private String TAG = "llm-MyService";
    private boolean flag = true;
    private int count = 0;
    private LocalBinder binder = new LocalBinder();
    /**
     * id不可设置为0,否则不能设置为前台service
     */
    private static final int NOTIFICATION_DOWNLOAD_PROGRESS_ID = 0x0001;


    public class LocalBinder extends Binder {
        public Service getService(){
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        new Thread(()->{
            while (flag){
                try {
                    Thread.sleep(1000);
                    if (count%10==0){
                        Log.i(TAG, "onBind: 发送广播");
                        Intent intent1 =new Intent();
                        intent1.setAction("MyBroadcastReceiver_action");
                        intent1.putExtra("msg","MyService服务运行了："+count+"秒");
                        sendBroadcast(intent1);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                Log.i(TAG, "服务启动运行秒数："+count);
            }
        }).start();
        return binder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        stopForeground(true);
        super.unbindService(conn);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: onCreate！");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: 服务启动！");
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        Log.i(TAG, "onDestroy: 服务关闭");
    }

    public int getCount() {
        return count;
    }

}