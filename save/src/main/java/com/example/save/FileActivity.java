package com.example.save;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FileActivity extends AppCompatActivity {
private TextView mText;
private EditText mEdit;
private FileDemo mFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        mEdit = findViewById(R.id.file_edit);
        mText = findViewById(R.id.file_text);
        mFile = new FileDemo(this);
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
        mText.setText(mFile.read());
        super.onResume();
    }
}