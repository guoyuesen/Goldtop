package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.BeautyAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 郭月森 on 2018/9/26.
 */

public class ShareListActivity extends BaseActivity {
    RecyclerView share_recyclerview;
    BeautyAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_share_list);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("我的邀请码");
        share_recyclerview = findViewById(R.id.share_recyclerview);
        //使用瀑布流布局,第一个参数 spanCount 列数,第二个参数 orentation 排列方向
        StaggeredGridLayoutManager recyclerViewLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //线性布局Manager
//        LinearLayoutManager recyclerViewLayoutManager = new LinearLayoutManager(this);
//        recyclerViewLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //网络布局Manager
//        GridLayoutManager recyclerViewLayoutManager = new GridLayoutManager(this, 3);
        //给recyclerView设置LayoutManager
        share_recyclerview.setLayoutManager(recyclerViewLayoutManager);
        adapter = new BeautyAdapter(new JSONArray(), this);
        //设置adapter
        share_recyclerview.setAdapter(adapter);
        Httpshow(this);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.invitationList, new HashMap<String, String>(), new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Httpdismiss();
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    adapter = new BeautyAdapter(jsonArray, ShareListActivity.this);
                    //设置adapter
                    share_recyclerview.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Httpdismiss();
            }
        }));
    }
}
