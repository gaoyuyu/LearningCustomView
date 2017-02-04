package com.gaoyy.learningcustomview.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gaoyy.learningcustomview.R;

/**
 * Created by gaoyy on 2017/1/24.
 */

public class WaveView extends View
{
    private static final String TAG= WaveView.class.getSimpleName();
    private PaintFlagsDrawFilter mDrawFilter;
    private Paint mPaint;
    private Path mPath;
    private int mScreenWidth;
    private int mScreenHeight;
    //贝塞尔曲线控制点X轴坐标
    private int mControlX;
    //贝塞尔曲线控制点Y轴坐标
    private int mControlY;

    private int currentControlY;

    public int getCurrentControlY()
    {
        return currentControlY;
    }

    public WaveView(Context context)
    {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WaveView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }

    private void initPaint()
    {
        mDrawFilter = new PaintFlagsDrawFilter(Paint.ANTI_ALIAS_FLAG, Paint.DITHER_FLAG);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        // 从canvas层面去除锯齿
        canvas.setDrawFilter(mDrawFilter);

        canvas.translate((getWidth() + getPaddingLeft() - getPaddingRight()) / 2, (getHeight() + getPaddingTop() - getPaddingBottom()) / 2);

        mPaint.setColor(getResources().getColor(R.color.colorPrimary));

        mPath.reset();


        //屏幕外边的一条波纹
        mPath.moveTo(-mScreenWidth*3/2+getCurrentControlY(),0);
        mPath.quadTo(-mScreenWidth*5/4+getCurrentControlY(),100,-mScreenWidth+getCurrentControlY(),0);
        mPath.quadTo(-mScreenWidth*3/4+getCurrentControlY(),-100,-mScreenWidth/2+getCurrentControlY(),0);


        //屏幕里面的波纹
        mPath.quadTo(-mScreenWidth/4+getCurrentControlY(),100,0+getCurrentControlY(),0);
        mPath.quadTo(mScreenWidth/4+getCurrentControlY(),-100,mScreenWidth/2+getCurrentControlY(),0);




        mPath.lineTo(mScreenWidth/2,mScreenHeight/2);
        //在此处封闭Path的时候也同时需要偏移量，否则出现左侧跳动
        mPath.lineTo(-mScreenWidth/2-getCurrentControlY(),mScreenHeight/2);
        mPath.close();
        canvas.drawPath(mPath,mPaint);




    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        Log.i(TAG, "onMeasure" + getMeasuredWidth() + "_______" + getMeasuredHeight());
    }

    private int measureWidth(int widthMeasureSpec)
    {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode)
        {
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "width MeasureSpec.EXACTLY");
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "width MeasureSpec.UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "width MeasureSpec.AT_MOST");
                break;
        }
        mScreenWidth = (result);
        return result;
    }

    private int measureHeight(int heightMeasureSpec)
    {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (specMode)
        {
            case MeasureSpec.EXACTLY:
                Log.i(TAG, "height MeasureSpec.EXACTLY");
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                Log.i(TAG, "height MeasureSpec.UNSPECIFIED");
                break;
            case MeasureSpec.AT_MOST:
                Log.i(TAG, "height MeasureSpec.AT_MOST");
                //wrap_content下默认为200dp
                break;
        }
        mScreenHeight = (result);
        return result;
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth = w;
        mScreenHeight = h;
        Log.e(TAG,"mScreenWidth-->"+mScreenWidth);
    }

    public void anim()
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 200);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                currentControlY = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();

    }
}
