package com.yuhaiyang.loopingviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.yuhaiyang.looping.viewpager.LoopingViewPager;
import com.yuhaiyang.loopingviewpager.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        LoopingViewPager viewPager = (LoopingViewPager) findViewById(R.id.viewpager);
        TestAdapter adaper = new TestAdapter(this);
        adaper.setData(getData());

        viewPager.setAdapter(adaper);
    }

    private List<String> getData() {
        List<String> datas = new ArrayList<>();
        datas.add("http://www.yuhaiyang.net/header/header1.jpg");
        datas.add("http://www.yuhaiyang.net/header/header2.jpg");
        datas.add("http://www.yuhaiyang.net/header/header3.jpg");
        datas.add("http://www.yuhaiyang.net/header/header4.jpg");
        datas.add("http://www.yuhaiyang.net/header/header5.jpg");
        return datas;
    }
}
