package com.example.game.animation;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.game.R;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public  class PlayerMoveAnimation {
    private final ImageView imageView;
    private int seep;
    private Context context;
    private AnimationDrawable mAnimationDrawable;
    private  AnimationDrawable mAnimationDrawableUp;
    private  AnimationDrawable mAnimationDrawableDown;
    private  AnimationDrawable mAnimationDrawableRight;
    private  AnimationDrawable mAnimationDrawableLeft;
    private ThreadPoolExecutor threadPool;

    private String TAG = "llm_PlayerMoveAnimation";
    private boolean isOneShot = true;

    public PlayerMoveAnimation(Context context, ImageView imageView,int seep) {
        this.imageView = imageView;
        this.context = context;
        this.seep = seep;
        init();
    }

    private void init() {
        threadPool = new ThreadPoolExecutor(1,2,3, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2));
        mAnimationDrawableUp = new AnimationDrawable();
        mAnimationDrawableUp.addFrame(context.getResources().getDrawable(R.drawable.player_up1),seep);
        mAnimationDrawableUp.addFrame(context.getResources().getDrawable(R.drawable.player_up2),seep);
        mAnimationDrawableUp.addFrame(context.getResources().getDrawable(R.drawable.player_up3),seep);

        mAnimationDrawableDown = new AnimationDrawable();
        mAnimationDrawableDown.addFrame(context.getResources().getDrawable(R.drawable.player_down1),seep);
        mAnimationDrawableDown.addFrame(context.getResources().getDrawable(R.drawable.player_down2),seep);
        mAnimationDrawableDown.addFrame(context.getResources().getDrawable(R.drawable.player_down3),seep);

        mAnimationDrawableRight = new AnimationDrawable();
        mAnimationDrawableRight.addFrame(context.getResources().getDrawable(R.drawable.player_right1),seep);
        mAnimationDrawableRight.addFrame(context.getResources().getDrawable(R.drawable.player_right2),seep);
        mAnimationDrawableRight.addFrame(context.getResources().getDrawable(R.drawable.player_right3),seep);


        mAnimationDrawableLeft = new AnimationDrawable();
        mAnimationDrawableLeft.addFrame(context.getResources().getDrawable(R.drawable.player_left1),seep);
        mAnimationDrawableLeft.addFrame(context.getResources().getDrawable(R.drawable.player_left2),seep);
        mAnimationDrawableLeft.addFrame(context.getResources().getDrawable(R.drawable.player_left3),seep);

    }
    public void playerUp(){
        if (mAnimationDrawable!=null){
            closeAnimation();
        }
        mAnimationDrawable = mAnimationDrawableUp;
        mAnimationDrawable.setOneShot(isOneShot);
        imageView.setBackground(mAnimationDrawable);
            playAnimation();
        //mAnimationDrawable.start();
    }
    public void playerRight(){
        if (mAnimationDrawable!=null){
            closeAnimation();
        }
        mAnimationDrawable = mAnimationDrawableRight;
        mAnimationDrawable.setOneShot(isOneShot);
        //mAnimationDrawable.start();
        imageView.setBackground(mAnimationDrawable);
        //mAnimationDrawable.start();
            playAnimation();

    }
    public void playerLeft(){
        if (mAnimationDrawable!=null){
            closeAnimation();
        }
        mAnimationDrawable = mAnimationDrawableLeft;
        mAnimationDrawable.setOneShot(isOneShot);
        //mAnimationDrawable.start();
        imageView.setBackground(mAnimationDrawable);
        //mAnimationDrawable.start();
            playAnimation();
    }
    public void playerDown(){
        if (mAnimationDrawable!=null){
            //Log.i(TAG, "playerDown: ");
            closeAnimation();
        }
        mAnimationDrawable = mAnimationDrawableDown;
        mAnimationDrawable.setOneShot(isOneShot);
        imageView.setBackground(mAnimationDrawable);
        //mAnimationDrawable.start();
            playAnimation();

    }
    public void closeAnimation(){
        if (mAnimationDrawable!=null){
            if (mAnimationDrawable.isRunning()){
            mAnimationDrawable.stop();
            }
            mAnimationDrawable =null;
        }
    }
    private void playAnimation(){
        if (mAnimationDrawable!=null)
        mAnimationDrawable.start();
        isOneShot = true;
    }

    public void setOneShot(boolean flag){
        isOneShot = flag;
    }

    public int getSeep() {
        return seep;
    }

    public void setSeep(int seep) {
        this.seep = seep;
        init();
    }
}
