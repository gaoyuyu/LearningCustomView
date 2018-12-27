package com.gaoyy.learningcustomview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gaoyy.learningcustomview.R;


public class MaskTextView extends View {

    private int mWidth;
    private int mHeight;
    private Paint mTextPaint;
    //文本
    private String mText;
    //字体大小
    private int mTextSize;
    private Bitmap mMaskBitmap;


    private PorterDuffXfermode mPorterDuffXfermode = null;

    public MaskTextView(Context context) {
        this(context, null);
    }

    public MaskTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MaskTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(context, attrs);
        initPaint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaskTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initParams(context, attrs);
        initPaint();
    }

    private void initParams(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MaskTextView);
        //遮罩的图片
        int mMaskImgResId = ta.getResourceId(R.styleable.MaskTextView_mtv_maskImageRes, 0);
        mTextSize = ta.getDimensionPixelSize(R.styleable.MaskTextView_mtv_textSize, 13);
        mText = ta.getString(R.styleable.MaskTextView_mtv_text);
        if (mMaskImgResId == 0) {
            throw new NullPointerException("必须设置遮罩图片资源");
        }
        mMaskBitmap = ((BitmapDrawable) getResources().getDrawable(mMaskImgResId)).getBitmap();
        ta.recycle();

        mPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    }

    private void initPaint() {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mTextPaint = new Paint();
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mTextPaint.setTypeface(font);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(Color.BLACK);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mWidth = w;
        mHeight = h;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.saveLayer(0, 0, mWidth, mHeight, null, Canvas.ALL_SAVE_FLAG);
        int textHeight = getTextHeight(mTextPaint);
        canvas.drawText(mText, 0, textHeight, mTextPaint);
        //取交集，和上层
        mTextPaint.setXfermode(mPorterDuffXfermode);
        canvas.drawBitmap(mMaskBitmap, 0, 0, mTextPaint);
        mTextPaint.setXfermode(null);
        canvas.restore();
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }

    private int measureWidth(int widthMeasureSpec) {
        int result = (int) mTextPaint.measureText(mText);
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                break;
        }
        mWidth = result;
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int result = getTextHeight(mTextPaint);
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        switch (specMode) {
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            case MeasureSpec.AT_MOST:
                break;
        }
        mHeight = result;
        return result;
    }

}
