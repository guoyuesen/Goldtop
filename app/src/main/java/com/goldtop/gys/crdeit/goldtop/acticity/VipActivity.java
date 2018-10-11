package com.goldtop.gys.crdeit.goldtop.acticity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.CardAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.CardTransformer;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/9.
 */

public class VipActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;
    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.vip_text)
    TextView vipText;
    @Bind(R.id.vip_layout_vip)
    LinearLayout vipLayoutVip;
    @Bind(R.id.vip_layout_hy)
    LinearLayout vipLayoutHy;
    @Bind(R.id.vip_layout_qyzh)
    LinearLayout vipLayoutQyzh;
    @Bind(R.id.vip_submit)
    TextView vipSubmit;
    @Bind(R.id.vip_vip)
    TextView vipVip;
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
        }

        ;
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("会员特权").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setBackgrund(Color.parseColor("#ffffff"));
        vipSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayinfo(0);
            }
        });
        vipVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayinfo(1);
            }
        });
        int[] ins = new int[0];
        switch (UserModel.custLevelSample) {
            case "AGENT":
                vipText.setText("尊敬的企业账号用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您将享受以下专属权益");
                ins = new int[]{R.mipmap.vip__03};
                vipLayoutVip.setVisibility(View.GONE);
                vipLayoutHy.setVisibility(View.GONE);
                vipLayoutQyzh.setVisibility(View.VISIBLE);
                break;
            case "MEMBER":
                vipText.setText("尊敬的会员用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您将享受以下专属权益");
                ins = new int[]{R.mipmap.vip__02, R.mipmap.vip__01};
                vipLayoutVip.setVisibility(View.GONE);
                vipLayoutHy.setVisibility(View.VISIBLE);
                vipLayoutQyzh.setVisibility(View.GONE);
                break;
            case "VIP":
                vipText.setText("尊敬的VIP用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您将享受以下专属权益");
                ins = new int[]{R.mipmap.vip__01};
                vipLayoutVip.setVisibility(View.VISIBLE);
                vipLayoutHy.setVisibility(View.GONE);
                vipLayoutQyzh.setVisibility(View.GONE);
                break;
            case "NORMAL":
                vipText.setText("尊敬的用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您还不是会员 如果开通会员，您将享受以下专属权益");
                ins = new int[]{R.mipmap.vip__02, R.mipmap.vip__01};
                vipLayoutVip.setVisibility(View.GONE);
                vipLayoutHy.setVisibility(View.VISIBLE);
                vipLayoutQyzh.setVisibility(View.GONE);
                vipSubmit.setVisibility(View.VISIBLE);
                break;
        }
        viewPager = findViewById(R.id.viewpager);
        viewPager.setPageTransformer(false, new CardTransformer());
        viewPager.setOffscreenPageLimit(5);
        viewPager.setPageMargin(-200);
        viewPager.setAdapter(new CardAdapter(this, ins));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        switch (UserModel.custLevelSample) {
                            case "MEMBER":
                                vipText.setText("尊敬的会员用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您将享受以下专属权益");
                                vipLayoutVip.setVisibility(View.GONE);
                                vipLayoutHy.setVisibility(View.VISIBLE);
                                vipLayoutQyzh.setVisibility(View.GONE);
                                vipSubmit.setVisibility(View.GONE);
                                vipVip.setVisibility(View.GONE);
                                break;
                            case "NORMAL":
                                vipText.setText("尊敬的用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您还不是会员 如果开通会员，您将享受以下专属权益");
                                vipLayoutVip.setVisibility(View.GONE);
                                vipLayoutHy.setVisibility(View.VISIBLE);
                                vipLayoutQyzh.setVisibility(View.GONE);
                                vipSubmit.setVisibility(View.VISIBLE);
                                vipVip.setVisibility(View.GONE);
                                break;
                        }
                        vipLayoutVip.setVisibility(View.GONE);
                        vipLayoutHy.setVisibility(View.VISIBLE);
                        vipLayoutQyzh.setVisibility(View.GONE);
                        break;
                    case 1:
                        switch (UserModel.custLevelSample) {
                            case "MEMBER":
                                vipText.setText("尊敬的会员用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您还不是VIP 如果开通，您将享受以下专属权益");
                                vipLayoutVip.setVisibility(View.VISIBLE);
                                vipLayoutHy.setVisibility(View.GONE);
                                vipLayoutQyzh.setVisibility(View.GONE);
                                break;
                            case "NORMAL":
                                vipText.setText("尊敬的用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您还不是VIP 如果开通，您将享受以下专属权益");
                                vipLayoutVip.setVisibility(View.VISIBLE);
                                vipLayoutHy.setVisibility(View.GONE);
                                vipLayoutQyzh.setVisibility(View.GONE);
                                break;
                        }
                        vipLayoutVip.setVisibility(View.VISIBLE);
                        vipLayoutHy.setVisibility(View.GONE);
                        vipLayoutQyzh.setVisibility(View.GONE);
                        vipSubmit.setVisibility(View.GONE);
                        vipVip.setVisibility(View.VISIBLE);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getPayinfo(int i) {
        if (UserModel.custId.isEmpty()) {
            Toast.makeText(this, "请登录", Toast.LENGTH_LONG).show();
            return;
        }
        if (i == 0 && (!UserModel.custLevelSample.equals("MEMBER") && !UserModel.custLevelSample.equals("NORMAL"))) {
            Toast.makeText(this, "您已是VIP或以上级别，升级请拨打招商电话", Toast.LENGTH_LONG).show();
            return;
        }
        if (i == 1 && !UserModel.custLevelSample.equals("NORMAL")) {
            Toast.makeText(this, "您已是会员或以上级别，升级VIP或拨打招商电话", Toast.LENGTH_LONG).show();
            return;
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("custId", UserModel.custId);
        if (i == 0) {
            map.put("type", "V");
        } else {
            map.put("type", "M");
        }
        Httpshow(this);
        MyVolley.addRequest(new formRequest(Action.createOrder, map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("status") == 0) {
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

    private void pay(final String orderInfo) {
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
