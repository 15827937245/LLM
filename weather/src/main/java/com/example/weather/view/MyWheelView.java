package com.example.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.camera2.CameraDevice;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class MyWheelView extends View {
    private String TAG = "llm_MyWheelView";
    private int width;
    private int height;
    private Paint mPaint;
    private float y1;
    private float y2;
    private String[] item;
    private int position = 0;
    private float y3;
    private StateCallBack stateCallBack;

    public MyWheelView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public MyWheelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setTextSize(50);
        mPaint.setColor(Color.RED);
        if (item!=null&&(position<=(item.length-1))&&(position>=0)){
        if (position==0){
        canvas.drawText("",width/2,height/6,mPaint);
        }else {
            canvas.drawText(item[position-1],width/2,height/6,mPaint);
        }
        canvas.drawText(item[position],width/2,height/2,mPaint);
        if (position==item.length-1){
            canvas.drawText("",width/2,5*height/6,mPaint);
        }else {
        canvas.drawText(item[position+1],width/2,5*height/6,mPaint);
        }}

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :{
                y1 = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                /*y2 = event.getY();
                if (Math.abs(y2-y1)>height/3){
                    if ((y2-y1)>0){
                        position -= 1;
                    }else {
                        position += 1;
                    }
                    if (position<0){
                        position =0;
                    }
                    if (position>=(item.length-1)){
                        position = (item.length-1);
                    }
                    invalidate();
                    Log.i(TAG, "onTouchEvent: "+(y2-y1)+"");
                }*/
                break;
            }
            case MotionEvent.ACTION_UP:{
                y3 = event.getY();
                if (Math.abs(y3-y1)>height/3){
//                    if ((y3-y1)>0){
//                        position -= 1;
//                    }else {
//                        position += 1;
//                    }
                    int v = (int) (y3 - y1) / (height / 3);
                    position += -v;
                    if (position<0){
                        position =0;
                    }
                    if (position>=(item.length-1)){
                        position = (item.length-1);
                    }
                    stateCallBack.stateCallBack(item[position]);
                    invalidate();
                   // Log.i(TAG, "onTouchEvent: "+(y3-y1)+"");
                }
                break;
            }
            default:break;

        }
        return true;
    }

    public void setItem(String[] item) {

        this.item = item;
        invalidate();
    }
    public void initData(StateCallBack stateCallBack){
        this.stateCallBack = stateCallBack;
    }

    public String getItem(){
        return item[position];
    }

   public interface StateCallBack{
        void stateCallBack(String item);
    }
}
