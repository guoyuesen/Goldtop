package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/10.
 */

public class SettionsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("设置").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.settings_01, R.id.settings_02, R.id.settings_03, R.id.settings_04})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings_01:
                break;
            case R.id.settings_02:
                break;
            case R.id.settings_03:
                break;
            case R.id.settings_04:
                break;
        }
    }
}
