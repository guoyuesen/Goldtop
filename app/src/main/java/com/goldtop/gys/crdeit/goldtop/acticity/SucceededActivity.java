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
    }

    @OnClick({R.id.succeeded_wancheng, R.id.succeeded_chakan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.succeeded_wancheng:
                break;
            case R.id.succeeded_chakan:
                startActivity(new Intent(this,RepaymentMsgActivity.class));
                break;
        }
    }
}
