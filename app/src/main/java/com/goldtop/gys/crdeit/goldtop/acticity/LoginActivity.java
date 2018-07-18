package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/5.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.login_phone)
    EditText loginPhone;
    @Bind(R.id.login_pass)
    EditText loginPass;
    @Bind(R.id.login_submit)
    Button loginSubmit;
    @Bind(R.id.login_getpass)
    TextView loginGetpass;
    @Bind(R.id.login_crate)
    TextView loginCrate;
    @Bind(R.id.login_close)
    ImageView loginClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginCrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("from", "register");
                startActivity(intent);
            }
        });
        loginClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = loginPhone.getText().toString().trim();
                String pass = loginPass.getText().toString().trim();
                if (user.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("loginIdent", user);
                params.put("password", pass);
                Httpshow(LoginActivity.this);

                MyVolley.addRequest(new VolleyRequest(Action.login, params, new MyVolleyCallback() {
                    @Override
                    public void CallBack(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("code").equals("1")) {
                                UserModel.setInfo(jsonObject.getJSONObject("data"));
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            //Log.d("<=======>",e.getMessage());
                        }
                        Httpdismiss();
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Httpdismiss();
                    }
                }));
            }
        });
        loginGetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("from", "pass");
                startActivity(intent);
            }
        });
    }
}
