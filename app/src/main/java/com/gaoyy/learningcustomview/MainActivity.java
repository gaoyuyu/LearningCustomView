package com.gaoyy.learningcustomview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.gaoyy.learningcustomview.ui.CircleStatisticalActivity;
import com.gaoyy.learningcustomview.ui.ColorfulBarActivity;
import com.gaoyy.learningcustomview.ui.CouponActivity;
import com.gaoyy.learningcustomview.ui.CustomTitleViewActivity;
import com.gaoyy.learningcustomview.ui.FadeImageActivity;
import com.gaoyy.learningcustomview.ui.FlowActivity;
import com.gaoyy.learningcustomview.ui.LoveWaveActivity;
import com.gaoyy.learningcustomview.ui.MagicBallActivity;
import com.gaoyy.learningcustomview.ui.MaskTextViewActivity;
import com.gaoyy.learningcustomview.ui.RadarActivity;
import com.gaoyy.learningcustomview.ui.WaveActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button ctv;
    private Button csv;
    private Button wave;
    private Button viewgroup;
    private Button colorfulbar;
    private Button coupon;
    private Button radar;
    private Button loveWave;
    private Button fadeImg;
    private Button magicBall;
    private Button maskTv;

    private void assignViews() {
        ctv = (Button) findViewById(R.id.ctv);
        csv = (Button) findViewById(R.id.csv);
        wave = (Button) findViewById(R.id.wave);
        viewgroup = (Button) findViewById(R.id.viewgroup);
        colorfulbar = (Button) findViewById(R.id.colorfulbar);
        coupon = (Button) findViewById(R.id.coupon);
        radar = (Button) findViewById(R.id.radar);
        loveWave = (Button) findViewById(R.id.love_wave);
        fadeImg = (Button) findViewById(R.id.fade_img);
        magicBall = (Button) findViewById(R.id.magic_ball);
        maskTv = (Button) findViewById(R.id.mask_tv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        setListener();
    }

    private void setListener() {
        ctv.setOnClickListener(this);
        csv.setOnClickListener(this);
        wave.setOnClickListener(this);
        viewgroup.setOnClickListener(this);
        colorfulbar.setOnClickListener(this);
        coupon.setOnClickListener(this);
        radar.setOnClickListener(this);
        loveWave.setOnClickListener(this);
        fadeImg.setOnClickListener(this);
        magicBall.setOnClickListener(this);
        maskTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent();
        switch (id) {
            case R.id.ctv:
                intent.setClass(MainActivity.this, CustomTitleViewActivity.class);
                break;
            case R.id.csv:
                intent.setClass(MainActivity.this, CircleStatisticalActivity.class);
                break;
            case R.id.wave:
                intent.setClass(MainActivity.this, WaveActivity.class);
                break;
            case R.id.viewgroup:
                intent.setClass(MainActivity.this, FlowActivity.class);
                break;
            case R.id.colorfulbar:
                intent.setClass(MainActivity.this, ColorfulBarActivity.class);
                break;
            case R.id.coupon:
                intent.setClass(MainActivity.this, CouponActivity.class);
                break;
            case R.id.radar:
                intent.setClass(MainActivity.this, RadarActivity.class);
                break;
            case R.id.love_wave:
                intent.setClass(MainActivity.this, LoveWaveActivity.class);
                break;
            case R.id.fade_img:
                intent.setClass(MainActivity.this, FadeImageActivity.class);
                break;
            case R.id.magic_ball:
                intent.setClass(MainActivity.this, MagicBallActivity.class);
                break;
            case R.id.mask_tv:
                intent.setClass(MainActivity.this, MaskTextViewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
