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
    private WaveView wave1;
    private WaveView wave2;
    private WaveView wave3;
    private WaveView wave4;
    private Button btn;

    private void assignViews() {
        activityWave = (LinearLayout) findViewById(R.id.activity_wave);
        wave1 = (WaveView) findViewById(R.id.wave1);
        wave2 = (WaveView) findViewById(R.id.wave2);
        wave3 = (WaveView) findViewById(R.id.wave3);
        wave4 = (WaveView) findViewById(R.id.wave4);
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
                wave1.anim();
                wave2.anim();
                wave3.anim();
                wave4.anim();
            }
        });


//        WaveView waveView = new WaveView(this,null);
//        setContentView(waveView);
//
//        waveView.anim();
    }
}
