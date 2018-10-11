package com.goldtop.gys.crdeit.goldtop.Adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.goldtop.gys.crdeit.goldtop.R;

/**
 * Created by 郭月森 on 2018/9/25.
 */

public class CardAdapter extends PagerAdapter {
    Context context;
    int[] imgs;

    public CardAdapter(Context context,int[] imgs) {
        this.context = context;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return imgs.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemview, null);
        ImageView imageView = view.findViewById(R.id.home_viewpage_item_img);
        imageView.setImageResource(imgs[position]);

        //view.home_viewpage_item_img.setImageResource(horoscopestrImgs.get(position%12))

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
