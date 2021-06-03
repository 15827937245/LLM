package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.thread.synchronizedDemo.Test;

public class MainActivity extends AppCompatActivity {
    private String TAG = "llm_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void start(View view) {
        Test test = new Test();
       test.init();
//        test.test();
        test.classLock();
        //test.instanceLock();
       // test();
    }

    private void test(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    Log.i(TAG, "第1次获取锁，这个锁是：" + this);
                    //System.out.println("第1次获取锁，这个锁是：" + this);
                    int index = 1;
                    while (true) {
                        synchronized (this) {
                            Log.i(TAG, "第" + (++index) + "次获取锁，这个锁是：" + this);
                            //System.out.println("第" + (++index) + "次获取锁，这个锁是：" + this);
                        }
                        if (index == 10) {
                            break;
                        }
                    }
                }
            }
        }).start();
    }

}