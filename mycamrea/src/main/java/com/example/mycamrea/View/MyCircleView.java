package com.example.mycamrea.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCircleView extends View{
    private int width;
    private int height;
    private Paint mPaint;

    public MyCircleView(Context context) {
        super(context);
    }

    public MyCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getSize(MeasureSpec.getSize(heightMeasureSpec),widthMeasureSpec);
        height = getSize(MeasureSpec.getSize(heightMeasureSpec),heightMeasureSpec);

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(width/2,height/2,height/2-10,mPaint);
    }

    private int getSize(int defaultSize, int measureSpec) {
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
}
