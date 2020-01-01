package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.ArcProgressBar;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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
    TextView herdtrepayment_totalFee;
    TextView fee;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_repayment_msg);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("还款详情").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        card = getIntent().getStringExtra("card");
        playId = getIntent().getStringExtra("applyId");
        try {
            String js = getIntent().getStringExtra("jsono");
            if (js!=null)
            cardobj = new JSONObject(js);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initFooter();
        initHead();


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
            try {//{"paymentAmt":"5000","endDate":"2018-09-28","finTerm":"0","term":"5","id":"201809061509351646727615","startDate":"2018-09-24","status":"PROCESSIING"}
                String yhmc = AddCard02Activity.getBankName(card.substring(0,6));
                if (yhmc == null){
                    yhmc = "";
                }
                bommNumber.setText(yhmc.substring(0, yhmc.indexOf("·") != -1 ? yhmc.indexOf("·") : yhmc.length())+"(" + card.substring(card.length() - 4) + ")");
                String time = cardobj.getString("deadline");
                if (isInteger(time)){
                    bommTime.setText(ContextUtil.dataTostr(cardobj.getLong("deadline"), "yyyy-MM-dd"));
                }else {
                    bommTime.setText(cardobj.getString("deadline"));
                }

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
                    AlertDialog dialog = new AlertDialog.Builder(RepaymentMsgActivity.this).setMessage("计划取消后将不再执行，确定要取消本次计划吗?").setNeutralButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            closePlan();
                            dialogInterface.dismiss();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create();
                    dialog.show();
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
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
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
            herdtrepayment_totalFee = header.findViewById(R.id.repayment_totalFee);
            try {
                herdtext01.setText(MoneyUtils.getShowMoney("" + (cardobj.getDouble("applyAmt") - cardobj.getDouble("balanceAmt"))));
                herdtext02.setText(cardobj.getInt("totalTerm") - (cardobj.getInt("balanceTerm")) + "");
                herdtext03.setText("/" + (cardobj.getInt("totalTerm")));
                String time = cardobj.getString("deadline");
                if (isInteger(time)){
                    herdtext04.setText(ContextUtil.dataTostr(cardobj.getLong("deadline"), "yyyy-MM-dd"));
                }else {
                    herdtext04.setText(cardobj.getString("endDate"));
                }
                herdtext05.setText("" + cardobj.getDouble("applyAmt"));
                String yhmc = AddCard02Activity.getBankName(card.substring(0,6));
                if (yhmc==null){
                    yhmc = "";
                }
                herdtext06.setText(yhmc.substring(0, yhmc.indexOf("·") != -1 ? yhmc.indexOf("·") : yhmc.length())+"(" + card.substring(card.length() - 4) + ")");
                ArcProgressBar bar= header.findViewById(R.id.repayment_progress);
                bar.setSetbacks((int)((cardobj.getDouble("totalTerm") - cardobj.getDouble("balanceTerm"))/cardobj.getDouble("totalTerm")*180));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            header = LayoutInflater.from(this).inflate(R.layout.item_repayment_toptow, null);
            TextView t1= header.findViewById(R.id.toptow_money);
            TextView t2= header.findViewById(R.id.toptow_money01);
            TextView t3= header.findViewById(R.id.toptow_day);
            fee= header.findViewById(R.id.toptow_fee);
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
        MyVolley.addRequest(new VolleyRequest(Action.closePlan, map, new MyVolleyCallback(this) {
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
        map.put("applyId",playId);
        Httpshow(this);
        Log.d("==》",Action.applyDetail+"?custId="+UserModel.custId+"&applyId="+playId);
        VolleyRequest request = new VolleyRequest(Request.Method.GET, Action.applyDetail+"?custId="+UserModel.custId+"&applyId="+playId, map, new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Log.d("==》",jsonObject.toString());
                try {
                    JSONObject object = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = object.getJSONArray("dataList");
                    if (jsonArray.length()>0){
                        if (cardobj!=null) {
                            herdtext01.setText(object.getString("finAmt"));
                            bommorder.setText(playId);
                            bommTime.setText(ContextUtil.dataTostr(jsonArray.getJSONObject(0).getJSONArray("planList").getJSONObject(0).getLong("paymentTime"), "yyyy-MM-dd"));
                            herdtrepayment_totalFee.setText(object.getString("totalFee"));
                        }else {
                            fee.setText(object.getString("totalFee")+"元");
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
