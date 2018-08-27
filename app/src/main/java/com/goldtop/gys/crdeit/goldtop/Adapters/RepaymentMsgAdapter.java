package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/7/4.
 */

public class RepaymentMsgAdapter extends BaseAdapter {
    private Context context;
    private JSONArray array;

    public RepaymentMsgAdapter(Context context, JSONArray array) {
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
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThisItem item;
        if (view==null){
            item = new ThisItem();
            view = LayoutInflater.from(context).inflate(R.layout.item_repayment,null);
            item.riqi = view.findViewById(R.id.item_repayment_text01);
            item.number = view.findViewById(R.id.item_repayment_text10);
            item.time01 = view.findViewById(R.id.item_repayment_text06);
            item.money01 = view.findViewById(R.id.item_repayment_text07);
            item.money02 = view.findViewById(R.id.item_repayment_text10);
            item.time02 = view.findViewById(R.id.item_repayment_text00);
            item.code01 = view.findViewById(R.id.item_repayment_text08);
            item.code02 = view.findViewById(R.id.item_repayment_text11);
            item.time03 = view.findViewById(R.id.item_repayment_text12);
            //item.free = view.findViewById(R.id.item_repayment_text13);
            item.money03 = view.findViewById(R.id.item_repayment_text14);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        try {
            JSONObject o = array.getJSONObject(i);
            JSONArray all = o.getJSONArray("planList");
            JSONObject object1= all.getJSONObject(0);
            JSONObject object2= all.getJSONObject(1);
            String riqi = ContextUtil.dataTostr(object1.getLong("paymentTime"),"yyyy-MM-dd");
            item.riqi.setText("还款日期："+riqi);
            item.time01.setText(ContextUtil.dataTostr(object1.getLong("paymentTime"),"HH:mm"));
            item.time02.setText(ContextUtil.dataTostr(object2.getLong("paymentTime"),"HH:mm"));
            item.money01.setText(""+(object1.getDouble("paymentAmt")));//+object1.getDouble("transFee")
            item.money02.setText(""+(object2.getDouble("paymentAmt")));//+object2.getDouble("transFee")
            //item.free.setText("当日手续费："+(object1.getDouble("transFee")+object2.getDouble("transFee")));
            //还款计划状态（INIT待确认、CONFIRMED已生效、CANCELED计划被撤销、REPAY_ING 还款中、REPAY_SUCCESS还款成功、REPAY_FAIL 还款失败）
            setCode(item.code01,object1.getString("paymentStatus"));
            setCode(item.code02,object2.getString("paymentStatus"));
            item.money03.setText(o.getString("planMoney"));
            item.time03.setText(o.getString("withdrawTime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void notifyDataSetChanged(JSONArray array) {
        this.array = array;
        notifyDataSetChanged();
    }
    private class ThisItem{
        TextView riqi;
        TextView number;
        TextView time01;
        TextView money01;
        TextView money02;
        TextView time02;
        TextView code01;
        TextView code02;
        TextView time03;
        //TextView free;
        TextView money03;
    }
    public void setCode(TextView textView,String code){
        switch (code){
            case "REPAY_SUCCESS":
                textView.setText("成功");
                break;
            case "REPAY_FAIL":
                textView.setText("失败");
                break;
            case "INIT":
                textView.setText("待确认");
                break;
            case "CONFIRMED":
                textView.setText("待执行");
                break;
            case "CANCELED":
                textView.setText("取消");
                break;
            case "REPAY_ING":
                textView.setText("执行中");
                break;
        }
    }
}
