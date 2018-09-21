package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/13.
 */

public class RedEnvelopesActivity extends BaseActivity {
    @Bind(R.id.envelopes_money)
    TextView envelopesMoney;
    @Bind(R.id.envelopes_out_money)
    EditText envelopesOutMoney;
    @Bind(R.id.envelopes_ktx_money)
    TextView envelopesKtxMoney;
    private float money = 0.00f;
    private Object m;
    String id = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_envelopes);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("红包").setRightText("红包明细").setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailedActivity.inActivity(RedEnvelopesActivity.this,"红包明细",0,id);
            }
        });
        envelopesKtxMoney.setText("" + money);
        //envelopesMoney.setText("" + money);
        getM();

    }

    @OnClick({R.id.envelopes_money_all, R.id.envelopes_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.envelopes_money_all:
                //envelopesOutMoney.setText(""+ktx);
                break;
            case R.id.envelopes_submit:
                //String m = envelopesOutMoney.getText().toString().trim();
                if (money==0.00){
                    Toast.makeText(this,"没有可提现金额",Toast.LENGTH_LONG).show();
                    return;
                }
                Httpshow(this);
                MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.transfer+"?custId="+UserModel.custId+"&money="+money*100, new HashMap<String, String>(), new MyVolleyCallback() {
                    @Override
                    public void CallBack(JSONObject jsonObject) {
                        Httpdismiss();
                        try {
                            if (jsonObject.getInt("code") == 1){
                                Toast.makeText(RedEnvelopesActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                                getM();
                            }else {
                                Toast.makeText(RedEnvelopesActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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
                /*Toast.makeText(this,"转入成功",Toast.LENGTH_LONG).show();
                finish();*/
                break;
        }
    }

    public void getM() {
        MyVolley.addRequest(new formRequest(Request.Method.GET, Action.myRedpack+"?custId="+ UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code")==1){
                        money = jsonObject.getJSONObject("data").getInt("balance")/100.00f;

                        //envelopesKtxMoney.setText(MoneyUtils.getShowMoney(ktx));
                        envelopesMoney.setText(MoneyUtils.getShowMoney(money));
                        id = jsonObject.getJSONObject("data").getString("id");
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
