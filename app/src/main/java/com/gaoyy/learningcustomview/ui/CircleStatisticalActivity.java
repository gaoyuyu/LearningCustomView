package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gaoyy.learningcustomview.view.CircleStatisticalView;

public class CircleStatisticalActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        CircleStatisticalView c = new CircleStatisticalView(this);
        setContentView(c);
//        setContentView(R.layout.activity_circle_statistical);
        double[] datas = {0.1, 0.6, 0.15, 0.12, 0.03};
        c.setDatas(datas);
        c.animUpdate();

    }
}
