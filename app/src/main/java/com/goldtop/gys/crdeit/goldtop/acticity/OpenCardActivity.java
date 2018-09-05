package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.DateButtonListener;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.DateButton;
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
 * Created by 郭月森 on 2018/8/29.
 */

public class OpenCardActivity extends BaseActivity {
    @Bind(R.id.open_card_e1)
    EditText openCardE1;
    @Bind(R.id.open_card_e2)
    EditText openCardE2;
    @Bind(R.id.open_card_t1)
    TextView openCardT1;
    @Bind(R.id.open_card_t2)
    TextView openCardT2;
    @Bind(R.id.open_card_t3)
    TextView openCardT3;
    @Bind(R.id.open_card_t4)
    TextView openCardT4;
    @Bind(R.id.open_card_t5)
    TextView openCardT5;
    @Bind(R.id.open_card_e3)
    EditText openCardE3;
    @Bind(R.id.open_card_b1)
    DateButton openCardB1;
    JSONObject o;
    String openOrderId= "";
    public static void initActivity(Context context, JSONObject object){
        Intent intent = new Intent(context,OpenCardActivity.class);
        intent.putExtra("obj",object.toString());
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_open_card);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("银联认证");
        try {
            o = new JSONObject(getIntent().getStringExtra("obj"));
            openCardT1.setText(o.getString("accountName"));
            openCardT2.setText(UserModel.idCardNo);
            openCardT3.setText(o.getString("accountCode"));
            openCardT4.setText(o.getString("bankName"));
            openCardT5.setText(o.getString("mobileNo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        openCardB1.setthisText("获取验证码");
        openCardB1.setNum(60);
        openCardB1.setOnClick(new DateButtonListener() {
            @Override
            public void onClick(View view) {
                String date = openCardE1.getText().toString().trim();
                String cvv = openCardE2.getText().toString().trim();
                if (o == null){
                    Toast.makeText(OpenCardActivity.this,"卡信息有误，请重新登录",Toast.LENGTH_LONG).show();
                }
                if (date.isEmpty()||cvv.isEmpty()){
                    Toast.makeText(OpenCardActivity.this,"请认真填写相关信息",Toast.LENGTH_LONG).show();
                }
                Map<String,String> map = new HashMap<String, String>();
                map.put("custId", UserModel.custId);
                try {
                    map.put("cardId",o.getString("id"));
                    map.put("cvn2",cvv);
                    map.put("expired",date);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Httpshow(OpenCardActivity.this);
                MyVolley.addRequest(new formRequest(Action.openSms, map, new MyVolleyCallback() {
                    @Override
                    public void CallBack(JSONObject jsonObject) {
                        Httpdismiss();
                        try {
                            if (jsonObject.getString("code").equals("1")){
                                openOrderId = jsonObject.getJSONObject("data").getString("openOrderId");
                                openCardB1.Start();
                            }else {
                                Toast.makeText(OpenCardActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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

            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {

            }
        });
    }

    @OnClick(R.id.open_card_b2)
    public void onClick() {
        String code = openCardE3.getText().toString().trim();
        String date = openCardE1.getText().toString().trim();
        String cvv = openCardE2.getText().toString().trim();
        if (o == null){
            Toast.makeText(OpenCardActivity.this,"卡信息有误，请重新登录",Toast.LENGTH_LONG).show();
            return;
        }
        if (date.isEmpty()||cvv.isEmpty()||code.isEmpty()||openOrderId.isEmpty()){
            Toast.makeText(OpenCardActivity.this,"请认真填写相关信息",Toast.LENGTH_LONG).show();
            return;
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("smsCode",code);
        map.put("openOrderId",openOrderId);
        Httpshow(OpenCardActivity.this);
        MyVolley.addRequest(new formRequest(Action.openCard, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Httpdismiss();
                try {
                    if (jsonObject.getString("code").equals("1")){
                        finish();
                    }else {
                        Toast.makeText(OpenCardActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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
