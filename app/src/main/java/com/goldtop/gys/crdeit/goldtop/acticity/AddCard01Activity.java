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

public class AddCard01Activity extends BaseActivity {
    @Bind(R.id.add_card0_name)
    TextView addCard0Name;
    @Bind(R.id.add_card0_number)
    EditText addCard0Number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card01);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setTitleText("添加银行卡").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.add_card01_aq, R.id.add_card01_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_card01_aq:
                break;
            case R.id.add_card01_submit:
                startActivity(new Intent(this,AddCard02Activity.class));
                break;
        }
    }
}
