package com.gaoyy.learningcustomview.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyy on 2017/2/17.
 */

public class FlowLayout extends ViewGroup
{
    private static String TAG = FlowLayout.class.getSimpleName();


    public FlowLayout(Context context)
    {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs)
    {
        this(context, attrs, -1);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int childCount = getChildCount();
        Log.i(TAG, "childCount--->" + childCount);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "sizeWidth-->" + sizeWidth);
        Log.i(TAG, "sizeHeight-->" + sizeHeight);


        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        for (int i = 0; i < childCount; i++)
        {
            View childView = getChildAt(i);

            /**
             * 必须先调用measureChild()方法，childView的getMeasuredWidth(),getMeasuredHeight才能有值
             */
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams childMargin = (MarginLayoutParams) childView.getLayoutParams();

            //子view的宽
            int childWidth = childView.getMeasuredWidth() + childMargin.leftMargin + childMargin.rightMargin;
            //子view的高
            int childHeight = childView.getMeasuredHeight() + childMargin.topMargin + childMargin.bottomMargin;

            Log.i(TAG, "childWidth--->" + childWidth);
            Log.i(TAG, "childHeight--->" + childHeight);
            if (lineWidth + childWidth < sizeWidth - getPaddingLeft() - getPaddingRight())
            {
                lineWidth = lineWidth + childWidth;

                //取高度的最大值
                lineHeight = Math.max(lineHeight, childHeight);
            }
            else if (lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight())
            {
                //换行
                width = Math.max(lineWidth, childWidth);
                //重置宽度
                lineWidth = childWidth;

                height = height + lineHeight;

                lineHeight = childHeight;
            }

            if (i == childCount - 1)
            {
                width = Math.max(width, lineWidth);
                height = height + lineHeight;
            }

            Log.i(TAG, "width-->" + width);
            Log.i(TAG, "height-->" + height);


            setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                    (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height + getPaddingTop() + getPaddingBottom());

        }
    }

    private List<List<View>> mAllViews = new ArrayList<>();
    private List<Integer> mLineHeight = new ArrayList<>();


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();

        int childCount = getChildCount();

        for (int i = 0; i < childCount; i++)
        {
            View childView = getChildAt(i);


            MarginLayoutParams childMargin = (MarginLayoutParams) childView.getLayoutParams();

            int childWidth = childView.getMeasuredWidth() + childMargin.leftMargin + childMargin.rightMargin;
            int childHeight = childView.getMeasuredHeight() + childMargin.topMargin + childMargin.bottomMargin;

            if (lineWidth + childWidth > width - getPaddingLeft() - getPaddingRight())
            {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);

                lineWidth = 0;
                lineHeight = childHeight;

                lineViews = new ArrayList<>();

            }


            lineWidth = lineWidth + childWidth;
            lineHeight = Math.max(lineHeight, childHeight);
            lineViews.add(childView);


        }

        //最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);


        int left = getPaddingLeft();
        int top = getPaddingTop();

        //总行数
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++)
        {
            //每一行的总view
            lineViews = mAllViews.get(i);

            lineHeight = mLineHeight.get(i);
            Log.e(TAG, "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
            Log.e(TAG, "第" + i + "行， ：" + lineHeight);

            for (int j = 0; j < lineViews.size(); j++)
            {
                View child = lineViews.get(j);

                if (child.getVisibility() == View.GONE)
                {
                    continue;
                }
                MarginLayoutParams childMargin = (MarginLayoutParams) child.getLayoutParams();


                int lc = left + childMargin.leftMargin;
                int tc = top + childMargin.rightMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left = left + child.getMeasuredWidth() + childMargin.leftMargin + childMargin.rightMargin;
            }
            left = getPaddingLeft();
            top = top + lineHeight;

        }


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
