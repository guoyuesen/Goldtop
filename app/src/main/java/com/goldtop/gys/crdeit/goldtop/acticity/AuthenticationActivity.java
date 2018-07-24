package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
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
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/10.
 */

public class AuthenticationActivity extends BaseActivity {
    @Bind(R.id.authen_name)
    EditText authenName;
    @Bind(R.id.authen_idnumber)
    EditText authenIdnumber;
    @Bind(R.id.authen_cardnumber)
    EditText authenCardnumber;
    @Bind(R.id.authen_adderss)
    EditText authenAdderss;
    @Bind(R.id.authen_sheng)
    EditText authensheng;
    @Bind(R.id.authen_shi)
    EditText authenshi;
    @Bind(R.id.authen_yhmc)
    EditText authenyhmc;
    @Bind(R.id.authen_phone)
    EditText authenPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("实名认证");
    }

    @OnClick(R.id.authen_submit)
    public void onClick() {
        try {
        String name = authenName.getText().toString().trim();
        String idnumber = authenIdnumber.getText().toString().trim();
        String cardnumber = authenCardnumber.getText().toString().trim();
        String adderss = authenAdderss.getText().toString().trim();
        String phone = authenPhone.getText().toString().trim();
        String sheng = authensheng.getText().toString().trim();
        String shi = authenshi.getText().toString().trim();
        String yhmc = authenyhmc.getText().toString().trim();
        if (name.isEmpty()||idnumber.isEmpty()||cardnumber.isEmpty()||adderss.isEmpty()||phone.isEmpty()||sheng.isEmpty()||shi.isEmpty()||yhmc.isEmpty()){
            Toast.makeText(this,"请认真填写相关信息",Toast.LENGTH_LONG).show();
            return;
        }
        final Map<String, String> params = new HashMap<String, String>();
        params.put("custId", UserModel.custId);
        params.put("custName", name);
        params.put("idCardNo", idnumber);
        params.put("custMobile", phone);
        final Map<String, String> map = new HashMap<String, String>();
        map.put("custId",UserModel.custId);
        map.put("accountCode",cardnumber);
            map.put("accountName",name);
            map.put("bankName",yhmc);
            map.put("openingSubBankName",adderss);
            map.put("openningBankProvince",sheng);
            map.put("openningBankCity",shi);
            map.put("mobileNo",phone);
            map.put("bankCardType","D");
        JSONObject object = new JSONObject(map);
        JSONArray array = new JSONArray();
        array.put(object);
        JSONObject jsonObject = new JSONObject(params);
        jsonObject.put("bankCards",array);
        Httpshow(AuthenticationActivity.this);
        //Log.d("<===请求地址====>",Action.register);
        Log.d("请求参数：==》",jsonObject.toString());
        MyVolley.addRequest(new JsonObjectRequest(Request.Method.POST, Action.authentication,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("返回参数：==》",response.toString());
                Httpdismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("错误参数：==》","error.getMessage()");
                Httpdismiss();
            }
        }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
