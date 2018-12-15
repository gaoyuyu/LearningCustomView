package com.gaoyy.learningcustomview.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gaoyy.learningcustomview.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class NumberTrackViewAllInOne extends View
{
    private int mWidth;
    private int mHeight;


    private int mPathCount = 5;
    private Paint circlePaint;
    private List<PathPoint> circlePathPointList = new ArrayList<>();
    private List<Paint> textPaintList = new ArrayList<>();
    private List<Path> pathList = new ArrayList<>();
    private List<PathMeasure> pathMeasureList = new ArrayList<>();
    private List<PathPoint> pathPointList = new ArrayList<>();


    private Bitmap srcBitmap = null;


    private static String TAG = NumberTrackViewAllInOne.class.getSimpleName();

    public NumberTrackViewAllInOne(Context context)
    {
        this(context, null);
    }

    public NumberTrackViewAllInOne(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public NumberTrackViewAllInOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NumberTrackViewAllInOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams(context, attrs);
        initPaint();
    }

    private void initParams(Context context, AttributeSet attrs)
    {
        //初始化文本的画笔
        if (textPaintList.size() == 0)
        {
            for (int i = 0; i < mPathCount; i++)
            {
                Paint textPaint = new Paint();
                textPaint.setAntiAlias(true);
                textPaint.setColor(Color.BLACK);
                textPaint.setTextSize(120);
                textPaintList.add(textPaint);
            }
        }

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.YELLOW);
        circlePaint.setStrokeWidth(5f);
        circlePaint.setStyle(Paint.Style.STROKE);


    }

    private void initPaint()
    {
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;

        if (srcBitmap == null)
        {
            srcBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        }
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);

        //画圆
        drawCirclePath(canvas);

        //
        drawPath(canvas);
        //取交集，和上层
        circlePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));

        //遮罩层bitmap
        Bitmap mMaskBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.img_crystal_heart)).getBitmap();
        //bitmap缩放到整个view的大小
        mMaskBitmap = Bitmap.createScaledBitmap(mMaskBitmap, mWidth, mHeight, false);

        canvas.drawBitmap(mMaskBitmap, 0, 0, circlePaint);
        circlePaint.setXfermode(null);
        canvas.restore();


        //遮罩层bitmap
        Bitmap back = ((BitmapDrawable) getResources().getDrawable(R.mipmap.img_crystal_heart)).getBitmap();
        //bitmap缩放到整个view的大小
        back = Bitmap.createScaledBitmap(back, mWidth, mHeight, false);

        canvas.drawBitmap(back, 0, 0, circlePaint);


    }

    private void drawCirclePath(Canvas canvas)
    {
        circlePathPointList.clear();
        Path circlePath = new Path();


        circlePath.addCircle(mWidth / 2, mHeight / 2, mWidth / 2, Path.Direction.CW);
        canvas.drawPath(circlePath, circlePaint);

        PathMeasure pathMeasure = new PathMeasure(circlePath, false);

        for (int i = 0; i < pathMeasure.getLength(); i++)
        {
            float[] pos = new float[2];
            float[] tan = new float[2];
            pathMeasure.getPosTan(i, pos, tan);
            circlePathPointList.add(new PathPoint(pos[0], pos[1]));
            pos = null;
            tan = null;
        }
    }

    private void drawPath(Canvas canvas)
    {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);

        if (pathList.size() == 0)
        {
            for (int i = 0; i < mPathCount; i++)
            {
                Path path = new Path();
                path.moveTo(getRandomPointInCircle().getX(), getRandomPointInCircle().getY());

                for (int k = 0; k < 5; k++)
                {
                    path.lineTo(getRandomPointInCircle().getX(), getRandomPointInCircle().getY());
                }

                canvas.drawPath(path, p);

                //初始化path数据
                pathList.add(path);
                //初始化PathMeasure数据
                PathMeasure pathMeasure = new PathMeasure(path, false);
                pathMeasureList.add(pathMeasure);
                //构建每一条path上长度为0的时候的初始点

                float[] pos = new float[2];
                float[] tan = new float[2];
                pathMeasure.getPosTan(0, pos, tan);
                pathPointList.add(new PathPoint(pos[0], pos[1]));
                pos = null;
                tan = null;
            }

            for (int i = 0; i < mPathCount; i++)
            {
                canvas.drawText(i + "", pathPointList.get(i).getX(), pathPointList.get(i).getY(), textPaintList.get(i));
            }
        }
        else
        {
            for (int i = 0; i < mPathCount; i++)
            {
                canvas.drawPath(pathList.get(i), p);
            }

            for (int i = 0; i < mPathCount; i++)
            {
                canvas.drawText(i + "", pathPointList.get(i).getX(), pathPointList.get(i).getY(), textPaintList.get(i));
            }
        }
    }


    /**
     * 随机获取在圆内的点
     *
     * @return
     */
    private PathPoint getRandomPointInCircle()
    {
        int x = getRandomXPoint();
        ArrayList<Float> tmpY = new ArrayList<>();
        for (int i = 0; i < circlePathPointList.size(); i++)
        {
            PathPoint pathPoint = circlePathPointList.get(i);
            if (((int) pathPoint.getX()) == x)
            {
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
    private int getRandomXPoint()
    {
        return new Random().nextInt(mWidth - 80) + 80;
    }


    /**
     * 获取[min,max]之间的随机时
     *
     * @param max
     * @param min
     * @return
     */
    private int getRandomYPoint(int max, int min)
    {
        return new Random().nextInt(max - min) + min;
    }


    public void startAnim()
    {
        for (int i = 0; i < mPathCount; i++)
        {
            final PathMeasure pathMeasure = pathMeasureList.get(i);
            ValueAnimator pathAnimator = ValueAnimator.ofFloat(1, pathMeasure.getLength());
            final int finalI = i;
            pathAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator)
                {
                    float currentPathLength = (float) valueAnimator.getAnimatedValue();
                    float[] pos = new float[2];
                    float[] tan = new float[2];
                    pathMeasure.getPosTan(currentPathLength, pos, tan);
                    pathPointList.add(finalI, new PathPoint(pos[0], pos[1]));
                    pos = null;
                    tan = null;
                    invalidate();

                }
            });
            pathAnimator.setRepeatCount(ValueAnimator.INFINITE);
            pathAnimator.setRepeatMode(ValueAnimator.REVERSE);
            pathAnimator.setDuration(3000);
            pathAnimator.start();


            ValueAnimator alphaAnimator = ValueAnimator.ofInt(40, 255);
            alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator)
                {
                    int currentAlpha = (int) valueAnimator.getAnimatedValue();

                    Paint textPaint = textPaintList.get(finalI);
                    textPaintList.remove(finalI);
                    textPaint.setAlpha(currentAlpha);
                    textPaint.setTextSize(currentAlpha);
                    textPaintList.add(finalI, textPaint);
                    invalidate();

                }
            });
            alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);
            alphaAnimator.setDuration(800);
            alphaAnimator.start();
        }
    }


    /**
     * 获取文本的高度
     *
     * @param paint 文本绘制的画笔
     * @return 文本高度
     */
    private int getTextHeight(Paint paint)
    {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (int) (fontMetrics.descent - fontMetrics.ascent);
    }

}

class PathPoint
{
    private float x;
    private float y;

    public PathPoint(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }
}
