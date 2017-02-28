package com.gaoyy.learningcustomview.ui;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.AnimItemV;
import com.gaoyy.learningcustomview.view.AnimLinearLayout;

public class ColorfulBarActivity extends AppCompatActivity
{
    private static final String TAG = ColorfulBarActivity.class.getSimpleName();
    private int[] colors = {android.R.color.holo_blue_dark, android.R.color.holo_red_light,
            android.R.color.holo_green_light, android.R.color.holo_orange_dark, android.R.color.holo_purple};
    private AnimLinearLayout activityColorfulBar;
    private AnimItemV item1;
    private AnimItemV item2;
    private AnimItemV item3;
    private AnimItemV item4;
    private AnimItemV item5;

    private void assignViews()
    {
        activityColorfulBar = (AnimLinearLayout) findViewById(R.id.activity_colorful_bar);
        item1 = (AnimItemV) findViewById(R.id.item1);
        item2 = (AnimItemV) findViewById(R.id.item2);
        item3 = (AnimItemV) findViewById(R.id.item3);
        item4 = (AnimItemV) findViewById(R.id.item4);
        item5 = (AnimItemV) findViewById(R.id.item5);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorful_bar01);
        assignViews();

        int childCount = activityColorfulBar.getChildCount();

        Log.i(TAG, "childCount-->" + childCount);


//        activityColorfulBar.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event)
//            {
//                if (event.getAction() == MotionEvent.ACTION_DOWN)
//                {
//                    Log.e(TAG,"raw x-->"+event.getRawX());
//                    Log.e(TAG,"raw y-->"+event.getRawY());
//                    Log.e(TAG,"x-->"+event.getX());
//                    Log.e(TAG,"y-->"+event.getY());
//                }
//                return false;
//            }
//        });


        for (int i = 0; i < childCount; i++)
        {
            final AnimItemV animItemV = (AnimItemV) activityColorfulBar.getChildAt(i);
            Log.e(TAG, "第" + i + "个" + "====tag--->" + (Integer) animItemV.getTag());

            final int finalI = i;
            animItemV.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        Log.e(TAG, "第" + finalI + "个" + "====tag--->" + (int) animItemV.getTag());
                        for (int i = 0; i < activityColorfulBar.getChildCount(); i++)
                        {
                            if(animItemV.getId() == activityColorfulBar.getChildAt(i).getId())
                            {
                                Log.i(TAG,"是同一个");
                                continue;
                            }
                            ((AnimItemV)activityColorfulBar.getChildAt(i)).reverseAnim();
                        }
                        int radius = Math.max(activityColorfulBar.getWidth(), activityColorfulBar.getHeight());
                        Log.e(TAG,"raw x-->"+motionEvent.getRawX());
                        Log.e(TAG,"raw y-->"+motionEvent.getRawY());
                        Log.e(TAG,"x-->"+motionEvent.getX());
                        Log.e(TAG,"y-->"+motionEvent.getY());


                        Log.e(TAG,"w-->"+animItemV.getWidth());
                        Log.e(TAG,"h-->"+animItemV.getHeight());



                        float a = (float)finalI+0.5f;


                        Log.e(TAG,"a-->"+a);

                        Log.e(TAG,"h1-->"+animItemV.getHeight()/2);

//                        int x = (int) motionEvent.getRawX();
//                        /**
//                         * 假如外层有再加一层layout，Y坐标需要减去父layout的高度
//                         */
//                        if(activityColorfulBar.getParent() != null)
//                        {
//                            ViewGroup parent = (ViewGroup) activityColorfulBar.getParent();
//                            int y = (int)Math.abs(motionEvent.getRawY()-parent.getHeight());
//                            activityColorfulBar.setChildY(y);
//                        }
//                        else
//                        {
//                            int y = (int) motionEvent.getRawY();
//                            activityColorfulBar.setChildY(y);
//
//                        }
//
//                        activityColorfulBar.setChildX(x);

                        activityColorfulBar.setChildX((int)(a*animItemV.getWidth()));
                        activityColorfulBar.setChildY(animItemV.getHeight()/2);

                        activityColorfulBar.setColor(getResources().getColor(colors[finalI]));


                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, (float) radius);
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                        {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator)
                            {
                                float currentRadius = (float) valueAnimator.getAnimatedValue();

                                activityColorfulBar.setRadius(currentRadius);

                                activityColorfulBar.invalidate();



                            }
                        });
                        valueAnimator.setDuration(500);
                        valueAnimator.setInterpolator(new DecelerateInterpolator());
                        valueAnimator.start();

                    }
                    return false;
                }
            });
        }


    }

}


/**
 * ObjectAnimator revealAnimator = ObjectAnimator.ofFloat( //缩放X 轴的
 * activityColorfulBar, "scaleX", 0, 1.0f);
 * ObjectAnimator revealAnimator1 = ObjectAnimator.ofFloat(//缩放Y 轴的
 * activityColorfulBar, "scaleY", 0, 1.0f);
 * AnimatorSet set = new AnimatorSet();
 * set.setDuration(500);//设置播放时间
 * set.setInterpolator(new LinearInterpolator());//设置播放模式，这里是平常模式
 * set.playTogether(revealAnimator, revealAnimator1);//设置一起播放
 * set.start();
 */
