package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/9/30.
 */

public class OpenRedActivity extends BaseActivity {
    @Bind(R.id.red_close)
    ImageView redClose;
    @Bind(R.id.red_text)
    TextView redText;
    @Bind(R.id.red_openclose)
    ImageView redOpenclose;
    @Bind(R.id.red_open)
    ImageView redOpen;
    @Bind(R.id.red_rednum)
    TextView redRednum;
    @Bind(R.id.red_iv)
    TextView redIv;
    @Bind(R.id.red_money)
    TextView redMoney;
    @Bind(R.id.red_next)
    Button redNext;
    int redparkeNum = 0;
    boolean isOpen = false;
    JSONObject jsonObject = new JSONObject();
Handler handler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Glide.with(OpenRedActivity.this).load(R.mipmap.redopen).into(redOpen);
        try {
            redparkeNum = jsonObject.getJSONObject("data").getInt("packNum");
            redMoney.setText("获得了" + MoneyUtils.getShowMoney(jsonObject.getJSONObject("data").getInt("money") / 100.00d) + "元红包");
            redMoney.setVisibility(View.VISIBLE);
            redText.setVisibility(View.GONE);
            redOpen.setVisibility(View.GONE);
            redIv.setVisibility(View.VISIBLE);
            if (redparkeNum > 0) {
                redRednum.setText("待拆红包" + redparkeNum + "个");
                redRednum.setVisibility(View.VISIBLE);
                redOpenclose.setVisibility(View.GONE);
                //redOpen.setVisibility(View.VISIBLE);
                redNext.setVisibility(View.VISIBLE);
            } else {
                redRednum.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openred);
        hiedBar(this);
        ButterKnife.bind(this);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.packNum+ UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    redparkeNum = jsonObject.getJSONObject("data").getInt("packNum");
                    if (redparkeNum>0){
                        redRednum.setText("待拆红包"+redparkeNum+"个");
                        redRednum.setVisibility(View.VISIBLE);
                        redOpenclose.setVisibility(View.GONE);
                        redOpen.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.red_open, R.id.red_next,R.id.red_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red_open:
                if (isOpen){
                    break;
                }
                open();
                break;
            case R.id.red_next:
                next();
                break;
            case R.id.red_close:
                finish();
                break;
        }
    }

    private void next() {
        redMoney.setVisibility(View.GONE);
        redText.setVisibility(View.VISIBLE);
        redOpen.setVisibility(View.VISIBLE);
        redIv.setVisibility(View.GONE);
        redNext.setVisibility(View.GONE);
    }
    long optime = 0;
    private void open() {
        optime = new Date().getTime();
        Glide.with(this).load(R.drawable.open).into(redOpen);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.unpack+ UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback(this) {
            @Override
            public void CallBack(final JSONObject jsonObject) {
                OpenRedActivity.this.jsonObject = jsonObject;
                if (new Date().getTime()-optime<2000){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            handler.sendMessage(new Message());
                        }
                    }).start();
                }else {
                    handler.sendMessage(new Message());
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OpenRedActivity.this,"网络请求失败",Toast.LENGTH_LONG).show();
                Glide.with(OpenRedActivity.this).load(R.mipmap.redopen).into(redOpen);
            }
        }));
    }
}
