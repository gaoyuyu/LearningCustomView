package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gaoyy.learningcustomview.view.WaveView;

public class WaveActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wave);


        WaveView waveView = new WaveView(this,null);
        setContentView(waveView);

        waveView.anim();
    }
}
