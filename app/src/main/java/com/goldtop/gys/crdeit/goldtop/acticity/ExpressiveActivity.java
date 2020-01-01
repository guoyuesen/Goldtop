package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    Double mo = 0.00d;
    @Bind(R.id.envelopes_ktx_money)
    TextView envelopesKtxMoney;
    @Bind(R.id.envelopes_money_all)
    TextView envelopesMoneyAll;

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
        mo = getIntent().getDoubleExtra("money", 0.00d);
        envelopesKtxMoney.setText(MoneyUtils.getShowMoney(mo/100));
        envelopesMoneyAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txMoney.setText(MoneyUtils.getShowMoney(mo/100));
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
                String money = txMoney.getText().toString().trim();
                float m = Float.parseFloat(money);
                if (money.isEmpty() || m < 100) {
                    Toast.makeText(ExpressiveActivity.this, "请输入正确的金额", Toast.LENGTH_LONG).show();
                    return;
                }
                if (m % 100 != 0) {
                    Toast.makeText(ExpressiveActivity.this, "请输入100的整数倍", Toast.LENGTH_LONG).show();
                    return;
                }
                if (number.isEmpty()) {
                    Toast.makeText(ExpressiveActivity.this, "请选择储蓄卡", Toast.LENGTH_LONG).show();
                    return;
                }
                Map<String, String> map = new HashMap<String, String>();
                map.put("custId", UserModel.custId);
                map.put("money", m * 100 + "");
                map.put("cardNo", number);
                Httpshow(this);
                MyVolley.addRequest(new formRequest(Request.Method.GET,Action.withdrawFromIncome+"?custId="+UserModel.custId+"&money="+m * 100+"&cardNo="+number, map, new MyVolleyCallback(this) {
                    @Override
                    public void CallBack(JSONObject jsonObject) {
                        Httpdismiss();
                        try {
                            if (jsonObject.getInt("code") == 1) {
                                //Toast.makeText(ExpressiveActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                AlertDialog dialog = new AlertDialog.Builder(ExpressiveActivity.this).setMessage("提现申请成功，资金将在24小时内到账(节假日顺延)，请注意查收!").setNeutralButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                }).create();
                                dialog.show();
                            } else {
                                Toast.makeText(ExpressiveActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Httpdismiss();
                        Log.d("1234165411===","---------------");
                    }
                }));
                break;
        }
    }

    public void getcard(final boolean t) {
        Httpshow(this);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.queryBankCard + "?custId=" + UserModel.custId + "&cardType=D", new HashMap<String, String>(), new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Httpdismiss();
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        JSONObject object1 = jsonObject.getJSONObject("data");
                        JSONArray array = object1.getJSONArray("bankCardList");
                        if (t && array.length() > 0) {
                            JSONObject object = array.getJSONObject(0);
                            MyVolley.getImage(object.getString("icon"), txIcon);
                            txName.setText(object.getString("bankName"));
                            txKh.setText("尾号 " + object.getString("accountCode").substring(object.getString("accountCode").length() - 4, object.getString("accountCode").length()) + " 储蓄卡");
                            number = object.getString("accountCode");
                        }
                        if (t) {
                            return;
                        }
                        ReceivablesDialogView dialogView = new ReceivablesDialogView(ExpressiveActivity.this, array, new ReceivablesDialogView.backTo() {
                            @Override
                            public void sercsse(String T, JSONObject object) {
                                try {

                                    MyVolley.getImage(object.getString("icon"), txIcon);
                                    txName.setText(object.getString("bankName"));
                                    txKh.setText("尾号 " + object.getString("accountCode").substring(object.getString("accountCode").length() - 4, object.getString("accountCode").length()) + " 储蓄卡");
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
