package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.magic.MagicBallView;

public class MagicBallActivity extends AppCompatActivity {

    private MagicBallView luckMagicBall;
    private AppCompatImageView magicBallBottom;
    private AppCompatButton start;
    private AppCompatButton stop;

    private void assignViews() {
        luckMagicBall = (MagicBallView) findViewById(R.id.luck_magic_ball);
        magicBallBottom = (AppCompatImageView) findViewById(R.id.magic_ball_bottom);
        start = (AppCompatButton) findViewById(R.id.start);
        stop = (AppCompatButton) findViewById(R.id.stop);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_ball);
        assignViews();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                luckMagicBall.startAnim();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luckMagicBall.stopAnim();

            }
        });


    }
}
