package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.goldtop.gys.crdeit.goldtop.Adapters.RepaymentMsgAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/4.
 */

public class RepaymentCreateActivity extends BaseActivity {

    @Bind(R.id.repayment_list)
    ListView repaymentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_repayment_msg);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("生成计划").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        View hendrview = LayoutInflater.from(this).inflate(R.layout.item_repayment_creat_top,null);
        repaymentList.addHeaderView(hendrview);
        View bommview = LayoutInflater.from(this).inflate(R.layout.item_repayment_creat_bomm,null);
        bommview.findViewById(R.id.creat_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RepaymentCreateActivity.this,RepaymentMsgActivity.class));
            }
        });
        repaymentList.addFooterView(bommview);
        JSONArray array = new JSONArray();
        array.put(1);
        array.put(1);
        array.put(1);
        array.put(1);
        array.put(1);
        repaymentList.setAdapter(new RepaymentMsgAdapter(this,array));
    }
}
