package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class MyCardAdapter extends BaseAdapter {
    Context context;
    JSONArray array;

    public MyCardAdapter(Context context, JSONArray array) {
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

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThisItem item = null;
        if (view == null) {
            item = new ThisItem();
            view = LayoutInflater.from(context).inflate(R.layout.item_my_card, null);
            item.name = view.findViewById(R.id.item_mycard_name);
            item.num = view.findViewById(R.id.item_mycard_num);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        try {
            JSONObject object = array.getJSONObject(i);
            item.name.setText(object.getString("bankName"));
            String num = object.getString("accountCode");
            StringBuffer sb = new StringBuffer("");
            for (int s = 0;s<num.length();s++){
                if (s<num.length()-4){
                    sb.append("*");
                }else {
                    sb.append(num.charAt(s));
                }
                if ((s+1)%4==0){
                    sb.append(" ");
                }
            }
            item.num.setText(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
    class ThisItem{
        TextView name;
        TextView num;
    }
}
