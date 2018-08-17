package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.goldtop.gys.crdeit.goldtop.Adapters.FindAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.Orderdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class OrderActivity extends BaseActivity{
    @Bind(R.id.order_tabb)
    View orderTabb;
    @Bind(R.id.order_tab1)
    ListView orderTab1;
    @Bind(R.id.order_tab2)
    ListView orderTab2;
    @Bind(R.id.order_tab3)
    ListView orderTab3;
    @Bind(R.id.order_tab4)
    ListView orderTab4;
    private ListView listView;
    int W;
    int a = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_order);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setTitleText("我的订单");
        ButterKnife.bind(this);
        orderTab1.setVisibility(View.VISIBLE);
        JSONArray array = new JSONArray();

        orderTab1.setAdapter(new Orderdapter(this, array));
        JSONArray array1 = new JSONArray();

        orderTab2.setAdapter(new Orderdapter(this, array1));
        JSONArray array2 = new JSONArray();

        orderTab3.setAdapter(new Orderdapter(this, array2));
        JSONArray array3 = new JSONArray();

        orderTab4.setAdapter(new Orderdapter(this, array3));
        listView = orderTab1;
        W = ContextUtil.getX(this)/8+ContextUtil.dip2px(this,15);
        orderTabb.setTranslationX(W);
    }


    @OnClick({R.id.order_tabhost1, R.id.order_tabhost2, R.id.order_tabhost3, R.id.order_tabhost4})
    public void onClick(View view) {
        listView.setVisibility(View.GONE);
        orderTabb.setTranslationX(W);
        switch (view.getId()){
            case R.id.order_tabhost1:
                orderTab1.setVisibility(View.VISIBLE);
                listView = orderTab1;
                orderTabb.setTranslationX(W);
                break;
            case R.id.order_tabhost2:
                orderTab2.setVisibility(View.VISIBLE);
                listView = orderTab2;
                orderTabb.setTranslationX(W+ContextUtil.getX(this)/4);
                break;
            case R.id.order_tabhost3:
                orderTab3.setVisibility(View.VISIBLE);
                listView = orderTab3;
                orderTabb.setTranslationX(W+ContextUtil.getX(this)/2);
                break;
            case R.id.order_tabhost4:
                orderTab4.setVisibility(View.VISIBLE);
                listView = orderTab4;
                orderTabb.setTranslationX(W+ContextUtil.getX(this)/4*3);
                break;
        }
    }

}
