package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gaoyy.learningcustomview.R;

public class LoveWaveActivity extends AppCompatActivity {

    private LoveWaveView loveWaveView;
    private Button start;
    private Button stop;
    private Button set;

    private void assignViews() {
        loveWaveView = (LoveWaveView) findViewById(R.id.love_wave_view);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        set = (Button) findViewById(R.id.set);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_wave);
        assignViews();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loveWaveView.startAnim();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loveWaveView.stopAnim();
            }
        });


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loveWaveView.setMaxValue(5);
                loveWaveView.setCurrentProgress(4);
                loveWaveView.setCenterText("4.0");
            }
        });


    }
}
