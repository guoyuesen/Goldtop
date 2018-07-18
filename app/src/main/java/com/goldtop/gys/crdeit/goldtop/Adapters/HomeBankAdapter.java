package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.RepaymentInstallActivity;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/3.
 */

public class HomeBankAdapter extends BaseAdapter {
    private Context context;
    private JSONArray array;

    public HomeBankAdapter(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    public void notifyDataSetChanged(JSONArray array) {
        this.array = array;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return array.length()+1;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThisItem item;

        if (view==null) {
            item = new ThisItem();
            view = LayoutInflater.from(context).inflate(R.layout.item_home_list, null);
            item.button = view.findViewById(R.id.repayment);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        item.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, RepaymentInstallActivity.class));
            }
        });
        return view;
    }
    class ThisItem{
        Button button;
        ImageView imageView;
        

    }
}
