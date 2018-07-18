package com.goldtop.gys.crdeit.goldtop.acticity;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.goldtop.gys.crdeit.goldtop.Adapters.FindAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.MsgAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.NewsAdapter;
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

public class NewsActivity extends BaseActivity{
    @Bind(R.id.news_tabb)
    View newTabb;
    @Bind(R.id.news_tab1)
    ListView newTab1;
    @Bind(R.id.news_tab2)
    ListView newTab2;
    @Bind(R.id.news_tab3)
    ListView newTab3;
    private ListView listView;
    private View view;
    private FindAdapter adapter1;
    private int Vx;
    private int Vy;
    int W;
    int a = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiedBar(this);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("消息中心").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        newTab1.setVisibility(View.VISIBLE);
        JSONArray array = new JSONArray();
        array.put(1);
        array.put(1);
        newTab1.setAdapter(new NewsAdapter(this, array));
        JSONArray array1 = new JSONArray();
        array1.put(1);
        array1.put(1);
        newTab2.setAdapter(new MsgAdapter(this, array1));
        JSONArray array2 = new JSONArray();
        array2.put(1);
        array2.put(1);
        array2.put(1);
        newTab3.setAdapter(new MsgAdapter(this, array2));

        listView = newTab1;
        Vx = (int) newTabb.getX();
        Vy = (int) newTabb.getY();
        W = ContextUtil.getX(this)/8+ContextUtil.dip2px(this,20);
        newTabb.setTranslationX(W);
    }
    /*@Override
    public View onCreatea(@Nullable Bundle savedInstanceState) {

        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.news, container, false);

            return view;
        }
    }*/
    @OnClick({R.id.news_tabhost1, R.id.news_tabhost2, R.id.news_tabhost3})
    public void onClick(View view) {
        listView.setVisibility(View.GONE);
        newTabb.setTranslationX(W);
        switch (view.getId()){
            case R.id.news_tabhost1:
                newTab1.setVisibility(View.VISIBLE);
                listView = newTab1;
                newTabb.setTranslationX(W);
                break;
            case R.id.news_tabhost2:
                newTab2.setVisibility(View.VISIBLE);
                listView = newTab2;
                newTabb.setTranslationX(W+ContextUtil.getX(this)/3);
                break;
            case R.id.news_tabhost3:
                newTab3.setVisibility(View.VISIBLE);
                listView = newTab3;
                newTabb.setTranslationX(W+ContextUtil.getX(this)/3*2);
                break;
            /*case R.id.news_tabhost4:
                newTab4.setVisibility(View.VISIBLE);
                listView = newTab4;
                newTabb.setTranslationX(W+ContextUtil.getX(this)/4*3);
                break;*/
        }
    }
   /* @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife.unbind(this);
    }*/
}
