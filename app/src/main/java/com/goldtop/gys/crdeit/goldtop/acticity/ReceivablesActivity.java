package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.ButtomDialogView;
import com.goldtop.gys.crdeit.goldtop.view.ReceivablesDialogView;
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

public class ReceivablesActivity extends BaseActivity {
    @Bind(R.id.receivables_card01)
    TextView receivablesCard01;
    @Bind(R.id.receivables_card02)
    TextView receivablesCard02;
    @Bind(R.id.receivables_money)
    EditText receivablesMoney;
    JSONObject objectin;
    JSONObject objectout;
    /*@Bind(R.id.receivables_cvn)
    EditText receivablesCvn;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivables);
        hiedBar(this);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("收款");
    }

    @OnClick({R.id.receivables_submit,R.id.receivables_incard,R.id.receivables_outcard})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.receivables_submit:
                //startActivity(new Intent(this,ScheduleActivity.class));
                try {
                if (objectin==null){
                    Toast.makeText(this,"请选择储蓄卡",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!objectin.getString("bindStatus").equals("REG_SUCCESS")){
                    Toast.makeText(this,"储蓄卡未绑定商户，请联系客服",Toast.LENGTH_LONG).show();
                }
                if (objectout==null){
                    Toast.makeText(this,"请选择信用卡",Toast.LENGTH_LONG).show();
                    return;
                }
                    if(!objectin.getString("openStatus").equals("OPEN_SUCCESS")){
                        Toast.makeText(this,"信用卡未进行银联认证，请联系客服",Toast.LENGTH_LONG).show();
                    }
                String money = receivablesMoney.getText().toString().trim();
                if (money.isEmpty()){
                    Toast.makeText(this,"请输入金额",Toast.LENGTH_LONG).show();
                    return;
                }
                double m = Double.parseDouble(money);
                if (m>1000){
                    Toast.makeText(this,"为了您的资金安全请输入小于1000的金额",Toast.LENGTH_LONG).show();
                    return;
                }
                if (m<100){
                    Toast.makeText(this,"金额不能小于100元",Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String,String> map = new HashMap<String, String>();

                    map.put(" custId",UserModel.custId);
                    map.put(" inCardId",objectin.getString("id"));
                    map.put(" outCardId",objectout.getString("id"));
                    map.put(" amount",""+(m*100));
                    MyVolley.addRequest(new formRequest(Action.withdraw, map, new MyVolleyCallback() {
                        @Override
                        public void CallBack(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getString("code").equals("1")){
                                    startActivity(new Intent(ReceivablesActivity.this,ScheduleActivity.class));
                                }else {
                                    Toast.makeText(ReceivablesActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.receivables_incard:
                getcard("D");
                break;
            case R.id.receivables_outcard:
                getcard("C");
                break;
        }

    }
    public void getcard(final String t){
        Httpshow(this);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.queryBankCard+"?custId="+ UserModel.custId+"&cardType="+t, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Httpdismiss();
                try {
                    if (jsonObject.getString("code").equals("1")){
                        JSONArray array = jsonObject.getJSONArray("data");
                        ReceivablesDialogView dialogView = new ReceivablesDialogView(ReceivablesActivity.this, array, new ReceivablesDialogView.backTo() {
                            @Override
                            public void sercsse(String T, JSONObject object) {
                                try {
                                    if (T.equals("C")) {
                                        receivablesCard01.setText(object.getString("bankName") + "(" + object.getString("accountCode").substring(object.getString("accountCode").length() - 4) + ")");
                                        objectout = object;
                                    }else {
                                        receivablesCard02.setText(object.getString("bankName") + "(" + object.getString("accountCode").substring(object.getString("accountCode").length() - 4) + ")");
                                        objectin = object;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },t);
                        dialogView.show();
                    }
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
