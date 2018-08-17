package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.ReceivablesDialogView;
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
 * Created by 郭月森 on 2018/7/12.
 */

public class ExpressiveActivity extends BaseActivity {
    @Bind(R.id.tx_icon)
    ImageView txIcon;
    @Bind(R.id.tx_name)
    TextView txName;
    @Bind(R.id.tx_kh)
    TextView txKh;
    @Bind(R.id.tx_money)
    EditText txMoney;
    String number = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_expressive);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("提现").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getcard(true);
    }

    @OnClick({R.id.tx_r, R.id.tx_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_r:
                getcard(false);
                break;
            case R.id.tx_submit:
                Map<String,String> map = new HashMap<String, String>();
                map.put("custId",UserModel.custId);
                MyVolley.addRequest(new formRequest(Action.check, map, new MyVolleyCallback() {
                    @Override
                    public void CallBack(JSONObject jsonObject) {
                        try {
                            if (jsonObject.getInt("code")==1){
                                Toast.makeText(ExpressiveActivity.this,"message",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(ExpressiveActivity.this,"message",Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }));
                break;
        }
    }
    public void getcard(final boolean t) {
        Httpshow(this);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.queryBankCard + "?custId=" + UserModel.custId + "&cardType=D", new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Httpdismiss();
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        if (t&&array.length()>0){
                            JSONObject object = array.getJSONObject(0);
                            MyVolley.getImage(object.getString("icon"),txIcon);
                            txName.setText(object.getString("bankName"));
                            txKh.setText("尾号 "+object.getString("accountCode").substring(object.getString("accountCode").length()-4,object.getString("accountCode").length())+" 储蓄卡");
                            number = object.getString("accountCode");
                        }
                        if (t){
                            return;
                        }
                        ReceivablesDialogView dialogView = new ReceivablesDialogView(ExpressiveActivity.this, array, new ReceivablesDialogView.backTo() {
                            @Override
                            public void sercsse(String T, JSONObject object) {
                                try {

                                    MyVolley.getImage(object.getString("icon"),txIcon);
                                    txName.setText(object.getString("bankName"));
                                    txKh.setText("尾号 "+object.getString("accountCode").substring(object.getString("accountCode").length()-4,object.getString("accountCode").length())+" 储蓄卡");
                                    number = object.getString("accountCode");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, "D");
                        dialogView.show();
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
