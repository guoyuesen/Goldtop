package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class Orderdapter extends BaseAdapter {
    Context context;
    JSONArray array;

    public Orderdapter(Context context, JSONArray array) {
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

    public void notifyDataSetChanged( JSONArray array) {
        this.array = array;
        notifyDataSetChanged();
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
            view = LayoutInflater.from(context).inflate(R.layout.item_order, null);
            item.orderno = view.findViewById(R.id.orderNo);
            item.ordername = view.findViewById(R.id.ordername);
            item.orderimg = view.findViewById(R.id.orderimg);
            item.ordertime = view.findViewById(R.id.ordertime);
            item.ordermoney = view.findViewById(R.id.ordermoney);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        try {
            JSONObject object = array.getJSONObject(i);
            item.orderno.setText("订单编号："+object.getString("orderNo"));
            JSONObject product = object.getJSONObject("product");
            item.ordername.setText(product.getString("productName"));
            MyVolley.getImage("http://www.tuoluo718.com"+product.getString("productPic"),item.orderimg);
            String mn = "";
            if (product.getString("type").equals("money")){
                mn = "共1件商品 合计："+ MoneyUtils.getShowMoney(product.getString("price"))+"元";
            }else {
                mn = "共1件商品 合计："+ product.getString("price")+"积分";
            }
            item.ordermoney.setText(mn);
            item.ordertime.setText(ContextUtil.dataTostr(object.getLong("createTime"),"yyyy-MM-dd"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
    class ThisItem{
        TextView orderno;
        ImageView orderimg;
        TextView ordername;
        TextView ordermoney;
        TextView ordertime;
    }
}
