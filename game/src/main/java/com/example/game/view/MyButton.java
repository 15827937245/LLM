package com.example.game.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyButton extends androidx.appcompat.widget.AppCompatButton {
    private long startTime;
    private long endTime;
    private String TAG = "llm_MyButton";
    private MyButtonListener myButtonListener;
    private float x;
    private float y;
    private final float offset = 10;
    private final int longTime = 200;
    private boolean flagTime = true;
    private int timeCount = 0;
    private long doubleTouch1 = 0;
    private long doubleTouch2 = 0;
    private boolean isLongTouch = false;
    private boolean isDoubleTouch = false;
    private ThreadPoolExecutor poolExecutor;


    private int touchMode;
    private final static int TOUCH_MODE_SINGLE_TOUCH = 0;
    private final static int TOUCH_MODE_DOUBLE_TOUCH = 1;
    private final static int TOUCH_MODE_LONG_TOUCH = 2;
    private final static int TOUCH_MODE_DOUBLE_LONG_TOUCH = 3;


    public MyButton(Context context) {
        super(context);
        poolExecutor = new ThreadPoolExecutor(1,2,3, TimeUnit.SECONDS,new ArrayBlockingQueue<>(2));
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        poolExecutor = new ThreadPoolExecutor(2,2,3, TimeUnit.SECONDS,new ArrayBlockingQueue<>(4));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                doubleTouch2 = System.currentTimeMillis();
                isDoubleTouch();
                startTime = System.currentTimeMillis();
                x = event.getX();
                y = event.getY();
               // Log.i(TAG, "onTouchEvent: "+event.getDownTime());
                isLongTouch();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                //Log.i(TAG, "onTouchEvent: + MotionEvent.ACTION_MOVE");
                if (Math.abs(event.getX()-x)>offset||Math.abs(event.getY()-y)>offset){
                    
                }
               break;
            }
            case MotionEvent.ACTION_UP:{
                flagTime = false;
//                if (isLongTouch){
//                    Log.d(TAG, "onTouchEvent:长按开始！！！ ");
//                }
                //

                switch (getTouchMode()){
                    case TOUCH_MODE_SINGLE_TOUCH:{
                        endTime = System.currentTimeMillis();
                        poolExecutor.execute(()->{
                            int timeCount = 0;
                            while (timeCount<(300-(endTime-startTime))){
                                try {
                                    Thread.sleep(10);
                                    timeCount+=10;
                                    if (isDoubleTouch){
                                        return;
                                    }
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            myButtonListener.onClickListener(this);
                        });
                        break;
                    }
                    case TOUCH_MODE_DOUBLE_TOUCH:{
                        myButtonListener.onDoubleTouch(this);
                        break;
                    }
                    case TOUCH_MODE_LONG_TOUCH:{
                        myButtonListener.onLongTouch(this);
                        myButtonListener.onLongTouchEnd(this);
                        break;
                    }
                    case TOUCH_MODE_DOUBLE_LONG_TOUCH:{
                        myButtonListener.onDoubleLongTouch(this);
                        myButtonListener.onDoubleLongTouchEnd(this);
                        break;
                    }
                }

                break;
            }
        }
        return true;
    }

    private void isLongTouch(){
       poolExecutor.execute(()->{
            flagTime = true;
            timeCount = 0;
            while (flagTime){
                try {
                    Thread.sleep(10);
                    timeCount+=10;
                    if (timeCount>longTime){
                        flagTime = false;
                        isLongTouch = true;
                        if (isDoubleTouch){
                            myButtonListener.onDoubleLongTouchStart(this);
                        }else{
                        myButtonListener.onLongTouchStart(this);}
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void isDoubleTouch(){
        if ((doubleTouch2-doubleTouch1)<300){
            isDoubleTouch = true;
        }else {
            isDoubleTouch = false;
        }
        Log.i(TAG, "isDoubleTouch: "+(doubleTouch2-doubleTouch1));
        doubleTouch1 = doubleTouch2;
    }

    private int getTouchMode(){
        if (isDoubleTouch){
            if (isLongTouch){
                touchMode = TOUCH_MODE_DOUBLE_LONG_TOUCH;
                isLongTouch = false;
            }else {
            touchMode = TOUCH_MODE_DOUBLE_TOUCH;
            isDoubleTouch = false;
            }
        }else {
            if (isLongTouch){
                touchMode = TOUCH_MODE_LONG_TOUCH;
                isLongTouch = false;
            }else {
                touchMode = TOUCH_MODE_SINGLE_TOUCH;
            }
        }
        Log.i(TAG, "getTouchMode: "+touchMode);
        return touchMode;
    }

 public interface MyButtonListener{
       void onClickListener(View view);
       void onLongTouchStart(View view);
       void onLongTouchEnd(View view);
       void onLongTouch(View view);
       void onDoubleLongTouchStart(View view);
       void onDoubleLongTouchEnd(View view);
       void onDoubleTouch(View view);
       void onDoubleLongTouch(View view);
  }
    public void setEvent(MyButtonListener listener){
        myButtonListener = listener;
    }

}
