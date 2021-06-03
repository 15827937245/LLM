package com.example.mycamrea.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mycamrea.R;
import com.example.mycamrea.utils.SharedPreferenceUtils;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "llm_SettingsActivity";
    private Switch mWaterMark;
    private Switch mReferenceLine;
    private ImageView mBack;
    private RelativeLayout mCountdownRL;
    private TextView mCountdownText;
    public static final String SHARED_PREFERENCE_NAME = "CameraData";
    public static final String WATER_MARK = "water_mark";
    public static final String REFERENCE_LINE = "reference_line";
    public static final String COUNTDOWN = "countdown";
    public static final String ISO = "iso";
    public static final int COUNTDOWN_2 = 0;
    public static final int COUNTDOWN_5 = 1;
    public static final int COUNTDOWN_10 = 2;
    public static final int COUNTDOWN_OFF = 3;

    private int mCountdownMode;

    private final String[] item = new String[]{"2秒", "5秒", "10秒", "关闭"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init() {
      mWaterMark = findViewById(R.id.set_water_mark);
      mReferenceLine = findViewById(R.id.set_reference_line);
      mBack = findViewById(R.id.set_back);
      mCountdownRL = findViewById(R.id.set_countdown_rl);
      mCountdownText = findViewById(R.id.set_countdown);
      mBack.setOnClickListener(this::onClick);
      mCountdownRL.setOnClickListener(this::onClick);
      //mCountdownText.setOnClickListener(this::onClick);
      mReferenceLine.setOnClickListener(this::onClick);
      mWaterMark.setOnClickListener(this::onClick);
    }

    private void initData() {
        mWaterMark.setChecked(SharedPreferenceUtils.getBooleanValue(this,WATER_MARK,SHARED_PREFERENCE_NAME));
        mReferenceLine.setChecked(SharedPreferenceUtils.getBooleanValue(this,REFERENCE_LINE,SHARED_PREFERENCE_NAME));
        mCountdownMode = SharedPreferenceUtils.getIntegerValue(this,COUNTDOWN,SHARED_PREFERENCE_NAME);
        //Log.i(TAG, "initData:mCountdownMode "+mCountdownMode);
        mCountdownText.setText(item[mCountdownMode]);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.set_water_mark:{
                    if (SharedPreferenceUtils.getBooleanValue(this,WATER_MARK,SHARED_PREFERENCE_NAME)){
                        SharedPreferenceUtils.saveBooleanValue(this,WATER_MARK,false,SHARED_PREFERENCE_NAME);
                    }else {
                        SharedPreferenceUtils.saveBooleanValue(this,WATER_MARK,true,SHARED_PREFERENCE_NAME);
                    }

                    break;
                }
                case R.id.set_reference_line:{
                    if (SharedPreferenceUtils.getBooleanValue(this,REFERENCE_LINE,SHARED_PREFERENCE_NAME)){
                        SharedPreferenceUtils.saveBooleanValue(this,REFERENCE_LINE,false,SHARED_PREFERENCE_NAME);
                    }else {
                        SharedPreferenceUtils.saveBooleanValue(this,REFERENCE_LINE,true,SHARED_PREFERENCE_NAME);
                    }
                    break;
                }
                case R.id.set_back:{
                    finish();
                    break;
                }
               // case R.id.set_countdown:
                case R.id.set_countdown_rl:{
                   AlertDialog alertDialog = new android.app.AlertDialog.Builder(this,R.style.Theme_AppCompat_Light_Dialog)
                            .setTitle("请选择")//默认为0表示选中第一个项目
                            .setSingleChoiceItems(item, mCountdownMode, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mCountdownMode = which;
                                    SharedPreferenceUtils.saveIntegerValue(SettingsActivity.this,COUNTDOWN,mCountdownMode,SHARED_PREFERENCE_NAME);
                                    mCountdownText.setText(item[which]);
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .create();
                    alertDialog.show();
                    break;
                }
//                case R.id.set_countdown:{
//                    break;
//                }
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}