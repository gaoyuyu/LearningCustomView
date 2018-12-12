package com.gaoyy.learningcustomview.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.FadeImageView;

public class FadeImageActivity extends AppCompatActivity {

    private FadeImageView fade;
    private AppCompatButton btn;
    private AppCompatButton btn1;

    private void assignViews() {
        fade = (FadeImageView) findViewById(R.id.fade);
        btn = (AppCompatButton) findViewById(R.id.btn);
        btn1 = (AppCompatButton) findViewById(R.id.btn1);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fade_image);
        assignViews();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade.startAnim();
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fade.resetAll();
            }
        });
    }
}
