package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/12.
 */

public class AddCard02Activity extends BaseActivity {
    @Bind(R.id.add_card02_adderss)
    EditText addCard02Adderss;
    @Bind(R.id.add_card02_phone)
    EditText addCard02Phone;
    @Bind(R.id.add_card02_yxq)
    EditText addCard02Yxq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card02);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("添加银行卡");
    }

    @OnClick(R.id.add_card02_submit)
    public void onClick() {
    }
}
