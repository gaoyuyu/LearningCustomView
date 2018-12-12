package com.gaoyy.learningcustomview.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

import com.gaoyy.learningcustomview.R;


public class FadeImageView extends View {

    private int mWidth;
    private int mHeight;
    private Bitmap mFrontBitmap = null;
    private Bitmap mBackgroundBitmap = null;
    //左边一半部分的动画
    private ValueAnimator leftPartAnimator = null;
    //右边一半部分的动画
    private ValueAnimator rightPartAnimator = null;
    //动画集合
    private AnimatorSet animatorSet = null;

    //左边一半部分的动画翻转
    private ValueAnimator leftPartReverseAnimator = null;
    //右边一半部分的动画翻转
    private ValueAnimator rightPartReverseAnimator = null;
    //动画集合翻转
    private AnimatorSet reverseAnimatorSet = null;

    private int mLeftCurrentX;
    private int mRightCurrentX;

    //用于变化透明度的画笔
    private Paint mAlphaPaint;
    //前景图资源ID
    private int mFrontImageResId;
    //背景景图资源ID
    private int mBackgroundImageResId;
    //动画时间
    private int mAnimDuration;

    public FadeImageView(Context context) {
        this(context, null);
    }

    public FadeImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FadeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mAlphaPaint = new Paint();
        mAlphaPaint.setAlpha(255);
        if (mFrontImageResId == 0)
            throw new NullPointerException("必须设置前景图片资源");
        if (mBackgroundImageResId == 0)
            throw new NullPointerException("必须设置背景图片资源");
        mFrontBitmap = BitmapFactory.decodeResource(getResources(), mFrontImageResId);
        mBackgroundBitmap = BitmapFactory.decodeResource(getResources(), mBackgroundImageResId);
    }

    private void initParams(Context context, AttributeSet attrs) {
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FadeImageView);
        mBackgroundImageResId = ta.getResourceId(R.styleable.FadeImageView_fiv_backgroundImageRes, 0);
        mFrontImageResId = ta.getResourceId(R.styleable.FadeImageView_fiv_frontImageRes, 0);
        mAnimDuration = ta.getInt(R.styleable.FadeImageView_fiv_animDuration, 1000);
        ta.recycle();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLeftPart(canvas);
        drawRightPart(canvas);
        canvas.drawBitmap(mBackgroundBitmap, 0, 0, mAlphaPaint);
    }

    private void drawRightPart(Canvas canvas) {
        Rect rightSrc = new Rect(mRightCurrentX, 0, mWidth, mHeight);
        Rect rightDst = new Rect(mRightCurrentX, 0, mWidth, mHeight);
        canvas.drawBitmap(mFrontBitmap, rightSrc, rightDst, null);
    }

    private void drawLeftPart(Canvas canvas) {
        Rect leftSrc = new Rect(0, 0, mLeftCurrentX, mHeight);
        Rect leftDst = new Rect(0, 0, mLeftCurrentX, mHeight);
        canvas.drawBitmap(mFrontBitmap, leftSrc, leftDst, null);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);
        mWidth = width;
        mHeight = height;
        mRightCurrentX = mWidth;
        initAnim();

        mFrontBitmap = Bitmap.createScaledBitmap(mFrontBitmap, mWidth, mHeight, false);
        mBackgroundBitmap = Bitmap.createScaledBitmap(mBackgroundBitmap, mWidth, mHeight, false);


    }

    /**
     * 初始化动画效果
     */
    private void initAnim() {
        if (leftPartAnimator == null) {
            leftPartAnimator = ValueAnimator.ofInt(0, mWidth / 2);
            leftPartAnimator.setInterpolator(new FastOutSlowInInterpolator());
            leftPartAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mLeftCurrentX = (int) valueAnimator.getAnimatedValue();
                    postInvalidate();
                }
            });
        }

        if (rightPartAnimator == null) {
            rightPartAnimator = ValueAnimator.ofInt(mRightCurrentX, mWidth / 2);
            rightPartAnimator.setInterpolator(new FastOutSlowInInterpolator());
            rightPartAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mRightCurrentX = (int) valueAnimator.getAnimatedValue();
                    postInvalidate();
                }
            });
        }

        if (leftPartReverseAnimator == null) {
            leftPartReverseAnimator = ValueAnimator.ofInt(mWidth / 2, 0);
            leftPartReverseAnimator.setInterpolator(new FastOutSlowInInterpolator());
            leftPartReverseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mLeftCurrentX = (int) valueAnimator.getAnimatedValue();
                    postInvalidate();
                }
            });
        }

        if (rightPartReverseAnimator == null) {
            rightPartReverseAnimator = ValueAnimator.ofInt(mWidth / 2, mRightCurrentX);
            rightPartReverseAnimator.setInterpolator(new FastOutSlowInInterpolator());
            rightPartReverseAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    mRightCurrentX = (int) valueAnimator.getAnimatedValue();
                    postInvalidate();
                }
            });
        }
    }

    public void startAnim() {
        if (animatorSet != null) {
            animatorSet.start();
        } else {
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(leftPartAnimator, rightPartAnimator);
            animatorSet.setDuration(mAnimDuration);
            animatorSet.setInterpolator(new FastOutSlowInInterpolator());
            animatorSet.start();
        }
    }

    public void resetAll() {
        if (reverseAnimatorSet != null) {
            reverseAnimatorSet.start();
        } else {
            reverseAnimatorSet = new AnimatorSet();
            reverseAnimatorSet.playTogether(leftPartReverseAnimator, rightPartReverseAnimator);
            reverseAnimatorSet.setDuration(mAnimDuration);
            animatorSet.setInterpolator(new FastOutSlowInInterpolator());
            reverseAnimatorSet.start();
        }
    }

    public void resetLeft() {
        leftPartReverseAnimator.setDuration(mAnimDuration);
        leftPartReverseAnimator.start();
    }

    public void resetRight() {
        rightPartReverseAnimator.setDuration(mAnimDuration);
        rightPartReverseAnimator.start();
    }

}
