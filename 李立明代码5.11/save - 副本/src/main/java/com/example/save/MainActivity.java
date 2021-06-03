package com.example.save;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private EditText mString;
    private Switch mSwitch;
    private EditText mInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInt = findViewById(R.id.main_text_int);
        mString = findViewById(R.id.main_text_string);
        mSwitch = findViewById(R.id.main_switch);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        mInt.setText(SharedPreferencesUtils.getInt(this,"test1",100,"SP",MODE_PRIVATE)+"");
        mString.setText(SharedPreferencesUtils.getString(this,"test2","空","SP",MODE_PRIVATE));
        mSwitch.setChecked(SharedPreferencesUtils.getBoolean(this,"test3",false,"SP",MODE_PRIVATE));
    }

    public void commit(View view) {
        SharedPreferencesUtils.saveInt(this,"test1",Integer.valueOf(mInt.getText().toString()),"SP",MODE_PRIVATE);
        SharedPreferencesUtils.saveBoolean(this,"test3",mSwitch.isChecked(),"SP",MODE_PRIVATE);
        SharedPreferencesUtils.saveString(this,"test2",mString.getText().toString(),"SP",MODE_PRIVATE);
    }
}