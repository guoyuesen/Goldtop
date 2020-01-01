package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.HistoryItem;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/9/5.
 */

public class HistoryPlanActivity extends BaseActivity {
    @Bind(R.id.history_list)
    LinearLayout historyList;
    String number = "";
    public static void inActivity(Context context,String cardnumber){
        Intent intent = new Intent(context,HistoryPlanActivity.class);
        intent.putExtra("number",cardnumber);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_history_plan);
        ButterKnife.bind(this);
        number = getIntent().getStringExtra("number");
        String title = "";
        if (number!= null){
            title = number.substring(number.length()-4,number.length());
        }
        new TitleBuder(this).setTitleText(title+"历史计划").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initActivity();
    }

    private void initActivity() {
        String url = Action.history+"?cardNo="+number;
        Log.d("history?===>",url);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, url, new HashMap<String, String>(), new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        initList(jsonObject.getJSONArray("data"));
                    }else {}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    private void initList(JSONArray data) throws JSONException {
        if (data.length()>0){
            findViewById(R.id.order_empty).setVisibility(View.GONE);
        }else {
            findViewById(R.id.order_empty).setVisibility(View.VISIBLE);
        }
        for(int i = 0;i<data.length();i++){
            JSONObject jsonObject = data.getJSONObject(i);
            TextView textView = new TextView(this);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ContextUtil.dip2px(this,40)));
            textView.setBackgroundColor(Color.parseColor("#f9f9f9"));
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setPadding(ContextUtil.dip2px(this,10),0,0,0);
            textView.setText(jsonObject.getString("month"));
            historyList.addView(textView);
            JSONArray array = jsonObject.getJSONArray("data");
            for (int j = 0;j<array.length();j++){
                final JSONObject object = array.getJSONObject(j);
                final HistoryItem item = new HistoryItem(this,object);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HistoryPlanActivity.this, RepaymentMsgActivity.class);
                        intent.putExtra("card", number);
                        try {
                            intent.putExtra("applyId",object.getString("id"));
                            object.put("applyAmt",object.getString("paymentAmt"));
                            object.put("totalTerm",object.getString("term"));
                            object.put("balanceTerm",object.getInt("term")-object.getInt("finTerm")+"");
                            //object.put("balanceAmt",object.getString("balanceAmt"));
                            object.put("deadline",object.getString("startDate"));
                            Log.d("++++++++++++++++",object.toString()+number);
                            intent.putExtra("jsono",object.toString());
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                historyList.addView(item);
            }
        }
    }
}
