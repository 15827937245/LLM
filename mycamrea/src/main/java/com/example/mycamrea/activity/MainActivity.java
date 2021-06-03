package com.example.mycamrea.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.util.Size;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mycamrea.R;
import com.example.mycamrea.View.FocusView;
import com.example.mycamrea.View.MyCircleView;
import com.example.mycamrea.View.MyImageCircleView;
import com.example.mycamrea.View.MyReferenceLineView;
import com.example.mycamrea.View.MyTakePictureButton;
import com.example.mycamrea.View.MyTextureView;
import com.example.mycamrea.adapter.MyAdapter;
import com.example.mycamrea.controller.CameraController;
import com.example.mycamrea.utils.SharedPreferenceUtils;

import java.io.File;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener, MyAdapter.onClickItemListener {
    private String TAG = "llm_MainActivity";
private ImageView mSettings;
private MyImageCircleView mShowPicture;
private ImageView mFlash;
private ImageView mRatio;
private TextView mCountdownTime;
private TextView mVideoTime;
private TextView mVideoTimeAnimation;

private LinearLayout mVideoTimeLinearLayout;

private RelativeLayout mSeekBarRelativeLayout;
private TextView mSeekBarText;
private TextView mSeekBarValue;
private SeekBar mSeekBar;

public static final int ORIENTATION_HYSTERESIS = 5;
private MyTakePictureButton mTakePictureBtn;
private MyReferenceLineView mReferenceLineView;
private FocusView mFocusView;
private MyCircleView mLensReversal;
private MyTextureView mTextureView;
private CameraController mCameraController;
private ObjectAnimator animator;
private ObjectAnimator animator1;
private AnimatorSet animatorSet;
private RecyclerView mRecyclerView;
private MyOrientationEventListener myOrientationEventListener;
private final String[] item = {"拍照","录像","慢动作","专业模式","更多","111","222","333"};
private Handler mHandel = new Handler(Looper.getMainLooper()){
    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 0:{
            Bitmap bitmap =(Bitmap) msg.obj;
            mShowPicture.setBitmap(bitmap);
                break;
            }
            case 1:{
                mFile = (File)msg.obj;
                break;
            }
            case 3:{
                mReferenceLineView.setSize((Size)msg.obj);
                mFocusView.setSize((Size)msg.obj);
            }
        }
    }
};
    private int mPhoneOrientation;
    private File mFile;
    private FrameLayout mBottomFL;
    private LinearLayout mTopLL;
    public static int countdown = 0;
    private boolean mReferenceLine;
    private boolean mWaterMark;
    private int CameraMode;
    private int prePosition;
    private MyAdapter myAdapter;
    private boolean isAliveVideo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        prePosition = 0;
        isAliveVideo = false;
        mSettings = findViewById(R.id.main_settings);
        mFlash = findViewById(R.id.main_flash);
        mTakePictureBtn = findViewById(R.id.main_take_picture);
        mLensReversal = findViewById(R.id.main_lens_reversal);
        mTextureView = findViewById(R.id.main_texture_view);
        mShowPicture = findViewById(R.id.main_image_show);
        mRatio = findViewById(R.id.main_ratio);
        mCountdownTime = findViewById(R.id.main_countdown_time);
        mBottomFL = findViewById(R.id.main_bottom_fl);
        mTopLL =  findViewById(R.id.main_top_ll);
        mReferenceLineView = findViewById(R.id.main_reference_line_view);
        mFocusView = findViewById(R.id.main_focus_view);
        mRecyclerView = findViewById(R.id.main_recyclerView);
        mVideoTime = findViewById(R.id.main_video_time);
        mVideoTimeAnimation = findViewById(R.id.main_video_time_animation);
        mVideoTimeLinearLayout = findViewById(R.id.main_video_time_ll);
        mSeekBarRelativeLayout = findViewById(R.id.main_seekBar_rl);
        mSeekBar = findViewById(R.id.main_seekBar);
        mSeekBarText = findViewById(R.id.main_seekBar_Text);
        mSeekBarValue = findViewById(R.id.main_seekBar_value);
        mSeekBarRelativeLayout.setVisibility(View.INVISIBLE);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i(TAG, "onProgressChanged: "+progress);
                mSeekBarValue.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(item,this);
        myAdapter.setOnClickItemListener(this::onClickItem);

        mRecyclerView.setAdapter(myAdapter);
        mReferenceLineView.setVisibility(View.INVISIBLE);
        mLensReversal.setOnClickListener(this::onClick);
        mSettings.setOnClickListener(this);
        mFlash.setOnClickListener(this);
        mRatio.setOnClickListener(this::onClick);
        //mTakePictureBtn.setOnClickListener(this);
        mTakePictureBtn.setOnTouchListener(this::onTouch);
        mFocusView.setOnTouchListener(this::onTouch);
        mShowPicture.setOnClickListener(this::onClick);
        mCameraController = new CameraController(this,mTextureView,mHandel);
        myOrientationEventListener = new MyOrientationEventListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferenceUtils.saveIntegerValue(this,SettingsActivity.ISO,Integer.valueOf(mSeekBarValue.getText().toString()),SettingsActivity.SHARED_PREFERENCE_NAME);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_flash :{
                if (mCameraController.flashSwitch()){
                    mFlash.setImageResource(R.mipmap.flash_on);
                }else {
                    mFlash.setImageResource(R.mipmap.flash_off);
                }
                break;
            }
            case R.id.main_settings :{
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.main_lens_reversal:{
              mCameraController.lensReversal();
              animator = ObjectAnimator.ofFloat(mLensReversal,"rotationY",0f,90f,0f);
              animator1 = ObjectAnimator.ofFloat(mLensReversal,"scaleY",1f,1.3f,1);
              animatorSet = new AnimatorSet();
              animatorSet.setDuration(500);
              animatorSet.playTogether(animator,animator1);
              animatorSet.start();
                break;
            }

            case R.id.main_ratio:{
                if (mCameraController.changeRatio()==CameraController.RATIO_MODE_2){
                    mRatio.setImageResource(R.mipmap.ratio16_9);
                }else {
                    mRatio.setImageResource(R.mipmap.ratio4_3);
                }
                break;
            }
            case R.id.main_image_show:{
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
                if (null == mFile)
                    break;
                Uri uri = Uri.fromFile(mFile);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "image/jpeg");
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction()==MotionEvent.ACTION_UP){
        switch(v.getId()){
            case R.id.main_take_picture :{
                if (CameraMode == CameraController.CAMERA_MODE_TAKE_PICTURE){
                    takePicture();
                }
                else if(CameraMode==CameraController.CAMERA_MODE_TAKE_VIDEO){
                    if (mCameraController.startVideo()){
                        mVideoTimeLinearLayout.setVisibility(View.VISIBLE);
                        isAliveVideo = true;
                        startVideoTimeAnimation();
                        mTakePictureBtn.takeVideoSpot();
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        mTopLL.setVisibility(View.INVISIBLE);
                    }else {
                        isAliveVideo = false;
                        animatorSet.cancel();
                        mTakePictureBtn.takeVideoStart();
                        mVideoTimeLinearLayout.setVisibility(View.INVISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mTopLL.setVisibility(View.VISIBLE);
                    }
                }
                return true;
            }
            case R.id.main_focus_view:{
                mFocusView.setFocusArea(event.getX(),event.getY());
                mCameraController.startFocus(mFocusView.getX(),mFocusView.getY());
                animator = ObjectAnimator.ofFloat(mFocusView,"alpha",1.0f,1.0f).setDuration(2000);
                animator1 = ObjectAnimator.ofFloat(mFocusView,"alpha",1.0f,0f).setDuration(1000);
                animatorSet = new AnimatorSet();
                animatorSet.playSequentially(animator,animator1);
                animatorSet.start();
                break;
            }
         }
        }  return false;
    }

    private void startVideoTimeAnimation(){
        animator = ObjectAnimator.ofFloat(mVideoTimeAnimation,"alpha",1.0f,0).setDuration(1000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animator);
        animatorSet.start();
            new Thread(()->{
                int time_count=0;
                while (isAliveVideo){
                    time_count++;
                    int finalTime_count = time_count;
                    runOnUiThread(()->{
                        mVideoTime.setText((finalTime_count/60/10>0?finalTime_count/60:"0"+finalTime_count/60)+":"+(finalTime_count%60/10>0?finalTime_count%60:"0"+finalTime_count%60));
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }

    private void takePicture(){
        if (SharedPreferenceUtils.getIntegerValue(this,SettingsActivity.COUNTDOWN,SettingsActivity.SHARED_PREFERENCE_NAME)!=SettingsActivity.COUNTDOWN_OFF){
            countdown = SharedPreferenceUtils.getIntegerValue(this,SettingsActivity.COUNTDOWN,SettingsActivity.SHARED_PREFERENCE_NAME);
            mTopLL.setVisibility(View.INVISIBLE);
            mBottomFL.setVisibility(View.INVISIBLE);

            new Thread(()->{
                if (countdown == SettingsActivity.COUNTDOWN_2){
                    countdown = 2;
                }else if (countdown == SettingsActivity.COUNTDOWN_5){
                    countdown = 5;
                }else if (countdown == SettingsActivity.COUNTDOWN_10){
                    countdown = 10;
                }
                while (countdown > 0){
                    runOnUiThread(()->{
                        mCountdownTime.setText(countdown+"");
                        mCountdownTime.setVisibility(View.VISIBLE);
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countdown--;
                }
                runOnUiThread(()->{
                    mCameraController.takePicture();
                    animator = ObjectAnimator.ofFloat(mTakePictureBtn,"scaleX",1.0f,0.8f,1.0f);
                    animator1 = ObjectAnimator.ofFloat(mTakePictureBtn,"scaleY",1.0f,0.8f,1.0f);
                    animatorSet = new AnimatorSet();
                    animatorSet.setDuration(500);
                    animatorSet.playTogether(animator,animator1);
                    animatorSet.start();
                    mCountdownTime.setVisibility(View.INVISIBLE);
                    mTopLL.setVisibility(View.VISIBLE);
                    mBottomFL.setVisibility(View.VISIBLE);
                });

            }).start();
        }
        else {
            mCameraController.takePicture();
            animator = ObjectAnimator.ofFloat(mTakePictureBtn,"scaleX",1.0f,0.8f,1.0f);
            animator1 = ObjectAnimator.ofFloat(mTakePictureBtn,"scaleY",1.0f,0.8f,1.0f);
            animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.playTogether(animator,animator1);
            animatorSet.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCameraController.startBackgroundThread();
        myOrientationEventListener.enable();//开始方向监听
        if (mTextureView.isAvailable()){
            mCameraController.openCamera();
        }else {
            mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
                mCameraController.openCamera();
            }


            @Override
            public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
            }
        });}
        initData();
    }

    private void initData() {
        mReferenceLine = SharedPreferenceUtils.getBooleanValue(this,SettingsActivity.REFERENCE_LINE,SettingsActivity.SHARED_PREFERENCE_NAME);
        if (mReferenceLine){
            startReferenceLine();
        }else {
            closeReferenceLine();
        }
        mWaterMark = SharedPreferenceUtils.getBooleanValue(this,SettingsActivity.WATER_MARK,SettingsActivity.SHARED_PREFERENCE_NAME);
        mCameraController.setWaterMarkState(mWaterMark);
    }

    private void closeReferenceLine() {
        mReferenceLineView.setVisibility(View.INVISIBLE);
    }

    private void startReferenceLine() {
       mReferenceLineView.setVisibility(View.VISIBLE);
    }

    public int roundOrientation(int orientation, int orientationHistory) {
        boolean changeOrientation = false;
        if (orientationHistory == OrientationEventListener.ORIENTATION_UNKNOWN) {
            changeOrientation = true;
        } else {
            int dist = Math.abs(orientation - orientationHistory);
            dist = Math.min(dist, 360 - dist);
            changeOrientation = (dist >= 45 + ORIENTATION_HYSTERESIS);
        }
        if (changeOrientation) {
            return ((orientation + 45) / 90 * 90) % 360;
        }
        return orientationHistory;
    }

    @Override
    public void onClickItem(MyAdapter.MyViewHolder holder, int position) {
           changeIcon(position);
        switch (position){
            case 0:{
                mSeekBarValue.setText(String.valueOf(SharedPreferenceUtils.getIntegerValue(this,SettingsActivity.ISO,SettingsActivity.SHARED_PREFERENCE_NAME)));
                mSeekBar.setProgress(SharedPreferenceUtils.getIntegerValue(this,SettingsActivity.ISO,SettingsActivity.SHARED_PREFERENCE_NAME));
                mSeekBarRelativeLayout.setVisibility(View.VISIBLE);
                CameraMode =  mCameraController.openPreviewMode();
                mTakePictureBtn.takePicture();
                break;
            }
            case 1:{
                CameraMode =  mCameraController.openVideoMode();
                mTakePictureBtn.takeVideoStart();
                break;
            }
        }
    }

    public void changeIcon(int position){
        if (position !=prePosition){
            myAdapter.getMyViewHolder(prePosition).textView.setTextColor(Color.WHITE);
            myAdapter.getMyViewHolder(position).textView.setTextColor(Color.GREEN);
            prePosition = position;
        }
    }

    private class MyOrientationEventListener
            extends OrientationEventListener {
        public MyOrientationEventListener(Context context) {
            super(context);
        }

        @Override
        public void onOrientationChanged(int orientation) {
            if (orientation == ORIENTATION_UNKNOWN) {
                return;
            }
            mPhoneOrientation = roundOrientation(orientation, mPhoneOrientation);
            //Log.i(TAG, "onOrientationChanged: mPhoneOrientation:"+mPhoneOrientation);
            mCameraController.setPhoneDeviceDegree(mPhoneOrientation);

        }
    }
}