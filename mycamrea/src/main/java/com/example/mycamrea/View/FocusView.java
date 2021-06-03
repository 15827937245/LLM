package com.example.mycamrea.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Size;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FocusView extends View {
    private Paint mPaint;
    private float x=0;
    private float y=0;
    private Size size;
    private boolean flag = false;
    public FocusView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    public FocusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }


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
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        if (size!=null&&flag){
            canvas.drawRect(x-100,y-100,x+100,y+100,mPaint);
        }
    }

    public void setSize(Size size){
        this.size = new Size(size.getHeight(),size.getWidth());
        requestLayout();
        invalidate();
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public void setFocusArea(float x, float y){
            this.x = x;
            this.y = y;
            if (x<100){
                this.x = 100;
            }
            if (x>size.getWidth()-100){
                this.x = size.getWidth()-100;
            }
            if (y<100){
                this.y=100;
            }if (y>size.getHeight()-100){
                this.y = size.getHeight()-100;
        }
            this.flag = true;
            invalidate();
    }

    public void close(){
        this.flag = false;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
