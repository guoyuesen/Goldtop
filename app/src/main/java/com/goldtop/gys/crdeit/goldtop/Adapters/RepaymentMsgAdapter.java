package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.HttpsDialogView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 郭月森 on 2018/7/4.
 */

public class RepaymentMsgAdapter extends BaseAdapter {
    private Context context;
    private JSONArray array;
    Map<String,String> map = new HashMap<>();
    List<String> list = new ArrayList<>();

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
            item.time03 = view.findViewById(R.id.item_repayment_text12);
            //item.free = view.findViewById(R.id.item_repayment_text13);
            item.money03 = view.findViewById(R.id.item_repayment_text14);
            item.list = view.findViewById(R.id.repayment_item_list);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        try {
            JSONObject o = array.getJSONObject(i);
            JSONArray all = o.getJSONArray("planList");
            JSONObject object1= all.getJSONObject(0);
            item.list.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ContextUtil.dip2px(context,36*all.length())));
            item.list.setAdapter(new ItemItem(all,context,i));
            String riqi = ContextUtil.dataTostr(object1.getLong("paymentTime"),"yyyy-MM-dd");
            item.riqi.setText("还款日期："+riqi);
            item.money03.setText(MoneyUtils.getShowMoney(o.getString("planMoney")));
            item.time03.setText(o.getString("withdrawTime"));
            view.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ContextUtil.dip2px(context,55+36+36*all.length()+50+2)));

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
        TextView time03;
        TextView money03;
        ListView list;
    }
    public void setCode(TextView textView,String code){
        switch (code){
            case "REPAY_SUCCESS":
                textView.setText("成功");
                textView.setTextColor(Color.parseColor("#00CD00"));
                break;
            case "REPAY_FAIL":
                textView.setText("失败");
                textView.setTextColor(Color.parseColor("#CD3700"));
                break;
            case "INIT":
                textView.setText("待确认");
                textView.setTextColor(Color.parseColor("#fece00"));
                break;
            case "CONFIRMED":
                textView.setText("待执行");
                textView.setTextColor(Color.parseColor("#fece00"));
                break;
            case "CANCELED":
                textView.setText("取消");
                textView.setTextColor(Color.parseColor("#CD3700"));
                break;
            case "PROCESSING":
                textView.setText("执行中");
                textView.setTextColor(Color.parseColor("#fece00"));
                break;
        }
    }
    class ItemItem extends BaseAdapter{
        JSONArray array;
        Context context;
        int index;

        public ItemItem(JSONArray array, Context context, int index) {
            this.array = array;
            this.context = context;
            this.index = index;
            if (list.size()<1) {
                map.put("百货商超", "M001");
                map.put("酒吧", "M010");
                map.put("酒店", "M011");
                map.put("电影院", "M012");
                map.put("餐饮", "M002");
                map.put("珠宝/首饰", "M003");
                map.put("服饰", "M004");
                map.put("化妆品", "M005");
                map.put("健身", "M006");
                map.put("美容/SPA", "M007");
                map.put("洗浴/按摩", "M008");
                map.put("加油站", "M009");
                Set<String> set = map.keySet();
                list.add("");
                for (String str : set) {
                    list.add(str);
                }
            }
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ItemItemI itemI = null;
            if (view == null){
                itemI = new ItemItemI();
                view = LayoutInflater.from(context).inflate(R.layout.item_repayment_item,null);
                itemI.t0 = view.findViewById(R.id.item_01);
                itemI.t1 = view.findViewById(R.id.item_02);
                itemI.t2 = view.findViewById(R.id.item_03);
                itemI.t3 = view.findViewById(R.id.item_04);
                itemI.t4 = view.findViewById(R.id.item_05);
                itemI.t5 = view.findViewById(R.id.item_06);
                itemI.t6 = view.findViewById(R.id.item_item_gg);
                view.setTag(itemI);
            }else {
                itemI = (ItemItemI) view.getTag();
            }
            try {
                final JSONObject object = array.getJSONObject(i);
                itemI.t0.setText(i+1+"");
                itemI.t1.setText(ContextUtil.dataTostr(object.getLong("paymentTime"),"HH:mm"));
                itemI.t2.setText(MoneyUtils.getShowMoney(object.getString("paymentAmt")));
                setCode(itemI.t4,object.getString("paymentStatus"));
                itemI.t4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                itemI.t3.setText(object.getString("hyName"));
                if (!"CONFIRMED".equals(object.getString("paymentStatus"))){
                    itemI.t5.setVisibility(View.GONE);
                    itemI.t6.setTextColor(Color.parseColor("#e9e9e9"));
                }else {
                    itemI.t5.setVisibility(View.VISIBLE);
                    itemI.t6.setTextColor(Color.parseColor("#0049FF"));
                    //适配器
                    ArrayAdapter<String> arr_adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, list);
                    //设置样式
                    arr_adapter.setDropDownViewResource(R.layout.simple_spinner_item);
                    itemI.t5.setAdapter(arr_adapter);
                    final ItemItemI finalItemI = itemI;
                    itemI.t5.setSelection(0, true);
                    itemI.t5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int p, long l) {
                            //finalItemI.t3.setText(list.get(i));
                            try {
                            if (!object.getString("hyName").equals(list.get(p))){
                                    setHy(finalItemI.t3,list.get(p),object.getString("ID"),index,i);
                            }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }
    }
    public void setHy(final TextView t, final String s, String id, final int di, final int xi){
        Map<String,String> m = new HashMap<>();
        m.put("id",id);
        m.put("mccCode",map.get(s));
        final HttpsDialogView dialog = new HttpsDialogView(context);
        dialog.show();
        MyVolley.addRequest(new formRequest(Action.updateMcc, m, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                dialog.dismiss();
                t.setText(s);
                try {
                    array.getJSONObject(di).getJSONArray("planList").getJSONObject(xi).put("hyName",s);
                    //notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }));
    }
    class ItemItemI{
        TextView t0;
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        Spinner t5;
        TextView t6;
    }
}
