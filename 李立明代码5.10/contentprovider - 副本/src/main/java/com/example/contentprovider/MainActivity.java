package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String mTable;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTable = MySQLHelper.TABLE_NAME;
        mContext = this;
        mDatabase = new MySQLHelper(mContext,"mySqlHelper",null,MySQLHelper.DB_VERSION).getWritableDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.execSQL("insert into " + mTable + " values(1,'shixinzhang','handsome boy')");
            }
        }).start();
    }


}