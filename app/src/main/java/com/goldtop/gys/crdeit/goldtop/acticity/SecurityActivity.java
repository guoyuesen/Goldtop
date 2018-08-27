package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.GraphicUnlocking;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/8/16.
 */

public class SecurityActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setTitleText("安全设置").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.shoushimima)
    public void onClick() {
        //GraphicActivity.isLogin = false;
        //startActivity(new Intent(this,GraphicActivity.class));
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.putExtra("from","pass");
        startActivity(intent);
    }
}
