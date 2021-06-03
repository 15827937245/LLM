package com.example.weather.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.weather.R;
import com.example.weather.pojo.City;
import com.example.weather.view.MyWheelView;


public class MyDialog extends Dialog {
    private Context context;
    private TextView mCancel;
    private TextView mOK;
    private MyWheelView mProvince;
    private MyWheelView mCity;
    private MyWheelView mCounty;
    private City city;
    private String weatherCode;
    private Handler mHandler;

    public MyDialog(@NonNull Context context, City city, Handler handler) {
        super(context);
        this.mHandler = handler;
        this.context = context;
        this.city = city;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mydialog_layout);
        init();
    }

    private void init() {
        mCancel = findViewById(R.id.my_dialog_cancel);
        mOK = findViewById(R.id.my_dialog_OK);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mOK.setOnClickListener((v)->{
            Message message = Message.obtain();
            message.what = 0;
            message.obj = weatherCode;
            mHandler.sendMessage(message);
            dismiss();
        });

        mProvince = findViewById(R.id.province);
        mCity = findViewById(R.id.city);
        mCounty = findViewById(R.id.county);

        mProvince.initData(new MyWheelView.StateCallBack() {
            @Override
            public void stateCallBack(String item) {
                mCity.setItem((String[])(city.getCityList(item).toArray(new String[0])));
                mCounty.setItem((String[])(city.getCountyList(item,mCity.getItem()).toArray(new String[0])));
            }
        });

        mCity.initData(new MyWheelView.StateCallBack() {
            @Override
            public void stateCallBack(String item) {
                mCounty.setItem((String[])(city.getCountyList(mProvince.getItem(),item).toArray(new String[0])));
                weatherCode = city.getWeatherCode(mProvince.getItem(), mCity.getItem(), item);
            }
        });
        mCounty.initData(new MyWheelView.StateCallBack() {
            @Override
            public void stateCallBack(String item) {
                weatherCode = city.getWeatherCode(mProvince.getItem(), mCity.getItem(), item);
            }
        });

        mProvince.setItem((String[]) (city.getProvinceList().toArray(new String[0])));
        mCity.setItem((String[]) (city.getCityList("北京").toArray(new String[0])));
        mCounty.setItem((String[]) (city.getCountyList("北京","北京").toArray(new String[0])));
    }

}
