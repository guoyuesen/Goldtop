package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.DetailedAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.DetailedModel;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
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
    String title = "";
    int type = 1;
    DetailedAdapter adapter;
    public static void inActivity(Context context,String title,int type){
        Intent intent = new Intent(context,DetailedActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("type",type);
        context.startActivity(intent);
    }
    public static void inActivity(Context context,String title,int type,String id){
        Intent intent = new Intent(context,DetailedActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("type",type);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }
    public static void inActivity(Context context,String title,String time){
        Intent intent = new Intent(context,DetailedActivity.class);
        intent.putExtra("title",title);
        if (time.length()==7) {
            intent.putExtra("type", 2);
        }else {
            intent.putExtra("type", 3);
        }
        intent.putExtra("time",time);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);
        hiedBar(this);
        title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type",1);
        detailedList.setEmptyView(findViewById(R.id.order_empty));
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText(title);
        DetailedModel model = new DetailedModel(new JSONArray(),type);
        adapter = new DetailedAdapter(this,model);
        detailedList.setAdapter(adapter);
        initA();
    }

    private void initA() {
        String url = "";
        switch (type){
            case 0:
                url = Action.redPackDetail + "?redpackId=" + getIntent().getStringExtra("id");
                break;
            case 1:
                url = Action.tradeDetailmsg + "?custId=" + UserModel.custId;
                break;
            case 2:
                url = Action.incomeDetail + "?custId=" + UserModel.custId+"&time="+getIntent().getStringExtra("time")+"&type=M";
                break;
            case 3:
                url = Action.incomeDetail + "?custId=" + UserModel.custId+"&time="+getIntent().getStringExtra("time");
                break;
            case 4:
                url = Action.detail+"?bonusId="+getIntent().getStringExtra("id");
                break;
            case 5:
                url = Action.ztList+"?mobile="+UserModel.custMobile;
                break;
            case 6:
                url = Action.vipList+"?mobile="+UserModel.custMobile;
                break;
        }
        getType0(url);
    }
    void getType0(String url){
        Log.d("访问地址",url);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, url, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        DetailedModel model = new DetailedModel(jsonObject.getJSONArray("data"),type);
                        adapter.notifyDataSetChanged(model);
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
