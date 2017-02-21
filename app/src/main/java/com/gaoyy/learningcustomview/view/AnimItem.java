package com.gaoyy.learningcustomview.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoyy.learningcustomview.R;


/**
 * Created by gaoyy on 2017/2/21.
 */

public class AnimItem extends LinearLayout
{
    private ImageView icon;
    private TextView text;
    private AnimatorSet animatorSet = new AnimatorSet();

    private int mIconID;
    private String mBottomText;



    public AnimItem(Context context)
    {
        this(context,null);
    }

    public AnimItem(Context context, AttributeSet attrs)
    {
        this(context, attrs,-1);
    }

    public AnimItem(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
        initParams(context,attrs);
        initChildViews(context);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initParams(context,attrs);
        initChildViews(context);
    }

    private void initParams(Context context, AttributeSet attrs)
    {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AnimItem);
        mIconID = ta.getResourceId(R.styleable.AnimItem_icon,0);
        mBottomText = ta.getString(R.styleable.AnimItem_bottomText);
        ta.recycle();
    }

    private void initChildViews(Context context)
    {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        icon  =  new ImageView(context);
        icon.setImageDrawable(getResources().getDrawable(mIconID));
        icon.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));

        text = new TextView(context);
        text.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        text.setTextSize(16);
        text.setText(mBottomText);
        text.setTextColor(getResources().getColor(R.color.colorAccent));

        addView(icon,0);
        addView(text,1);
    }


    public void startAnim()
    {
        ObjectAnimator textScaleAnim1 = ObjectAnimator
                .ofFloat(text, "ScaleX", 0.3F, 1.0F)
                .setDuration(500);
        ObjectAnimator textScaleAnim2 = ObjectAnimator
                .ofFloat(text, "ScaleY", 0.3F, 1.0F)
                .setDuration(500);
        ObjectAnimator iconScaleAnim1 = ObjectAnimator
                .ofFloat(icon, "ScaleX", 1.0F, 0.8F)
                .setDuration(500);
        ObjectAnimator iconScaleAnim2 = ObjectAnimator
                .ofFloat(icon, "ScaleY", 1.0F, 0.8F)
                .setDuration(500);

        ObjectAnimator textAlphaAnim = ObjectAnimator.ofFloat(text,"Alpha",text.getAlpha(),1.0f).setDuration(500);

        ObjectAnimator yTextAnim = ObjectAnimator.ofFloat(text,"TranslationY",-icon.getMeasuredHeight()*0.2f).setDuration(500);



        animatorSet.playTogether(textScaleAnim1,textScaleAnim2,iconScaleAnim1,iconScaleAnim2,yTextAnim,textAlphaAnim);


        animatorSet.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                text.setVisibility(View.VISIBLE);
            }
        });


        animatorSet.start();
    }

    public void reverseAnim()
    {
        ObjectAnimator textScaleAnim1 = ObjectAnimator
                .ofFloat(text, "ScaleX", text.getScaleX(), 0.0F)
                .setDuration(500);
        ObjectAnimator textScaleAnim2 = ObjectAnimator
                .ofFloat(text, "ScaleY", text.getScaleY(), 0.0F)
                .setDuration(500);
        ObjectAnimator iconScaleAnim1 = ObjectAnimator
                .ofFloat(icon, "ScaleX",  icon.getScaleX(),1.0F)
                .setDuration(500);
        ObjectAnimator iconScaleAnim2 = ObjectAnimator
                .ofFloat(icon, "ScaleY", icon.getScaleY(),1.0F)
                .setDuration(500);
        ObjectAnimator textAlphaAnim = ObjectAnimator.ofFloat(text,"Alpha",text.getAlpha(),0.0f).setDuration(500);
        ObjectAnimator yTextAnim = ObjectAnimator.ofFloat(text,"TranslationY",icon.getMeasuredHeight()*0.2f).setDuration(500);

        animatorSet.playTogether(textScaleAnim1,textScaleAnim2,iconScaleAnim1,iconScaleAnim2,textAlphaAnim,yTextAnim);
        animatorSet.start();
    }



    private void init()
    {
        //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode())
        {
            return;
        }
    }
}
