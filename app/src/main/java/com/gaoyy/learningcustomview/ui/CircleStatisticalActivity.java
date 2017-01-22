package com.gaoyy.learningcustomview.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.gaoyy.learningcustomview.R;
import com.gaoyy.learningcustomview.view.CircleStatisticalView;
import com.gaoyy.learningcustomview.view.PieChatView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

public class CircleStatisticalActivity extends AppCompatActivity
{

    private LinkedHashMap kindsMap = new LinkedHashMap<String, Integer>();
    private ArrayList<Integer> colors = new ArrayList<>();
    private LinearLayout activityCircleStatistical;
    private CircleStatisticalView csv;
    private PieChatView pie;

    private void assignViews() {
        activityCircleStatistical = (LinearLayout) findViewById(R.id.activity_circle_statistical);
        csv = (CircleStatisticalView) findViewById(R.id.csv);
        pie = (PieChatView) findViewById(R.id.pie);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        CircleStatisticalView c = new CircleStatisticalView(this);
//        setContentView(c);
        setContentView(R.layout.activity_circle_statistical);
        assignViews();
        double[] datas = {0.4, 0.3, 0.15, 0.1, 0.05};
//        double[] datas = {0.2,0.8};

        csv.setDatas(datas);
        csv.animUpdate();


        PieChatView pieChatView = (PieChatView) findViewById(R.id.pie);
        kindsMap.put("苹果", 10);
        kindsMap.put("梨子", 30);
        kindsMap.put("香蕉", 10);
        kindsMap.put("葡萄", 30);
        kindsMap.put("哈密瓜", 10);
        kindsMap.put("猕猴桃",30);
        kindsMap.put("草莓", 10);
        kindsMap.put("橙子", 30);
        kindsMap.put("火龙果", 10);
        kindsMap.put("椰子", 20);
        for (int i = 1; i <= 40; i++){
            int r= (new Random().nextInt(100)+10)*i;
            int g= (new Random().nextInt(100)+10)*3*i;
            int b= (new Random().nextInt(100)+10)*2*i;
            int color = Color.rgb(r,g,b);
            if(Math.abs(r-g)>10&&Math.abs(r-b)>10&&Math.abs(b-g)>10){
                colors.add(color);
            }
        }
        pieChatView.setCenterTitle("水果大拼盘");
        pieChatView.setDataMap(kindsMap);
        pieChatView.setColors(colors);
        pieChatView.setMinAngle(200);
        pieChatView.startDraw();




    }
}
