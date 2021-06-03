package com.example.save;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileDemo {
    private Context mContext;

    public FileDemo(Context mContext) {
        this.mContext = mContext;
    }

    public String read(){
        try {
            FileInputStream inputStream = mContext.openFileInput("test.txt");
            byte[] bytes = new byte[1024];
            int hasRead = 0;
            StringBuffer stringBuffer = new StringBuffer();
            while ((hasRead = inputStream.read(bytes)) != -1){
                stringBuffer.append(new String(bytes,0,hasRead));
            }
            inputStream.close();
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void write(String msg){
        if (msg == null){
            return;
        }
        try {
            FileOutputStream fileOutputStream = mContext.openFileOutput("test.txt", Context.MODE_APPEND);
            fileOutputStream.write(msg.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
