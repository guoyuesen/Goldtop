package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.interfaces.DateButtonListener;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.DateButton;
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

public class ReceivablesActivity extends BaseActivity {
    @Bind(R.id.receivables_card01)
    TextView receivablesCard01;
    @Bind(R.id.receivables_card02)
    TextView receivablesCard02;
    @Bind(R.id.receivables_money)
    EditText receivablesMoney;
    JSONObject objectin;
    JSONObject objectout;
    @Bind(R.id.shouxuf)
    TextView shouxuf;
    Double sxf;
    String card;
    @Bind(R.id.yhxetext)
    TextView yhxetext;
    @Bind(R.id.yhxeimg)
    ScrollView yhxeimg;
    /*@Bind(R.id.receivables_cvn)
    EditText receivablesCvn;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivables);
        hiedBar(this);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("刷卡收款");
        getcard("D");
        receivablesMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int money = Integer.parseInt(editable.toString().trim().length() == 0 ? "0" : editable.toString().trim());
                if (money > 50000) {
                    money = 50000;
                    receivablesMoney.setText("" + 50000);
                }
                switch (UserModel.custLevelSample) {
                    case "AGENT":
                        sxf = money * 55 / 10000.00d;
                        break;
                    case "MEMBER":
                        sxf = money * 58 / 10000.00d;
                        break;
                    case "VIP":
                        sxf = money * 55 / 10000.00d;
                        break;
                    case "NORMAL":
                        sxf = money * 60 / 10000.00d;
                        break;
                }
                sxf++;
                shouxuf.setText("实时到账，需支付" + MoneyUtils.getShowMoney(sxf) + "元手续费");
            }
        });
    }

    @OnClick({R.id.receivables_submit, R.id.receivables_incard, R.id.receivables_outcard, R.id.yhxetext, R.id.yhxeimg, R.id.yhxeimg1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.receivables_submit:
                //
                getSms();
                break;
            case R.id.receivables_incard:

                break;
            case R.id.receivables_outcard:
                getcard("C");
                break;
            case R.id.yhxetext:
                yhxeimg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.activity_in));
                yhxeimg.setVisibility(View.VISIBLE);
                break;
            case R.id.yhxeimg1:
                yhxeimg.setAnimation(AnimationUtils.loadAnimation(this, R.anim.activity_out));
                yhxeimg.setVisibility(View.GONE);
                break;
        }

    }

    public void getcard(final String t) {
        Httpshow(this);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.queryBankCard + "?custId=" + UserModel.custId + "&cardType=" + t, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Httpdismiss();
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        JSONObject object1 = jsonObject.getJSONObject("data");
                        JSONArray array = object1.getJSONArray("bankCardList");
                        if (t.equals("D") && array.length() > 0) {
                            JSONObject object = array.getJSONObject(0);
                            receivablesCard02.setText(object.getString("bankName") + " (" + object.getString("accountCode").substring(object.getString("accountCode").length() - 4) + ")");
                            objectin = object;
                        } else {
                            ReceivablesDialogView dialogView = new ReceivablesDialogView(ReceivablesActivity.this, array, new ReceivablesDialogView.backTo() {
                                @Override
                                public void sercsse(String T, JSONObject object) {
                                    try {
                                        if (T.equals("C")) {
                                            card = object.getString("bankName") + " (" + object.getString("accountCode").substring(object.getString("accountCode").length() - 4) + ")";
                                            receivablesCard01.setText(card);
                                            objectout = object;
                                        } else {
                                            receivablesCard02.setText(object.getString("bankName") + " (" + object.getString("accountCode").substring(object.getString("accountCode").length() - 4) + ")");
                                            objectin = object;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, t);
                            dialogView.show();
                        }

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

    private void showCode(Map<String, String> map) throws JSONException {
        Httpshow(this);
        MyVolley.addRequest(new formRequest(Action.bigPaySms, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Httpdismiss();
                Log.d("返回参数------", jsonObject.toString());
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        showDialogmy(object.getString("workId"));
                    } else {
                        Toast.makeText(ReceivablesActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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

    private void showDialogmy(final String workId) throws JSONException {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_showcode, null);
        final EditText e1 = view.findViewById(R.id.edit_01);
        final EditText e2 = view.findViewById(R.id.edit_02);
        final EditText e3 = view.findViewById(R.id.edit_03);
        final EditText e4 = view.findViewById(R.id.edit_04);
        final EditText e5 = view.findViewById(R.id.edit_05);
        final EditText e6 = view.findViewById(R.id.edit_06);
        addTextChange(e1, e2, e3, e4, e5, e6);
        setN(e1);
        view.findViewById(R.id.show_dissmis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.show_dissmis1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        DateButton btn = view.findViewById(R.id.show_getcode);
        btn.setText("重新获取验证");
        btn.setNum(60);
        btn.setOnClick(new DateButtonListener() {
            @Override
            public void onClick(View view) {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {

            }
        });
        TextView phone = view.findViewById(R.id.show_codephone);
        phone.setText(objectout.getString("mobileNo"));

        view.findViewById(R.id.show_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = e1.getText().toString().trim() + e2.getText().toString().trim() + e3.getText().toString().trim() + e4.getText().toString().trim() + e5.getText().toString().trim() + e6.getText().toString().trim();
                if (code.length() != 6) {
                    Toast.makeText(ReceivablesActivity.this, "请认真输入验证码", Toast.LENGTH_LONG).show();
                } else {
                    tixian(dialog, workId, code);
                }
            }
        });
        btn.Start();

        dialog.setView(view);
        dialog.show();
    }

    private void addTextChange(final EditText e1, final EditText e2, final EditText e3, final EditText e4, final EditText e5, final EditText e6) {
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1 && i == 0) {
                    setN(e2);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1 && i == 0) {
                    setN(e3);
                }
                if (charSequence.length() == 0 && i == 0) {
                    e1.setText("");
                    setN(e1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1 && i == 0) {
                    setN(e4);
                }
                if (charSequence.length() == 0 && i == 0) {
                    e2.setText("");
                    setN(e1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1 && i == 0) {
                    setN(e5);
                }
                if (charSequence.length() == 0 && i == 0) {
                    e3.setText("");
                    setN(e1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1 && i == 0) {
                    setN(e6);
                }
                if (charSequence.length() == 0 && i == 0) {
                    e4.setText("");
                    setN(e1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        e6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 1 && i == 0) {
                    e1.setFocusable(false);
                    e1.setFocusableInTouchMode(false);
                    e1.requestFocus();
                    View view = getWindow().peekDecorView();
                    if (view != null) {
                        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                if (charSequence.length() == 0 && i == 0) {
                    e5.setText("");
                    setN(e1);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void tixian(final AlertDialog dialog, String id, String code) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("workId", id);
        map.put("smsCode", code);
        Httpshow(this);
        MyVolley.addRequest(new formRequest(Action.bigPay, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                Httpdismiss();
                Log.d("提现返回结果", jsonObject.toString());
                dialog.dismiss();
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        AlertDialog d = new AlertDialog.Builder(ReceivablesActivity.this).setTitle("提示").setMessage("交易申请成功，请注意您的到账信息").setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                stactivity();
                                dialogInterface.dismiss();
                            }
                        }).create();
                        d.show();
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Httpdismiss();
            }
        }));
    }

    private void stactivity() {
        //startActivity(new Intent(this,ScheduleActivity.class));
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra("money", receivablesMoney.getText().toString().trim());
        intent.putExtra("card", card);
        intent.putExtra("sxf", MoneyUtils.getShowMoney(sxf));
        startActivity(intent);
    }

    public void setN(EditText e1) {
        e1.setFocusable(true);
        e1.setFocusableInTouchMode(true);
        e1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(e1, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void getSms() {
        try {
            if (objectin == null) {
                Toast.makeText(this, "请选择储蓄卡", Toast.LENGTH_LONG).show();
                return;
            }
            if (!objectout.getString("bindStatus").equals("REG_SUCCESS")) {
                Toast.makeText(this, "储蓄卡未绑定商户，请联系客服", Toast.LENGTH_LONG).show();
                return;
            }
            if (objectout == null) {
                Toast.makeText(this, "请选择信用卡", Toast.LENGTH_LONG).show();
                return;
            }
            if (!objectout.getString("openStatus").equals("OPEN_SUCCESS")) {
                Toast.makeText(this, "信用卡未进行银联认证，请联系客服", Toast.LENGTH_LONG).show();
                return;
            }
            String money = receivablesMoney.getText().toString().trim();
            if (money.isEmpty()) {
                Toast.makeText(this, "请输入金额", Toast.LENGTH_LONG).show();
                return;
            }
            final int m = Integer.parseInt(money);
                /*if (m>1000){
                    Toast.makeText(this,"为了您的资金安全请输入小于1000的金额",Toast.LENGTH_LONG).show();
                    return;
                }*/
            if (m < 100) {
                Toast.makeText(this, "金额不能小于100元", Toast.LENGTH_LONG).show();
                return;
            }
            Map<String, String> map = new HashMap<String, String>();

            map.put(" custId", UserModel.custId);
            map.put(" cardId", objectout.getString("id"));
            map.put("payAmount", "" + (m * 100));
            showCode(map);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
