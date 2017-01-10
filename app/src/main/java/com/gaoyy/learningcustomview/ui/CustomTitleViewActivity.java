package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gaoyy.learningcustomview.R;

public class CustomTitleViewActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        CustomTitleView a = new CustomTitleView(this);
//        setContentView(a);

        setContentView(R.layout.activity_custom_title_view);
    }
}
