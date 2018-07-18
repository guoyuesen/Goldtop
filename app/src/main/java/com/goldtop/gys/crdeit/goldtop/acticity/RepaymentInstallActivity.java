package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class RepaymentInstallActivity extends BaseActivity {
    @Bind(R.id.install_exdit)
    EditText installExdit;
    @Bind(R.id.install_xuanze)
    RelativeLayout installXuanze;
    @Bind(R.id.install_money)
    TextView installMoney;
    @Bind(R.id.install_day)
    TextView installDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment_install);
        BaseActivity.hiedBar(this);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("设置还款");
    }

    @OnClick(R.id.install_submit)
    public void onClick() {
        startActivity(new Intent(this,SucceededActivity.class));
    }
}
