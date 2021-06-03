package com.example.mycamrea.View;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyTextureView extends TextureView {
private String TAG = "llm-MyTextureView";
    private int width;
    private int height;
    private Size mSize;

    public MyTextureView(@NonNull Context context) {
        super(context);
    }

    public MyTextureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mSize==null){
        width = getSize(MeasureSpec.getSize(widthMeasureSpec),widthMeasureSpec);
        height = getSize(MeasureSpec.getSize(heightMeasureSpec),heightMeasureSpec);
        }else {
            width = mSize.getWidth();
            height = mSize.getHeight();
        }
        Log.i(TAG, "onMeasure: width:"+width);
        Log.i(TAG, "onMeasure: height:"+height);
        setMeasuredDimension(width,height);
    }

    public  void changeSize(Size size){
            mSize = new Size(size.getHeight(),size.getWidth());
            requestLayout();
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
