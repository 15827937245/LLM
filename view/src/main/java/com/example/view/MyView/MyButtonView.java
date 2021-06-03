package com.example.view.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyButtonView extends View {
    private static final String TAG = "llm_MyButtonView";
    private Paint mPaint;
    private int width;
    private int height;
    private float currentX;
    private float currentY;

    public MyButtonView(Context context) {
        super(context);
    }

    public MyButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMySize(400,widthMeasureSpec);
        height = getMySize(400,heightMeasureSpec);
//        Log.i(TAG, "onMeasure: width:"+width);
//        Log.i(TAG, "onMeasure: height:"+height);
//        currentX = 0;
//        currentY = height/2;
        setMeasuredDimension(400,400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();

        //canvas.drawCircle(currentX,currentY,50,mPaint);

       // mPaint.setColor(Color.GREEN);
        //canvas.drawRect(currentX,0,currentX+1000,height,mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawRect(0,0,400,400,mPaint);

        //canvas.drawCircle(width/2,height/2,(width>height?height/2:width/2),mPaint);
    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {
                //如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                //如果测量模式是最大取值为size
                //如果默认尺寸大于最大尺寸，大小就为最大尺寸，如果小于则是默认尺寸
                if (defaultSize>size) {
                    mySize = size;
                }else{
                    mySize = defaultSize;
                }
                break;
            }
            case MeasureSpec.EXACTLY:
                //如果是固定的大小，那就不要去改变它
                mySize = size;
                break;
        }
        return mySize;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            /*switch (event.getAction()){
                case MotionEvent.ACTION_UP:
            }*/
//        Log.i(TAG, "onTouchEvent: X:Y = "+event.getX()+"："+event.getY());
//        currentX = event.getX();
//        //currentY = event.getY();
//        if (currentX<-200){
//            currentX = 0;
//        }else if (currentX>1000){
//            currentX = 900;
//        }
        //invalidate();
        Log.i(TAG, "onTouchEvent: ");
        return false;
    }


}
