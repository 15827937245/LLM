package com.example.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.weather.dialog.MyDialog;
import com.example.weather.pojo.City;
import com.example.weather.pojo.WeatherInfo;
import com.example.weather.service.WeatherService;
import com.example.weather.utils.XmlParsing;

import java.io.File;

public class MainActivity extends AppCompatActivity {
private String TAG = "llm_main";
    private WeatherService mWeatherService;
    private TextView mCity;
    private TextView mTemp;
    private TextView mTempRate;
    private TextView mWeather;
    private LinearLayout mBodyLL;
    private RelativeLayout mMainRL;
    private City city;
    private MyDialog myDialog;
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0 :{
                    getWeather((String) msg.obj);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent = new Intent(this,WeatherService.class);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //Log.i(TAG, "onServiceConnected: ");
                mWeatherService = ((WeatherService.MyBinder) service).getService();
                getWeather(city.getWeatherCode("湖北","武汉","蔡甸"));
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                //Log.i(TAG, "onServiceDisconnected: ");
            }
        }, Service.BIND_AUTO_CREATE);
    }

    private void initView() {
           mCity = findViewById(R.id.main_body_ll_text_city);
           mTempRate = findViewById(R.id.main_body_ll_text_tempRate);
           mTemp = findViewById(R.id.main_body_ll_text_temp);
           mWeather = findViewById(R.id.main_body_ll_text_weather);
           mBodyLL = findViewById(R.id.main_body_ll);
           mMainRL = findViewById(R.id.main_ll);

    }

//    public void getWeather(View view) {
//        String weatherCode = "101010100";
//        new Thread(()->{
//            try {
//            String path = "http://wthrcdn.etouch.cn/WeatherApi?citykey=101010100";
//                URL url = new URL(path);
//                HttpURLConnection conn = (HttpURLConnection) url
//                        .openConnection();
//                conn.setRequestMethod("GET");
//                conn.setConnectTimeout(5000);
//                conn.setReadTimeout(5000);
////					conn.connect();
//                //发送http GET请求，获取相应码
//                if (conn.getResponseCode() == 200) {
//
//                    File file = new File(Environment.getExternalStorageDirectory()+"/weather/cache",
//                            "weather"+weatherCode+"_"+System.currentTimeMillis()+".xml");
//                    if (!file.getParentFile().exists()){
//                     file.getParentFile().mkdirs();
//                    }
//                    OutputStream os = new FileOutputStream(file);
//                    InputStream is = conn.getInputStream();
//                    byte[] bytes = new byte[1024];
//                    int hasRead;
//                    while ((hasRead=is.read(bytes))!=-1){
//                        os.write(bytes);
//                    }
//                    is.close();
//                    os.close();
//                    Log.i(TAG, "getWeather: "+file.length());
//                }
//            }catch (Exception e){
//
//            }
//        }).start();
//    }


    @Override
    protected void onResume() {
        super.onResume();
        File file = new File(this.getFilesDir().getAbsolutePath()+"/city.xml");
        city = new City(XmlParsing.getInstance().cityParsing(file));
        myDialog = new MyDialog(this,city,handler);
        //Log.i(TAG, "onResume: "+city.getProvinceList().toString());
    }

    public void test(View view) {
        getWeather(city.getRandomWeatherCode());
    }

    private void getWeather(String cityId){
        mWeatherService.getWeather(cityId,new WeatherService.weatherCallback() {
            @Override
            public void success(WeatherInfo weather) {
                runOnUiThread(()->{
                    upDateWeather(weather);
                });
                mWeatherService.getBg(weather.getImg1(), new WeatherService.processCallback() {
                    @Override
                    public void process(int current, int size) {
                        //Log.i(TAG, "process: "+(current*100/size)+"%");
                    }

                    @Override
                    public void success(String filepath) {
                        runOnUiThread(()->{
                            Log.i(TAG, "success: "+filepath);
                            mMainRL.setBackground(Drawable.createFromPath(filepath));
                        });
                    }

                    @Override
                    public void fail() {
                    }
                });
                mWeatherService.getBg(weather.getImg2(), new WeatherService.processCallback() {
                    @Override
                    public void process(int current, int size) {

                    }

                    @Override
                    public void success(String filepath) {

                    }

                    @Override
                    public void fail() {

                    }
                });
            }
            @Override
            public void fail() {
                Log.i(TAG, "fail: ");
            }
        });
    }
    public void upDateWeather(WeatherInfo weather){
        StringBuffer buffer = new StringBuffer(weather.getImg1());
        buffer.insert(1,"0");
        buffer.replace(buffer.indexOf("."),buffer.length(),".jpg");
        File file = new File(Environment.getExternalStorageDirectory()+"/weather/bg",buffer.toString());
        if (file.exists()){
            Log.i(TAG, "upDateWeather: "+weather.getImg1());
            mMainRL.setBackground(Drawable.createFromPath(file.getPath()));
            Log.i(TAG, "upDateWeather: "+file.getPath());
        }
        mWeather.setText(weather.getWeather());
        mCity.setText(weather.getCity());
        mTemp.setText(weather.getTemp1());
        mTempRate.setText(weather.getTemp1()+"/"+weather.getTemp2());
    }

    public void getProvince(View view) {
        myDialog.show();
    }
}