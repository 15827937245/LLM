package com.example.game.animation;

import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;

import com.example.game.R;

public class FrameAnimation {
    private AnimationDrawable mAnimationDrawable;

    public AnimationDrawable playerUp(int seep){
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_up1),seep);
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_up2),seep);
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_up3),seep);
        //mAnimationDrawable.start();
        return mAnimationDrawable;
    }
    public AnimationDrawable playerRight(int seep){
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_right1),seep);
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_right2),seep);
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_right3),seep);
        //mAnimationDrawable.start();
        return mAnimationDrawable;
    }
    public AnimationDrawable playerLeft(int seep){
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_left1),seep);
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_left2),seep);
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_left3),seep);
        //mAnimationDrawable.start();
        return mAnimationDrawable;
    }
    public AnimationDrawable playerDown(int seep){
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_down1),seep);
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_down2),seep);
        mAnimationDrawable.addFrame(Resources.getSystem().getDrawable(R.drawable.player_down3),seep);
        //mAnimationDrawable.start();
        return mAnimationDrawable;
    }
}
