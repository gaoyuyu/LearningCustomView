package com.gaoyy.learningcustomview.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.Point;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gaoyy.learningcustomview.R;

import java.util.Arrays;

/**
 * 雷达数值蜘蛛网状图
 *
 * @author gaoyuyu
 */
public class RadarScoreView extends View {
    //数据个数
    private int mDataCount;
    //每个角的弧度
    private float mRadian;
    //雷达图半径
    private float mRadius;
    //中心X坐标
    private int mCenterX;
    //中心Y坐标
    private int mCenterY;
    //各维度标题
    private String[] mTextArray = {"履约能力", "信用历史", "人脉关系", "行为偏好", "身份特质"};
    //各维度分值
    private float[] mValueArray = {8.5f, 8.5f, 8.8f, 7.0f, 6.0f};
    //各维度数据最大值
    private float mMaxValue;
    //雷达线条颜色
    private int mLineColor;
    //雷达线条类型
    private int mLineType;
    //雷达线条粗细
    private int mLineWidth;
    //雷达图与标题的间距
    private int mRadarMargin = DensityUtils.dp2px(getContext(), 15);
    //雷达区画笔
    private Paint mMainPaint;
    //覆盖区域画笔
    private Paint mCoverPaint;
    //数值覆盖区域画笔
    private Paint mNumberRangeCoverPaint;
    //覆盖区域颜色
    private int mCoverColor;
    //覆盖区域颜色
    private int[] mNumberRangeCoverColor;
    //雷达文字画笔
    private Paint mRadarTextPaint;
    //雷达文字颜色
    private int mRadarTextColor;
    //雷达文字大小
    private int mRadarTextSize;
    //雷达数值画笔
    private Paint mRadarNumberTextPaint;
    //雷达数值文字大小
    private int mRadarNumberTextSize;
    //雷达数值文字颜色
    private int mRadarNumberTextColor;

    private float[] temValues = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    private int mAnimDuration;//动画时长

    public RadarScoreView(Context context) {
        this(context, null);
    }

    public RadarScoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RadarScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RadarScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams(context, attrs);
        initPaint();
    }

    /**
     * 设置雷达图的文本和数值文本
     */
    /**
     * @param text          雷达图的文本数据
     * @param value         雷达图的数值
     * @param gradientColor 覆盖区域的渐变颜色数值
     */
    public void setData(String[] text, float[] value, int[] gradientColor) {
        this.mTextArray = text;
        this.mValueArray = value;
        this.mNumberRangeCoverColor = gradientColor;
        postInvalidate();
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadarScoreView);
        mDataCount = ta.getInt(R.styleable.RadarScoreView_rsv_dataCount, 5);

        //初始化弧度
        mRadian = (float) (Math.PI * 2 / mDataCount);

        mMaxValue = ta.getFloat(R.styleable.RadarScoreView_rsv_maxValue, 10);
        mLineColor = ta.getColor(R.styleable.RadarScoreView_rsv_lineColor, Color.parseColor("#000000"));
        //1-Fill，2-stroke
        mLineType = ta.getInt(R.styleable.RadarScoreView_rsv_lineType, 1);
        mLineWidth = ta.getDimensionPixelSize(R.styleable.RadarScoreView_rsv_lineWidth, 2);
        mRadarTextSize = ta.getDimensionPixelSize(R.styleable.RadarScoreView_rsv_radarTextSize, 13);
        mRadarTextColor = ta.getColor(R.styleable.RadarScoreView_rsv_radarTextColor, Color.parseColor("#000000"));
        mRadarNumberTextSize = ta.getDimensionPixelSize(R.styleable.RadarScoreView_rsv_radarNumberTextSize, 13);
        mRadarNumberTextColor = ta.getColor(R.styleable.RadarScoreView_rsv_radarNumberTextColor, Color.parseColor("#000000"));
        mCoverColor = ta.getColor(R.styleable.RadarScoreView_rsv_radarCoverColor, Color.parseColor("#000000"));
        //默认1s
        mAnimDuration = ta.getInt(R.styleable.RadarScoreView_rsv_animDuration, 1000);
        ta.recycle();
    }

    private void initPaint() {
        mMainPaint = new Paint();
        mMainPaint.setAntiAlias(true);
        mMainPaint.setStrokeWidth(mLineWidth);
        mMainPaint.setColor(mLineColor);
        mMainPaint.setStyle(Paint.Style.STROKE);

        mCoverPaint = new Paint();
        mCoverPaint.setAntiAlias(true);
        mCoverPaint.setColor(mCoverColor);
        mCoverPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mNumberRangeCoverPaint = new Paint();
        mNumberRangeCoverPaint.setAlpha(150);
        mNumberRangeCoverPaint.setAntiAlias(true);
        mNumberRangeCoverPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mRadarTextPaint = new Paint();
        mRadarTextPaint.setAntiAlias(true);
        mRadarTextPaint.setTextSize(mRadarTextSize);
        mRadarTextPaint.setColor(mRadarTextColor);
        mRadarTextPaint.setStyle(Paint.Style.FILL);

        mRadarNumberTextPaint = new Paint();
        mRadarNumberTextPaint.setAntiAlias(true);
        mRadarNumberTextPaint.setTextSize(mRadarNumberTextSize);
        mRadarNumberTextPaint.setColor(mRadarNumberTextColor);
        mRadarNumberTextPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //雷达图半径
        mRadius = Math.min(h, w) / 2 * 0.5f;
        //中心坐标
        mCenterX = w / 2;
        mCenterY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //绘制多边形
        drawPolygon(canvas);
        //绘制连接线
        drawLines(canvas);
        //绘制覆盖区域
        drawRegion(canvas);
        //绘制文本
        drawText(canvas);
    }

    /**
     * 绘制多边形
     *
     * @param canvas 画布
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        if (mLineType == 2) {
            path.addCircle(0, 0, 2, Path.Direction.CW);
            mMainPaint.setPathEffect(new PathDashPathEffect(path, 15, 0, PathDashPathEffect.Style.ROTATE));
        }
        for (int i = 0; i < mDataCount; i++) {
            if (i == 0) {
                path.moveTo(getPoint(i).x, getPoint(i).y);
            } else {
                path.lineTo(getPoint(i).x, getPoint(i).y);
            }
        }

        //闭合路径
        path.close();
        canvas.drawPath(path, mMainPaint);
        canvas.drawPath(path, mCoverPaint);
    }

    /**
     * 绘制连接线
     *
     * @param canvas 画布
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        if (mLineType == 2) {
            path.addCircle(0, 0, 2, Path.Direction.CW);
            mMainPaint.setPathEffect(new PathDashPathEffect(path, 15, 0, PathDashPathEffect.Style.ROTATE));
        }
        for (int i = 0; i < mDataCount; i++) {
            path.reset();
            path.moveTo(mCenterX, mCenterY);
            path.lineTo(getPoint(i).x, getPoint(i).y);
            canvas.drawPath(path, mMainPaint);
        }
    }

    /**
     * 绘制覆盖区域
     *
     * @param canvas 画布
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();

        for (int i = 0; i < mDataCount; i++) {
            //计算百分比
            float percent = temValues[i] / mMaxValue;
            int x = getPoint(i, 0, percent).x;
            int y = getPoint(i, 0, percent).y;

            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }
        if (mNumberRangeCoverColor != null) {
            RadialGradient radialGradient = new RadialGradient(mCenterX, mCenterY, mRadius,
                    mNumberRangeCoverColor,
                    null,
                    Shader.TileMode.CLAMP);

            mNumberRangeCoverPaint.setShader(radialGradient);
        }

        //绘制填充区域的边界
        path.close();
        mCoverPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, mNumberRangeCoverPaint);

        //绘制填充区域
        mCoverPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, mNumberRangeCoverPaint);
    }

    /**
     * 绘制标题
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        for (int i = 0; i < mDataCount; i++) {
            int x = getPoint(i, mRadarMargin, 1).x;
            int y = getPoint(i, mRadarMargin, 1).y;
            //用于增加底部2个位置的高度
            int bottomHeight = 100;
            float titleWidth = mRadarTextPaint.measureText(mTextArray[i]);
            float pointWidth = mRadarTextPaint.measureText(String.valueOf(mValueArray[i]));
            float pointHeight = getTextHeight(mRadarTextPaint);

            //底下两个角的坐标需要向下移动bottomHeight/2的位置（1、2）
            if (i == 1) {
                y += (bottomHeight / 2);
            } else if (i == 2) {
                x -= titleWidth;
                y += (bottomHeight / 2);
            } else if (i == 3) {
                x -= titleWidth;
            } else if (i == 4) {
                x -= titleWidth / 2;
                y -= pointHeight;
            }
            canvas.drawText(mTextArray[i], x, y, mRadarTextPaint);

            canvas.drawText(String.valueOf(mValueArray[i]), x + titleWidth / 3, y + pointHeight, mRadarNumberTextPaint);

        }
    }

    /**
     * 获取雷达图上各个点的坐标
     *
     * @param position 坐标位置（右上角为0，顺时针递增）
     * @return 坐标
     */
    private Point getPoint(int position) {
        return getPoint(position, 0, 1);
    }

    /**
     * 获取雷达图上各个点的坐标（包括维度标题与图标的坐标）
     *
     * @param position    坐标位置
     * @param radarMargin 雷达图与维度标题的间距
     * @param percent     覆盖区的的百分比
     * @return 坐标
     */
    private Point getPoint(int position, int radarMargin, float percent) {
        int x = 0;
        int y = 0;

        if (position == 0) {
            x = (int) (mCenterX + (mRadius + radarMargin) * Math.sin(mRadian) * percent);
            y = (int) (mCenterY - (mRadius + radarMargin) * Math.cos(mRadian) * percent);

        } else if (position == 1) {
            x = (int) (mCenterX + (mRadius + radarMargin) * Math.sin(mRadian / 2) * percent);
            y = (int) (mCenterY + (mRadius + radarMargin) * Math.cos(mRadian / 2) * percent);

        } else if (position == 2) {
            x = (int) (mCenterX - (mRadius + radarMargin) * Math.sin(mRadian / 2) * percent);
            y = (int) (mCenterY + (mRadius + radarMargin) * Math.cos(mRadian / 2) * percent);

        } else if (position == 3) {
            x = (int) (mCenterX - (mRadius + radarMargin) * Math.sin(mRadian) * percent);
            y = (int) (mCenterY - (mRadius + radarMargin) * Math.cos(mRadian) * percent);

        } else if (position == 4) {
            x = mCenterX;
            y = (int) (mCenterY - (mRadius + radarMargin) * percent);
        }

        return new Point(x, y);
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
     * 开启动画扩散
     */
    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mAnimDuration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();

                for (int i = 0; i < mDataCount; i++) {
                    temValues[i] = mValueArray[i] * value / mAnimDuration;
                }
                Log.d("Radar", Arrays.toString(temValues));
                postInvalidate();
            }
        });
        animator.setDuration(mAnimDuration);
        animator.start();
    }
}
