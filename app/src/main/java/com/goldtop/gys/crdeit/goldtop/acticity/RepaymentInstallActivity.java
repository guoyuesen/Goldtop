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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.DateButtonListener;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.Pickers;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.CSxzDialogView;
import com.goldtop.gys.crdeit.goldtop.view.DateButton;
import com.goldtop.gys.crdeit.goldtop.view.DateDialogView;
import com.goldtop.gys.crdeit.goldtop.view.PickerDialog;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class RepaymentInstallActivity extends BaseActivity {
    @Bind(R.id.install_exdit)
    EditText installExdit;
    @Bind(R.id.install_xuanze)
    RelativeLayout installXuanze;
    @Bind(R.id.install_money)
    TextView installMoney;
    @Bind(R.id.install_day)
    TextView installDay;
    @Bind(R.id.install_date)
    TextView installDate;
    @Bind(R.id.install_fee)
    TextView installfee;
    @Bind(R.id.install_cs)
    TextView installfcs;
    @Bind(R.id.install_choice)
    TextView installChoice;
    private int day = 0;
    private String money = "";
    private String daystr = "";
    DateDialogView dialogView;
    private String cardid = "";
    boolean s = true;
    String daymoney = "";
    private String city = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment_install);
        BaseActivity.hiedBar(this);
        ButterKnife.bind(this);
        cardid = getIntent().getStringExtra("cardid");
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("设置还款");
        dialogView = new DateDialogView(this, new DateDialogView.DateBack() {
            @Override
            public void callback(String str) {

                int a = str.length() - str.replaceAll(",", "").length();
                //Log.d("==》",str);
                //Log.d("==》",a+1+"");
                daystr = str;
                installDate.setText(str);
                day = a + 1;
                if (day == 0)
                    installDate.setText("请选择 >");
                installDay.setText(day + "天");
                insertUI();
            }
        });
        installExdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                money = charSequence.toString().trim();
                insertUI();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick({R.id.install_submit, R.id.install_xuanze, R.id.repayment_pay_cs, R.id.install_picker})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.install_submit:
                if (s) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("温馨提示");
                    builder.setMessage("执行此计划需要卡余额" + daymoney + "元，请确保卡余额度充足，不足请充值");
                    builder.setPositiveButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            submit();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                break;
            case R.id.install_xuanze:
                dialogView.show();
                break;
            case R.id.repayment_pay_cs:
                new CSxzDialogView(this, new CSxzDialogView.CsBack() {
                    @Override
                    public void Back(String c,String C, String S, String s) {
                        city = s;
                        installfcs.setText(c+C);
                    }
                }).show();
                break;
            case R.id.install_picker:
                showPicker();
                break;
        }

    }

    private void showPicker() {
        List<List<Pickers>> lists = new ArrayList<List<Pickers>>();
        List<Pickers> pickers0 = new ArrayList<Pickers>();
        List<Pickers> pickers1 = new ArrayList<Pickers>();
        for (int i = 1; i < 31; i++) {
            Pickers pickers = new Pickers("每月" + i + "日", "" + i);
            pickers0.add(pickers);
            pickers1.add(pickers);
        }
        lists.add(pickers0);
        lists.add(pickers1);
        new PickerDialog(this, lists, new String[]{"账单日", "还款日"}, new PickerDialog.PickerBack() {
            @Override
            public void back(String[] choices) {
                installChoice.setText("账单日：每月"+choices[0]+"号 还款日：每月"+choices[1]+"号");
                //Toast.makeText(RepaymentInstallActivity.this, choices[0] + "-" + choices[1], Toast.LENGTH_LONG).show();
            }
        }).show();
    }

    private void submit() {
        if (day == 0 || money.isEmpty()) {
            Toast.makeText(this, "计划金额或计划时间不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        s = false;
        Map<String, String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("paymentAmt", money);
        map.put("paymentDate", daystr);
        map.put("cardCode", cardid);
        if (!city.isEmpty()) {
            map.put("city", city);
        }
        Httpshow(this);
        JSONObject o = new JSONObject(map);
        Log.d("请求参数：==》", o.toString());
        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, Action.paymentApply+"?token="+UserModel.token, o, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("返回参数：==》", response.toString());
                try {
                    if (response.getString("code").equals("1")) {
                        JSONObject object = response.getJSONObject("data");
                        Intent intent = new Intent(RepaymentInstallActivity.this, SucceededActivity.class);
                        intent.putExtra("card", cardid);
                        intent.putExtra("Money1", money);
                        intent.putExtra("Money2", daymoney);
                        intent.putExtra("Day", "" + day);
                        intent.putExtra("applyId", object.getString("applyId"));
                        startActivity(intent);
                        finish();
                    }else if ("NOC".equals(response.getString("code"))){

                        openCardMsg(cardid);
                    }else {

                        Toast.makeText(RepaymentInstallActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                    }
                    s = true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Httpdismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("错误参数：==》", "error.getMessage()");
                s = true;
                Httpdismiss();
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        MyVolley.addRequest(request);
    }

    private void openCardMsg(String cardid) {
        JsonRequest<JSONObject> request = new JsonObjectRequest(Action.openCardSms + cardid, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if ("1".equals(response.getString("code"))){
                        JSONObject object = response.getJSONObject("data");
                        showDialogmy(cardid,object.getString("bizOrderNumber"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        MyVolley.addRequest(request);
    }

    private void insertUI() {
        Log.d("==》", day + "----" + money);
        if (day == 0 || money.isEmpty())
            return;
        Map<String, String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("paymentAmt", money);
        map.put("paymentDates", daystr);
        MyVolley.addRequest(new VolleyRequest(Action.calcReserveMoney, map, new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        installfee.setText("服务费率" + object.getDouble("rate") * 10000d / 100d + "%，总计" + object.getDouble("fee") + "元");
                        daymoney = object.getString("totalReserveMoney");
                        installMoney.setText(daymoney + "元");
                    } else {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialogView.dismiss();
    }

    private void showDialogmy(String acid,String bizOrderNumber) throws JSONException {
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
        phone.setText("短信验证码已发送");

        view.findViewById(R.id.show_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = e1.getText().toString().trim() + e2.getText().toString().trim() + e3.getText().toString().trim() + e4.getText().toString().trim() + e5.getText().toString().trim() + e6.getText().toString().trim();
                if (code.length() != 6) {
                    Toast.makeText(RepaymentInstallActivity.this, "请认真输入验证码", Toast.LENGTH_LONG).show();
                } else {
                    openCardMsg(acid,bizOrderNumber,code);
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
    public void setN(EditText e1) {
        e1.setFocusable(true);
        e1.setFocusableInTouchMode(true);
        e1.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(e1, 0);
    }
    private void openCardMsg(String cardid,String bizOrderNumber,String code){
        JsonRequest<JSONObject> request = new JsonObjectRequest(Action.openCardDe + cardid+"?bizOrderNumber="+bizOrderNumber+"&smsCode="+code, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if ("1".equals(response.getString("code"))){
                        submit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        MyVolley.addRequest(request);
    }
}
