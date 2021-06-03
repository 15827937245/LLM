package com.example.mycamrea.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Size;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyReferenceLineView extends View {
    private Size size;
    private Paint mPaint;
    public MyReferenceLineView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public MyReferenceLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (size==null){
            setMeasuredDimension(0,0);
        }else {
            setMeasuredDimension(size.getWidth(),size.getHeight());
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (size!=null){
            mPaint.setColor(Color.WHITE);
            canvas.drawLine(size.getWidth()/3,0,size.getWidth()/3,size.getHeight(),mPaint);
            canvas.drawLine(2*size.getWidth()/3,0,2*size.getWidth()/3,size.getHeight(),mPaint);
            canvas.drawLine(0,size.getHeight()/3,size.getWidth(),size.getHeight()/3,mPaint);
            canvas.drawLine(0,2*size.getHeight()/3,size.getWidth(),2*size.getHeight()/3,mPaint);
        }
    }

    public void setSize(Size size){
        this.size = new Size(size.getHeight(),size.getWidth());
        requestLayout();
        invalidate();
    }
}
