package com.gaoyy.learningcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gaoyy.learningcustomview.ui.CircleStatisticalActivity;
import com.gaoyy.learningcustomview.ui.CustomTitleViewActivity;
import com.gaoyy.learningcustomview.ui.FlowActivity;
import com.gaoyy.learningcustomview.ui.WaveActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button ctv;
    private Button csv;
    private Button wave;
    private Button viewgroup;

    private void assignViews() {
        ctv = (Button) findViewById(R.id.ctv);
        csv = (Button) findViewById(R.id.csv);
        wave = (Button) findViewById(R.id.wave);
        viewgroup = (Button) findViewById(R.id.viewgroup);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        setListener();
    }

    private void setListener()
    {
        ctv.setOnClickListener(this);
        csv.setOnClickListener(this);
        wave.setOnClickListener(this);
        viewgroup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        Intent intent = new Intent();
        switch (id)
        {
            case R.id.ctv:
                intent.setClass(MainActivity.this, CustomTitleViewActivity.class);
                break;
            case R.id.csv:
                intent.setClass(MainActivity.this, CircleStatisticalActivity.class);
                break;
            case R.id.wave:
                intent.setClass(MainActivity.this, WaveActivity.class);
            case R.id.viewgroup:
                intent.setClass(MainActivity.this, FlowActivity.class);
                break;
        }
        startActivity(intent);
    }
}
