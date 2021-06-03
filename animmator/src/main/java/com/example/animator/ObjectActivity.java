package com.example.animator;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ObjectActivity extends AppCompatActivity {
    private ImageView mImageView;
  private ObjectAnimator animator;
    private ObjectAnimator animator1;
    private ObjectAnimator animator2;
    private ObjectAnimator animator3;
    private AnimatorSet set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);
        mImageView = findViewById(R.id.object_animator);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.image10));
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    public void startObjectAction(View view){
        animator = ObjectAnimator.ofFloat(mImageView,"TranslationX",0f,600f)
                .setDuration(4000);

        animator1 = ObjectAnimator.ofFloat(mImageView,"TranslationY",0f,600f)
                .setDuration(2000);

        animator2 = ObjectAnimator.ofFloat(mImageView,"rotation",0f,360f)
                .setDuration(3000);

        animator3 = ObjectAnimator.ofFloat(mImageView,"alpha",1.0f,0.0f)
                .setDuration(10000);




        set = new AnimatorSet();
        // 串行
        //set.playSequentially(animator,animator1,animator2,animator3);
        // 并行
        set.playTogether(animator,animator1,animator2,animator3);
        //set.setDuration(10000);
        set.start();

        /**
         * 这里也可以使用这样
         set.play(animator1).before(animator2);
         set.play(animator2).with(animator3);
         * 或者
         set.play(animator2).with(animator3).after(animator1);
         */
    }
}