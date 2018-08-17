package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.RepaymentMsgAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.ArcProgress;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONException;
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
    RepaymentMsgAdapter adapter;
    View header;
    View Footer;
    TextView bommNumber;
    TextView bommTime;
    TextView bommorder;
    String playId = "";
    JSONObject cardobj;
    TextView herdtext01;
    TextView herdtext02;
    TextView herdtext03;
    TextView herdtext04;
    TextView herdtext05;
    TextView herdtext06;
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
        try {
            String js = getIntent().getStringExtra("jsono");
            if (js!=null)
            cardobj = new JSONObject(js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initHead();
       initFooter();

        JSONArray array = new JSONArray();
        adapter = new RepaymentMsgAdapter(this,array);
        repaymentList.setAdapter(adapter);
        getMsg();

    }

    private void initFooter() {
        if (cardobj!=null) {
            Footer = LayoutInflater.from(this).inflate(R.layout.item_repayment_bomm, null);
            bommNumber = Footer.findViewById(R.id.item_repayment_bomm_bank);
            bommTime = Footer.findViewById(R.id.item_repayment_bomm_creattime);
            bommorder = Footer.findViewById(R.id.item_repayment_bomm_order);
            try {
            bommNumber.setText(cardobj.getString("bankName")+"(" + card.substring(card.length() - 4) + ")");
            bommTime.setText(ContextUtil.dataTostr(cardobj.getLong("deadline"), "yyyy-MM-dd"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Footer.findViewById(R.id.item_repayment_bomm_creatplay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RepaymentMsgActivity.this, RepaymentInstallActivity.class);
                    intent.putExtra("cardid", card);
                    startActivity(intent);
                }
            });
            Footer.findViewById(R.id.item_repayment_bomm_cloesplay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    closePlan();
                }
            });
        }else {
            Footer = LayoutInflater.from(this).inflate(R.layout.item_repayment_bommtwo, null);
            Footer.findViewById(R.id.sp_bomm_succeeded).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        repaymentList.addFooterView(Footer);
    }

    private void initHead() {
        if (cardobj!=null) {
            header = LayoutInflater.from(this).inflate(R.layout.item_repayment_top, null);
            herdtext01 = header.findViewById(R.id.repayment_money_complete);
            herdtext02 = header.findViewById(R.id.repayment_periods_complete);
            herdtext03 = header.findViewById(R.id.repayment_periods);
            herdtext04 = header.findViewById(R.id.repayment_time);
            herdtext05 = header.findViewById(R.id.repayment_money);
            herdtext06 = header.findViewById(R.id.head_card_number);
            try {
                herdtext01.setText("" + (cardobj.getDouble("applyAmt") - cardobj.getDouble("balanceAmt")));
                herdtext02.setText(cardobj.getInt("totalTerm") - (cardobj.getInt("balanceTerm")) + "");
                herdtext03.setText("/" + (cardobj.getInt("totalTerm")));
                herdtext04.setText(ContextUtil.dataTostr(cardobj.getLong("deadline"), "yyyy-MM-dd"));
                herdtext05.setText("" + cardobj.getDouble("applyAmt"));
                herdtext06.setText(cardobj.getString("bankName")+"(" + card.substring(card.length() - 4) + ")");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            header = LayoutInflater.from(this).inflate(R.layout.item_repayment_toptow, null);
            TextView t1= header.findViewById(R.id.toptow_money);
            TextView t2= header.findViewById(R.id.toptow_money01);
            TextView t3= header.findViewById(R.id.toptow_day);
            t1.setText(getIntent().getStringExtra("Money1"));
            t2.setText(getIntent().getStringExtra("Money2")+"元");
            t3.setText(getIntent().getStringExtra("Day")+"天");
        }
            repaymentList.addHeaderView(header);
    }

    private void closePlan() {
        if (playId.isEmpty()) {
            Log.d("==",playId);
            return;
        }
        Log.d("==","------开始");
        Httpshow(this);
        Map<String,String> map = new HashMap<String, String>();
        map.put("applyId",playId);
        map.put("custId",UserModel.custId);
        MyVolley.addRequest(new VolleyRequest(Action.closePlan, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        Toast.makeText(RepaymentMsgActivity.this,"计划取消成功",Toast.LENGTH_LONG).show();
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Httpdismiss();
                Log.d("==","------成功");
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Httpdismiss();
                Log.d("==","------失败");
            }
        }));
    }

    public void getMsg() {
        Map<String,String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("cardNo",card);
        Httpshow(this);
        Log.d("==》",Action.paymentPlanListByDate+"?custId="+UserModel.custId+"&cardNo="+card);
        VolleyRequest request = new VolleyRequest(Request.Method.GET, Action.paymentPlanListByDate+"?custId="+UserModel.custId+"&cardNo="+card, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Log.d("==》",jsonObject.toString());
                try {
                    JSONObject object = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = object.getJSONArray("dataList");
                    if (jsonArray.length()>0){
                        playId=jsonArray.getJSONObject(0).getJSONArray("planList").getJSONObject(0).getString("applyId");
                        if (cardobj!=null) {
                            herdtext01.setText(object.getString("finAmt"));
                            bommorder.setText(playId);
                            bommTime.setText(ContextUtil.dataTostr(jsonArray.getJSONObject(0).getJSONArray("planList").getJSONObject(0).getLong("paymentTime"), "yyyy-MM-dd"));
                        }
                    }
                    adapter.notifyDataSetChanged(jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
