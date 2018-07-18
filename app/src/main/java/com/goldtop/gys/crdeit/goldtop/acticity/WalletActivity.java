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
 * Created by 郭月森 on 2018/7/11.
 */

public class WalletActivity extends BaseActivity {
    @Bind(R.id.wallet_money)
    TextView walletMoney;
    @Bind(R.id.wallet_money_num)
    TextView walletMoneyNum;
    @Bind(R.id.wallet_user_num)
    TextView walletUserNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("钱包");
    }

    @OnClick({R.id.wallet_R_01, R.id.wallet_R_02, R.id.wallet_R_03, R.id.wallet_R_04})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.wallet_R_01:
                startActivity(new Intent(this,TransactionStatisticsActivity.class));
                break;
            case R.id.wallet_R_02:
                startActivity(new Intent(this,AnalysisActivity.class));
                break;
            case R.id.wallet_R_03:
                startActivity(new Intent(this,RecommendedAwardsActivity.class));
                break;
            case R.id.wallet_R_04:
                startActivity(new Intent(this,ExpressiveActivity.class));
                break;
        }
    }
}
