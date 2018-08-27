package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Context;
import android.content.Intent;
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
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/12.
 */

public class AddCard01Activity extends BaseActivity {
    @Bind(R.id.add_card0_name)
    TextView addCard0Name;
    @Bind(R.id.add_card0_number)
    EditText addCard0Number;
    public static String type = "CC";
    public static String CType = "信用卡";
    @Bind(R.id.addcardt1)
    TextView addcardt1;

    public static void initActivity(Context context, String cd) {
        type = cd;
        context.startActivity(new Intent(context, AddCard01Activity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card01);
        ButterKnife.bind(this);
        hiedBar(this);
        if (type.equals("CC")) {
            CType = "信用卡";
        } else {
            CType = "储蓄卡";
        }
        addcardt1.setText("请绑定持卡人本人的"+CType);
        addCard0Number.setHint("请输入"+CType+"卡号");
        addCard0Name.setText(UserModel.custName);
        new TitleBuder(this).setTitleText("添加" + CType).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.add_card01_aq, R.id.add_card01_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_card01_aq:
                WebUtilActivity.InWeb(this, "https://m.zhongan.com/p/85132614", "", null);
                break;
            case R.id.add_card01_submit:
                String number = addCard0Number.getText().toString().trim();
                if (number.isEmpty()) {
                    Toast.makeText(this, "请认真填写相关信息", Toast.LENGTH_LONG).show();
                    return;
                }
                queryBankNo(number);

                break;

        }
    }

    public void startA(String number, String type) {
        AddCard01Activity.type = type;
        Intent intent = new Intent(this, AddCard02Activity.class);
        intent.putExtra("number", number);
        startActivity(new Intent(intent));
    }

    public void queryBankNo(final String bankNo) {
        //银行代码请求接口 url
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=" + bankNo + "&cardBinCheck=true";
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, url, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("validated")) {
                        if (type.equals(jsonObject.getString("cardType"))) {
                            startA(bankNo, jsonObject.getString("cardType"));
                        }else {
                            Toast.makeText(getApplication(), "请输入 ‘"+CType+"’ 卡号", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplication(), "请检查银行卡号码", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), "网络请求错误", Toast.LENGTH_LONG).show();
            }
        }));

    }
}
