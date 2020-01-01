package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.acticity.AnalysisActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.DetailedActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.ExpressiveActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RecommendedAwardsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.TransactionStatisticsActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/2.
 */

public class WealthFragment extends Fragment {

    @Bind(R.id.money)
    TextView moneyText;
    private View view;
    private Double money = 0.00;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_wealth, container, false);
            ButterKnife.bind(this, view);

            return view;
        }
        //return super.onCreateView(inflater, container, savedInstanceState);

    }

    private void initView() {
        MyVolley.addRequest(new VolleyRequest(Action.totalIncome + UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback(getContext()) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        money = object.getDouble("totalIncomeAmount");
                        moneyText.setText(MoneyUtils.getShowMoney(money / 100.00d));
                       /* walletMoneyNum.setText(""+(object.getString("incomeCount").equals("null")||(object.getString("incomeCount")==null)?"0":object.getString("incomeCount")));
                        walletUserNum.setText(""+(object.getString("custNum").equals("null")||(object.getString("custNum")==null)?"0":object.getString("custNum")));*/
                        //walletUserNum.setText(""+object.getString("custNum"));
                    } else {
                        Toast.makeText(getContext(), "message", Toast.LENGTH_LONG).show();
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

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @OnClick({R.id.shouyiminxi, R.id.jiaoyimingxi, R.id.yonghufenxi, R.id.yaoqinghaoyou, R.id.tixianjilu,R.id.tixian,R.id.zhuanqian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shouyiminxi:
                DetailedActivity.inActivity(getContext(), "钱包明细", 1);
                break;
            case R.id.jiaoyimingxi:
                startActivity(new Intent(getContext(), TransactionStatisticsActivity.class));
                break;
            case R.id.yonghufenxi:
                startActivity(new Intent(getContext(), AnalysisActivity.class));
                break;
            case R.id.yaoqinghaoyou:
                DetailedActivity.inActivity(getContext(), "直推用户", 5);
                break;
            case R.id.tixianjilu:
                DetailedActivity.inActivity(getContext(), "钱包明细", 1);
                break;
            case R.id.tixian:
                Intent intent = new Intent(getContext(), ExpressiveActivity.class);
                intent.putExtra("money", money);
                startActivity(intent);
                break;//
            case R.id.zhuanqian:
                getActivity().startActivity(new Intent(getContext(), RecommendedAwardsActivity.class));
                break;
        }
    }

}
