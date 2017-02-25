package com.gaoyy.learningcustomview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * Created by gaoyy on 2017/2/25.
 */

public class AnimLinearLayout extends LinearLayout
{
    private static final String TAG=AnimLinearLayout.class.getSimpleName();
    private int[] colors = {android.R.color.holo_blue_dark,android.R.color.holo_red_light,
            android.R.color.holo_green_light,android.R.color.holo_orange_dark,android.R.color.holo_purple};
    private int childX;
    private int childY;
    private float radius;
    private int color;

    public int getChildX()
    {
        return childX;
    }

    public void setChildX(int childX)
    {
        this.childX = childX;
    }

    public int getChildY()
    {
        return childY;
    }

    public void setChildY(int childY)
    {
        this.childY = childY;
    }


    public float getRadius()
    {
        return radius;
    }

    public void setRadius(float radius)
    {
        this.radius = radius;
    }

    public int getColor()
    {
        return color;
    }

    public void setColor(int color)
    {
        this.color = color;
    }

    public AnimLinearLayout(Context context)
    {
        this(context,null);
    }

    public AnimLinearLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs,-1);
    }

    public AnimLinearLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
    }


    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Log.e(TAG,"onDraw childX--->"+childX);
        Log.e(TAG,"onDraw childY--->"+childY);
        Log.e(TAG,"onDraw radius--->"+radius);
        if(childX == 0|| childY == 0) return;
        Paint mp  = new Paint();
        mp.setColor(color);
//        mp.setColor(Color.BLACK);
        canvas.drawCircle(childX,childY,radius,mp);
    }


    /**
     *

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        int childCount = getChildCount();

        Log.e(TAG,"onAttachedToWindow childCount--->"+childCount);


        for(int i=0;i<childCount;i++)
        {
            AnimItemV animItemV = (AnimItemV)getChildAt(i);
            if(animItemV.getTag() != null)
            {
                Log.e(TAG,"第"+i+"个"+"====tag--->"+(int)animItemV.getTag());
            }

            final int finalI = i;
            animItemV.setOnTouchListener(new OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                    {
                        int radius = Math.max(getWidth(), getHeight());
                        int x = (int) motionEvent.getRawX();
                        int y = (int) motionEvent.getRawY();
                        setChildX(x);
                        setChildY(y);
                        setColor(getResources().getColor(colors[finalI]));


                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f, (float) radius);
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                        {
                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator)
                            {
                                float currentRadius = (float) valueAnimator.getAnimatedValue();

                                setRadius(currentRadius);

                                invalidate();

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
     */
}
