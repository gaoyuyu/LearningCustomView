package com.gaoyy.learningcustomview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.gaoyy.learningcustomview.R;

/**
 * Created by gaoyy on 2017/2/19.
 */

public class AnimItem extends View
{
    private static final String TAG = AnimItem.class.getSimpleName();
    private Paint mPaint;
    private Bitmap icon;

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
        icon = getBitmapFromVectorDrawable(context, R.drawable.ic_camera_black_24dp);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
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
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "padding bottom-->" + getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        int w = icon.getWidth();
        int h = icon.getHeight();
        Log.i(TAG, "w-->" + w);
        Log.i(TAG, "h-->" + h);
        icon = Bitmap.createScaledBitmap(icon,100,100,false);
        icon = ((BitmapDrawable)getResources().getDrawable(R.mipmap.ic_flower)).getBitmap();
        canvas.drawBitmap(icon, 0, 0, mPaint);
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
