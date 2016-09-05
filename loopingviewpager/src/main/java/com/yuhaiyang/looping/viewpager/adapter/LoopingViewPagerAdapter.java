package com.yuhaiyang.looping.viewpager.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuhaiyang.looping.viewpager.R;

import java.util.ArrayList;
import java.util.List;


public abstract class LoopingViewPagerAdapter<DATA, HOLDER extends LoopingViewPagerAdapter.Holder> extends PagerAdapter {
    private static final String TAG = "LoopingViewPagerAdapter";
    private List<DATA> mData = new ArrayList<>();
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;

    public LoopingViewPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
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

    public DATA getItem(int realPosition) {
        return mData.get(realPosition);
    }

    @Override
    public int getCount() {
        int realCount = getRealCount();
        if (realCount <= 0) {
            return 0;
        } else {
            return realCount + 2;
        }
    }

    public int getRealCount() {
        return mData.size();
    }

    public int getRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0) {
            return 0;
        }

        int realPosition = (position - 1) % realCount;
        if (realPosition < 0) {
            realPosition += realCount;
        }
        return realPosition;
    }

    public int getInnerPosition(int realPosition) {
        return realPosition + 1;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final int realPosition = getRealPosition(position);
        Holder holder = onCreateView(container, realPosition, position);
        View item = holder.getItemView();
        onBindView((HOLDER) holder, realPosition, position);
        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            View item = (View) object;
            container.removeView(item);
            recycle(item, position);
        } else {
            super.destroyItem(container, position, object);
        }
    }


    protected void recycle(View item, int position) {
        //  TODO
    }

    /**
     * 创建View容器
     *
     * @param position 当前的 position
     * @return 当前view的容器
     */
    public abstract HOLDER onCreateView(ViewGroup parent, int position, int innerPosition);

    /**
     * 通过容器来 绑定view
     *
     * @param holder   当前view的容器
     * @param position 当前的 position
     */
    public abstract void onBindView(HOLDER holder, int position, int innerPosition);


    /**
     * ViewHolder复用View的
     */
    public static abstract class Holder {
        private View item;
        private int mType;

        public Holder(View item, int type) {
            mType = type;
            if (item == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.item = item;
            item.setTag(R.id.tag_looping_viewpager_holder, this);
        }

        public int getType() {
            return mType;
        }

        public View getItemView() {
            return item;
        }
    }
}
