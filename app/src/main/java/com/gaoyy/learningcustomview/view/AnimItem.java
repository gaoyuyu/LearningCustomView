package com.gaoyy.learningcustomview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.gaoyy.learningcustomview.R;

/**
 * Created by gaoyy on 2017/2/19.
 */

public class AnimItem extends View implements View.OnClickListener
{
    private static final String TAG = AnimItem.class.getSimpleName();
    private Paint mPaint;

    private float textSize = 0f;

    public float getTextSize()
    {
        return textSize;
    }

    public void setTextSize(float textSize)
    {
        this.textSize = textSize;
    }

    public AnimItem(Context context)
    {
        this(context, null);
    }

    public AnimItem(Context context, AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public AnimItem(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initPaint();
        setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initPaint();
        setOnClickListener(this);
    }

    private void initPaint()
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "padding bottom-->" + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        mPaint.setColor(getResources().getColor(R.color.flower));



        Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_flower)).getBitmap();
        int iconWidth = icon.getWidth();
        int iconHeight = icon.getHeight();
        Log.i(TAG,"iconWidth-->"+iconWidth);
        Log.i(TAG,"iconHeight-->"+iconHeight);


        String text = "Friend";
        Rect textRect = new Rect();
        mPaint.getTextBounds(text,0,text.length(),textRect);

        Log.i(TAG,"text width-->"+textRect.width());
        Log.i(TAG,"text height-->"+textRect.height());



        mPaint.setTextSize(35f);
        canvas.drawText("Friend",0,iconHeight+textRect.height(),mPaint);


        canvas.drawBitmap(icon, 0, 0, mPaint);
    }

    @Override
    public void onClick(View view)
    {
        Log.e(TAG,"===onClick===");
//        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f,35f);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
//        {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator)
//            {
//                textSize = (float)valueAnimator.getAnimatedValue();
//                setTextSize(textSize);
//                invalidate();
//            }
//        });
        ScaleAnimation animation =new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.start();
    }

    /**
     * VectorDrawable转Bitmap
     * @param context
     * @param drawableId
     * @return
     */
    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId)
    {
        Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
        {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
