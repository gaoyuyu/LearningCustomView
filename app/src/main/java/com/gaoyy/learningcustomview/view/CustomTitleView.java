package com.gaoyy.learningcustomview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.gaoyy.learningcustomview.R;

/**
 * Created by gaoyy on 2017/1/10 0010.
 */
public class CustomTitleView extends View
{
    private static final String TAG = CustomTitleView.class.getSimpleName();
    //文字
    private String mTitleText;
    //文字颜色
    private int mTitleTextColor;
    //文字大小
    private float mTitleTextSize;

    //画笔
    private Paint mPaint;
    //矩形
    private Rect mRect;

    private int mDefaultPadding = 10;

    public CustomTitleView(Context context)
    {
        this(context, null);
    }

    public CustomTitleView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams(context, attrs);
        initPaint();
    }

    private void initParams(Context context, AttributeSet attrs)
    {
        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.CustomTitleView);
        mTitleText = ta.getString(R.styleable.CustomTitleView_titleText);
        mTitleTextSize = ta.getDimension(R.styleable.CustomTitleView_titleTextSize,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics()));
        mTitleTextColor = ta.getColor(R.styleable.CustomTitleView_titleTextColor, Color.BLUE);

        Log.i(TAG,mTitleText+"==="+mTitleTextSize+"===="+mTitleTextColor);

        ta.recycle();
    }

    private void initPaint()
    {
        mPaint = new Paint();

        mPaint.setTextSize(mTitleTextSize);
        mPaint.setColor(mTitleTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        mRect = new Rect();
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mRect);

        Log.i(TAG,"计算出来的文字的宽度："+mRect.width());
        Log.i(TAG,"计算出来的文字的高度："+mRect.height());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);




        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0,0,mRect.width()+mDefaultPadding*2,mRect.height()+mDefaultPadding*2,mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.drawRect(mDefaultPadding,mDefaultPadding,mRect.width()+mDefaultPadding,mRect.height()+mDefaultPadding,mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText, mDefaultPadding,mRect.height()+mDefaultPadding,mPaint);

        Log.i(TAG,"mRect.width()+mDefaultPadding*2===>"+(mRect.width()+mDefaultPadding*2));
        Log.i(TAG,"mRect.height()+mDefaultPadding*2===>"+(mRect.height()+mDefaultPadding*2));
        Log.i(TAG,"mDefaultPadding===>"+mDefaultPadding);
        Log.i(TAG,"mRect.height()+mDefaultPadding===>"+(mRect.height()+mDefaultPadding));
    }
}
