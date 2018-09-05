package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.goldtop.gys.crdeit.goldtop.Adapters.FindAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.MycardFragmentAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.Orderdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.Fragment.MycardTabFragment;
import com.goldtop.gys.crdeit.goldtop.Fragment.OrderTabFragment;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class OrderActivity extends BaseActivity{
    /*@Bind(R.id.order_tabb)
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
    int a = 1;*/
    private String[] titles = new String[]{"全部", "待发货","待收货","已完成"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MycardFragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
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
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);

        mTitles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.size(); i++) {
        mFragments.add(OrderTabFragment.newInstance(i));
        }
        adapter = new MycardFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mViewPager.setAdapter(adapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来
        try {
            settab();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        /*orderTab1.setVisibility(View.VISIBLE);
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
        orderTabb.setTranslationX(W);*/
    }

    private void settab() throws NoSuchFieldException, IllegalAccessException {
        Class<?> tablayout = mTabLayout.getClass();
        Field tabStrip = tablayout.getDeclaredField("mTabStrip");
        tabStrip.setAccessible(true);
        LinearLayout ll_tab = (LinearLayout) tabStrip.get(mTabLayout);
        for (int i = 0; i < ll_tab.getChildCount(); i++) {
            View child = ll_tab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.setMarginStart(50);
            params.setMarginEnd(50);
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
    /*@OnClick({R.id.order_tabhost1, R.id.order_tabhost2, R.id.order_tabhost3, R.id.order_tabhost4})
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
    }*/

}
