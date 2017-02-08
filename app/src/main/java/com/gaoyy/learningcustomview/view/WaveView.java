package com.gaoyy.learningcustomview.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.gaoyy.learningcustomview.R;

/**
 * Created by gaoyy on 2017/1/24.
 */

public class WaveView extends View implements Runnable
{
    private static final String TAG= WaveView.class.getSimpleName();
    private PaintFlagsDrawFilter mDrawFilter;
    private Paint mPaint;
    private Path mPath;
    private int mScreenWidth;
    private int mScreenHeight;

    private int riseY;
    private int offsetX;

    private Handler mHandler  = new Handler();

    public int getOffsetX()
    {
        return offsetX;
    }

    public void setOffsetX(int offsetX)
    {
        this.offsetX = offsetX;
    }

    public int getRiseY()
    {
        return riseY;
    }

    public void setRiseY(int riseY)
    {
        this.riseY = riseY;
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
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void run()
    {
        Log.i(TAG,"run");
        offsetX+=10;
        if(offsetX >= mScreenWidth)
        {
            offsetX= 0;
        }
        setOffsetX(offsetX);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        //遮罩层bitmap
        Bitmap maskBitmap = ((BitmapDrawable)getResources().getDrawable(R.mipmap.img)).getBitmap();
        //bitmap缩放到整个view的大小
        maskBitmap = Bitmap.createScaledBitmap(maskBitmap,mScreenWidth,mScreenHeight,false);
        //获取长度和宽度
        int maskWidth = maskBitmap.getWidth();
        int maskHeight = maskBitmap.getHeight();

        int saveFlags = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, maskWidth, maskHeight, null, saveFlags);

        canvas.drawBitmap(maskBitmap, 0, 0, mPaint);

        //取交集，和上层
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));


        // 从canvas层面去除锯齿
        canvas.setDrawFilter(mDrawFilter);
        canvas.translate((getWidth() + getPaddingLeft() - getPaddingRight()) / 2, (getHeight() + getPaddingTop() - getPaddingBottom()) / 2);

        mPaint.setColor(getResources().getColor(R.color.colorPrimary));

        mPath.reset();

        //屏幕外边的一条波纹
        mPath.moveTo(-mScreenWidth*3/2+offsetX,mScreenHeight/2-riseY);
        mPath.quadTo(-mScreenWidth*5/4+offsetX,mScreenHeight/2-20-riseY,-mScreenWidth+offsetX,mScreenHeight/2-riseY);
        mPath.quadTo(-mScreenWidth*3/4+offsetX,mScreenHeight/2-(-20)-riseY,-mScreenWidth/2+offsetX,mScreenHeight/2-riseY);


        //屏幕里面的波纹
        mPath.quadTo(-mScreenWidth/4+offsetX,mScreenHeight/2-20-riseY,0+offsetX,mScreenHeight/2-riseY);
        mPath.quadTo(mScreenWidth/4+offsetX,mScreenHeight/2-(-20)-riseY,mScreenWidth/2+offsetX,mScreenHeight/2-riseY);

        mPath.lineTo(mScreenWidth/2,mScreenHeight/2);
        //在此处封闭Path的时候也同时需要偏移量，否则出现左侧跳动
        mPath.lineTo(-mScreenWidth/2-offsetX,mScreenHeight/2);
        mPath.close();
        canvas.drawPath(mPath,mPaint);

        mPaint.setXfermode(null);
        canvas.restore();

        mHandler.postDelayed(this,20);
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
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator)
            {
                riseY = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.setDuration(5000);
        valueAnimator.setStartDelay(1000);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.start();

    }
}
