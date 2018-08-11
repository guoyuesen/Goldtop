package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.TransactionAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/12.
 */

public class TransactionStatisticsActivity extends BaseActivity {
    @Bind(R.id.statictics_tabb)
    View staticticsTabb;
    @Bind(R.id.statistics_list)
    ListView list;
    @Bind(R.id.statistics_list1)
    ListView list1;
    JSONArray Yobj = new JSONArray();
    JSONArray Dobj = new JSONArray();
    TransactionAdapter adapter1;
    TransactionAdapter adapter2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_statistics);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setTitleText("交易统计").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter1 = new TransactionAdapter(this,Dobj);
        adapter2 = new TransactionAdapter(this,Yobj);
        staticticsTabb.setTranslationX(ContextUtil.getX(this) / 6 + ContextUtil.dip2px(this, 30));
        list.setAdapter(adapter1);
        list1.setAdapter(adapter2);
        Map<String,String> mapd = new HashMap<String, String>();
        mapd.put("custId", UserModel.custId);
        mapd.put("analysisType","D");
        MyVolley.addRequest(new formRequest(Action.tradeDetail, mapd, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        Dobj = jsonObject.getJSONArray("data");
                        adapter1.notifyDataSetChanged(Dobj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
        Map<String,String> mapm = new HashMap<String, String>();
        mapm.put("custId", UserModel.custId);
        mapm.put("analysisType","M");
        MyVolley.addRequest(new formRequest(Action.tradeDetail, mapm, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        Yobj = jsonObject.getJSONArray("data");
                        adapter2.notifyDataSetChanged(Yobj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }



    @OnClick({R.id.statictics_tabhost1, R.id.statictics_tabhost3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.statictics_tabhost1:
                staticticsTabb.setTranslationX(ContextUtil.getX(this) / 6 + ContextUtil.dip2px(this, 20));
                list.setVisibility(View.VISIBLE);
                list1.setVisibility(View.GONE);
                break;
            case R.id.statictics_tabhost3:
                staticticsTabb.setTranslationX(ContextUtil.getX(this) / 2 + ContextUtil.dip2px(this, 80));
                list1.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                break;
        }
    }
}
