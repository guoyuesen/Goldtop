package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/12/19.
 */

public class SpInfoActivity extends BaseActivity {
    @Bind(R.id.sp_info_img)
    ImageView spInfoImg;
    @Bind(R.id.sp_info_money)
    TextView spInfoMoney;
    @Bind(R.id.sp_info_text)
    TextView spInfoText;
    @Bind(R.id.sp_info_msg)
    TextView spInfoMsg;
    @Bind(R.id.sp_info_money1)
    TextView spInfoMoney1;
    JSONObject object;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_info);
        hiedBar(this);
        ButterKnife.bind(this);
        String str = getIntent().getStringExtra("itmeObject");
        if (!str.isEmpty()){
            try {
                object = new JSONObject(str);
            } catch (JSONException e) {
            }
        }else {
        }
        if (object != null){
            try {
                MyVolley.getImage(object.getString("productPic"),spInfoImg);
                spInfoText.setText(object.getString("description"));
                //orderCommodityMoney.setText("￥ "+ MoneyUtils.getShowMoney(object.getString("price")));
                spInfoMoney.setText("￥ "+ MoneyUtils.getShowMoney(object.getString("price")));
                spInfoMoney1.setText("￥ "+ MoneyUtils.getShowMoney(object.getString("price")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            Log.d("-----<<","json未实例化成功");
        }
    }

    @OnClick({R.id.back_to, R.id.sp_info_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_to:
                finish();
                break;
            case R.id.sp_info_submit:
                Intent intent = getIntent();
                intent.setClass(this,ConfirmOrderActivity.class);
                startActivity(intent);
                break;
        }
    }
}
