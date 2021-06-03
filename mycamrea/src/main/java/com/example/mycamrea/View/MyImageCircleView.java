package com.example.mycamrea.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyImageCircleView extends androidx.appcompat.widget.AppCompatImageView {
    private Paint mPaint;
    private float mScale;
    private int mRadius = 0;
    private Bitmap mBitmap;

    public MyImageCircleView(Context context) {
        super(context);
    }

    public MyImageCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mRadius == 0){
            mRadius = getSize(mRadius,widthMeasureSpec)/2;
            setMeasuredDimension(getSize(mRadius,widthMeasureSpec),getSize(mRadius,heightMeasureSpec));
        }else setMeasuredDimension(2*mRadius, 2*mRadius);

    }
    protected void onDraw(Canvas canvas) {
        mPaint = new Paint();
        if (null != mBitmap) {
            Bitmap bitmap = mBitmap ;
            //初始化BitmapShader，传入bitmap对象
            BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            //计算缩放比例
            mScale = (mRadius * 2.0f) / Math.min(bitmap.getHeight(), bitmap.getWidth());
            Matrix matrix = new Matrix();
            matrix.setScale(mScale, mScale);
            bitmapShader.setLocalMatrix(matrix);
            mPaint.setShader(bitmapShader);
            //画圆形，指定好坐标，半径，画笔
            canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
        } else {
            super.onDraw(canvas);
        }
    }

    public void setRadius(int radius){
        this.mRadius = radius;
        requestLayout();
        invalidate();
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
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
}

