package com.gaoyy.learningcustomview.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by gaoyy on 2017/1/11.
 */

public class CircleStatisticalView extends View
{

    private int currentAnimValue;
    private double[] datas = {0.3, 0.4, 0.15, 0.1, 0.05};

    public double[] getDatas()
    {
        return datas;
    }

    public void setDatas(double[] datas)
    {
        this.datas = datas;
    }


    private int start;
    private int hudu;

    public int getStart()
    {
        return start;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public int getHudu()
    {
        return hudu;
    }

    public void setHudu(int hudu)
    {
        this.hudu = hudu;
    }

    private int[] colors = {getResources().getColor(android.R.color.holo_blue_bright),
            getResources().getColor(android.R.color.holo_red_light),
            getResources().getColor(android.R.color.holo_green_light),
            getResources().getColor(android.R.color.holo_purple), getResources().getColor(android.R.color.holo_orange_light)};

    private static final String TAG = CircleStatisticalView.class.getSimpleName();
    private Paint mPaint;

    public CircleStatisticalView(Context context)
    {
        this(context, null);
    }

    public CircleStatisticalView(Context context, AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public CircleStatisticalView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleStatisticalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
    }


    private void initPaint()
    {
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Log.i(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        //在默认的屏幕坐标系中角度增大方向为顺时针。
        Log.i(TAG, "onDraw");
        super.onDraw(canvas);

        //外矩形
        mPaint.setColor(Color.LTGRAY);
        RectF rect1 = new RectF(100, 100, 600, 600);
//        canvas.drawRect(rect1, mPaint);

        //内矩形
        mPaint.setColor(Color.DKGRAY);
        RectF rect2 = new RectF(225, 225, 475, 475);
//        canvas.drawRect(rect2, mPaint);


        int start = 0;
        int end = 0;
        for (int i = 0; i < datas.length; i++)
        {
            int hudu = (int) (360 * datas[i]);
            start = end;
            end = end + hudu;

            mPaint.setColor(colors[i]);
            canvas.drawArc(rect1, start, Math.min(hudu-1,currentAnimValue), true, mPaint);

        }
        canvas.drawArc(rect1, start, hudu, true, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(rect2, 0, 360, true, mPaint);

    }

    public void animUpdate()
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0,360);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                currentAnimValue = (int)valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(5000);
        valueAnimator.start();

    }
}
