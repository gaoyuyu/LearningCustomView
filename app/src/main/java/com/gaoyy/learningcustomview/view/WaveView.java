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
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gaoyy.learningcustomview.R;

import static android.content.ContentValues.TAG;

/**
 * Created by gaoyy on 2017/1/24.
 */

public class WaveView extends View
{

    private PaintFlagsDrawFilter mDrawFilter;
    private Paint mPaint;
    private Path mPath;
    private int mScreenWidth;
    private int mScreenHeight;
    //贝塞尔曲线控制点X轴坐标
    private int mControlX;
    //贝塞尔曲线控制点Y轴坐标
    private int mControlY;

    private int currentControlY=100;

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
        mPath.lineTo(-mScreenWidth/2,mScreenHeight/2);
        mPath.close();
        canvas.drawPath(mPath,mPaint);


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

        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 768);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                currentControlY = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();



    }
}
