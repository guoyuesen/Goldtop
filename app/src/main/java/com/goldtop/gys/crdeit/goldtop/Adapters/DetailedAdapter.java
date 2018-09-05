package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/7/17.
 */

public class DetailedAdapter extends BaseAdapter {
    Context context;
    JSONArray array;
    boolean T;

    public DetailedAdapter(Context context, JSONArray array,boolean T) {
        this.context = context;
        this.array = array;
        this.T = T;
    }

    public void notifyDataSetChanged(JSONArray array) {
        this.array = array;
        notifyDataSetChanged();
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
            return 0;
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThisItem item = null;
        if (view == null) {
            item = new ThisItem();
            view = LayoutInflater.from(context).inflate(R.layout.item_detailed_jf, null);
            item.t1 = view.findViewById(R.id.detaile_jf_t1);
            item.t2 = view.findViewById(R.id.detaile_jf_t2);
            item.t3 = view.findViewById(R.id.detaile_jf_t3);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        try {
            JSONObject object = array.getJSONObject(i);
            if (T){
                item.t1.setText("获得积分");
            }else {
                item.t1.setText("获得金额");
                item.t2.setText(object.getInt("sum")/100d+"元");
                item.t3.setText(ContextUtil.dataTostr(object.getLong("createTime"),"yyyy-MM-dd HH:mm"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
    class ThisItem{
        TextView t1;
        TextView t2;
        TextView t3;
    }
}
