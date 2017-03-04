package com.gaoyy.learningcustomview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

/**
 * Created by gaoyy on 2017/2/25.
 */

public class AnimLinearLayout extends LinearLayout
{
    private static final String TAG = AnimLinearLayout.class.getSimpleName();
    private int[] rippleColors;
    private int childX;
    private int childY;
    private float radius;
    private int rippleColor;

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

    public int getRippleColor()
    {
        return rippleColor;
    }

    public void setRippleColor(int rippleColor)
    {
        this.rippleColor = rippleColor;
    }

    public int[] getRippleColors()
    {
        return rippleColors;
    }

    public void setRippleColors(int[] rippleColors)
    {
        this.rippleColors = rippleColors;
    }

    public AnimLinearLayout(Context context)
    {
        this(context, null);
    }

    public AnimLinearLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public AnimLinearLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        if (childX == 0 || childY == 0) return;
        Paint mp = new Paint();
        mp.setColor(rippleColor);
        canvas.drawCircle(childX, childY, radius, mp);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        Log.e(TAG, TAG + " dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        Log.e(TAG, TAG + " onInterceptTouchEvent");
        int action = event.getAction();
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                int childCount = getChildCount();
                int x = (int) event.getX();
                int y = (int) event.getY();

                Log.e(TAG, "x-->" + x);
                for (int i = 0; i < childCount; i++)
                {
                    AnimItemV animItemV = (AnimItemV) getChildAt(i);
                    if(x>=animItemV.getTop()&&x<=animItemV.getRight())
                    {
                        setRippleColor(getResources().getColor(rippleColors[i]));
                        break;
                    }
                }


                setChildX(x);
                setChildY(y);


                break;

            case MotionEvent.ACTION_UP:
                int radius = Math.max(getWidth(), getHeight());
                ValueAnimator animator = ValueAnimator.ofInt(0, radius);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
                {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator)
                    {
                        int currentRadius = (int) valueAnimator.getAnimatedValue();
                        setRadius((float) (currentRadius));
                        invalidate();
                    }
                });

                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();

                animator.addListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        setBackgroundColor(rippleColor);
                    }
                });
                break;

        }


        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.e(TAG, TAG + " onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();


        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++)
        {

            final AnimItemV animItemV = (AnimItemV) getChildAt(i);
            animItemV.setOnTouchListener(new OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent)
                {
                    for (int i = 0; i < getChildCount(); i++)
                    {
                        if (animItemV.getId() == getChildAt(i).getId())
                        {
                            continue;
                        }
                        ((AnimItemV) getChildAt(i)).reverseAnim();
                    }
                    return false;
                }
            });
        }

    }
}
