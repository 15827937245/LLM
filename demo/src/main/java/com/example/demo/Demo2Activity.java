package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Demo2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2);

        fun();
    }

    private void fun() {
        Map map = new HashMap();
        Intent intent = getIntent();
        for (int i = 0; i <intent.getIntExtra("number",1)/10; i++) {
            int finalI = i;
            new Thread(()->{
                int sum = 1;
                    for (int j = 100*finalI;j < 100*(finalI +1); j++) {
                        for (int k = 1; k < j+1; k++) {
                            sum*=k;
                        }
                        map.put(j,sum);
                    }
                }).start();
        }

        new Thread(()->{
            int sum = 1;
            for (int j =intent.getIntExtra("number",1)/10*10;j<intent.getIntExtra("number",1); j++) {
                for (int k = 1; k < j+1; k++) {
                    sum*=k;
                }
                map.put(j,sum);
            }
        }).start();

    }

}