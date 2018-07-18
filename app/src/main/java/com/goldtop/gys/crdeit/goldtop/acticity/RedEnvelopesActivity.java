package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/13.
 */

public class RedEnvelopesActivity extends BaseActivity {
    @Bind(R.id.envelopes_money)
    TextView envelopesMoney;
    @Bind(R.id.envelopes_out_money)
    EditText envelopesOutMoney;
    @Bind(R.id.envelopes_ktx_money)
    TextView envelopesKtxMoney;
    private float money = 300.00f;
    private float ktx = 231.00f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_envelopes);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("红包");
        envelopesKtxMoney.setText(""+ktx);
        envelopesMoney.setText(""+money);
    }

    @OnClick({R.id.envelopes_money_all, R.id.envelopes_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.envelopes_money_all:
                envelopesOutMoney.setText(""+ktx);
                break;
            case R.id.envelopes_submit:
                String m = envelopesOutMoney.getText().toString().trim();
                if (m.isEmpty()){
                    Toast.makeText(this,"请输入金额",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(this,"转入成功",Toast.LENGTH_LONG).show();
                finish();
                break;
        }
    }
}
