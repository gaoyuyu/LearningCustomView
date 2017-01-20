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
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by gaoyy on 2017/1/11.
 */

public class CircleStatisticalView extends View
{

    private int currentAnimValue;
    private double[] datas = {0.3, 0.4, 0.15, 0.1, 0.05};
    private String[] texts = {"火影", "Ex-Aid", "扎克", "RxJava", "ReactNative"};

    private int outRectWidth;
    private int outRectHeight;

    public int getOutRectHeight()
    {
        return outRectHeight;
    }

    public void setOutRectHeight(int outRectHeight)
    {
        this.outRectHeight = outRectHeight;
    }

    public int getOutRectWidth()
    {
        return outRectWidth;
    }

    public void setOutRectWidth(int outRectWidth)
    {
        this.outRectWidth = outRectWidth;
    }

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
        setOutRectWidth(result);
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
        setOutRectHeight(result);
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        //在默认的屏幕坐标系中角度增大方向为顺时针。
        Log.i(TAG, "onDraw");
        super.onDraw(canvas);

        //外矩形
        mPaint.setColor(Color.LTGRAY);
        RectF rect1 = new RectF(0, 0, outRectWidth, outRectHeight);
//        canvas.drawRect(rect1, mPaint);

        //内矩形
        mPaint.setColor(Color.DKGRAY);
        RectF rect2 = new RectF((outRectWidth) / 4, (outRectHeight) / 4, (outRectWidth) * 3 / 4, (outRectHeight) * 3 / 4);
//        canvas.drawRect(rect2, mPaint);


//        if(outRectHeight != outRectWidth)
//        {
//            throw new RuntimeException("不是正方形");
//        }
        int radius = (outRectWidth) / 2;

        int start = 0;
        int end = 0;
        for (int i = 0; i < datas.length; i++)
        {
            int hudu = (int) (360 * datas[i]);
            start = end;
            end = end + hudu;

            mPaint.setColor(colors[i]);
            canvas.drawArc(rect1, start, Math.min(hudu - 1, currentAnimValue), true, mPaint);


            /**
             * 1.首先判断角度是处于第几象限，确定x，y的正负号
             * 2.最后计算x，y的值
             */

            int textAngle = hudu / 2;
            int feed = hudu / 2+start;

            Log.e(TAG, "start-->" + start);
            Log.e(TAG, "end-->" + end);
            Log.e(TAG, "hudu-->" + hudu);
            Log.e(TAG, "textAngle-->" + textAngle);
            Log.e(TAG, "feed-->" + feed);


            mPaint.setColor(Color.BLACK);
            mPaint.setTextSize(40f);
            if (feed >= 0 && feed <= 90)
            {
                Log.i(TAG, "第四象限"+radius);
                double x = Math.abs(radius * Math.cos(Math.toRadians(textAngle)));
                double y = Math.abs(radius * Math.sin(Math.toRadians(textAngle)));
                canvas.drawText(texts[i], (float) (x + radius), (float) (y + radius), mPaint);
                Log.w(TAG,texts[i]+"===="+"x:"+(float) (x + radius)+"==="+"y:"+(float) (y + radius));

            }
            else if (feed > 90 && feed <= 180)
            {
                Log.i(TAG, "第3象限");

                double x = Math.abs(radius * Math.cos(Math.toRadians(180-textAngle)));
                double y = Math.abs(radius * Math.sin(Math.toRadians(180-textAngle)));

                canvas.drawText(texts[i], (float) (radius - x), (float) (y + radius), mPaint);
                Log.w(TAG,texts[i]+"===="+"x:"+(float) (radius - x)+"==="+"y:"+(float) (y + radius));
            }
            else if (feed > 180 && feed <= 270)
            {
                Log.i(TAG, "第2象限");
                double x = Math.abs(radius * Math.cos(Math.toRadians(textAngle - 180)));
                double y = Math.abs(radius * Math.sin(Math.toRadians(textAngle - 180)));
                canvas.drawText(texts[i], (float) (radius - x), (float) (radius - y), mPaint);
                Log.w(TAG,texts[i]+"===="+"x:"+(float) (radius - x)+"==="+"y:"+(float) (radius - y));

            }
            else
            {
                Log.i(TAG, "第1象限");
                double x = Math.abs(radius * Math.cos(Math.toRadians(360-textAngle)));
                double y = Math.abs(radius * Math.sin(Math.toRadians(360-textAngle)));


                canvas.drawText(texts[i], (float) (x + radius), (float) (radius - y), mPaint);

                Log.w(TAG,texts[i]+"===="+"x:"+(float) (x + radius)+"==="+"y:"+(float) (radius - y));
            }


        }
        canvas.drawArc(rect1, start, hudu, true, mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(rect2, 0, 360, true, mPaint);

    }

    public void animUpdate()
    {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 360);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                currentAnimValue = (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(5000);
        valueAnimator.start();

    }
}
