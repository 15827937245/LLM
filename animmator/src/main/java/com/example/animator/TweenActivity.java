package com.example.animator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class TweenActivity extends AppCompatActivity {
    private Button mStartBtn;
    private Button mStartBtnJava;
    private Animation mAnimation;
    private Animation mAnimationJava;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tween);
        mStartBtn = findViewById(R.id.start_btn);
        mStartBtnJava = findViewById(R.id.start_btn_java);
        mAnimation  = AnimationUtils.loadAnimation(this,R.anim.tween_animation);

        mAnimationJava = new TranslateAnimation(0,200,0,100);
        mAnimationJava.setDuration(3000);
    }

    public void startAction(View view){
            mStartBtn.startAnimation(mAnimation);
//            mStartBtn.setVisibility(View.GONE);
    }
    public void startActionJava(View view){
        mStartBtnJava.startAnimation(mAnimationJava);
    }
}