package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.goldtop.gys.crdeit.goldtop.Adapters.AdderssAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/10.
 */

public class AddressActivity extends BaseActivity {
    @Bind(R.id.address_list)
    ListView addressList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("地址管理").setRightText("添加新地址").setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddressActivity.this,AddAdderssActivity.class));
            }
        });
        JSONArray array = new JSONArray();
        addressList.setAdapter(new AdderssAdapter(this,array));
    }
}
