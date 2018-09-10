package com.goldtop.gys.crdeit.goldtop.acticity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

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
            case "AGENT":
                ratesImg.setImageResource(R.mipmap.rates7);
                break;
            case "MEMBER":
                ratesImg.setImageResource(R.mipmap.rates6);
                break;
            case "VIP":
                ratesImg.setImageResource(R.mipmap.rates4);
                break;
            case "NORMAL":
                ratesImg.setImageResource(R.mipmap.rates5);
                break;
        }
    }

    @OnClick(R.id.rates_submit)
    public void onClick() {

    }
}
