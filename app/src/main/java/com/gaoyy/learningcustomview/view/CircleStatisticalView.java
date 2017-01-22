package com.gaoyy.learningcustomview.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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
    private float textSize = 0f;

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


        canvas.translate((getWidth() + getPaddingLeft() - getPaddingRight()) / 2, (getHeight() + getPaddingTop() - getPaddingBottom()) / 2);


        //外矩形
        mPaint.setColor(Color.LTGRAY);
        RectF rect1 = new RectF(-(outRectWidth - 200) / 2, -(outRectHeight - 200) / 2, (outRectWidth - 200) / 2, (outRectHeight - 200) / 2);
//        canvas.drawRect(rect1, mPaint);

        //内矩形
        mPaint.setColor(Color.DKGRAY);
        RectF rect2 = new RectF(-(outRectWidth) / 4, -(outRectHeight) / 4, (outRectWidth) / 4, (outRectHeight) / 4);
//        canvas.drawRect(rect2, mPaint);


//        if(outRectHeight != outRectWidth)
//        {
//            throw new RuntimeException("不是正方形");
//        }
        int radius = (outRectWidth - 200) / 2;

        int start = 0;
        int end = 0;
        for (int i = 0; i < datas.length; i++)
        {
            int hudu = (int) (360 * datas[i]);
            start = end;
            end = end + hudu;

            mPaint.setColor(colors[i]);
            canvas.drawArc(rect1, start, Math.min(hudu - 1, currentAnimValue), true, mPaint);

            int textAngle = hudu / 2;
            int feed = hudu / 2 + start;

            Log.e(TAG, "start-->" + start);
            Log.e(TAG, "end-->" + end);
            Log.e(TAG, "hudu-->" + hudu);
            Log.e(TAG, "textAngle-->" + textAngle);
            Log.e(TAG, "feed-->" + feed);


            drawText(canvas, feed, texts[i], hudu, radius);

        }
        canvas.drawArc(rect1, start, hudu, true, mPaint);
        mPaint.setColor(Color.WHITE);
//        canvas.drawArc(rect2, 0, 360, true, mPaint);

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

        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(0f, 35f);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                textSize = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator1.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator1.setDuration(2000);
        valueAnimator1.start();



    }


    //画文字
    private void drawText(Canvas mCanvas, float end, String kinds, float needDrawAngle, int mRadius)
    {
        Rect rect = new Rect();
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(textSize);
        mPaint.getTextBounds(kinds, 0, kinds.length(), rect);
        if (end >= 0 && end <= 90)
        { //画布坐标系第一象限(数学坐标系第四象限)
//            if (needDrawAngle < 30) { //如果小于某个度数,就把文字画在饼状图外面
            mCanvas.drawText(kinds, (float) (mRadius * 1.2 * Math.cos(Math.toRadians(end))),
                    (float) (mRadius * 1.2 * Math.sin(Math.toRadians(end))) + rect.height() / 2, mPaint);

            Log.i(TAG,"画布坐标系第一象限(数学坐标系第四象限)");
            Log.i(TAG,"end-->"+end);
            Log.i(TAG,"x--->"+(float) (mRadius * 1.2 * Math.cos(Math.toRadians(end))));
            Log.i(TAG,"y--->"+(float) (mRadius * 1.2 * Math.sin(Math.toRadians(end))) + rect.height() / 2);
//            } else {
//                mCanvas.drawText(kinds, (float) (mRadius * 0.75 * Math.cos(Math.toRadians(textAngle))), (float) (mRadius * 0.75 * Math.sin(Math.toRadians(textAngle)))+rect.height()/2, mPaint);
//            }
        }
        else if (end > 90 && end <= 180)
        { //画布坐标系第二象限(数学坐标系第三象限)
//            if (needDrawAngle < 30) {
            mCanvas.drawText(kinds, (float) (-mRadius * 1.2 * Math.cos(Math.toRadians(180 - end))),
                    (float) (mRadius * 1.2 * Math.sin(Math.toRadians(180 - end))) + rect.height() / 2, mPaint);
            Log.e(TAG,"画布坐标系第二象限(数学坐标系第三象限)");
            Log.e(TAG,"end-->"+end);
            Log.e(TAG,"x--->"+(float) (-mRadius * 1.2 * Math.cos(Math.toRadians(180 - end))));
            Log.e(TAG,"y--->"+(float) (mRadius * 1.2 * Math.sin(Math.toRadians(180 - end))) + rect.height() / 2);
//            } else {
//                mCanvas.drawText(kinds, (float) (-mRadius * 0.75 * Math.cos(Math.toRadians(180 - textAngle))), (float) (mRadius * 0.75 * Math.sin(Math.toRadians(180 - textAngle)))+rect.height()/2, mPaint);
//            }
        }
        else if (end > 180 && end <= 270)
        { //画布坐标系第三象限(数学坐标系第二象限)
//            if (needDrawAngle < 30) {
            mCanvas.drawText(kinds, (float) (-mRadius * 1.2 * Math.cos(Math.toRadians(end - 180))),
                    (float) (-mRadius * 1.2 * Math.sin(Math.toRadians(end - 180))) + rect.height() / 2, mPaint);
//            } else {
//                mCanvas.drawText(kinds, (float) (-mRadius * 0.75 * Math.cos(Math.toRadians(textAngle - 180))), (float) (-mRadius * 0.75 * Math.sin(Math.toRadians(textAngle - 180)))+rect.height()/2, mPaint);
//            }
            Log.w(TAG,"画布坐标系第三象限(数学坐标系第二象限)");
            Log.w(TAG,"end-->"+end);
            Log.w(TAG,"x--->"+(float) (-mRadius * 1.2 * Math.cos(Math.toRadians(end - 180))));
            Log.w(TAG,"y--->"+(float) (-mRadius * 1.2 * Math.sin(Math.toRadians(end - 180))) + rect.height() / 2);
        }
        else
        { //画布坐标系第四象限(数学坐标系第一象限)
//            if (needDrawAngle < 30) {
            mCanvas.drawText(kinds, (float) (mRadius * 1.2 * Math.cos(Math.toRadians(360 - end))),
                    (float) (-mRadius * 1.2 * Math.sin(Math.toRadians(360 - end))) + rect.height() / 2, mPaint);
            Log.d(TAG,"画布坐标系第四象限(数学坐标系第一象限)");
            Log.d(TAG,"end-->"+end);
            Log.d(TAG,"x--->"+(float) (mRadius * 1.2 * Math.cos(Math.toRadians(360 - end))));
            Log.d(TAG,"y--->"+(float) (-mRadius * 1.2 * Math.sin(Math.toRadians(360 - end))) + rect.height() / 2);
//            } else {
//                mCanvas.drawText(kinds, (float) (mRadius * 0.75 * Math.cos(Math.toRadians(360 - textAngle))), (float) (-mRadius * 0.75 * Math.sin(Math.toRadians(360 - textAngle)))+rect.height()/2, mPaint);
//            }
        }

    }
}
