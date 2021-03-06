package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class FindAdapter extends BaseAdapter {
    Context context;
    JSONArray array;

    public FindAdapter(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return array.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }
    public void notifyDataSetChanged(JSONArray array) {
        this.array = array;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThisItem item=null;
        if (view == null) {
            item = new ThisItem();
            view = LayoutInflater.from(context).inflate(R.layout.item_fragment_find, null);
            item.img = view.findViewById(R.id.item_find_img);
            item.title = view.findViewById(R.id.item_find_title);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        try {
            JSONObject object = array.getJSONObject(i);
            MyVolley.getImage(object.getString("img"),item.img);
            item.title.setText(object.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
    class ThisItem{
        ImageView img;
        TextView title;
    }
}
