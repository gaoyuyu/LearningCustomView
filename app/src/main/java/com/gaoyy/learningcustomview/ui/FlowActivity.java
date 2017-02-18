package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.FlowLayout;

public class FlowActivity extends AppCompatActivity
{
    private LinearLayout activityFlow;
    private FlowLayout flowLayout;

    private String[] vals = {"Antilla", "Bahia Honda", "Baracoa", "Havana", "Nueva Gerona", "Antilla", "Bahia Honda","CY Cyprus",
            "CV Cape Verde", "Santa Cruz del Sur", "Antilla", "Bahia Honda","Santa Lucia", "Santiago", "Tunas de Zaza", "Media Luna"};

    private void assignViews()
    {
        activityFlow = (LinearLayout) findViewById(R.id.activity_flow);
        flowLayout = (FlowLayout) findViewById(R.id.flow_layout);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow);
        assignViews();
        initData();
    }

    private void initData()
    {

        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < vals.length; i++)
        {
//            Button btn = new Button(this);
//            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT, ViewGroup.MarginLayoutParams.WRAP_CONTENT);
//            btn.setText(vals[i]);
//            flowLayout.addView(btn, lp);
            TextView tv = (TextView) inflater.inflate(R.layout.flow_item,flowLayout,false);

            tv.setText(vals[i]);
            flowLayout.addView(tv);



        }
    }
}
