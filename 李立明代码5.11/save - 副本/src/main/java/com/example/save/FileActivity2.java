package com.example.save;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FileActivity2 extends AppCompatActivity {
    private TextView mText;
    private EditText mEdit;
    private FileDemo2 mFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file2);
        mEdit = findViewById(R.id.file_edit);
        mText = findViewById(R.id.file_text);
        mFile = new FileDemo2();
    }

    public void commit(View view) {
        mFile.write(mEdit.getText().toString());
        mEdit.setText("");
        mText.setText(mFile.read());
    }

    public void flush(View view) {
        mText.setText(mFile.read());
    }

    @Override
    protected void onResume() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
        mText.setText(mFile.read());
        }else{
            Toast.makeText(this,"没有文件操作权限",Toast.LENGTH_SHORT).show();
        }
        super.onResume();
    }
}