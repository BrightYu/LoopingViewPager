/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yuhaiyang.loopingviewpager.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuhaiyang.looping.viewpager.adapter.LoopingViewPagerAdapter;
import com.yuhaiyang.loopingviewpager.R;

/**
 * Created by yuhaiyang on 2016/9/5.
 */
public class TestAdapter extends LoopingViewPagerAdapter<String, TestAdapter.ViewHolder> {

    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateView(ViewGroup parent, int position, int innerPosition) {
        View item = mLayoutInflater.inflate(R.layout.item_test, parent, false);
        return new ViewHolder(item, 0);
    }

    @Override
    public void onBindView(ViewHolder holder, int position, int innerPosition) {
        String path = getItem(position);
        Glide.with(mContext)
                .load(path)
                .into(holder.image);
    }

    public class ViewHolder extends LoopingViewPagerAdapter.Holder {
        public ImageView image;

        public ViewHolder(View item, int type) {
            super(item, type);
            image = (ImageView) item.findViewById(R.id.image);
        }
    }
}
