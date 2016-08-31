package com.yuhaiyang.looping.viewpager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public abstract class LoopingViewPagerAdapter<DATA, HOLDER> extends PagerAdapter {
    private static final String TAG = "LoopingViewPagerAdapter";
    private List<DATA> mData = new ArrayList<>();
    private Context mContext;

    public LoopingViewPagerAdapter(Context context) {

    }

    /**
     * @param data 设定的值
     */
    public void setData(List<DATA> data) {
        setData(data, true);
    }

    /**
     * @param data  设定的值
     * @param force 是否强制添加值
     */
    public void setData(List<DATA> data, boolean force) {
        if (data != null) {
            mData = data;
            notifyDataSetChanged();
        } else if (force) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 增加数据
     */
    public void plusData(List<DATA> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    /**
     * 增加数据
     */
    public void plusData(DATA data) {
        if (data != null) {
            mData.add(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    /**
     * 创建View容器
     *
     * @param position 当前的 position
     * @param type     当前view的type
     * @return 当前view的容器
     */
    public abstract HOLDER onCreateViewHolder(ViewGroup parent, int position, int type);

    /**
     * 通过容器来 绑定view
     *
     * @param holder   当前view的容器
     * @param position 当前的 position
     * @param type     当前view的type
     */
    public abstract void onBindViewHolder(HOLDER holder, int position, int type);
}
