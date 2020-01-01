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

}
