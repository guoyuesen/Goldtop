package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

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

public class AddCard02Activity extends BaseActivity {
    @Bind(R.id.add_card02_adderss)
    EditText addCard02Adderss;
    @Bind(R.id.add_card02_phone)
    EditText addCard02Phone;
    @Bind(R.id.add_card02_sheng)
    EditText addCard02sheng;
    @Bind(R.id.add_card02_shi)
    EditText addCard02shi;
    @Bind(R.id.add_card02_yh)
    EditText addCard02yh;
    @Bind(R.id.add_card02_yxq)
    EditText addCard02Yxq;
    String number="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card02);
        ButterKnife.bind(this);
        hiedBar(this);
        number = getIntent().getStringExtra("number")==null?"":getIntent().getStringExtra("number");
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("添加银行卡");
    }

    @OnClick(R.id.add_card02_submit)
    public void onClick() {
        String address = addCard02Adderss.getText().toString().trim();
        String phone = addCard02Phone.getText().toString().trim();
        String sheng = addCard02sheng.getText().toString().trim();
        String shi = addCard02shi.getText().toString().trim();
        String yh = addCard02yh.getText().toString().trim();
        if (address.isEmpty()||phone.isEmpty()||sheng.isEmpty()||shi.isEmpty()||yh.isEmpty()){
            Toast.makeText(this,"请认真填写相关信息",Toast.LENGTH_LONG).show();
            return;
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("accountCode",number);
        map.put("accountName",UserModel.custName);
        map.put("bankCardType","C");
        map.put("bankName",yh);
        map.put("openingSubBankName",address);
        map.put("openningBankProvince",sheng);
        map.put("openningBankCity",shi);
        map.put("mobileNo",phone);
        Httpshow(this);
        Log.d(Action.addcard+"==》",map.toString());
        MyVolley.addRequest(new JsonObjectRequest(Request.Method.POST, Action.addcard,new JSONObject(map), new Response.Listener<JSONObject>() {
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

    }
}
