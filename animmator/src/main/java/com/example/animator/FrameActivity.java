package com.example.animator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.res.loader.ResourcesLoader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class FrameActivity extends AppCompatActivity {
private ImageView mImageView;
private ImageView mCurrentImg;
private AnimationDrawable mAnimation;
private Animation animation;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        mImageView = findViewById(R.id.frame_animator);
        mCurrentImg = findViewById(R.id.current_img);
        mAnimation = (AnimationDrawable)getResources().getDrawable(R.drawable.frame_animation);
        mImageView.setBackground(mAnimation);
        animation = AnimationUtils.loadAnimation(this,R.anim.tween_animation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAnimation.start();
        mImageView.startAnimation(animation);
        flag = true;
    }
    public void stopAnimation(View view){
        if (flag){
        mAnimation.stop();
        Drawable current = mAnimation.getCurrent();
        //mCurrentImg.setBackground(current);
        mCurrentImg.setImageDrawable(current);
        flag = false;
        }else {
            mAnimation.start();
            flag = true;
        }
    }

}