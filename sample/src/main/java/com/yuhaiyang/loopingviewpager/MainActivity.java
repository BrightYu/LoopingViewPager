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

        Log.i(TAG, "initViews: 1 = " + (-1 % 3));
        Log.i(TAG, "initViews: 2 = " + (0 % 3));
        Log.i(TAG, "initViews: 3 = " + (1 % 3));
        Log.i(TAG, "initViews: 4 = " + (2 % 3));
        Log.i(TAG, "initViews: 4 = " + (3 % 3));
        Log.i(TAG, "initViews: 5 = " + (4 % 3));
    }

    private List<String> getData() {
        List<String> datas = new ArrayList<>();
        datas.add("http://www.yuhaiyang.net/header/header1.jpg");
        datas.add("http://www.yuhaiyang.net/header/header2.jpg");
        datas.add("http://www.yuhaiyang.net/header/header3.jpg");
        return datas;
    }
}
