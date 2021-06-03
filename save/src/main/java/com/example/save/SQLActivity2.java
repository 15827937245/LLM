package com.example.save;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SQLActivity2 extends AppCompatActivity {
   private String TAG = "llm_SQLActivity2";
    private MySQLiteHelper mMySQLiteHelper;
    private SQLiteDatabase db;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_l2);
        mMySQLiteHelper = new MySQLiteHelper(this,"mysql",null,1);
    }



    public void insert(String name,int price){
        db = mMySQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("price",price);
        db.insert("mytable",null,values);
        db.close();
    }

    public int update(int count,int price){
        db = mMySQLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price",price);
        int number = db.update("mytable",values,"id =?",new String[]{1+""});
        db.close();
        return number;
    }

    public int delete(int id){
        db = mMySQLiteHelper.getWritableDatabase();
        int number = db.delete("mytable", "id =?", new String[]{id + ""});
        db.close();
        return number;
    }

    public boolean query(){
        db = mMySQLiteHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from mytable",null);
        if (cursor.moveToFirst()){
            Log.i(TAG, "query: name:"+cursor.getString(1));
            Log.i(TAG, "query: price:"+cursor.getInt(2));
            while (cursor.moveToNext()){
                Log.i(TAG, "query: name:"+cursor.getString(1));
                Log.i(TAG, "query: price:"+cursor.getInt(2));
            }
        }
        cursor.close();
        db.close();
        return false;
    }

    public void insertBtn(View view) {
        insert("test"+count,count);
        count++;
    }


    public void deleteBtn(View view) {
        delete(count);
    }

    public void updateBtn(View view) {
        update(count,count+10);
    }

    public void queryBtn(View view) {
       query();
    }
}