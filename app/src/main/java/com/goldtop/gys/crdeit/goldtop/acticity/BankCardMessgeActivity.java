package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Fragment.MycardTabFragment;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.DateDialogView;
import com.goldtop.gys.crdeit.goldtop.view.MyCardDialogView;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/9/7.
 */

public class BankCardMessgeActivity extends BaseActivity {
    @Bind(R.id.item_mycard_name)
    TextView itemMycardName;
    @Bind(R.id.card_type)
    TextView cardType;
    @Bind(R.id.item_mycard_num)
    TextView itemMycardNum;
    JSONObject object;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_messge);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("银行卡详情");
        String str = getIntent().getStringExtra("bankobj");
        try {
            object = new JSONObject(str);
            itemMycardName.setText(object.getString("bankName"));
            if (object.getString("bankCardType").equals("D")) {
                cardType.setText("储蓄卡");
            } else {
                cardType.setText("信用卡");
            }
            String num = object.getString("accountCode");
            StringBuffer sb = new StringBuffer("");
            for (int s = 0; s < num.length(); s++) {
                if (s < num.length() - 4) {
                    sb.append("*");
                } else {
                    sb.append(num.charAt(s));
                }
                if ((s + 1) % 4 == 0) {
                    sb.append(" ");
                }
            }
            itemMycardNum.setText(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    MyCardDialogView dialogView = null;
    @OnClick(R.id.submit_card)
    public void onClick() {


        dialogView = new MyCardDialogView(this, new DateDialogView.DateBack() {
            @Override
            public void callback(String str) {
                Log.d("~~~~~~~~~~~~~~~~~","~~~~~~~~~~~~~~~~~~~~~~");
                try {
                    di();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        dialogView.show();
    }
    public void di() throws JSONException {
        Log.d("~~~~~~~~~~~~~~~~~","~~~~~~~~~~~~~~~~~~~~~~");
        Httpshow(this);
        Log.d("Action.unbindCard","?cardNo="+object.getString("accountCode"));
        MyVolley.addRequest(new formRequest(Request.Method.GET, Action.unbindCard+"?cardNo="+object.getString("accountCode"), new HashMap<String, String>(), new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Httpdismiss();

                try {
                    if (jsonObject.getInt("code") == 1){
                        MycardTabFragment.p = object.getString("bankCardType");
                        dialogView.dismiss();
                        Toast.makeText(BankCardMessgeActivity.this,"解绑成功",Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(BankCardMessgeActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("~~~~~~~~~~~~~~~~~","~~~~~~~~~~~~~~~~~~~~~~");
                Httpdismiss();
            }
        }));
    }
}
