package com.gaoyy.learningcustomview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.gaoyy.learningcustomview.R;

public class LoveWaveView extends View {

    // 默认属性值
    private static final int DEFAULT_AMPLITUDE = 30;
    private static final int DEFAULT_PERIOD = 16;
    private static final float DEFAULT_SPEED = 0.1F;
    private static final float DEFAULT_QUADRANT = 0.33F;
    private static final float DEFAULT_FREQUENCY = 1F / 360F;

    // 振幅
    private int mAmplitude;
    // 波浪位于View的位置
    private float mQuadrant;
    // 波浪的频率,这个值越大,波浪越密集
    private float mFrequency;
    // 速度
    private float mSpeed;
    //遮罩图资源id
    private int mMaskImageResId;
    //前景图资源id
    private int mFrontImageResId;

    //中间文本的内容
    private String mCenterText;
    //中间文本字体大小
    private int mCenterTextSize;
    //中间文本字体颜色
    private int mCenterTextColor;
    //中间文本的画笔
    private Paint mCenterTextPaint;

    //最大值
    private int mMaxValue;
    //当前进度
    private float mCurrentProgress;

    private float mShift;
    private int mWidth;
    private int mHeight;

    private Bitmap mMaskBitmap;
    private Bitmap mFrontBitmap;

    private PorterDuffXfermode mPorterDuffXfermode;

    private ValueAnimator valueAnimator;

    //波浪的渐变颜色
    private int[] mWaveColor =
            new int[]{Color.parseColor("#C1e975e2"), Color.parseColor("#C1755bed")};

    private Paint mWavePaint;
    private Path mWavePath;

    private LinearGradient mLinearGradient;

    public LoveWaveView(Context context) {
        this(context, null);
    }

    public LoveWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();
        initAnimation();
    }

    private void initParams(Context context, AttributeSet set) {
        final TypedArray ta = context.obtainStyledAttributes(set, R.styleable.LoveWaveView);
        mSpeed = ta.getFloat(R.styleable.LoveWaveView_lwv_speed, DEFAULT_SPEED);
        mAmplitude = ta.getInt(R.styleable.LoveWaveView_lwv_amplitude, DEFAULT_AMPLITUDE);
        mQuadrant = ta.getFloat(R.styleable.LoveWaveView_lwv_quadrant, DEFAULT_QUADRANT);

        //最大值
        mMaxValue = ta.getInt(R.styleable.LoveWaveView_lwv_maxValue, 10);
        //当前进度
        mCurrentProgress = ta.getFloat(R.styleable.LoveWaveView_lwv_currentProgress, 0);
        //处理百分比进度数值转化
        calQuadrant();
        mFrequency = ta.getFloat(R.styleable.LoveWaveView_lwv_frequency, DEFAULT_FREQUENCY);
        mMaskImageResId = ta.getResourceId(R.styleable.LoveWaveView_lwv_maskImageRes, 0);
        mFrontImageResId = ta.getResourceId(R.styleable.LoveWaveView_lwv_frontImageRes, 0);
        mCenterText = ta.getString(R.styleable.LoveWaveView_lwv_centerText);
        //中间文字为空的情况下，用当前进度作为文本显示
        if (TextUtils.isEmpty(mCenterText)) {
            mCenterText = String.valueOf(mCurrentProgress);
        }
        mCenterTextSize = ta.getDimensionPixelSize(R.styleable.LoveWaveView_lwv_centerTextSize, 13);
        mCenterTextColor = ta.getColor(R.styleable.LoveWaveView_lwv_centerTextColor, Color.WHITE);

        ta.recycle();

        //初始化遮罩模式
        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
    }

    private void initPaint() {
        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePaint.setStrokeWidth(2);
        mWavePath = new Path();


        mCenterTextPaint = new Paint();
        mCenterTextPaint.setAntiAlias(true);
        mCenterTextPaint.setTextSize(mCenterTextSize);
        mCenterTextPaint.setColor(mCenterTextColor);

    }

    /**
     * 初始化动画
     */
    public void initAnimation() {
        valueAnimator = ValueAnimator.ofInt(0, 1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mWavePath.reset();
                mShift += mSpeed;
                invalidate();
            }
        });
    }


    /**
     * 处理百分比进度数值转化
     */
    private void calQuadrant() {
        float percent = mCurrentProgress / mMaxValue;
        if (percent >= 1) {
            //大于等于1时，此时时充满整个形状，mQuadrant设置为负数，防止顶部充不满
            mQuadrant = -1f;
        } else if (percent == 0) {
            //等于0时，此时整个形状都是空的，mQuadrant设置为大于1，防止底部仍然出现小丢丢的波浪
            mQuadrant = 1.5f;
        } else {
            //主要这里是1-percent之后在赋值给到mQuadrant
            mQuadrant = 1 - percent;
        }
    }

    /**
     * 设置进度
     *
     * @param currentProgress
     */
    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        mWavePath.reset();
        //每次设置都需要重新计算quadrant
        calQuadrant();
        postInvalidate();
    }

    public void setMaxValue(int mMaxValue) {
        this.mMaxValue = mMaxValue;
        mWavePath.reset();
        //每次设置都需要重新计算quadrant
        calQuadrant();
        postInvalidate();
    }


    /**
     * 设置中间的文本
     *
     * @param centerText
     */
    public void setCenterText(String centerText) {
        this.mCenterText = centerText;
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {

        mWidth = width;
        mHeight = height;

        if (mMaskImageResId == 0) {
            throw new NullPointerException("必须设置遮罩层的图片资源！");
        }
        if (mFrontImageResId == 0) {
            throw new NullPointerException("必须设置前景层的图片资源！");
        }

        //判空，防止重复初始化
        if (mLinearGradient == null) {
            //初始化波浪的渐变颜色
            mLinearGradient = new LinearGradient(0, 0, 0, mHeight, mWaveColor,
                    null, Shader.TileMode.CLAMP);
        }

        //遮罩层bitmap
        mMaskBitmap = ((BitmapDrawable) getResources().getDrawable(mMaskImageResId)).getBitmap();
        //bitmap缩放到整个view的大小
        mMaskBitmap = Bitmap.createScaledBitmap(mMaskBitmap, mWidth, mHeight, false);

        //前景层bitmap
        mFrontBitmap = ((BitmapDrawable) getResources().getDrawable(mFrontImageResId)).getBitmap();
        //bitmap缩放到整个view的大小
        mFrontBitmap = Bitmap.createScaledBitmap(mFrontBitmap, mWidth, mHeight, false);

        super.onSizeChanged(width, height, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("Wave", "onDraw==>");

        //设置波浪的渐变颜色
        mWavePaint.setShader(mLinearGradient);

        canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);

        drawWave(canvas, mWidth, mHeight);

        //取交集，和上层
        mWavePaint.setXfermode(mPorterDuffXfermode);

        canvas.drawBitmap(mMaskBitmap, 0, 0, mWavePaint);

        mWavePaint.setXfermode(null);
        canvas.restore();

        canvas.drawBitmap(mFrontBitmap, 0, 0, mWavePaint);

        float textWidth = mCenterTextPaint.measureText(mCenterText);
        float textHeight = getTextHeight(mCenterTextPaint);

        canvas.drawText(mCenterText, mWidth / 2 - textWidth / 2, mHeight / 2 + textHeight / 2, mCenterTextPaint);

    }

    private void drawWave(Canvas canvas, int width, int height) {

        final int waveHeight = (int) (getHeight() * mQuadrant);

        mWavePath.moveTo(0, height);
        mWavePath.lineTo(0, waveHeight);
        for (int i = 1; i <= width; i++) {
            // 绘制正弦曲线 y = A Sin(ωt+ ρ) = A sin(2πft + ρ)
            final float y = (float) (waveHeight + mAmplitude * Math.sin(2 * Math.PI * i * mFrequency + mShift));
            mWavePath.lineTo(i, y);
        }
        // 将曲线闭合
        mWavePath.lineTo(width, height);
        canvas.drawPath(mWavePath, mWavePaint);


        //重置path后，绘制第二条波浪，350为了增加偏移
        mWavePath.reset();

        mWavePath.moveTo(0, height);
        mWavePath.lineTo(0, waveHeight);
        for (int i = 1; i <= width; i++) {
            final float y = (float) (waveHeight + mAmplitude * Math.sin(2 * Math.PI * i * mFrequency + mShift + 350));
            mWavePath.lineTo(i, y);
        }
        mWavePath.lineTo(width, height);
        canvas.drawPath(mWavePath, mWavePaint);

        //重置path后，绘制第三条波浪，700为了增加偏移
        mWavePath.reset();

        mWavePath.moveTo(0, height);
        mWavePath.lineTo(0, waveHeight);
        for (int i = 1; i <= width; i++) {
            final float y = (float) (waveHeight + mAmplitude * Math.sin(2 * Math.PI * i * mFrequency + mShift + 700));
            mWavePath.lineTo(i, y);
        }
        mWavePath.lineTo(width, height);
        canvas.drawPath(mWavePath, mWavePaint);
    }

    /**
     * 获取文本的高度
     *
     * @param paint 文本绘制的画笔
     * @return 文本高度
     */
    private int getTextHeight(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

    /**
     * 开启动画
     */
    public void startAnim() {
        if (valueAnimator != null) {
            valueAnimator.start();
        }
    }

    /**
     * 停止动画动画
     */
    public void stopAnim() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }


}