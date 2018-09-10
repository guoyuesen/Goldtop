package com.goldtop.gys.crdeit.goldtop.acticity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.MyCardAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.MycardFragmentAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Fragment.MycardTabFragment;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/11.
 */

public class MyCardActivity extends BaseActivity {

    private String[] titles = new String[]{"储蓄卡", "信用卡"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MycardFragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_card);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setTitleText("我的银行卡").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);

        mTitles = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            mTitles.add(titles[i]);
        }
        mFragments = new ArrayList<>();
        //for (int i = 0; i < mTitles.size(); i++) {
            mFragments.add(MycardTabFragment.newInstance("D"));
            mFragments.add(MycardTabFragment.newInstance("C"));
        //}
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
            params.setMarginStart(150);
            params.setMarginEnd(150);
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    /*@OnClick({R.id.my_card_1, R.id.my_card_2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_card_1:
                t1.setTextColor(Color.parseColor("#000000"));
                t2.setTextColor(Color.parseColor("#cccccc"));
                findViewById(R.id.my_card_11).setVisibility(View.VISIBLE);
                findViewById(R.id.my_card_21).setVisibility(View.GONE);
                myCardList.setVisibility(View.VISIBLE);
                myCardList1.setVisibility(View.GONE);
                getcard("D");
                break;
            case R.id.my_card_2:
                t2.setTextColor(Color.parseColor("#000000"));
                t1.setTextColor(Color.parseColor("#cccccc"));
                findViewById(R.id.my_card_21).setVisibility(View.VISIBLE);
                findViewById(R.id.my_card_11).setVisibility(View.GONE);
                myCardList1.setVisibility(View.VISIBLE);
                myCardList.setVisibility(View.GONE);
                getcard("C");
                break;
        }
    }*/
}
