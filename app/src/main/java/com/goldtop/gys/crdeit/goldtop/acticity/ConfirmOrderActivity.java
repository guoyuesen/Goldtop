package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.Utils.PayResult;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.PayDialog;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/11/13.
 */

public class ConfirmOrderActivity extends BaseActivity {
    public static int payType = 1;
    private static final int SDK_PAY_FLAG = 1;
    @Bind(R.id.order_address)
    TextView orderAddress;
    @Bind(R.id.order_nameandphone)
    TextView orderNameandphone;
    @Bind(R.id.order_add_address)
    TextView orderAddAddress;
    @Bind(R.id.order_paytype_text)
    TextView orderPaytypeText;
    @Bind(R.id.order_commodity_img)
    ImageView orderCommodityImg;
    @Bind(R.id.order_commodity_text)
    TextView orderCommodityText;
    @Bind(R.id.order_commodity_money)
    TextView orderCommodityMoney;
    @Bind(R.id.order_commodity_edit)
    EditText orderCommodityEdit;
    @Bind(R.id.order_money01)
    TextView orderMoney01;
    @Bind(R.id.order_money02)
    TextView orderMoney02;
    JSONObject object;
    JSONObject addressObject;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        if (UserModel.custLevelSample.indexOf("NORMAL")!=-1) {
                            UserModel.custLevelSample = "MEMBER";
                        }
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ConfirmOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        String str = getIntent().getStringExtra("itmeObject");
        if (!str.isEmpty()){
            try {
                object = new JSONObject(str);
            } catch (JSONException e) {
            }
        }else {
        }
        new TitleBuder(this).setBackgrund(Color.parseColor("#FFCF20")).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("确认订单");
        hiedBar(this);
        ButterKnife.bind(this);
        initActivity();
    }

    private void initActivity() {
        if (object != null){
            try {
                MyVolley.getImage(object.getString("productPic"),orderCommodityImg);
                orderCommodityText.setText(object.getString("description"));
                orderCommodityMoney.setText("￥ "+ MoneyUtils.getShowMoney(object.getString("price")));
                orderMoney01.setText("￥ "+ MoneyUtils.getShowMoney(object.getString("price")));
                orderMoney02.setText("￥ "+ MoneyUtils.getShowMoney(object.getString("price")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Log.d("-----<<","json未实例化成功");
        }
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.getaddress + UserModel.custId, new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    JSONArray array = jsonObject.getJSONArray("data");
                    if (array.length()>0){
                        addressObject = array.getJSONObject(0);
                        orderAddress.setVisibility(View.VISIBLE);
                        orderNameandphone.setVisibility(View.VISIBLE);
                        orderAddAddress.setVisibility(View.GONE);
                        orderAddress.setText(addressObject.getString("province")+addressObject.getString("city")+addressObject.getString("address"));
                        orderNameandphone.setText(addressObject.getString("receiverName")+"    "+addressObject.getString("telephone"));
                    }else {
                        orderAddress.setVisibility(View.GONE);
                        orderNameandphone.setVisibility(View.GONE);
                        orderAddAddress.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.order_address_to, R.id.order_pay_type, R.id.order_go_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_address_to:
                Intent intent = new Intent(this, AddressActivity.class);
                intent.putExtra("address","123");
                startActivityForResult(intent,1011);
                break;
            case R.id.order_pay_type:
                /*new PayDialog(this, new PayDialog.DialogBack() {
                    @Override
                    public void back(int id) {
                        payType = id;
                        switch (id){
                            case 0:
                                payType = 0;
                                orderPaytypeText.setText("支付宝支付");
                                break;
                            case 1:
                                payType = 1;
                                orderPaytypeText.setText("微信支付");
                                break;
                        }
                    }
                }).show();*/
                break;
            case R.id.order_go_pay:
                if (addressObject == null){
                    Toast.makeText(this,"请设置收货地址",Toast.LENGTH_LONG).show();
                    return;
                }
                if (object == null){
                    Toast.makeText(this,"订单错误",Toast.LENGTH_LONG).show();
                    return;
                }
                final Map<String, String> map = new HashMap<String, String>();

                try {
                    map.put("custId", UserModel.custId);
                    map.put("productId", object.getString("id"));
                    map.put("addressId", addressObject.getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (payType == 0){
                    pay(map);
                }else {
                    wxPay(map);
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1011 && resultCode == 1001)
        {
            String result_value = data.getStringExtra("result");
            try {
                addressObject = new JSONObject(result_value);
                orderAddress.setVisibility(View.VISIBLE);
                orderNameandphone.setVisibility(View.VISIBLE);
                orderAddAddress.setVisibility(View.GONE);
                orderAddress.setText(addressObject.getString("province")+addressObject.getString("city")+addressObject.getString("address"));
                orderNameandphone.setText(addressObject.getString("receiverName")+"    "+addressObject.getString("telephone"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private void pay(Map<String,String> map) {
        Httpshow(this);
        MyVolley.addRequest(new formRequest(Action.createOrder, map, new MyVolleyCallback(this) {
            @Override
            public void CallBack(final JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("status") == 0) {
                        Runnable authRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(ConfirmOrderActivity.this);
                                Map<String, String> result = null;
                                try {
                                    result = alipay.payV2(jsonObject.getString("result"), true);
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    msg.arg1 = payType;
                                    mHandler.sendMessage(msg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        // 必须异步调用
                        Thread authThread = new Thread(authRunnable);
                        authThread.start();
                    }
                } catch (JSONException e) {
                    Log.d("====","6");
                    e.printStackTrace();
                }
                Httpdismiss();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Httpdismiss();
            }
        }));

    }
    private void wxPay(Map<String,String> map){
        Httpshow(this);
        MyVolley.addRequest(new formRequest(Action.wxpay, map, new MyVolleyCallback(this) {
            @Override
            public void CallBack(final JSONObject jsonObject) {

                try {
                    IWXAPI api = WXAPIFactory.createWXAPI(ConfirmOrderActivity.this, null);
                    api.registerApp("wx6ba6c1cfaec6442b");
                    PayReq req = new PayReq();
                    JSONObject object = jsonObject.getJSONObject("data");
                    req.appId			= object.getString("appid");
                    req.partnerId		= object.getString("partnerid");
                    req.prepayId		= object.getString("prepayid");
                    req.nonceStr		= object.getString("noncestr");
                    req.timeStamp		= object.getString("timestamp");
                    req.packageValue	= object.getString("package");
                    req.sign			= object.getString("sign");
                    req.extData			= "optional"; // optional
                    api.sendReq(req);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Httpdismiss();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Httpdismiss();
            }
        }));
    }
}
