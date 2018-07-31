package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
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
        addCard0Name.setText(UserModel.custName);
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
                WebUtilActivity.InWeb(this,"https://m.zhongan.com/p/85132614","",null);
                break;
            case R.id.add_card01_submit:
                String number = addCard0Number.getText().toString().trim();
                if (number.isEmpty()){
                    Toast.makeText(this,"请认真填写相关信息",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(this,AddCard02Activity.class);
                intent.putExtra("number",number);
                startActivity(new Intent(intent));
                break;

        }
    }
}
