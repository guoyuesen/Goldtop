package com.goldtop.gys.crdeit.goldtop.acticity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/8/17.
 */

public class RatesActivity extends BaseActivity {
    @Bind(R.id.rates_img)
    ImageView ratesImg;
    @Bind(R.id.rates_name)
    TextView rates_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setBackgrund(Color.parseColor("#00000000")).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("交易费率");

        switch (UserModel.custLevelSample) {
            case "NORMAL"://普通用户
                rates_name.setText("普通用户");
                break;
            case "MEMBER"://会员
                rates_name.setText("会员");
                break;
            case "NEW_MEMBER"://VIP会员
                rates_name.setText("VIP会员");
                break;
            case "MANAGER"://VIP1
                rates_name.setText("VIP1");
                break;
            case "CHIEF"://VIP2
                rates_name.setText("VIP2");
                break;
            case "VIP3"://VIP3
                rates_name.setText("VIP3");
                break;
            case "AGENT"://合伙人VIP4
                rates_name.setText("合伙人VIP4");
                break;
            case "NORMAL1":
                rates_name.setText("VIP1");
                break;
            case "NORMAL2":
                rates_name.setText("VIP2");
                break;
            case "NORMAL3":
                rates_name.setText("VIP3");
                break;
            case "NORMAL4":
                rates_name.setText("VIP4");
                break;
            case "NORMAL5":
                rates_name.setText("VIP5");
                break;

            /*case "MEMBER":
                ratesImg.setImageResource(R.mipmap.rates6);
                break;
            case "MANAGER":
                ratesImg.setImageResource(R.mipmap.rates8);
                break;
            case "CHIEF":
                ratesImg.setImageResource(R.mipmap.rates9);
                break;
            case "AGENT":
                ratesImg.setImageResource(R.mipmap.rates7);
                break;*/
            default:
                rates_name.setText("会员");
        }
    }

    @OnClick(R.id.rates_submit)
    public void onClick() {

    }
}
