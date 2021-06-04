package com.example.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private boolean flag = false;
    private TextView mText;
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 0:{
                mText.setText("哈喽");
                    break;
                }
                case 1:{
                    mText.setText(msg.obj.toString());
                    break;
                }
                default:{
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println(mHandler.getLooper().getThread().getName());
        mText = findViewById(R.id.text);
    }

    public void changeUI(View view){
        new ChangeUI(mHandler).changeUI();
//        if (!flag){
//        new Thread(()->{
//            int count = 10;
//            while (flag){
//                try {
//                    Message message = new Message();
//                    message.what = 1;
//                    message.obj = count;
//                    mHandler.sendMessage(message);
//                    Thread.sleep(1000);
//                    count--;
//                    if (count<=0){
//                        flag = false;
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//    }
        Intent intent = new Intent(this,MainActivity2.class);
        Bundle bundle = new Bundle();

    }

}