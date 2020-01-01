package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    TextView loginSubmit;
    @Bind(R.id.login_getpass)
    TextView loginGetpass;
    @Bind(R.id.login_crate)
    TextView loginCrate;
    @Bind(R.id.login_clear)
    ImageView loginClear;
    @Bind(R.id.login_close)
    ImageView loginClose;
    @Bind(R.id.login_show)
    ImageView loginShow;
    @Bind(R.id.login_checkbox)
    CheckBox checkBox;
     boolean show = false;
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
        SharedPreferences sp = getSharedPreferences("SP_PEOPLE", Activity.MODE_PRIVATE);
        String pass = sp.getString("KEY_LOGING_PASS","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        String people = sp.getString("KEY_LOGING_PHONE","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if (pass.isEmpty()){
            checkBox.setChecked(false);
        }else {
            checkBox.setChecked(true);
        }
        loginPhone.setText(people);
        loginPass.setText(pass);
        loginClose.setAlpha(170);
        loginClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String user = loginPhone.getText().toString().trim();
                final String pass = loginPass.getText().toString().trim();
                if (user.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入手机号", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pass.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String, String> params = new HashMap<String, String>();
                params.put("loginIdent", user);
                params.put("password", pass);
                Httpshow(LoginActivity.this);

                MyVolley.addRequest(new VolleyRequest(Action.login, params, new MyVolleyCallback(LoginActivity.this) {
                    @Override
                    public void CallBack(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getString("code").equals("1")) {
                                UserModel.setInfo(jsonObject.getJSONObject("data"));
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                SharedPreferences sp = getSharedPreferences("SP_PEOPLE", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("KEY_LOGING_PHONE", user) ; //存入json串
                                if (checkBox.isChecked()) {
                                    editor.putString("KEY_LOGING_PASS", pass);
                                }else {
                                    editor.putString("KEY_LOGING_PASS", "");
                                }
                                editor.commit() ; //提交
                                finish();
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
        loginClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPhone.setText("");
            }
        });
        loginShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!show) {
                    loginPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    show = true;
                    loginShow.setImageResource(R.mipmap.login4);
                }else {
                    loginPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    show = false;
                    loginShow.setImageResource(R.mipmap.login5);
                }
            }
        });
    }
}
