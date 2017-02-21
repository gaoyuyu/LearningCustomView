package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gaoyy.learningcustomview.R;

public class ColorfulBarActivity extends AppCompatActivity
{
//    private static final String TAG = ColorfulBarActivity.class.getSimpleName();
//    private LinearLayout activityColorfulBar;
//    private ImageView iconImg;
//    private TextView iconText;
//    private Button reset;
//    AnimatorSet animatorSet = new AnimatorSet();
//
//    private void assignViews() {
//        activityColorfulBar = (LinearLayout) findViewById(R.id.activity_colorful_bar);
//        iconImg = (ImageView) findViewById(R.id.icon_img);
//        iconText = (TextView) findViewById(R.id.icon_text);
//        reset = (Button) findViewById(R.id.reset);
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorful_bar01);
//        final AnimItem animItem = (AnimItem) findViewById(R.id.item);
//        Button start = (Button)findViewById(R.id.start);
//        start.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                animItem.startAnim();
//            }
//        });
//        Button reverse = (Button)findViewById(R.id.reverse);
//        reverse.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                animItem.reverseAnim();
//            }
//        });



        /*
        assignViews();


        activityColorfulBar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ObjectAnimator textScaleAnim1 = ObjectAnimator
                        .ofFloat(iconText, "ScaleX", 0.3F, 1.0F)
                        .setDuration(500);
                ObjectAnimator textScaleAnim2 = ObjectAnimator
                        .ofFloat(iconText, "ScaleY", 0.3F, 1.0F)
                        .setDuration(500);
                ObjectAnimator iconScaleAnim1 = ObjectAnimator
                        .ofFloat(iconImg, "ScaleX", 1.0F, 0.8F)
                        .setDuration(500);
                ObjectAnimator iconScaleAnim2 = ObjectAnimator
                        .ofFloat(iconImg, "ScaleY", 1.0F, 0.8F)
                        .setDuration(500);

                ObjectAnimator textAlphaAnim = ObjectAnimator.ofFloat(iconText,"Alpha",iconText.getAlpha(),1.0f).setDuration(500);

                ObjectAnimator yTextAnim = ObjectAnimator.ofFloat(iconText,"TranslationY",-iconImg.getMeasuredHeight()*0.2f).setDuration(500);



                animatorSet.playTogether(textScaleAnim1,textScaleAnim2,iconScaleAnim1,iconScaleAnim2,yTextAnim,textAlphaAnim);


                animatorSet.addListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        iconText.setVisibility(View.VISIBLE);
                    }
                });


                animatorSet.start();
            }
        });



        reset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ObjectAnimator textScaleAnim1 = ObjectAnimator
                        .ofFloat(iconText, "ScaleX", iconText.getScaleX(), 0.0F)
                        .setDuration(500);
                ObjectAnimator textScaleAnim2 = ObjectAnimator
                        .ofFloat(iconText, "ScaleY", iconText.getScaleY(), 0.0F)
                        .setDuration(500);
                ObjectAnimator iconScaleAnim1 = ObjectAnimator
                        .ofFloat(iconImg, "ScaleX",  iconImg.getScaleX(),1.0F)
                        .setDuration(500);
                ObjectAnimator iconScaleAnim2 = ObjectAnimator
                        .ofFloat(iconImg, "ScaleY", iconImg.getScaleY(),1.0F)
                        .setDuration(500);
                ObjectAnimator textAlphaAnim = ObjectAnimator.ofFloat(iconText,"Alpha",iconText.getAlpha(),0.0f).setDuration(500);
                ObjectAnimator yTextAnim = ObjectAnimator.ofFloat(iconText,"TranslationY",iconImg.getMeasuredHeight()*0.2f).setDuration(500);

                animatorSet.playTogether(textScaleAnim1,textScaleAnim2,iconScaleAnim1,iconScaleAnim2,textAlphaAnim,yTextAnim);
                animatorSet.start();

            }
        });

        */

    }
}
