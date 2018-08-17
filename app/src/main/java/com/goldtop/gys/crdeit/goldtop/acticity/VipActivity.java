package com.goldtop.gys.crdeit.goldtop.acticity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.PayResult;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 郭月森 on 2018/7/9.
 */

public class VipActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;
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
                        Toast.makeText(VipActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(VipActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_vip);
        new TitleBuder(this).setTitleText("会员").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setBackgrund(Color.parseColor("#20ffffff"));
        findViewById(R.id.vip_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayinfo();
            }
        });

        if (UserModel.custLevelSample.equals("VIP")){
            TextView textView = findViewById(R.id.vip_text);
            textView.setText("您已是尊贵的VIP会员");
            findViewById(R.id.vip_submit).setVisibility(View.GONE);
        }
    }
    private void getPayinfo(){
        if (UserModel.custId.isEmpty()){
            Toast.makeText(this,"请登录",Toast.LENGTH_LONG).show();
            return;
        }
        if (UserModel.custLevelSample.equals("VIP")){
            Toast.makeText(this,"您已是VIP用户，升级代理请拨打招商电话",Toast.LENGTH_LONG).show();
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        map.put("tradeMoney","0.01");
        Httpshow(this);
        MyVolley.addRequest(new formRequest(Action.createOrder, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("status")==0){
                        pay(jsonObject.getString("result"));
                    }
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
    private void pay(final String orderInfo){
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(VipActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }
}
