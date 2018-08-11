package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.DateDialogView;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class RepaymentInstallActivity extends BaseActivity {
    @Bind(R.id.install_exdit)
    EditText installExdit;
    @Bind(R.id.install_xuanze)
    RelativeLayout installXuanze;
    @Bind(R.id.install_money)
    TextView installMoney;
    @Bind(R.id.install_day)
    TextView installDay;
    @Bind(R.id.install_date)
    TextView installDate;
    @Bind(R.id.install_fee)
    TextView installfee;
    private int day=0;
    private String money = "";
    private String daystr="";
    DateDialogView dialogView;
    private String cardid = "";
    boolean s = true;
    String daymoney="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment_install);
        BaseActivity.hiedBar(this);
        ButterKnife.bind(this);
        cardid = getIntent().getStringExtra("cardid");
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("设置还款");
        dialogView = new DateDialogView(this, new DateDialogView.DateBack() {
            @Override
            public void callback(String str) {

                int a  = str.length()-str.replaceAll(",", "").length();
                //Log.d("==》",str);
                //Log.d("==》",a+1+"");
                daystr = str;
                installDate.setText(str);
                day = a+1;
                if (day == 0)
                    installDate.setText("请选择 >");
                installDay.setText(day+"天");
                insertUI();
            }
        });
        installExdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                money = charSequence.toString().trim();
                insertUI();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });/*setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                return false;
            }
        });*/
    }

    @OnClick({R.id.install_submit,R.id.install_xuanze})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.install_submit:
                if (s) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setTitle("温馨提示");
                    builder.setMessage("执行此计划需要卡余额"+daymoney+"元，请确保卡余额度充足，不足请充值");
                    builder.setPositiveButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            submit();
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();
                }
                break;
            case R.id.install_xuanze:
                dialogView.show();
                break;
        }

    }

    private void submit() {
        if (day == 0 || money.isEmpty())
            return;
        s = false;
        Map<String,String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("paymentAmt", money);
        map.put("paymentDate", daystr);
        map.put("cardCode", cardid);
        Httpshow(this);
        JSONObject o = new JSONObject(map);
        Log.d("请求参数：==》",o.toString());
        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, Action.paymentApply, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("返回参数：==》", response.toString());
                try {
                    if (response.getString("code").equals("1")){
                        Intent intent = new Intent(RepaymentInstallActivity.this,SucceededActivity.class);
                        intent.putExtra("card",cardid);
                        intent.putExtra("Money1",money);
                        intent.putExtra("Money2",daymoney);
                        intent.putExtra("Day",""+day);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(RepaymentInstallActivity.this,response.getString("message"),Toast.LENGTH_LONG).show();
                    }
                    s = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Httpdismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("错误参数：==》", "error.getMessage()");
                s = true;
                Httpdismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
      MyVolley.addRequest(request);
    }

    private void insertUI(){
        Log.d("==》",day+"----"+money);
        if (day == 0 || money.isEmpty())
            return;
        Map<String,String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("paymentAmt", money);
        map.put("paymentDates", daystr);
        MyVolley.addRequest(new VolleyRequest(Action.calcReserveMoney, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        JSONObject object = jsonObject.getJSONObject("data");
                        installfee.setText("服务费率"+object.getDouble("rate")*10000d/100d+"%，总计"+object.getDouble("fee")+"元");
                        daymoney = object.getString("totalReserveMoney");
                        installMoney.setText(daymoney);
                    }else {

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
