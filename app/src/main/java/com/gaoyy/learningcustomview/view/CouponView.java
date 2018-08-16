package com.gaoyy.learningcustomview.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.gaoyy.learningcustomview.R;


public class CouponView extends View
{
    private static final String TAG = CouponView.class.getSimpleName();
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 2;
    private static final int DASH_LINE_TOP = 1;
    private static final int DASH_LINE_BOTTOM = 2;
    private PaintFlagsDrawFilter pfd;
    private int mWidth;
    private int mHeight;

    private int mMode;
    private boolean mLeftTop, mLeftBottom, mRightTop, mRightBottom;
    private int mBgColor, mLineColor;
    private int mLineWidth, mCornerCircleRadius, mRectCornerRadius, mMidDashLineWidth;
    private int mMidDashLinePosition;

    private Paint mRectPaint, mCirclePaint, mWhitePaint, mWhiteDashPaint, mMidLineDashPaint;
    private Path mPath;

    public CouponView(Context context)
    {
        this(context, null);
    }

    public CouponView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public CouponView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CouponView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams(context, attrs);
        initPaint();
    }

    private void initParams(Context context, AttributeSet attrs)
    {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CouponView);
        mMode = ta.getInt(R.styleable.CouponView_mode, 2);
        mLeftTop = ta.getBoolean(R.styleable.CouponView_leftTop, false);
        mLeftBottom = ta.getBoolean(R.styleable.CouponView_leftBottom, false);
        mRightTop = ta.getBoolean(R.styleable.CouponView_rightTop, false);
        mRightBottom = ta.getBoolean(R.styleable.CouponView_rightBottom, false);
        mBgColor = ta.getColor(R.styleable.CouponView_bgColor, Color.parseColor("#FFBE23"));
        mLineColor = ta.getColor(R.styleable.CouponView_lineColor, Color.parseColor("#949494"));
        mLineWidth = ta.getDimensionPixelSize(R.styleable.CouponView_lineWidth, mLineWidth);
        mCornerCircleRadius = ta.getDimensionPixelSize(R.styleable.CouponView_cornerCircleRadius, mCornerCircleRadius);
        mRectCornerRadius = ta.getDimensionPixelSize(R.styleable.CouponView_rectCornerRadius, mRectCornerRadius);
        mMidDashLinePosition = ta.getInt(R.styleable.CouponView_midDashLinePosition, 0);
        mMidDashLineWidth = ta.getDimensionPixelSize(R.styleable.CouponView_midDashLineWidth, mMidDashLineWidth);
        ta.recycle();
    }

    private void initPaint()
    {
        pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);

        mWhitePaint = new Paint();
        mWhitePaint.setAntiAlias(true);
        mWhitePaint.setColor(Color.WHITE);
        mWhitePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //白色虚线画笔，画虚线必须用path
        mWhiteDashPaint = new Paint();
        mWhiteDashPaint.setAntiAlias(true);
        mWhiteDashPaint.setColor(Color.WHITE);
        mWhiteDashPaint.setStyle(Paint.Style.STROKE);
        mWhiteDashPaint.setStrokeWidth(mMidDashLineWidth);
        mWhiteDashPaint.setPathEffect(new DashPathEffect(new float[]{15, 10}, 0));

        //gray虚线画笔
        mMidLineDashPaint = new Paint();
        mMidLineDashPaint.setAntiAlias(true);
        mMidLineDashPaint.setColor(mLineColor);
        mMidLineDashPaint.setStyle(Paint.Style.STROKE);
        mMidLineDashPaint.setStrokeWidth(mMidDashLineWidth);
        mMidLineDashPaint.setPathEffect(new DashPathEffect(new float[]{15, 10}, 0));

        mPath = new Path();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.setDrawFilter(pfd);

        if (mMode == ACTIVE)
        {
            mRectPaint.setColor(mBgColor);
            mRectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRoundRect(rectF, mRectCornerRadius, mRectCornerRadius, mRectPaint);

            mCirclePaint.setColor(Color.WHITE);
            mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

            if (mMidDashLinePosition == DASH_LINE_TOP)
            {
                mPath.reset();
                mPath.moveTo(0, 0);
                mPath.lineTo(getMeasuredWidth(), 0);
                canvas.drawPath(mPath, mWhiteDashPaint);

            }
            else if (mMidDashLinePosition == DASH_LINE_BOTTOM)
            {
                mPath.reset();
                mPath.moveTo(0, getMeasuredHeight());
                mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
                canvas.drawPath(mPath, mWhiteDashPaint);
            }

        }
        else if (mMode == INACTIVE)
        {
            mRectPaint.setColor(mLineColor);
            mRectPaint.setStyle(Paint.Style.FILL);
            RectF rectF = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
            canvas.drawRoundRect(rectF, mRectCornerRadius, mRectCornerRadius, mRectPaint);


            RectF whiteRectF = new RectF(mLineWidth, mLineWidth, getMeasuredWidth()-mLineWidth, getMeasuredHeight()-mLineWidth);
            canvas.drawRoundRect(whiteRectF, mRectCornerRadius, mRectCornerRadius, mWhitePaint);



            mCirclePaint.setColor(mLineColor);
            mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

            if (mMidDashLinePosition == DASH_LINE_TOP)
            {
                //覆盖实线
                Paint tmpPaint = new Paint();
                tmpPaint.setStrokeWidth(10);
                tmpPaint.setColor(Color.WHITE);
                canvas.drawLine(0, 0, getMeasuredWidth(), 0, tmpPaint);

                mPath.reset();
                mPath.moveTo(0, 0);
                mPath.lineTo(getMeasuredWidth(), 0);
                canvas.drawPath(mPath, mMidLineDashPaint);
            }
            else if (mMidDashLinePosition == DASH_LINE_BOTTOM)
            {
                //覆盖实线
                Paint tmpPaint = new Paint();
                tmpPaint.setStrokeWidth(10);
                tmpPaint.setColor(Color.WHITE);
                canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), tmpPaint);

                mPath.reset();
                mPath.moveTo(0, getMeasuredHeight());
                mPath.lineTo(getMeasuredWidth(), getMeasuredHeight());
                canvas.drawPath(mPath, mMidLineDashPaint);
            }
        }

        drawCornerCircle(canvas);

    }

    /**
     * 绘制四个角的圆形
     *
     * @param canvas
     */
    private void drawCornerCircle(Canvas canvas)
    {
        if (mLeftTop)
        {
            canvas.drawCircle(0, 0, mCornerCircleRadius, mCirclePaint);
            canvas.drawCircle(0, 0, mCornerCircleRadius - mLineWidth, mWhitePaint);
        }
        if (mLeftBottom)
        {
            canvas.drawCircle(0, getMeasuredHeight(), mCornerCircleRadius, mCirclePaint);
            canvas.drawCircle(0, getMeasuredHeight(), mCornerCircleRadius - mLineWidth, mWhitePaint);
        }

        if (mRightTop)
        {
            canvas.drawCircle(getMeasuredWidth(), 0, mCornerCircleRadius, mCirclePaint);
            canvas.drawCircle(getMeasuredWidth(), 0, mCornerCircleRadius - mLineWidth, mWhitePaint);
        }

        if (mRightBottom)
        {
            canvas.drawCircle(getMeasuredWidth(), getMeasuredHeight(), mCornerCircleRadius, mCirclePaint);
            canvas.drawCircle(getMeasuredWidth(), getMeasuredHeight(), mCornerCircleRadius - mLineWidth, mWhitePaint);
        }
    }


    public void setMode(int mMode)
    {
        this.mMode = mMode;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        Log.i(TAG, "onMeasure" + getMeasuredWidth() + "_______" + getMeasuredHeight());
    }

    private int measureWidth(int widthMeasureSpec)
    {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
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
        mWidth = result;
        return result;
    }

    private int measureHeight(int heightMeasureSpec)
    {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
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
        mHeight = result;
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
}
