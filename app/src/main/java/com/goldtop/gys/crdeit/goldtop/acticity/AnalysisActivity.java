package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
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
 * Created by 郭月森 on 2018/7/12.
 */

public class AnalysisActivity extends BaseActivity {
    @Bind(R.id.analysis_toonum)
    TextView analysisToonum;
    @Bind(R.id.analysis_nex)
    TextView analysisNex;
    @Bind(R.id.analysis_vip)
    TextView analysisVip;
    @Bind(R.id.real_num)
    TextView realNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.acitivty_analysis);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("用户分析").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.customerAnalysis + UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        analysisToonum.setText("" + object.getInt("totalNum"));
                        analysisNex.setText("" + object.getString("ztNum"));
                        analysisVip.setText("" + object.getInt("vipNum"));
                        realNum.setText(object.getString("realNum"));
                    } else {
                        Toast.makeText(AnalysisActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
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

    @OnClick({R.id.zt_cust, R.id.vip_cust})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zt_cust:
                DetailedActivity.inActivity(this, "直推用户", 5);
                break;
            case R.id.vip_cust:
                DetailedActivity.inActivity(this, "团队VIP", 6);
                break;
        }
    }
}
