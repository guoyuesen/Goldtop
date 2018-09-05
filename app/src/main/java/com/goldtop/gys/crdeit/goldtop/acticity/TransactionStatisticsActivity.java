package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.MycardFragmentAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.TransactionAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.Fragment.MycardTabFragment;
import com.goldtop.gys.crdeit.goldtop.Fragment.TransactionTabFragment;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/12.
 */

public class TransactionStatisticsActivity extends BaseActivity {
   /* @Bind(R.id.statictics_tabb)
    View staticticsTabb;
    @Bind(R.id.statistics_list)
    ListView list;
    @Bind(R.id.statistics_list1)
    ListView list1;
    JSONArray Yobj = new JSONArray();
    JSONArray Dobj = new JSONArray();
    TransactionAdapter adapter1;
    TransactionAdapter adapter2;*/
   private String[] titles = new String[]{"日报", "月报"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MycardFragmentAdapter adapter;
    //ViewPage选项卡页面列表
    private List<Fragment> mFragments;
    private List<String> mTitles;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_statistics);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setTitleText("交易统计").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
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
        mFragments.add(TransactionTabFragment.newInstance("D"));
        mFragments.add(TransactionTabFragment.newInstance("M"));
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
        /*adapter1 = new TransactionAdapter(this,Dobj);
        adapter2 = new TransactionAdapter(this,Yobj);
        staticticsTabb.setTranslationX(ContextUtil.getX(this) / 6 + ContextUtil.dip2px(this, 30));
        list.setAdapter(adapter1);
        list1.setAdapter(adapter2);
        Map<String,String> mapd = new HashMap<String, String>();
        mapd.put("custId", UserModel.custId);
        mapd.put("analysisType","D");
        MyVolley.addRequest(new formRequest(Action.tradeDetail, mapd, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        Dobj = jsonObject.getJSONArray("data");
                        adapter1.notifyDataSetChanged(Dobj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
        Map<String,String> mapm = new HashMap<String, String>();
        mapm.put("custId", UserModel.custId);
        mapm.put("analysisType","M");
        MyVolley.addRequest(new formRequest(Action.tradeDetail, mapm, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        Yobj = jsonObject.getJSONArray("data");
                        adapter2.notifyDataSetChanged(Yobj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));*/
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


   /* @OnClick({R.id.statictics_tabhost1, R.id.statictics_tabhost3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.statictics_tabhost1:
                staticticsTabb.setTranslationX(ContextUtil.getX(this) / 6 + ContextUtil.dip2px(this, 20));
                list.setVisibility(View.VISIBLE);
                list1.setVisibility(View.GONE);
                break;
            case R.id.statictics_tabhost3:
                staticticsTabb.setTranslationX(ContextUtil.getX(this) / 2 + ContextUtil.dip2px(this, 80));
                list1.setVisibility(View.VISIBLE);
                list.setVisibility(View.GONE);
                break;
        }
    }*/
}
