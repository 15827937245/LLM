package com.example.save;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileDemo2 {
    public  void write(String msg){
        File sdCardDir  = Environment.getExternalStorageDirectory();
        File saveFilepath = new File(sdCardDir+"/saveInfo","a.txt");
        System.out.println(saveFilepath.getAbsoluteFile());
        try {
            if (!saveFilepath.exists()){
            if (!saveFilepath.getParentFile().exists()) {
                saveFilepath.getParentFile().mkdirs();
            }
            saveFilepath.createNewFile();
            }
        } catch (IOException e) {
        e.printStackTrace();
    }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(saveFilepath);
            fileOutputStream.write(msg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  String read(){
            File sdCardDir  = Environment.getExternalStorageDirectory();
            File saveFilepath = new File(sdCardDir+"/saveInfo","a.txt");
        System.out.println(saveFilepath.getAbsoluteFile());
        try {
            if (!saveFilepath.exists()){
                if (!saveFilepath.getParentFile().exists()) {
                    saveFilepath.getParentFile().mkdirs();
                }
                saveFilepath.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
            try {
                FileInputStream fileInputStream = new FileInputStream(saveFilepath);
                byte[] bytes = new byte[1024];
                int hasRead = 0;
                StringBuffer stringBuffer = new StringBuffer();
                while ((hasRead = fileInputStream.read(bytes))!=-1){
                    stringBuffer.append(new String(bytes,0,hasRead));
                }
                return stringBuffer.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return null;
    }
}
