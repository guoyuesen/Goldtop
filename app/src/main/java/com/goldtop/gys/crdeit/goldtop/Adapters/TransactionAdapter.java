package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.acticity.DetailedActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/8/11.
 */

public class TransactionAdapter extends BaseAdapter {
    Context context;
    JSONArray array;

    public TransactionAdapter(Context context, JSONArray array) {
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
            return array.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
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
        if (view==null){
            item = new ThisItem();
            view = LayoutInflater.from(context).inflate(R.layout.item_transaction,null);
            item.t1 = view.findViewById(R.id.statictics_time);
            item.t2 = view.findViewById(R.id.statictics_money);
            item.t3 = view.findViewById(R.id.statictics_num);
            item.t4 = view.findViewById(R.id.statictics_usernum);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        try {
            final JSONObject object = array.getJSONObject(i);
            item.t1.setText(object.getString("tradeTime")+"收益（元）");
            item.t2.setText(MoneyUtils.getShowMoney(object.getDouble("amount")/100.00d));
            item.t3.setText(""+object.getInt("tradeCount"));
            item.t4.setText(""+object.getInt("addedNum"));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        DetailedActivity.inActivity(context,"交易明细",object.getString("tradeTime"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
    class ThisItem{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
    }
}
