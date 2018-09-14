package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.DetailedAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/17.
 */

public class DetailedActivity extends BaseActivity {
    @Bind(R.id.detailed_list)
    ListView detailedList;
    boolean in = true;
    DetailedAdapter adapter;
    public static void inActivity(Context context,boolean t,String id){
        Intent intent = new Intent(context,DetailedActivity.class);
        intent.putExtra("mx",t);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);
        hiedBar(this);
        in = getIntent().getBooleanExtra("mx",true);
        detailedList.setEmptyView(findViewById(R.id.order_empty));
        String title = "";
        if (in){
            title = "积分明细";
        }else {
            title = "红包明细";
        }
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText(title);
        JSONArray array = new JSONArray();
        adapter = new DetailedAdapter(this,array,in);
        detailedList.setAdapter(adapter);
        initA();
    }

    private void initA() {
        if (!in) {
            MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.redPackDetail + "?redpackId=" + getIntent().getStringExtra("id"), new HashMap<String, String>(), new MyVolleyCallback() {
                @Override
                public void CallBack(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getString("code").equals("1")) {
                            adapter.notifyDataSetChanged(jsonObject.getJSONArray("data"));
                        } else {
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
    }
}
