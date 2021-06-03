package com.example.mycamrea.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
public class MyTakePictureButton extends View{
    private Paint mPaint;
    private int width;
    private int height;
    private int mode = 0;
    private String TAG = "llm-MyTakePictureButton";

    public MyTakePictureButton(Context context) {
        super(context);
    }

    public MyTakePictureButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getSize(MeasureSpec.getSize(heightMeasureSpec),widthMeasureSpec);
        height = getSize(MeasureSpec.getSize(heightMeasureSpec),heightMeasureSpec);

        Log.i(TAG, "onMeasure: width:"+width);
        Log.i(TAG, "onMeasure: height:"+height);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        switch (mode){
            case 0:{
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(height/2,height/2,height/2-40,mPaint);
                break;
            }
            case 1:{
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(height/2,height/2,height/2-40,mPaint);
                mPaint.setColor(Color.RED);
                canvas.drawCircle(height/2,height/2,height/10,mPaint);
                break;
            }
            case 2:{
                mPaint.setColor(Color.WHITE);
                canvas.drawRect(3*height/8,3*height/8,5*height/8,5*height/8,mPaint);
                break;
            }
        }
        super.onDraw(canvas);
    }

    public void takePicture(){
        mode = 0;
        invalidate();
    }

    public void takeVideoStart(){
        mode = 1;
        invalidate();
    }

    public void takeVideoSpot(){
        mode = 2;
        invalidate();
    }

    private int getSize(int defaultSize,int measureSpec) {
        int finalSize = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode){
            case MeasureSpec.UNSPECIFIED:
                //如果没有指定大小，就设置为默认大小
                finalSize = defaultSize;
                break;

            case MeasureSpec.AT_MOST:{
                //如果测量模式是最大取值为size
                //如果默认尺寸大于最大尺寸，大小就为最大尺寸，如果小于则是默认尺寸
                if (defaultSize>size) {
                    finalSize = size;
                }else{
                    finalSize = defaultSize;
                }
                break;
            }

            case MeasureSpec.EXACTLY:{
                //如果是固定的大小，那就不要去改变它
                finalSize = size;
                break;
            }
            default:{
                finalSize = defaultSize;
                break;
            }
        }

        return finalSize;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "onTouchEvent: 子事件被点击："+event.getAction());
        return true;
    }



}
