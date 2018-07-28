package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.RepaymentMsgAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.ArcProgress;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/4.
 */

public class RepaymentMsgActivity extends BaseActivity {

    @Bind(R.id.repayment_list)
    ListView repaymentList;
    String card="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_repayment_msg);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("代还详情").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        card = getIntent().getStringExtra("card");
        View hendrview = LayoutInflater.from(this).inflate(R.layout.item_repayment_top,null);
        repaymentList.addHeaderView(hendrview);
        View bommview = LayoutInflater.from(this).inflate(R.layout.item_repayment_bomm,null);
        repaymentList.addFooterView(bommview);
        JSONArray array = new JSONArray();
        repaymentList.setAdapter(new RepaymentMsgAdapter(this,array));
        Map<String,String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("cardNo",card);
        Httpshow(this);
        Log.d("==》",Action.paymentPlanListByDate+"?custId="+UserModel.custId+"&cardNo="+card);
        VolleyRequest request = new VolleyRequest(Request.Method.GET, Action.paymentPlanListByDate+"?custId="+UserModel.custId+"&cardNo="+card, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Log.d("==》",jsonObject.toString());
                Httpdismiss();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("==》","----------------------");
                Httpdismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        MyVolley.addRequest(request);
    }
}
