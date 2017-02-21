package com.gaoyy.learningcustomview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gaoyy on 2017/2/21.
 */

public class AnimItemV extends ViewGroup
{
    private static final String TAG = AnimItemV.class.getSimpleName();

    public AnimItemV(Context context)
    {
        this(context, null);
    }

    public AnimItemV(Context context, AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public AnimItemV(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimItemV(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int childCount = getChildCount();
        Log.i(TAG, "childCount-->" + childCount);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        /**
         * wrap_content下的width，height
         */

        int width = 0;
        int height = 0;


        for (int i = 0; i < childCount; i++)
        {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams childMargin = (MarginLayoutParams) childView.getLayoutParams();

            //子view的宽
            int childWidth = childView.getMeasuredWidth() + childMargin.leftMargin + childMargin.rightMargin;
            //子view的高
            int childHeight = childView.getMeasuredHeight() + childMargin.topMargin + childMargin.bottomMargin;

            Log.i(TAG, "childWidth--->" + childWidth);
            Log.i(TAG, "childHeight--->" + childHeight);

            width = Math.max(width, childWidth);
            height += childHeight;


        }


        width = Math.max(width, height);
        height = Math.max(width, height);
        Log.i(TAG, "onMeasure width--->" + width);
        Log.i(TAG, "onMeasure height--->" + height);

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height + getPaddingTop() + getPaddingBottom());


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int width = getWidth();
        int height = getHeight();
        Log.i(TAG, "onLayout width--->" + width);
        Log.i(TAG, "onLayout height--->" + height);


        ImageView icon = (ImageView) getChildAt(0);
        MarginLayoutParams iconMargin = (MarginLayoutParams) icon.getLayoutParams();
        int iconWidth = icon.getMeasuredWidth() + iconMargin.leftMargin + iconMargin.rightMargin;
        int iconHeight = icon.getMeasuredHeight() + iconMargin.topMargin + iconMargin.bottomMargin;




        TextView text = (TextView) getChildAt(1);
        text.setTextColor(Color.BLACK);
        MarginLayoutParams textMargin = (MarginLayoutParams) text.getLayoutParams();
        int textWidth = text.getMeasuredWidth() + textMargin.leftMargin + textMargin.rightMargin;
        int textHeight = text.getMeasuredHeight() + textMargin.topMargin + textMargin.bottomMargin;


        Log.i(TAG, "width--->" + width);
        Log.i(TAG, "height--->" + height);
        Log.i(TAG, "iconWidth--->" + iconWidth);
        Log.i(TAG, "iconHeight--->" + iconHeight);
        Log.i(TAG, "textWidth--->" + textWidth);
        Log.i(TAG, "textHeight--->" + textHeight);



//        if(width == iconWidth)
//        {
//
//        }
//        else if(width>iconWidth)
//        {

            int lc = (width-iconWidth)/2;
            int tc = (width-iconWidth)/2;
            int rc = lc+iconWidth;
            int bc = tc+iconHeight;

            Log.i(TAG,"lc-->"+lc);
            Log.i(TAG,"tc-->"+tc);
            Log.i(TAG,"rc-->"+rc);
            Log.i(TAG,"bc-->"+bc);
            icon.layout(lc, tc, rc, bc);


            int lc1 = (width-textWidth)/2;
            int tc1 = bc;
            int rc1 = lc1+textWidth;
            int bc1 = tc1+textHeight;
            Log.i(TAG,"lc1-->"+lc1);
            Log.i(TAG,"tc1-->"+tc1);
            Log.i(TAG,"rc1-->"+rc1);
            Log.i(TAG,"bc1-->"+bc1);


        /**
         * -tc字体全部显示
         */
//            text.layout(lc1,tc1-tc,rc1,bc1-tc);
            text.layout(lc1,tc1,rc1,bc1);


//        }
    }

    

    /**
     * 使用系统的MarginLayoutParams
     *
     * @param attrs
     * @return
     */
    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
