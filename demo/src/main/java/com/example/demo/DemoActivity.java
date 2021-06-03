package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;

public class DemoActivity extends AppCompatActivity {

    private String TAG = "llm_DemoActivity";
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }

    public void threadSend(View view) {
       getContentFromContentProvider();

//        Intent intent = new Intent(this,Demo2Activity.class);
//        intent.putExtra("number",new Random().nextInt(10000)+1);
//        startActivity(intent);
    }

    public static final String AUTHORITY = "com.example.contentprovider.MyContentProvider";  //授权
    public static final Uri PERSON_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/person");

    private void getContentFromContentProvider() {
        Uri uri = PERSON_CONTENT_URI;    //ContentProvider 中注册的 URI
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id++);
        contentValues.put("name", "rourou"+new Date().toString());
        contentValues.put("description", "beautiful girl");
        ContentResolver contentResolver = getContentResolver();    //获取内容处理器
        contentResolver.insert(uri, contentValues);    //插入一条数据
        //再查询一次
        Cursor cursor = contentResolver.query(uri, new String[]{"name", "description"}, null, null, null, null);
        if (cursor == null) {
            return;
        }
        StringBuilder cursorResult = new StringBuilder("DB 查询结果：");
        while (cursor.moveToNext()) {
            String result = cursor.getString(0) + ", " + cursor.getString(1);
            Log.d(TAG, "DB 查询结果：" + result);
            cursorResult.append("\n").append(result);
        }
        cursor.close();
    }

}