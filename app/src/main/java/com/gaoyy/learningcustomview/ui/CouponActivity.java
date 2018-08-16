package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.CouponView;

public class CouponActivity extends AppCompatActivity
{
    private CouponView view;
    private Button btn;

    private void assignViews() {
        view = (CouponView) findViewById(R.id.view);
        btn = (Button) findViewById(R.id.btn);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        assignViews();
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                view.setMode(2);
            }
        });

    }
}
