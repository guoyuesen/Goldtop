package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/10.
 */

public class AuthenticationActivity extends BaseActivity {
    @Bind(R.id.authen_name)
    EditText authenName;
    @Bind(R.id.authen_idnumber)
    EditText authenIdnumber;
    @Bind(R.id.authen_cardnumber)
    EditText authenCardnumber;
    @Bind(R.id.authen_yhmc)
    TextView authenyhmc;
    @Bind(R.id.authen_phone)
    EditText authenPhone;
    @Bind(R.id.authen_khhss)
    TextView authenKhhss;
    @Bind(R.id.authen_khh)
    TextView authenKhh;
    @Bind(R.id.zhxz_t1)
    TextView zhxzT1;
    @Bind(R.id.zhxz_t2)
    TextView zhxzT2;
    @Bind(R.id.view_sheng)
    View viewSheng;
    @Bind(R.id.view_shi)
    View viewShi;
    @Bind(R.id.zhxz_ss)
    ListView zhxzSs;
    @Bind(R.id.zhxz_nr)
    LinearLayout zhxzNr;
    @Bind(R.id.zhxz_bg)
    RelativeLayout zhxzBg;
    @Bind(R.id.pb_load)
    ProgressBar pbLoad;
    @Bind(R.id.pb_text)
    TextView pbText;
    @Bind(R.id.like)
    EditText like;
    JSONArray ar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("实名认证");
        authenCardnumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    // 此处为得到焦点时的处理内容
                    String cardnumber = authenCardnumber.getText().toString().trim();
                    queryBankNo(cardnumber);
                }
            }
        });
        authenCardnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (str.length() > 16 && authenyhmc.getText().toString().trim().isEmpty()) {
                    authenyhmc.setText(AddCard02Activity.getBankName(str.substring(0, 6)));
                }
            }
        });
        final TextView thmc = findViewById(R.id.authen_yhmc);
        thmc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = authenCardnumber.getText().toString().trim();
                String yh = thmc.getText().toString().trim();
                if (yh == null || yh.equals("")) {
                    MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.queryBank + "?bankNo=" + n, new HashMap<String, String>(), new MyVolleyCallback(AuthenticationActivity.this) {
                        @Override
                        public void CallBack(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getInt("code") == 1) {
                                    thmc.setText(jsonObject.getJSONObject("data").getString("bankName"));
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
        });
        initzhxz();
        getList();
        like.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (index == 2) {
                    String l = editable.toString();
                    ar = new JSONArray();
                    if (!l.isEmpty()) {
                        for (int a = 0; a < array3.length(); a++) {
                            try {
                                if (array3.getJSONObject(a).getString("name").indexOf(l) != -1) {
                                    ar.put(array3.getJSONObject(a));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        zhxzSs.setAdapter(new ZhxzAdapter(ar));
                    }else {
                        ar = array3;
                        zhxzSs.setAdapter(new ZhxzAdapter(ar));
                    }
                }

            }
        });
    }


    @OnClick(R.id.authen_submit)
    public void onClick() {
        try {
            String name = authenName.getText().toString().trim();
            final String idnumber = authenIdnumber.getText().toString().trim();
            String cardnumber = authenCardnumber.getText().toString().trim();
            String phone = authenPhone.getText().toString().trim();
            String yhmc = authenyhmc.getText().toString().trim();
            if (!com.goldtop.gys.crdeit.goldtop.Utils.ContextUtil.isIDNumber(idnumber)){
                Toast.makeText(this, "请认真检查输入的身份证号码", Toast.LENGTH_LONG).show();
                return;
            }
            if (name.isEmpty() || idnumber.isEmpty() || cardnumber.isEmpty() || phone.isEmpty() || yhmc.isEmpty() || a0 == -1 || a1 == -1 || a2 == -1) {
                Toast.makeText(this, "请认真填写相关信息", Toast.LENGTH_LONG).show();
                return;
            }

            UserModel.custName = name;
            final Map<String, String> params = new HashMap<String, String>();
            params.put("custId", UserModel.custId);
            params.put("custName", name);
            params.put("idCardNo", idnumber);
            params.put("custMobile", phone);
            final Map<String, String> map = new HashMap<String, String>();
            map.put("custId", UserModel.custId);
            map.put("accountCode", cardnumber);
            map.put("accountName", name);
            map.put("bankName", yhmc.substring(0, yhmc.indexOf("·") != -1 ? yhmc.indexOf("·") : yhmc.length()));
            map.put("openningBankProvince", array1.getJSONObject(a0).getString("code"));
            map.put("openningBankCity", array2.getJSONObject(a1).getString("code"));
            map.put("openingSubBankName", array3.getJSONObject(a2).getString("code"));
            map.put("mobileNo", phone);
            map.put("bankCardType", "D");
            JSONObject object = new JSONObject(map);
            JSONArray array = new JSONArray();
            array.put(object);
            JSONObject jsonObject = new JSONObject(params);
            jsonObject.put("bankCards", array);
            Httpshow(AuthenticationActivity.this);
            Log.d("请求参数：==》", jsonObject.toString());
            MyVolley.addRequest(new JsonObjectRequest(Request.Method.POST, Action.authentication, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("返回参数：==》", response.toString());
                    //Toast.makeText(AuthenticationActivity.this,"实名认证提交成功",Toast.LENGTH_LONG).show();
                    try {
                        if (response.getString("code").equals("1")){
                            UserModel.shiMrenz = "REG_SUCCESS";
                            UserModel.idCardNo = idnumber;
                            AlertDialog dialog = new AlertDialog.Builder(AuthenticationActivity.this)
                                    .setMessage("实名认证成功")
                                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    }).create();
                            dialog.show();
                        }else {
                            AlertDialog dialog = new AlertDialog.Builder(AuthenticationActivity.this)
                                    .setMessage(response.getString("message"))
                                    .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    }).create();
                            dialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Httpdismiss();
                    //finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("错误参数：==》", "error.getMessage()");
                    Toast.makeText(AuthenticationActivity.this, "网络请求失败", Toast.LENGTH_LONG).show();
                    Httpdismiss();
                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryBankNo(final String bankNo) {
        //银行代码请求接口 url
        String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?_input_charset=utf-8&cardNo=" + bankNo + "&cardBinCheck=true";
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, url, new HashMap<String, String>(), new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getBoolean("validated")) {
                        if (!jsonObject.getString("cardType").equals("DC")) {
                            Toast.makeText(getApplication(), "请输入储蓄卡卡号", Toast.LENGTH_LONG).show();
                            return;
                        }
                        authenyhmc.setText(AddCard02Activity.getBankName(bankNo.substring(0, 6)));
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

    int a0 = -1, a1 = -1, a2 = -1;
    int index = 0;
    JSONArray array1 = new JSONArray(), array2 = new JSONArray(), array3 = new JSONArray();
    ZhxzAdapter adapter = new ZhxzAdapter(new JSONArray());

    private void initzhxz() {
        zhxzSs.setAdapter(adapter);
        zhxzSs.setEmptyView(findViewById(R.id.zhxz_em));
        findViewById(R.id.zhxz_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zhxzBg.setVisibility(View.GONE);
            }
        });
        zhxzNr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        authenKhh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zhxzBg.setVisibility(View.VISIBLE);
            }
        });
        zhxzBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
            }
        });
        zhxzT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a1 = -1;
                a2 = -1;
                index = 0;
                zhxzT2.setText("");
                zhxzSs.setAdapter(new ZhxzAdapter(array1));
                like.setVisibility(View.GONE);
            }
        });
        zhxzT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a2 = -1;
                index = 1;
                authenKhh.setText("");
                zhxzSs.setAdapter(new ZhxzAdapter(array2));
                like.setVisibility(View.GONE);
            }
        });
        zhxzSs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    if (index == 2) {
                        adapter.notifyDataSetChanged();
                        TextView textView = (TextView) view;
                        textView.setTextColor(Color.parseColor("#FECE00"));
                        a2 = i;
                        authenKhh.setText(ar.getJSONObject(i).getString("name"));
                        zhxzBg.setVisibility(View.GONE);
                    } else {
                        switch (index) {
                            case 0:
                                a0 = i;
                                zhxzT1.setText(array1.getJSONObject(i).getString("name"));
                                break;
                            case 1:
                                a1 = i;
                                zhxzT2.setText(array2.getJSONObject(i).getString("name"));
                                break;

                        }
                        index++;
                        if (index==2) {
                            like.setVisibility(View.VISIBLE);
                            like.setText("");
                        }
                        getList();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void getList() {
        try {
            final String url;
            Map<String,String> map = new HashMap<>();
            if (index == 1) {
                url = Action.getAnge;// + "?parentCode=" + array1.getJSONObject(a0).getString("code");
                map.put("parentCode",array1.getJSONObject(a0).getString("code"));
            } else if (index == 2) {
                String yhmc = authenyhmc.getText().toString().trim();
                url = Action.getAnge;// + "?parentCode=" + array2.getJSONObject(a1).getString("code") + "&bankName=" + yhmc.substring(0, yhmc.indexOf("·") != -1 ? yhmc.indexOf("·") : yhmc.length());
                map.put("parentCode",array2.getJSONObject(a1).getString("code"));
                map.put("bankName",yhmc.substring(0, yhmc.indexOf("·") != -1 ? yhmc.indexOf("·") : yhmc.length()));
            } else {
                url = Action.getAnge;
            }
            Log.d("<=====>", url);
            pbLoad.setVisibility(View.VISIBLE);
            pbText.setVisibility(View.GONE);
            zhxzSs.setAdapter(new ZhxzAdapter(new JSONArray()));
            MyVolley.addRequest(new formRequest(url, map, new MyVolleyCallback(this) {
                @Override
                public void CallBack(JSONObject jsonObject) {
                    pbLoad.setVisibility(View.GONE);
                    pbText.setVisibility(View.VISIBLE);
                    try {
                        if (jsonObject.getString("code").equals("1")) {
                            if (index == 0) {
                                array1 = jsonObject.getJSONArray("data");
                                zhxzSs.setAdapter(new ZhxzAdapter(array1));
                            } else if (index == 1) {
                                array2 = jsonObject.getJSONArray("data");
                                zhxzSs.setAdapter(new ZhxzAdapter(array2));
                            } else {
                                array3 = jsonObject.getJSONArray("data");
                                ar = array3;
                                zhxzSs.setAdapter(new ZhxzAdapter(ar));
                            }

                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    pbLoad.setVisibility(View.GONE);
                    pbText.setVisibility(View.VISIBLE);
                    pbText.setText("网络请求失败");
                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class ZhxzAdapter extends BaseAdapter {
        JSONArray array;

        public ZhxzAdapter(JSONArray array) {
            this.array = array;
        }

        @Override
        public int getCount() {
            return array.length();
        }

        @Override
        public Object getItem(int i) {
            return array;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            if (view == null) {
                textView = new TextView(AuthenticationActivity.this);
                textView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ContextUtil.dip2px(AuthenticationActivity.this, 40)));
                textView.setGravity(Gravity.CENTER_VERTICAL);
            } else {
                textView = (TextView) view;
            }
            try {
                JSONObject object = array.getJSONObject(i);
                textView.setText(object.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return textView;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (zhxzBg.getVisibility()==View.VISIBLE){
            zhxzBg.setVisibility(View.GONE);
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }


}
