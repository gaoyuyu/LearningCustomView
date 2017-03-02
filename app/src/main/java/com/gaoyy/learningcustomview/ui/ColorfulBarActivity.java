package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.AnimItemV;
import com.gaoyy.learningcustomview.view.AnimLinearLayout;

public class ColorfulBarActivity extends AppCompatActivity
{
    private static final String TAG = ColorfulBarActivity.class.getSimpleName();
    private int[] colors  = {android.R.color.holo_blue_dark, android.R.color.holo_red_light,
            android.R.color.holo_green_light, android.R.color.holo_orange_dark, android.R.color.holo_purple};
    private AnimLinearLayout activityColorfulBar;
    private AnimItemV item1;
    private AnimItemV item2;
    private AnimItemV item3;
    private AnimItemV item4;
    private AnimItemV item5;

    private void assignViews()
    {
        activityColorfulBar = (AnimLinearLayout) findViewById(R.id.activity_colorful_bar);
        item1 = (AnimItemV) findViewById(R.id.item1);
        item2 = (AnimItemV) findViewById(R.id.item2);
        item3 = (AnimItemV) findViewById(R.id.item3);
        item4 = (AnimItemV) findViewById(R.id.item4);
        item5 = (AnimItemV) findViewById(R.id.item5);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colorful_bar01);
        assignViews();


        activityColorfulBar.setRippleColors(colors);


    }

}
