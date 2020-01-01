package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/7/10.
 */

public class AdderssAdapter extends BaseAdapter {
    private Context context;
    private JSONArray array;

    public AdderssAdapter(Context context, JSONArray array) {
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
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public void notifyDataSetChanged(JSONArray array) {
        this.array = array;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThisItem item = null;
        if (view == null) {
            item = new ThisItem();
            view = LayoutInflater.from(context).inflate(R.layout.item_address, null);
            item.name = view.findViewById(R.id.address_itme_name);
            item.phone = view.findViewById(R.id.address_itme_phone);
            item.address = view.findViewById(R.id.address_itme_address);
            item.update = view.findViewById(R.id.address_itme_update);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        try {
            JSONObject object = array.getJSONObject(i);
            item.name.setText(object.getString("receiverName"));
            item.phone.setText(object.getString("telephone"));
            item.address.setText(object.getString("address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
    class ThisItem{
        TextView name;
        TextView phone;
        TextView address;
        ImageView update;
    }
}
