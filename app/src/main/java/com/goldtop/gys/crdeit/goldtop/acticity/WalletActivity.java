package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
    Double money = 0.00d;

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
        }).setTitleText("钱包").setRightText("钱包明细").setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailedActivity.inActivity(WalletActivity.this,"钱包明细",1);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        MyVolley.addRequest(new VolleyRequest(Action.totalIncome+ UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        JSONObject object = jsonObject.getJSONObject("data");
                        money = object.getDouble("totalIncomeAmount");
                        walletMoney.setText(MoneyUtils.getShowMoney(money/100.00d));
                        walletMoneyNum.setText(""+(object.getString("incomeCount").equals("null")||(object.getString("incomeCount")==null)?"0":object.getString("incomeCount")));
                        walletUserNum.setText(""+(object.getString("custNum").equals("null")||(object.getString("custNum")==null)?"0":object.getString("custNum")));
                        //walletUserNum.setText(""+object.getString("custNum"));
                    }else {
                        Toast.makeText(WalletActivity.this,"message",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    @OnClick({R.id.wallet_R_01, R.id.wallet_R_02, R.id.wallet_R_03, R.id.wallet_R_04,R.id.wallet_jiaoyi,R.id.wallet_zhitui})
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
                Intent intent = new Intent(this,ExpressiveActivity.class);
                intent.putExtra("money",money);
                startActivity(intent);
                break;
            case R.id.wallet_jiaoyi:
                DetailedActivity.inActivity(WalletActivity.this,"钱包明细",1);
                break;
            case R.id.wallet_zhitui:
                DetailedActivity.inActivity(this, "直推用户", 5);
                break;
        }
    }
}
