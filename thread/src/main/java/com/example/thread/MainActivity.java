package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private String TAG = "llm_MainActivity";
    private ThreadPoolExecutor threadPoolExecutor;
    private TextView mText0;
    private TextView mText1;
    private TextView mText2;
    private BlockingQueue<TextView> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText0 = findViewById(R.id.text0);
        mText1 = findViewById(R.id.text1);
        mText2 = findViewById(R.id.text2);
        list = new ArrayBlockingQueue<>(3);
        try {
            list.put(mText0);
            list.put(mText1);
            list.put(mText2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadPoolExecutor = new ThreadPoolExecutor(3,5,5, TimeUnit.SECONDS,new ArrayBlockingQueue<>(3));
    }

    public void start(View view) {
            threadPoolExecutor.execute(()->{
                TextView textView = null;
                try {
                    textView = list.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = 0;
            while (i<1000){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
                int finalI = i;
                TextView finalTextView = textView;
                runOnUiThread(()->{
                    finalTextView.setText(finalI +"");
                });
            }
                try {
                    list.put(textView);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
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