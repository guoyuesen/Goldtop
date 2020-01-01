package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.view.CSxzDialogView;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/10.
 */

public class AddAdderssActivity extends BaseActivity {
    @Bind(R.id.address_name)
    EditText addressName;
    @Bind(R.id.address_phone)
    EditText addressPhone;
    @Bind(R.id.address_address)
    TextView addressAddress;
    @Bind(R.id.address_details)
    EditText addressDetails;
    String province;
    String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_add_adderss);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("新增收货地址");
    }

    @OnClick({R.id.address_submit,R.id.address_address})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.address_submit:
                submit();
                break;
            case R.id.address_address:
                new CSxzDialogView(this, new CSxzDialogView.CsBack() {
                    @Override
                    public void Back(String c,String C, String S, String s) {
                        //city = s;
                        province = c;
                        city = C;
                        addressAddress.setText(c+C);
                    }
                }).show();
            break;
        }
    }

    private void submit() {
        String name = addressName.getText().toString();
        String phone = addressPhone.getText().toString();
        String cyti = addressAddress.getText().toString();
        String str = addressDetails.getText().toString();
        if (name.isEmpty()){
            Toast.makeText(this,"请输入收货人姓名",Toast.LENGTH_LONG).show();
            return;
        }
        if (phone.isEmpty()){
            Toast.makeText(this,"请输入收货人手机号",Toast.LENGTH_LONG).show();
            return;
        }
        if (cyti.isEmpty()){
            Toast.makeText(this,"请选择地区",Toast.LENGTH_LONG).show();
            return;
        }
        if (str.isEmpty()){
            Toast.makeText(this,"请输入详细地址",Toast.LENGTH_LONG).show();
            return;
        }
        Httpshow(this);
        JSONObject o = new JSONObject();
        try {
            o.put("custId", UserModel.custId);
            o.put("province",province);
            o.put("city",city);
            o.put("receiverName",name);
            o.put("address",str);
            o.put("telephone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("请求参数：==》", o.toString());
        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, Action.addaddress+"?token="+UserModel.token, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("返回参数：==》", response.toString());
                try {
                    if (response.getString("code").equals("1")) {
                        finish();
                    } else {
                        Toast.makeText(AddAdderssActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Httpdismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("错误参数：==》", "error.getMessage()");
                Httpdismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        MyVolley.addRequest(request);
    }
}
