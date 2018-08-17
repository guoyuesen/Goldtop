package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.GrapNotif;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.GraphicUnlocking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/8/16.
 */

public class GraphicActivity extends BaseActivity {
    @Bind(R.id.unlocking)
    GraphicUnlocking unlocking;
    @Bind(R.id.unlocking_text)
    TextView unlockingText;
    String p1 = "";
    public static boolean isLogin = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);
        ButterKnife.bind(this);
        hiedBar(this);
        findViewById(R.id.login_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GraphicActivity.this,LoginActivity.class));
                finish();
            }
        });
        unlocking.setNotif(new GrapNotif() {
            @Override
            public void Start(int p) {

            }

            @Override
            public void Move(int p, String pass) {

            }

            @Override
            public void Stop(String pass) {
                unlocking.cliar();
                if (isLogin){
                    SharedPreferences sp = getSharedPreferences("SP_PEOPLE", Activity.MODE_PRIVATE);
                    String peopleJson = sp.getString("KEY_LOGING_PASS","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
                    String people = sp.getString("KEY_LOGING_PHONE","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("mobile",people);
                    map.put("type","2");
                    map.put("sign",pass);
                    MyVolley.addRequest(new formRequest(Action.loginByType, map, new MyVolleyCallback() {
                        @Override
                        public void CallBack(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt("code") == 1){
                                        UserModel.setInfo(jsonObject.getJSONObject("data"));
                                        Toast.makeText(GraphicActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(GraphicActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                    }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }));
                }else {
                    if (p1.isEmpty() && pass.length() < 4) {
                        unlockingText.setText("密码过于简单");
                        return;
                    }
                    if (p1.isEmpty()) {
                        p1 = pass;
                        unlockingText.setText("请确认密码");
                        return;
                    }
                    if (p1.equals(pass)) {
                        setUnlocking();
                    } else {
                        unlockingText.setText("两次密码不匹配，请重新设置");
                        p1 = "";
                    }
                }
            }
        });
    }
    private void setUnlocking(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("patternPassword",p1);
        Httpshow(this);
        MyVolley.addRequest(new formRequest(Request.Method.GET,Action.createPatternPassword+"?custId="+UserModel.custId+"&patternPassword="+p1, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        unlockingText.setText("密码设置成功");
                        SharedPreferences sp = getSharedPreferences("SP_PEOPLE", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();;
                        editor.putString("KEY_LOGING_PASS", p1) ; //存入json串
                        editor.commit() ; //提交
                        finish();
                    }else {
                        unlockingText.setText("密码设置失败");
                        p1 = "";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Httpdismiss();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                unlockingText.setText("密码设置失败");
                p1 = "";
                Httpdismiss();
            }
        }));
    }
}
