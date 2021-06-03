package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.game.animation.PlayerMoveAnimation;
import com.example.game.view.MyButton;

public class MainActivity extends AppCompatActivity implements MyButton.MyButtonListener{
    private ImageView imageView;
    private ImageView image;
    private PlayerMoveAnimation mPlayerMoveAnimation;
    private MyButton mUpBtn;
    private MyButton mDownBtn;
    private MyButton mLeftBtn;
    private MyButton mRightBtn;


    private String TAG = "llm_main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        init();
    }


    private void init() {
        imageView = findViewById(R.id.main_img);
        mPlayerMoveAnimation = new PlayerMoveAnimation(getApplicationContext(),imageView,200);
        mDownBtn = findViewById(R.id.main_down_btn);
        mUpBtn = findViewById(R.id.main_up_btn);
        mLeftBtn = findViewById(R.id.main_left_btn);
        mRightBtn = findViewById(R.id.main_right_btn);
        image = findViewById(R.id.main_image);
        image.setBackground(this.getResources().getDrawable(R.drawable.player1));
        mRightBtn.setEvent(this);
        mLeftBtn.setEvent(this);
        mUpBtn.setEvent(this);
        mDownBtn.setEvent(this);

    }

    public void test(View view) {
      mPlayerMoveAnimation.closeAnimation();
    }



    @Override
    public void onClickListener(View view) {
        Log.i(TAG, "onClickListener: ");
        runOnUiThread(()->{
            switch (view.getId()){
                case R.id.main_down_btn :{
                    mPlayerMoveAnimation.playerDown();
                    break;
                }
                case R.id.main_up_btn :{
                    mPlayerMoveAnimation.playerUp();
                    break;
                }
                case R.id.main_left_btn :{
                    mPlayerMoveAnimation.playerLeft();
                    break;
                }
                case R.id.main_right_btn :{
                    mPlayerMoveAnimation.playerRight();
                    break;
                }

            }
        });

    }

    @Override
    public void onLongTouchStart(View view) {
        runOnUiThread(()->{
            mPlayerMoveAnimation.setOneShot(false);
            switch (view.getId()){
                case R.id.main_down_btn :{
                    mPlayerMoveAnimation.playerDown();
                    break;
                }
                case R.id.main_up_btn :{
                    mPlayerMoveAnimation.playerUp();
                    break;
                }
                case R.id.main_left_btn :{
                    mPlayerMoveAnimation.playerLeft();
                    break;
                }
                case R.id.main_right_btn :{
                    mPlayerMoveAnimation.playerRight();
                    break;
                }

            }
        });
        Log.i(TAG, "onLongTouchStart: ");
    }

    @Override
    public void onLongTouchEnd(View view) {
        mPlayerMoveAnimation.closeAnimation();
        mPlayerMoveAnimation.setOneShot(true);
        Log.i(TAG, "onLongTouchEnd: ");
    }

    @Override
    public void onLongTouch(View view) {
        Log.i(TAG, "onLongTouch: ");
    }

    @Override
    public void onDoubleLongTouchStart(View view) {
        Log.i(TAG, "onDoubleLongTouchStart: ");
    }

    @Override
    public void onDoubleLongTouchEnd(View view) {
        Log.i(TAG, "onDoubleLongTouchEnd: ");
    }

    @Override
    public void onDoubleTouch(View view) {
        mPlayerMoveAnimation.setOneShot(false);
        switch (view.getId()){
            case R.id.main_down_btn :{
                mPlayerMoveAnimation.playerDown();
                break;
            }
            case R.id.main_up_btn :{
                mPlayerMoveAnimation.playerUp();
                break;
            }
            case R.id.main_left_btn :{
                mPlayerMoveAnimation.playerLeft();
                break;
            }
            case R.id.main_right_btn :{
                mPlayerMoveAnimation.playerRight();
                break;
            }

        }
        Log.i(TAG, "onDoubleTouch: ");
    }

    @Override
    public void onDoubleLongTouch(View view) {
        Log.i(TAG, "onDoubleLongTouch: ");
    }
}