package com.gaoyy.learningcustomview.view.magic;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

import com.gaoyy.learningcustomview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MagicBallView extends View {

    public interface OnAnimListener {
        void onStart();

        void onStop();
    }

    private OnAnimListener onAnimListener = null;

    public void setOnAnimListener(OnAnimListener onAnimListener) {
        this.onAnimListener = onAnimListener;
    }

    private final static String TAG = MagicBallView.class.getSimpleName();

    private int mWidth;
    private int mHeight;

    private PorterDuffXfermode mPorterDuffXfermode = null;
    //路径的数量
    private int mPathCount = 10;
    //最外圈圆的画笔
    private Paint mCirclePaint;
    //最外圈圆的路径上的所有的点坐标
    private List<PathPoint> mCirclePathPointList = new ArrayList<>();
    //路径上的数字文本的画笔
    private List<Paint> mTextPaintList = new ArrayList<>();
    //路径的集合
    private List<Path> mPathList = new ArrayList<>();
    //路径PathMeasure的集合
    private List<PathMeasure> mPathMeasureList = new ArrayList<>();
    //路径上的点的集合
    private List<PathPoint> mPathPointList = new ArrayList<>();
    //路径上的数字文本的颜色
    private int[] mTextColorData = {
            Color.parseColor("#FFD500F9"),
            Color.parseColor("#FFD500F9"),
            Color.parseColor("#FF0FF73E"),
            Color.parseColor("#FFED2706"),
            Color.parseColor("#FF008577"),
            Color.parseColor("#FFEF5350"),
            Color.parseColor("#FF00574B"),
            Color.parseColor("#FF03A9F4"),
            Color.parseColor("#FF1DE9B6"),
            Color.parseColor("#FFEC407A"),
    };

    //路径上的数字文本的图片
    private int[] mTextImgData = {
            R.mipmap.zero,
            R.mipmap.one,
            R.mipmap.two,
            R.mipmap.three,
            R.mipmap.four,
            R.mipmap.five,
            R.mipmap.six,
            R.mipmap.seven,
            R.mipmap.eight,
            R.mipmap.nine,
    };

    //数字图片bitmap集合
    private List<Bitmap> mTextBitmapList = new ArrayList<>();

    //数字图片bitmap集合
    private List<Integer> mTextImgDegreeList = new ArrayList<>();


    //路径上的数字文本的图片的缩放范围数据
    private TextImgScaleRange[] mTextImgScaleRangeData = {
            new TextImgScaleRange(1.8f, 2f),
            new TextImgScaleRange(1.7f, 2f),
            new TextImgScaleRange(1.6f, 2f),
            new TextImgScaleRange(1.75f, 2f),
            new TextImgScaleRange(1.75f, 2f),
            new TextImgScaleRange(1.85f, 2f),
            new TextImgScaleRange(1.9f, 2f),
            new TextImgScaleRange(1.77f, 2f),
            new TextImgScaleRange(1.86f, 2f),
            new TextImgScaleRange(1.85f, 2f),
    };

    //路径上的数字文本的透明度范围的集合
    private AlphaRange[] mTextAlphaRangeData = {
            new AlphaRange(245, 255),
            new AlphaRange(203, 255),
            new AlphaRange(230, 255),
            new AlphaRange(220, 255),
            new AlphaRange(210, 255),
            new AlphaRange(200, 255),
            new AlphaRange(250, 255),
            new AlphaRange(180, 255),
            new AlphaRange(190, 255),
            new AlphaRange(195, 255),
    };
    //路径上的数字文本的大小变化范围的集合
    private TextSizeRange[] mTextSizeRangeData = {
            new TextSizeRange(100, 220),
            new TextSizeRange(100, 220),
            new TextSizeRange(190, 100),
            new TextSizeRange(90, 150),
            new TextSizeRange(80, 75),
            new TextSizeRange(180, 125),
            new TextSizeRange(150, 88),
            new TextSizeRange(60, 90),
            new TextSizeRange(220, 90),
            new TextSizeRange(79, 120),
    };

    //路径上的数字文本的大小变化范围的集合
    private TextImgDegreeRange[] mTextImgDegreeRangeData = {
            new TextImgDegreeRange(-50, 10),
            new TextImgDegreeRange(-40, 30),
            new TextImgDegreeRange(-45, 55),
            new TextImgDegreeRange(-60, 60),
            new TextImgDegreeRange(-30, 10),
            new TextImgDegreeRange(-20, 30),
            new TextImgDegreeRange(-5, 10),
            new TextImgDegreeRange(-10, 9),
            new TextImgDegreeRange(-20, 10),
            new TextImgDegreeRange(-30, 70),
    };


    //路径上的数字文本属性变化时间的集合
    private TextAnimDuration[] mTextAnimDurationData = {
            new TextAnimDuration(6500, 8500, 3000),
            new TextAnimDuration(6500, 8500, 3000),
            new TextAnimDuration(7500, 9500, 4000),
            new TextAnimDuration(7500, 9500, 5000),
            new TextAnimDuration(13000, 17500, 6000),
            new TextAnimDuration(8900, 14400, 3500),
            new TextAnimDuration(8888, 18000, 6500),
            new TextAnimDuration(5555, 10000, 7500),
            new TextAnimDuration(7777, 18000, 6300),
            new TextAnimDuration(10000, 18000, 4400),
    };
    //路径上的数字图片的当前缩放值
    private List<Float> mTextImgScaleList = new ArrayList<>();
    //圆形色块的数量
    private int mColorCircleCount = 6;
    //圆形色块的颜色
    private int[] mColorCircleColorData = {
            Color.parseColor("#147cbc"),
            Color.parseColor("#a3b29c"),
            Color.parseColor("#ea217a"),
            Color.parseColor("#ffdf00"),
            Color.parseColor("#7f37cc"),
            Color.parseColor("#09f919"),
            Color.parseColor("#74d6c2"),
            Color.parseColor("#2e99b8"),
            Color.parseColor("#ff1b0c"),
    };
    private List<Path> mColorCirclePathList = new ArrayList<>();
    private List<Paint> mColorCirclePaintList = new ArrayList<>();
    private List<PathMeasure> mColorCirclePathMeasureList = new ArrayList<>();
    private List<PathPoint> mColorCirclePathPointList = new ArrayList<>();

    //路径上的数字文本动画集合
    private List<Animator> mTextAnimSetList = new ArrayList<>();
    private AnimatorSet mTextAnimatorSet = null;
    //背景色块的动画集合
    private List<Animator> mColorCircleAnimSetList = new ArrayList<>();
    private AnimatorSet mColorCircleAnimatorSet = null;

    //所有动画的集合（路径+背景色块）
    private AnimatorSet mAllAnimSet = null;


    //遮罩层bitmap
    private Bitmap mMaskBitmap = null;
    //前景层Bitmap
    private Bitmap mFontBitmap = null;

    public MagicBallView(Context context) {
        this(context, null);
    }

    public MagicBallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MagicBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MagicBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams(context, attrs);
        initPaint();
    }

    private void initParams(Context context, AttributeSet attrs) {

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.YELLOW);
        mCirclePaint.setStrokeWidth(5f);
        mCirclePaint.setStyle(Paint.Style.STROKE);


        //初始化文本的画笔
        if (mTextPaintList.size() == 0) {
            for (int i = 0; i < mPathCount; i++) {
                Paint textPaint = new Paint();
                Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
                textPaint.setTypeface(font);
                textPaint.setAntiAlias(true);
                textPaint.setColor(mTextColorData[i]);
                textPaint.setTextSize(mTextSizeRangeData[i].getStartTextSize());
                textPaint.setAlpha(0);
                mTextPaintList.add(textPaint);
            }
        }

        //初始化当前的数字图片缩放值
        if (mTextImgScaleList.size() == 0) {
            for (int i = 0; i < mPathCount; i++) {
                mTextImgScaleList.add(mTextImgScaleRangeData[i].getStartImgScale());
            }
        }

        //初始化bitmap
        if (mTextBitmapList.size() == 0) {
            for (int i = 0; i < mPathCount; i++) {
                Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(mTextImgData[i])).getBitmap();
                float bitmapScaleWidth = bitmap.getWidth() * 1.3f;
                float bitmapScaleHeight = bitmap.getHeight() * 1.3f;
                bitmap = Bitmap.createScaledBitmap(bitmap, (int) bitmapScaleWidth, (int) bitmapScaleHeight, false);
                Matrix m = new Matrix();
                //随机角度变,-40,40
                int randomDegrees = new Random().nextInt(40);
                m.postRotate(i % 2 == 0 ? 0 - randomDegrees : randomDegrees);
                Bitmap new2 = Bitmap.createBitmap(bitmap, 0, 0, (int) bitmapScaleWidth, (int) bitmapScaleHeight, m, true);
                mTextBitmapList.add(new2);
            }
        }

        //初始化bitmap的初始角度
        if (mTextImgDegreeList.size() == 0) {
            for (int i = 0; i < mPathCount; i++) {
                mTextImgDegreeList.add(mTextImgDegreeRangeData[i].getStartDegree());
            }
        }
    }

    private void initPaint() {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        BlurMaskFilter filter = new BlurMaskFilter(mWidth / 3, BlurMaskFilter.Blur.NORMAL);
        //初始化背景色块的画笔
        if (mColorCirclePaintList.size() == 0) {
            for (int i = 0; i < mColorCircleCount; i++) {
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setColor(mColorCircleColorData[i]);
                paint.setMaskFilter(filter);
                mColorCirclePaintList.add(paint);
            }
        }

        if (mMaskBitmap == null) {
            //遮罩层bitmap
            mMaskBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.ball)).getBitmap();
            //bitmap缩放到整个view的大小
            mMaskBitmap = Bitmap.createScaledBitmap(mMaskBitmap, mWidth, mHeight, false);
        }

        if (mFontBitmap == null) {
            //遮罩层bitmap
            mFontBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.magic_glass_ball)).getBitmap();
            //bitmap缩放到整个view的大小
            mFontBitmap = Bitmap.createScaledBitmap(mFontBitmap, mWidth, mHeight, false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
        //画出外圆
        drawCirclePath(canvas);
        //画出背景色块
        drawColorCirclePath(canvas);
        //画出白色的模糊层
        drawWhiteBlur(canvas);
        //画出数字的路径
        drawPath(canvas);

        //取交集，和上层
        mCirclePaint.setXfermode(mPorterDuffXfermode);

        canvas.drawBitmap(mMaskBitmap, 0, 0, mCirclePaint);

        mCirclePaint.setXfermode(null);
        canvas.restore();

        canvas.drawBitmap(mFontBitmap, 0, 0, mCirclePaint);

    }

    /**
     * 画出最外圈的圆
     *
     * @param canvas
     */
    private void drawCirclePath(Canvas canvas) {
        mCirclePathPointList.clear();
        Path circlePath = new Path();
        circlePath.addCircle(mWidth / 2, mHeight / 2, mWidth / 2, Path.Direction.CW);
        PathMeasure pathMeasure = new PathMeasure(circlePath, false);
        for (int i = 0; i < pathMeasure.getLength(); i++) {
            float[] pos = new float[2];
            float[] tan = new float[2];
            pathMeasure.getPosTan(i, pos, tan);
            mCirclePathPointList.add(new PathPoint(pos[0], pos[1]));
            pos = null;
            tan = null;
        }
    }

    /**
     * 画出4个圆形色块
     *
     * @param canvas
     */
    private void drawColorCirclePath(Canvas canvas) {

        drawRandomPath(mColorCirclePathList, mColorCircleCount, mColorCirclePathMeasureList, mColorCirclePathPointList);

        for (int i = 0; i < mColorCircleCount; i++) {
            canvas.drawCircle(mColorCirclePathPointList.get(i).getX(), mColorCirclePathPointList.get(i).getY(), mWidth / 3, mColorCirclePaintList.get(i));
        }

        if (mColorCircleAnimSetList.size() == 0) {
            for (int i = 0; i < mColorCircleCount; i++) {
                final PathMeasure pathMeasure = mColorCirclePathMeasureList.get(i);
                ValueAnimator circleAnimator = ValueAnimator.ofFloat(1, pathMeasure.getLength());
                final int finalI = i;
                circleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float currentPathLength = (float) valueAnimator.getAnimatedValue();
                        float[] pos = new float[2];
                        float[] tan = new float[2];
                        pathMeasure.getPosTan(currentPathLength, pos, tan);
                        mColorCirclePathPointList.remove(finalI);
                        mColorCirclePathPointList.add(finalI, new PathPoint(pos[0], pos[1]));
                        pos = null;
                        tan = null;
                        invalidate();
                    }
                });

                circleAnimator.setRepeatCount(ValueAnimator.INFINITE);
                circleAnimator.setRepeatMode(ValueAnimator.REVERSE);
                circleAnimator.setDuration(5500);
                circleAnimator.setInterpolator(new FastOutLinearInInterpolator());

                mColorCircleAnimSetList.add(circleAnimator);
            }
        }
    }

    /**
     * 画出白色的模糊层
     *
     * @param canvas
     */
    private void drawWhiteBlur(Canvas canvas) {
        Paint rectPaint = new Paint();
        BlurMaskFilter filter = new BlurMaskFilter(mWidth, BlurMaskFilter.Blur.NORMAL);
        rectPaint.setMaskFilter(filter);
        rectPaint.setColor(Color.WHITE);
        rectPaint.setAlpha(100);

        canvas.drawRect(0, 0, mWidth, mHeight, rectPaint);
    }

    /**
     * 画路径
     *
     * @param canvas
     */
    private void drawPath(Canvas canvas) {

        drawRandomPath(mPathList, mPathCount, mPathMeasureList, mPathPointList);
        for (int i = 0; i < mPathCount; i++) {
//            canvas.drawText(i + 1 + "", mPathPointList.get(i).getX(), mPathPointList.get(i).getY(), mTextPaintList.get(i));
            Bitmap bitmap = mTextBitmapList.get(i);

//            float bitmapScaleWidth = bitmap.getWidth() * mTextImgScaleList.get(i);
//            float bitmapScaleHeight = bitmap.getHeight() * mTextImgScaleList.get(i);
//            bitmap = Bitmap.createScaledBitmap(bitmap, (int) bitmapScaleWidth, (int) bitmapScaleHeight, false);
//            Matrix m = new Matrix();
//            m.postRotate(mTextImgDegreeList.get(i));
//            Bitmap new2 = Bitmap.createBitmap(bitmap, 0, 0, (int) bitmapScaleWidth, (int) bitmapScaleHeight, m, true);
//            canvas.drawBitmap(bitmap, mPathPointList.get(i).getX() - bitmapScaleWidth, mPathPointList.get(i).getY() - bitmapScaleHeight, mTextPaintList.get(i));
            canvas.drawBitmap(bitmap, mPathPointList.get(i).getX() - bitmap.getWidth(), mPathPointList.get(i).getY() - bitmap.getHeight(), mTextPaintList.get(i));

        }

        //初始化每一条路径的动画
        if (mTextAnimSetList.size() == 0) {
            for (int i = 0; i < mPathCount; i++) {
                AnimatorSet animatorSet = new AnimatorSet();
                final PathMeasure pathMeasure = mPathMeasureList.get(i);
                final TextAnimDuration textAnimDuration = mTextAnimDurationData[i];
                final AlphaRange alphaRange = mTextAlphaRangeData[i];
                final TextSizeRange textSizeRange = mTextSizeRangeData[i];
                final TextImgScaleRange textImgScaleRange = mTextImgScaleRangeData[i];
                final TextImgDegreeRange textImgDegreeRange = mTextImgDegreeRangeData[i];

                ValueAnimator pathAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
                final int finalI = i;
                pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float currentPathLength = (float) valueAnimator.getAnimatedValue();
                        float[] pos = new float[2];
                        float[] tan = new float[2];
                        pathMeasure.getPosTan(currentPathLength, pos, tan);
                        mPathPointList.remove(finalI);
                        mPathPointList.add(finalI, new PathPoint(pos[0], pos[1]));
                        pos = null;
                        tan = null;
                        invalidate();

                    }
                });
                pathAnimator.setRepeatCount(ValueAnimator.INFINITE);
                pathAnimator.setRepeatMode(ValueAnimator.REVERSE);
                pathAnimator.setDuration(textAnimDuration.getPathDuration());

                ValueAnimator alphaAnimator = ValueAnimator.ofInt(alphaRange.getStartAlpha(), alphaRange.getEndAlpha());
                alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int currentAlpha = (int) valueAnimator.getAnimatedValue();
                        Paint textPaint = mTextPaintList.get(finalI);
                        mTextPaintList.remove(finalI);
                        textPaint.setAlpha(currentAlpha);
                        mTextPaintList.add(finalI, textPaint);
                        invalidate();

                    }
                });
                alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
                alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);
                alphaAnimator.setDuration(textAnimDuration.getAlphaDuration());

                ValueAnimator textSizeAnimator = ValueAnimator.ofInt((int) textSizeRange.getStartTextSize(), (int) textSizeRange.getEndTextSize());
                textSizeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int currentTextSize = (int) valueAnimator.getAnimatedValue();
                        Paint textPaint = mTextPaintList.get(finalI);
                        mTextPaintList.remove(finalI);
                        textPaint.setTextSize(currentTextSize);
                        mTextPaintList.add(finalI, textPaint);
                        invalidate();

                    }
                });
                textSizeAnimator.setRepeatCount(ValueAnimator.INFINITE);
                textSizeAnimator.setRepeatMode(ValueAnimator.REVERSE);
                textSizeAnimator.setDuration(textAnimDuration.getSizeDuration());

                ValueAnimator textImgScaleAnimator = ValueAnimator.ofFloat(textImgScaleRange.getStartImgScale(), textImgScaleRange.getEndImgScale());
                textImgScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float currentScale = (float) valueAnimator.getAnimatedValue();
                        mTextImgScaleList.remove(finalI);
                        mTextImgScaleList.add(finalI, currentScale);
                        invalidate();
                    }
                });

                textImgScaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
                textImgScaleAnimator.setRepeatMode(ValueAnimator.REVERSE);
                textImgScaleAnimator.setDuration(textAnimDuration.getSizeDuration());

                ValueAnimator textImgDegreeAnimator = ValueAnimator.ofInt(textImgDegreeRange.getStartDegree(), textImgDegreeRange.getEndDegree());
                textImgDegreeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int currentDegree = (int) valueAnimator.getAnimatedValue();
                        mTextImgDegreeList.remove(finalI);
                        mTextImgDegreeList.add(finalI, currentDegree);
                        invalidate();
                    }
                });

                textImgDegreeAnimator.setRepeatCount(ValueAnimator.INFINITE);
                textImgDegreeAnimator.setRepeatMode(ValueAnimator.REVERSE);
                textImgDegreeAnimator.setDuration(textAnimDuration.getAlphaDuration());

                animatorSet.playTogether(pathAnimator, alphaAnimator, textSizeAnimator, textImgScaleAnimator, textImgDegreeAnimator);
                mTextAnimSetList.add(animatorSet);
            }
        }
    }

    /**
     * 绘制随机路径
     *
     * @param targetPathList        path集合
     * @param count                 路径个数
     * @param targetPathMeasureList pathMeasure集合
     * @param targetPathPointList   path上的pathPoint集合
     */
    private void drawRandomPath(List<Path> targetPathList, int count, List<PathMeasure> targetPathMeasureList, List<PathPoint> targetPathPointList) {
        if (targetPathList.size() == 0) {
            for (int i = 0; i < count; i++) {
                Path path = new Path();
                path.moveTo(getRandomPointInCircle().getX(), getRandomPointInCircle().getY());

                for (int k = 0; k < 5; k++) {
                    path.lineTo(getRandomPointInCircle().getX(), getRandomPointInCircle().getY());
                }

                //初始化path数据
                targetPathList.add(path);
                //初始化PathMeasure数据
                PathMeasure pathMeasure = new PathMeasure(path, false);
                targetPathMeasureList.add(pathMeasure);
                //构建每一条path上长度为0的时候的初始点

                float[] pos = new float[2];
                float[] tan = new float[2];
                pathMeasure.getPosTan(0, pos, tan);
                targetPathPointList.add(new PathPoint(pos[0], pos[1]));
                pos = null;
                tan = null;
            }
        }
    }


    /**
     * 随机获取在圆内的点
     *
     * @return
     */
    private PathPoint getRandomPointInCircle() {
        int x = getRandomXPoint();
        ArrayList<Float> tmpY = new ArrayList<>();
        for (int i = 0; i < mCirclePathPointList.size(); i++) {
            PathPoint pathPoint = mCirclePathPointList.get(i);
            if (((int) pathPoint.getX()) == x) {
                tmpY.add(pathPoint.getY());
            }
        }
        float max = Collections.max(tmpY);
        float min = Collections.min(tmpY);

        int y = getRandomYPoint((int) max, (int) min);

        return new PathPoint(x, y);
    }


    /**
     * 随机返回[80,mWidth]的整数
     *
     * @return
     */
    private int getRandomXPoint() {
        return new Random().nextInt(mWidth - 80) + 80;
    }


    /**
     * 获取[min,max]之间的随机时
     *
     * @param max
     * @param min
     * @return
     */
    private int getRandomYPoint(int max, int min) {
        int range = max - min;
        if (range == 0) range = mHeight;
        return new Random().nextInt(range) + min;
    }

    /**
     * 开启动画
     */
    public void startAnim() {
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> valueAnimatorList = new ArrayList<>();
        for (int i = 0; i < mPathCount; i++) {
            final AlphaRange alphaRange = mTextAlphaRangeData[i];
            final int finalI = i;
            ValueAnimator textStartAlphaAnimator = ValueAnimator.ofInt(0, alphaRange.getStartAlpha());
            textStartAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int currentAlpha = (int) valueAnimator.getAnimatedValue();
                    Paint textPaint = mTextPaintList.get(finalI);
                    mTextPaintList.remove(finalI);
                    textPaint.setAlpha(currentAlpha);
                    mTextPaintList.add(finalI, textPaint);
                    invalidate();
                }
            });
            textStartAlphaAnimator.setStartDelay(new Random().nextInt(1000));
            textStartAlphaAnimator.setDuration(1000);
            textStartAlphaAnimator.setInterpolator(new FastOutSlowInInterpolator());
            valueAnimatorList.add(textStartAlphaAnimator);
        }
        animatorSet.playTogether(valueAnimatorList);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //动画开始回调
                if (onAnimListener != null) {
                    onAnimListener.onStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //开启路径动画和背景色块动画
                startAllAnim();
            }
        });
        animatorSet.start();
    }

    /**
     * 开启路径动画和背景色块动画
     */
    private void startAllAnim() {
        if (mColorCircleAnimSetList.size() != 0) {
            if (mColorCircleAnimatorSet == null) {
                mColorCircleAnimatorSet = new AnimatorSet();
                mColorCircleAnimatorSet.playTogether(mColorCircleAnimSetList);
                mColorCircleAnimatorSet.start();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    //若背景色块动画是暂停状态的，恢复启动
                    if (mColorCircleAnimatorSet.isPaused()) mColorCircleAnimatorSet.resume();
                } else {
                    //API向下兼容的时候，直接start吧
                    mColorCircleAnimatorSet.start();
                }
            }
        }

        if (mTextAnimSetList.size() != 0) {
            mTextAnimatorSet = new AnimatorSet();
            mTextAnimatorSet.playTogether(mTextAnimSetList);
            mTextAnimatorSet.start();
        }
    }

    public void stopAnim() {
        if (mColorCircleAnimatorSet.isRunning()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //暂停背景色块的动画
                mColorCircleAnimatorSet.pause();
            } else {
                //API向下兼容的时候，直接cancel吧
                mColorCircleAnimatorSet.cancel();
            }
        }

        mTextAnimatorSet.cancel();

        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> valueAnimatorList = new ArrayList<>();
        for (int i = 0; i < mPathCount; i++) {
            final AlphaRange alphaRange = mTextAlphaRangeData[i];
            final int finalI = i;
            ValueAnimator textStartAlphaAnimator = ValueAnimator.ofInt(alphaRange.getEndAlpha(), 0);
            textStartAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int currentAlpha = (int) valueAnimator.getAnimatedValue();
                    Paint textPaint = mTextPaintList.get(finalI);
                    mTextPaintList.remove(finalI);
                    textPaint.setAlpha(currentAlpha);
                    mTextPaintList.add(finalI, textPaint);
                    invalidate();
                }
            });
            textStartAlphaAnimator.setDuration(1000);
            textStartAlphaAnimator.setInterpolator(new FastOutSlowInInterpolator());
            valueAnimatorList.add(textStartAlphaAnimator);
        }
        animatorSet.playTogether(valueAnimatorList);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //重置路径
                resetPath();
                if (onAnimListener != null) {
                    onAnimListener.onStop();
                }
            }
        });
        animatorSet.start();
    }

    /**
     * 重置路径
     */
    private void resetPath() {
        mTextImgDegreeList.clear();
        for (int i = 0; i < mPathCount; i++) {
            final PathMeasure pathMeasure = mPathMeasureList.get(i);
            float[] pos = new float[2];
            float[] tan = new float[2];
            pathMeasure.getPosTan(0, pos, tan);
            mPathPointList.remove(i);
            mPathPointList.add(i, new PathPoint(pos[0], pos[1]));
            mTextImgDegreeList.add(mTextImgDegreeRangeData[i].getStartDegree());
            pos = null;
            tan = null;
            invalidate();
        }
    }

}


