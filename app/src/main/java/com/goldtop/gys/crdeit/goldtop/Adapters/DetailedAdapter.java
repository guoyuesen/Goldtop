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
import com.goldtop.gys.crdeit.goldtop.model.DetailedModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 郭月森 on 2018/7/17.
 */

public class DetailedAdapter extends BaseAdapter {
    Context context;
    DetailedModel detailed;


    public DetailedAdapter(Context context,DetailedModel detailed) {
        this.context = context;
        this.detailed = detailed;
    }

    public void notifyDataSetChanged(DetailedModel detailed) {
        this.detailed = detailed;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return detailed.list.size();
    }

    @Override
    public Object getItem(int i) {

            return detailed.list.get(i);

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

            DetailedModel.Detailed object = detailed.list.get(i);

                item.t1.setText(object.getName());
                item.t2.setText(object.getNumber());
                item.t3.setText(object.getTime());
        return view;
    }
    class ThisItem{
        TextView t1;
        TextView t2;
        TextView t3;
    }
}
