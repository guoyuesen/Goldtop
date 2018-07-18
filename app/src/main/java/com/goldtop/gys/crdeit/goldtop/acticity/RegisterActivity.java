package com.goldtop.gys.crdeit.goldtop.acticity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.DateButtonListener;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.DateButton;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/5.
 */

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.register_phone)
    EditText registerPhone;
    @Bind(R.id.register_verification)
    EditText registerVerification;
    @Bind(R.id.register_pass01)
    EditText registerPass01;
    @Bind(R.id.register_pass02)
    EditText registerPass02;
    @Bind(R.id.register_activation)
    EditText registerActivation;
    @Bind(R.id.register_submit)
    Button registerSubmit;
    @Bind(R.id.register_login)
    TextView registerLogin;
    @Bind(R.id.register_datebtn)
    DateButton registerDatebtn;
    @Bind(R.id.update_pass)
    RelativeLayout updatePass;
    boolean from = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        String src = getIntent().getStringExtra("from");
        if (src.equals("pass")) {
            new TitleBuder(this).setTitleText("修改密码").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            }).setBackgrund(Color.parseColor("#ffffff"));
            updatePass.setVisibility(View.GONE);
            from = false;
        } else {
            new TitleBuder(this).setTitleText("注册").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            }).setBackgrund(Color.parseColor("#ffffff"));
        }
        registerDatebtn.setNum(60);
        registerDatebtn.setthisText("获取验证码");
        registerDatebtn.setOnClick(new DateButtonListener() {
            @Override
            public void onClick(View view) {
                String p = registerPhone.getText().toString().trim();
                if (p.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("loginIdent", p);
                if (from)
                    params.put("checkMobile","false");
                Httpshow(RegisterActivity.this);
                MyVolley.addRequest(new VolleyRequest(Action.check, params, new MyVolleyCallback() {
                    @Override
                    public void CallBack(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("code").equals("1")) {
                                registerDatebtn.Start();
                                Toast.makeText(RegisterActivity.this, "获取验证码成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(RegisterActivity.this, "获取验证码失败，请重试", Toast.LENGTH_LONG).show();
                            }
                            Httpdismiss();
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
        registerSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = registerPhone.getText().toString().trim();
                String v = registerVerification.getText().toString().trim();
                String p1 = registerPass01.getText().toString().trim();
                String p2 = registerPass02.getText().toString().trim();
                String yqm = registerActivation.getText().toString().trim();
                Map<String, String> params = new HashMap<String, String>();
                String url;
                if (from) {
                    url = Action.register;
                    if (p.isEmpty() || v.isEmpty() || p1.isEmpty() || p2.isEmpty() || yqm.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "请认真填写注册信息", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!p1.equals(p2)) {
                        Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
                        return;
                    }
                    params.put("custMobile", p);
                    params.put("custPassword", p1);
                    params.put("identifyingCode", v);
                    params.put("inviterId", yqm);
                }else {
                    url = Action.changePassword;
                    if (p.isEmpty() || v.isEmpty() || p1.isEmpty() || p2.isEmpty()) {
                        Toast.makeText(RegisterActivity.this, "请认真填写注册信息", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (!p1.equals(p2)) {
                        Toast.makeText(RegisterActivity.this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
                        return;
                    }
                    params.put("custMobile", p);
                    params.put("custPassword", p1);
                    params.put("identifyingCode", v);
                    //params.put("inviterId", yqm);
                }
                Httpshow(RegisterActivity.this);
                //Log.d("<===请求地址====>",Action.register);
                MyVolley.addRequest(new VolleyRequest(Action.register, params, new MyVolleyCallback() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.d("<=======>",error.getMessage());
                        Httpdismiss();
                    }

                    @Override
                    public void CallBack(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("code").equals("1")) {
                                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(RegisterActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Httpdismiss();
                        //dialog.dismiss();
                    }
                }));
            }
        });
        registerLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
