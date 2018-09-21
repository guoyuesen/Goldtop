package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.android.volley.VolleyError;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Fragment.ShpingFragment;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                        Toast.makeText(VipActivity.this, "支付成功,请重新登录以更新数据", Toast.LENGTH_SHORT).show();

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
                getPayinfo(0);
            }
        });
        findViewById(R.id.vip_vip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayinfo(1);
            }
        });
        TextView phone = findViewById(R.id.vip_phone);
        phone.setText(UserModel.custMobile);
        /*if (UserModel.custLevelSample.equals("VIP")){


            findViewById(R.id.vip_submit).setVisibility(View.GONE);
        }*/
        TextView textView = findViewById(R.id.vip_text);
        List<Integer> ins = new ArrayList<>();
        switch (UserModel.custLevelSample){
            case "AGENT":
                textView.setText("您是尊贵的企业账户");
                ins.add(R.mipmap.vip__03);
                break;
            case "MEMBER":
                textView.setText("您的级别是会员");
                ins.add(R.mipmap.vip__02);
                ins.add(R.mipmap.vip__01);
                break;
            case "VIP":
                textView.setText("您是尊贵的VIP会员");
                ins.add(R.mipmap.vip__01);
                /*textView.setText("");
                ins.add(R.mipmap.testpayimg);*/
                break;
            case "NORMAL":
                textView.setText("您的级别是普通用户");
                ins.add(R.mipmap.vip__02);
                ins.add(R.mipmap.vip__01);
                break;
        }


        ConvenientBanner<Integer> convenientBanner = findViewById(R.id.vip_f_t_img);
        convenientBanner.setPages(new CBViewHolderCreator<VipActivity.ImageViewHolder>() {
            @Override
            public VipActivity.ImageViewHolder createHolder() {
                return new VipActivity.ImageViewHolder();
            }
        },ins).setPageIndicator(new int[]  {R.drawable.button_r_c,R.drawable.button_r_f})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPointViewVisible(true)
        ; //设置指示器的方向水平  居中
    }
    public class ImageViewHolder implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }
        @Override
        public void UpdateUI(Context context, int position, Integer data) {

            imageView.setImageResource(data);
        }
    }
    private void getPayinfo(int i){
        if (UserModel.custId.isEmpty()){
            Toast.makeText(this,"请登录",Toast.LENGTH_LONG).show();
            return;
        }
        if (i == 0&&(!UserModel.custLevelSample.equals("MEMBER")&&!UserModel.custLevelSample.equals("NORMAL"))){
            Toast.makeText(this,"您已是VIP或以上级别，升级请拨打招商电话",Toast.LENGTH_LONG).show();
            return;
        }
        if (i==1&&!UserModel.custLevelSample.equals("NORMAL")){
            Toast.makeText(this,"您已是会员或以上级别，升级VIP或拨打招商电话",Toast.LENGTH_LONG).show();
            return;
        }
        Map<String,String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        if (i==0) {
            map.put("type", "V");
        }else {
            map.put("type", "M");
        }
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
