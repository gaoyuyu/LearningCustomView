package com.gaoyy.learningcustomview.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.RadarScoreView;

public class RadarActivity extends AppCompatActivity {

    private RadarScoreView radar;
    private Button btn;

    private void assignViews() {
        radar = (RadarScoreView) findViewById(R.id.radar);
        btn = (Button) findViewById(R.id.btn);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        assignViews();


        int[] colors = new int[]{Color.parseColor("#7fbeff"),
                Color.parseColor("#a474fe"), Color.parseColor("#bb42fd")};
        String[] text = {"MONEY", "HEALTH", "CAREER", "LOVE", "OVERALL"};
        float[] value = {2.5f, 5.5f, 9.8f, 5.8f, 4.4f};
        radar.setData(text, value, colors);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radar.startAnim();
            }
        });

    }
}
