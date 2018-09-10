package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/12.
 */

public class ScheduleActivity extends BaseActivity {
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.money)
    TextView money;
    @Bind(R.id.card)
    TextView card;
    @Bind(R.id.time1)
    TextView time1;
    @Bind(R.id.money1)
    TextView money1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_schedule);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("收款").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        time.setText(ContextUtil.dataTostr(new Date().getTime(), "yyyy-MM-dd HH:mm"));
        time1.setText(ContextUtil.dataTostr(new Date().getTime(), "yyyy-MM-dd HH:mm"));
        money.setText("￥"+getIntent().getStringExtra("money"));
        money1.setText("￥"+getIntent().getStringExtra("sxf"));
        card.setText(getIntent().getStringExtra("card") + " " + UserModel.custName);
    }

    @OnClick(R.id.submit)
    public void onClick() {
        finish();
    }
}
