package com.example.weather.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.weather.pojo.Weather;
import com.example.weather.pojo.WeatherInfo;
import com.example.weather.utils.JsonParsing;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WeatherService extends Service {
    private String TAG = "llm_WeatherService";
    private MyBinder binder = new MyBinder();
    private ThreadPoolExecutor mThreadPoolExecutor;


    @Override
    public void onCreate() {
        super.onCreate();
        mThreadPoolExecutor = new ThreadPoolExecutor(2,5,5, TimeUnit.SECONDS,new ArrayBlockingQueue<>(3));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return binder;
    }


    public class MyBinder extends Binder {
        public WeatherService getService(){
            return WeatherService.this;
        }
    }

    public void getWeather(String cityId,weatherCallback callback){
                mThreadPoolExecutor.execute(()->{
            try {
                String path = "http://www.weather.com.cn/data/cityinfo/"+cityId+".html";
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
//					conn.connect();
                //发送http GET请求，获取相应码
                if (conn.getResponseCode() == 200){
                    InputStream is = conn.getInputStream();
                    byte[] bytes = new byte[1024];
                    int hasRead;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((hasRead=is.read(bytes))!=-1){
                        stringBuffer.append(new String(bytes,0,hasRead));
                    }
                    is.close();
                    WeatherInfo weatherInfo = JsonParsing.jsonParsing(stringBuffer.toString());
                    callback.success(weatherInfo);
                    //getBg(weatherInfo.getImg1());
                    //Log.i(TAG, "getWeather: "+weatherInfo.toString());
                    conn.disconnect();
                }else {
                    callback.fail();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void getBg(String img,processCallback callback){
        StringBuffer buffer = new StringBuffer(img);
        buffer.insert(1,"0");
        buffer.replace(buffer.indexOf("."),buffer.length(),".jpg");
        //Log.i(TAG, "getBg: "+buffer.toString());
        mThreadPoolExecutor.execute(()->{
            try {
                String path = "https://i.tq121.com.cn/i/wap2017/bg/"+buffer;
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
//					conn.connect();
                //发送http GET请求，获取相应码
                //Log.i(TAG, "getBg: "+conn.getResponseCode());
                if (conn.getResponseCode() == 200||conn.getResponseCode()==302){
                    //Log.i(TAG, "getBg: "+conn.getContentLength());
                    File file = new File(Environment.getExternalStorageDirectory()+"/weather/bg",buffer.toString());
                    if (!file.exists()){
                    if (!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    Log.i(TAG, "getBg: "+path);
                    OutputStream os = new FileOutputStream(file);
                    InputStream is = conn.getInputStream();
                    byte[] bytes = new byte[1024];
                        int hasRead;
                        int length = conn.getContentLength();
                        int currentLength = 0;
                        while ((hasRead=is.read(bytes))!=-1){
                        os.write(bytes,0,hasRead);
                        currentLength +=hasRead;
                        callback.process(currentLength,length);
                    }
                        callback.success(file.getPath());
                    is.close();
                    os.close();
                    }
                    conn.disconnect();
                }
                else {callback.fail();}
            }catch (Exception e){
                e.printStackTrace();
            }
        });

    }
    public void test(){
        Log.i(TAG, "test: ");
    }
    public  interface weatherCallback{
        void success(WeatherInfo weather);
        void fail();
    }
    public  interface processCallback{
        void process(int current,int size);
        void success(String filepath);
        void fail();
    }
}
