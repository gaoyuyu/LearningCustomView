package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.CircleStatisticalView;

public class CircleStatisticalActivity extends AppCompatActivity
{


    private LinearLayout activityCircleStatistical;
    private CircleStatisticalView csv;

    private void assignViews() {
        activityCircleStatistical = (LinearLayout) findViewById(R.id.activity_circle_statistical);
        csv = (CircleStatisticalView) findViewById(R.id.csv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        CircleStatisticalView c = new CircleStatisticalView(this);
//        setContentView(c);
        setContentView(R.layout.activity_circle_statistical);
        assignViews();
        double[] datas = {0.3, 0.4, 0.15, 0.1, 0.05};

        csv.setDatas(datas);
        csv.animUpdate();




    }
}
