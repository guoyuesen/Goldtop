package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
    TextView authenyhmc;
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
        authenCardnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    // 此处为得到焦点时的处理内容
                    String cardnumber = authenCardnumber.getText().toString().trim();
                    queryBankNo(cardnumber);
                }
            }
        });
        authenCardnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (str.length()>16&&authenyhmc.getText().toString().trim().isEmpty()){
                    authenyhmc.setText(AddCard02Activity.getBankName(str.substring(0,6)));
                }
            }
        });
        final TextView thmc = findViewById(R.id.authen_yhmc);
        thmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = authenCardnumber.getText().toString().trim();
                String yh = thmc.getText().toString().trim();
                if (yh == null||yh.equals("")) {
                    MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.queryBank +"?bankNo="+ n, new HashMap<String, String>(), new MyVolleyCallback() {
                        @Override
                        public void CallBack(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt("code")==1){
                                    thmc.setText(jsonObject.getJSONObject("data").getString("bankName"));
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
        });
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
        if (name.isEmpty()||idnumber.isEmpty()||cardnumber.isEmpty()||phone.isEmpty()||yhmc.isEmpty()){
            Toast.makeText(this,"请认真填写相关信息",Toast.LENGTH_LONG).show();
            return;
        }
        UserModel.custName = name;
        final Map<String, String> params = new HashMap<String, String>();
        params.put("custId", UserModel.custId);
        params.put("custName", name);
        params.put("idCardNo", idnumber);
        params.put("custMobile", phone);
        final Map<String, String> map = new HashMap<String, String>();
        map.put("custId",UserModel.custId);
        map.put("accountCode",cardnumber);
            map.put("accountName",name);
            map.put("bankName",yhmc.substring(0,yhmc.indexOf("·")));
            /*map.put("openingSubBankName",adderss);
            map.put("openningBankProvince",sheng);
            map.put("openningBankCity",shi);*/
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
                //Toast.makeText(AuthenticationActivity.this,"实名认证提交成功",Toast.LENGTH_LONG).show();
                UserModel.shiMrenz = "REG_ING";
                AlertDialog dialog = new AlertDialog.Builder(AuthenticationActivity.this)
                        .setMessage("实名认证提交成功，为了您的账户安全，实名认证可能需要3-5分钟，请耐心等待")
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).create();
                dialog.show();
                Httpdismiss();
                //finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("错误参数：==》","error.getMessage()");
                Toast.makeText(AuthenticationActivity.this,"网络请求失败",Toast.LENGTH_LONG).show();
                Httpdismiss();
            }
        }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryBankNo(final String bankNo){
        //银行代码请求接口 url
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo="+bankNo+"&cardBinCheck=true";
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, url, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("validated")){
                        if (!jsonObject.getString("cardType").equals("DC")){
                            Toast.makeText(getApplication(),"请输入储蓄卡卡号",Toast.LENGTH_LONG).show();
                            return;
                        }
                        authenyhmc.setText(AddCard02Activity.getBankName(bankNo.substring(0,6)));
                    }else {
                        Toast.makeText(getApplication(),"请检查银行卡号码",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(),"网络请求错误",Toast.LENGTH_LONG).show();
            }
        }));

    }
}
