package com.gaoyy.learningcustomview.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by gaoyy on 2017/2/21.
 */

public class AnimItemV extends ViewGroup implements View.OnClickListener
{

    private ImageView icon;
    private TextView text;

    private int x;
    private int y;

    //1-icon,2-icon+text
    private int status = 1;

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
        setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimItemV(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setTag(1);
        int childCount = getChildCount();
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


            width = Math.max(width, childWidth);
            height += childHeight;


        }


        width = Math.max(width, height);
        height = Math.max(width, height);


        //sizeWidth = width, sizeHeight = height是为了应对weight的情况
        sizeWidth = width;
        sizeHeight = height;

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                (heightMode == MeasureSpec.EXACTLY) ? sizeHeight : height + getPaddingTop() + getPaddingBottom());


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int width = getWidth();
        int height = getHeight();


        icon = (ImageView) getChildAt(0);
        MarginLayoutParams iconMargin = (MarginLayoutParams) icon.getLayoutParams();
        int iconWidth = icon.getMeasuredWidth() + iconMargin.leftMargin + iconMargin.rightMargin;
        int iconHeight = icon.getMeasuredHeight() + iconMargin.topMargin + iconMargin.bottomMargin;


        text = (TextView) getChildAt(1);
        text.setAlpha(0.0f);
        MarginLayoutParams textMargin = (MarginLayoutParams) text.getLayoutParams();
        int textWidth = text.getMeasuredWidth() + textMargin.leftMargin + textMargin.rightMargin;
        int textHeight = text.getMeasuredHeight() + textMargin.topMargin + textMargin.bottomMargin;


        int lc = (width - iconWidth) / 2;
        int tc = (width - iconWidth) / 2;
        int rc = lc + iconWidth;
        int bc = tc + iconHeight;

        icon.layout(lc, tc, rc, bc);


        int lc1 = (width - textWidth) / 2;
        int tc1 = bc;
        int rc1 = lc1 + textWidth;
        int bc1 = tc1 + textHeight;


        /**
         * -tc字体全部显示
         */
//            text.layout(lc1,tc1-tc,rc1,bc1-tc);
        text.layout(lc1, tc1, rc1, bc1);
    }


    /**
     * icon向上移动，text向上渐显动画
     */
    public void startAnim()
    {
        MarginLayoutParams iconMargin = (MarginLayoutParams) icon.getLayoutParams();
        int iconWidth = icon.getMeasuredWidth() + iconMargin.leftMargin + iconMargin.rightMargin;
        int tc = (getWidth() - iconWidth) / 2;

        //icon Y轴向上移动tc
        ObjectAnimator iconYAnim = ObjectAnimator.ofFloat(icon, "TranslationY", -tc).setDuration(500);

        //text透明度从0变到1
        ObjectAnimator textAlphaAnim = ObjectAnimator.ofFloat(text, "Alpha", text.getAlpha(), 1.0f).setDuration(500);
        //text Y轴向上移动tc
        ObjectAnimator textYAnim = ObjectAnimator.ofFloat(text, "TranslationY", -tc).setDuration(500);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(iconYAnim, textAlphaAnim, textYAnim);
        animatorSet.start();

        setTag(2);


    }

    /**
     * 复原动画
     */
    public void reverseAnim()
    {
        MarginLayoutParams iconMargin = (MarginLayoutParams) icon.getLayoutParams();
        int iconWidth = icon.getMeasuredWidth() + iconMargin.leftMargin + iconMargin.rightMargin;
        int tc = (getWidth() - iconWidth) / 2;

        /**
         *注意这里是0，因为是-tc+tc，所以是0
         */
        //icon Y轴向下移动0
        ObjectAnimator iconYAnim = ObjectAnimator.ofFloat(icon, "TranslationY", 0).setDuration(500);

        //text透明度从1变到0
        ObjectAnimator textAlphaAnim = ObjectAnimator.ofFloat(text, "Alpha", text.getAlpha(), 0.0f).setDuration(500);
        //text Y轴向下移动0
        ObjectAnimator textYAnim = ObjectAnimator.ofFloat(text, "TranslationY", 0).setDuration(500);


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(iconYAnim, textAlphaAnim, textYAnim);
        animatorSet.start();

        setTag(1);

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

    @Override
    public void onClick(View view)
    {
        if ((int) getTag() == 1)
        {
            startAnim();
        }
        else if ((int) getTag() == 2)
        {
            reverseAnim();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Log.e(TAG, TAG + " dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        Log.e(TAG, TAG + " onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.e(TAG, TAG + " onTouchEvent");
        return super.onTouchEvent(event);
    }

}
