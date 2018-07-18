package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
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

public class ReceivablesActivity extends BaseActivity {
    @Bind(R.id.receivables_card01)
    TextView receivablesCard01;
    @Bind(R.id.receivables_card02)
    TextView receivablesCard02;
    @Bind(R.id.receivables_money)
    EditText receivablesMoney;
    /*@Bind(R.id.receivables_cvn)
    EditText receivablesCvn;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receivables);
        hiedBar(this);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("收款");
    }

    @OnClick(R.id.receivables_submit)
    public void onClick() {
        startActivity(new Intent(this,ScheduleActivity.class));
    }
}
