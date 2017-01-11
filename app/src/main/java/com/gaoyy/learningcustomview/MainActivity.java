package com.gaoyy.learningcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gaoyy.learningcustomview.ui.CircleStatisticalActivity;
import com.gaoyy.learningcustomview.ui.CustomTitleViewActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button ctv;
    private Button csv;

    private void assignViews() {
        ctv = (Button) findViewById(R.id.ctv);
        csv = (Button) findViewById(R.id.csv);
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
        }
        startActivity(intent);
    }
}
