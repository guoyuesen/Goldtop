package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.ShareActivity;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by 郭月森 on 2018/9/26.
 */

public class BeautyAdapter extends RecyclerView.Adapter<BeautyAdapter.BeautyViewHolder> {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据集合
     */
    private JSONArray data;

    public BeautyAdapter(JSONArray data, Context context) {
        this.data = data;
        this.mContext = context;
    }

    @Override
    public BeautyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载item 布局文件
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beauty, parent, false);
        return new BeautyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final BeautyViewHolder holder, final int position) {
        try {
            Glide.with(mContext)
                    .load(data.getJSONObject(position).getString("url")).asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            ViewGroup.LayoutParams layoutParams = holder.beautyImage.getLayoutParams();
                            float s = (ContextUtil.getX((Activity)mContext)-ContextUtil.dip2px(mContext,9))/2.00f/width;
                            layoutParams.width = (ContextUtil.getX((Activity)mContext)-ContextUtil.dip2px(mContext,9))/2;
                            layoutParams.height = (int)(height*s);
                            holder.beautyImage.setLayoutParams(layoutParams);
                            holder.beautyImage.setImageBitmap(bitmap);
                        }
                    });

        holder.beautyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ShareActivity.class);
                try {
                    intent.putExtra("imgurl",data.getJSONObject(position).getString("url"));
                    intent.putExtra("type",data.getJSONObject(position).getString("type"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mContext.startActivity(intent);
            }
        });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.length();
    }

    static class BeautyViewHolder extends RecyclerView.ViewHolder {
        ImageView beautyImage;

        public BeautyViewHolder(View itemView) {
            super(itemView);
            beautyImage = itemView.findViewById(R.id.image_item);
        }
    }
}
