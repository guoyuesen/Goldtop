package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.PaypassDialog;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;
import com.goldtop.gys.crdeit.goldtop.view.initPhoneDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/12/19.
 */

public class PosActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        hiedBar(this);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("POS机办理").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.pos_01, R.id.pos_02, R.id.pos_03})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.pos_01:
                new initPhoneDialog(this, "请输入联系信息", "请确认办理型号为MP70的POS机，点击确认后工作人员将尽快与您联系", new initPhoneDialog.initDialog() {
                    @Override
                    public void back(String name, String phong) {
                        submit(name,phong,"pos");
                    }
                }).show();
                break;
            case R.id.pos_02:
                new initPhoneDialog(this, "请输入联系信息", "请确认办理型号为H9无线POS终端，点击确认后工作人员将尽快与您联系", new initPhoneDialog.initDialog() {
                    @Override
                    public void back(String name, String phong) {
                        submit(name,phong,"pos");
                    }
                }).show();
                break;
            case R.id.pos_03:
                new initPhoneDialog(this, "请输入联系信息", "请确认办理型号为云POS终端 MF919，点击确认后工作人员将尽快与您联系", new initPhoneDialog.initDialog() {
                    @Override
                    public void back(String name, String phong) {
                        submit(name,phong,"pos");
                    }
                }).show();
                break;
        }
        /*new PaypassDialog(this, "办理确认", "请确认办理型号为MP70的POS机，点击确认后工作人员将尽快与您联系", new PaypassDialog.Paypass() {
            @Override
            public void back(String pass) {

            }
        }).show();*/
    }
    private void submit(String name,String phone,String type){

        MyVolley.addRequest(new VolleyRequest(Request.Method.GET,Action.business+"type="+type+"&contactName="+name+"&telephone="+phone, new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code")==1){
                        Toast.makeText(PosActivity.this,"提交申请成功，我们将尽快联系您，请保持手机畅通！",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(PosActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
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
