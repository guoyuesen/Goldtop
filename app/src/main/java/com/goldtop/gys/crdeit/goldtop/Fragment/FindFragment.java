package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Adapters.FindAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class FindFragment extends Fragment{
    @Bind(R.id.fragment_find_tabb)
    View fragmentFindTabb;
    @Bind(R.id.fragment_find_tab1)
    ListView fragmentFindTab1;
    @Bind(R.id.fragment_find_tab2)
    ListView fragmentFindTab2;
    @Bind(R.id.fragment_find_tab3)
    ListView fragmentFindTab3;
    @Bind(R.id.fragment_find_tab4)
    ListView fragmentFindTab4;
    private ListView listView;
    private View view;
    private FindAdapter adapter1;
    private int Vx;
    private int Vy;
    int W;
    int a = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_find, container, false);
            ButterKnife.bind(this,view);
            fragmentFindTab1.setVisibility(View.VISIBLE);
            JSONArray array = new JSONArray();
            array.put(1);
            fragmentFindTab1.setAdapter(new FindAdapter(getActivity(), array));
            JSONArray array1 = new JSONArray();
            array1.put(1);
            array1.put(1);
            fragmentFindTab2.setAdapter(new FindAdapter(getActivity(), array1));
            JSONArray array2 = new JSONArray();
            array2.put(1);
            array2.put(1);
            array2.put(1);
            fragmentFindTab3.setAdapter(new FindAdapter(getActivity(), array2));
            JSONArray array3 = new JSONArray();
            array3.put(1);
            array3.put(1);
            array3.put(1);
            array3.put(1);
            fragmentFindTab4.setAdapter(new FindAdapter(getActivity(), array3));
            listView = fragmentFindTab1;
            Vx = (int) fragmentFindTabb.getX();
            Vy = (int) fragmentFindTabb.getY();
            W = ContextUtil.getX(getActivity())/8+ContextUtil.dip2px(getContext(),20);
            fragmentFindTabb.setTranslationX(W);
            return view;
        }
    }
    @OnClick({R.id.fragment_find_tabhost1, R.id.fragment_find_tabhost2, R.id.fragment_find_tabhost3, R.id.fragment_find_tabhost4})
    public void onClick(View view) {
        listView.setVisibility(View.GONE);
        fragmentFindTabb.setTranslationX(W);
        switch (view.getId()){
            case R.id.fragment_find_tabhost1:
                fragmentFindTab1.setVisibility(View.VISIBLE);
                listView = fragmentFindTab1;
                fragmentFindTabb.setTranslationX(W);
                break;
            case R.id.fragment_find_tabhost2:
                fragmentFindTab2.setVisibility(View.VISIBLE);
                listView = fragmentFindTab2;
                fragmentFindTabb.setTranslationX(W+ContextUtil.getX(getActivity())/4);
                break;
            case R.id.fragment_find_tabhost3:
                fragmentFindTab3.setVisibility(View.VISIBLE);
                listView = fragmentFindTab3;
                fragmentFindTabb.setTranslationX(W+ContextUtil.getX(getActivity())/2);
                break;
            case R.id.fragment_find_tabhost4:
                fragmentFindTab4.setVisibility(View.VISIBLE);
                listView = fragmentFindTab4;
                fragmentFindTabb.setTranslationX(W+ContextUtil.getX(getActivity())/4*3);
                break;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife.unbind(this);
    }
}
