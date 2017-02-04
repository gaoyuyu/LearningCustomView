package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.WaveView;

public class WaveActivity extends AppCompatActivity
{
    private LinearLayout activityWave;
    private WaveView wave;
    private Button btn;

    private void assignViews() {
        activityWave = (LinearLayout) findViewById(R.id.activity_wave);
        wave = (WaveView) findViewById(R.id.wave);
        btn = (Button) findViewById(R.id.btn);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        assignViews();
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                wave.anim();
            }
        });


//        WaveView waveView = new WaveView(this,null);
//        setContentView(waveView);
//
//        waveView.anim();
    }
}
