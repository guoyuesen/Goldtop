package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.acticity.AuthenticationActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/9/3.
 */

public class ZhxzAdapter extends BaseAdapter {
    JSONArray array;
    Context context;

    public ZhxzAdapter(Context context,JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public Object getItem(int i) {
        return array;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        if (view == null) {
            textView = new TextView(context);
            textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ContextUtil.dip2px(context, 40)));
            textView.setGravity(Gravity.CENTER_VERTICAL);
        } else {
            textView = (TextView) view;
        }
        try {
            JSONObject object = array.getJSONObject(i);
            textView.setText(object.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return textView;
    }
}
