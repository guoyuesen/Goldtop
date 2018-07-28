package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/12.
 */

public class SucceededActivity extends BaseActivity {
    @Bind(R.id.succeeded_text)
    TextView succeededText;
    String card="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_succeeded);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("计划设置成功");
        card = getIntent().getStringExtra("card");
        succeededText.setText("卡尾号（"+card.substring(card.length()-4)+"）代还款设置成功！ 代还款期间，请确保卡余额保持不变否则会影响还款成功率！");
    }

    @OnClick({R.id.succeeded_wancheng, R.id.succeeded_chakan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.succeeded_wancheng:
                finish();
                break;
            case R.id.succeeded_chakan:
                Intent intent = new Intent(this,RepaymentMsgActivity.class);
                intent.putExtra("card",card);
                startActivity(intent);
                break;
        }
    }
}
