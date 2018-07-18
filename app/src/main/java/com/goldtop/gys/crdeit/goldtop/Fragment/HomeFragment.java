package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.goldtop.gys.crdeit.goldtop.Adapters.HomeBankAdapter;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddCard01Activity;
import com.goldtop.gys.crdeit.goldtop.acticity.MyCardActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.NewsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WebUtilActivity;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/2.
 */

public class HomeFragment extends Fragment {
    @Bind(R.id.home_frame_add)
    ImageButton homeFrameAdd;
    @Bind(R.id.home_frame_msg)
    ImageButton homeFrameMsg;
   /* @Bind(R.id.home_frame_btn1)
    RelativeLayout homeFrameBtn1;
    @Bind(R.id.home_frame_btn2)
    RelativeLayout homeFrameBtn2;
    @Bind(R.id.home_frame_btn3)
    RelativeLayout homeFrameBtn3;
    @Bind(R.id.home_frame_btn4)
    RelativeLayout homeFrameBtn4;*/
    @Bind(R.id.home_frame_list)
    ListView homeFrameList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_home, container, false);
        }

        ButterKnife.bind(this, view);
        initActivity();
        return view;
    }

    private void initActivity() {
        homeFrameAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddCard01Activity.class));
            }
        });
        JSONArray array = new JSONArray();
        array.put(1);
        array.put(1);
        array.put(1);
        homeFrameList.setAdapter(new HomeBankAdapter(getContext(),array));
        View hview = LayoutInflater.from(getContext()).inflate(R.layout.item_home_top,null);
        hview.findViewById(R.id.home_frame_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        hview.findViewById(R.id.home_frame_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),MyCardActivity.class));
            }
        });
        hview.findViewById(R.id.home_frame_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        hview.findViewById(R.id.home_frame_btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        homeFrameList.addHeaderView(hview);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_fragm_bomm,null);
        view.findViewById(R.id.home_add_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), AddCard01Activity.class));
            }
        });
        view.findViewById(R.id.home_baoxian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebUtilActivity.InWeb(getContext(),"https://m.zhongan.com/p/85132614","",null);
            }
        });
        homeFrameList.addFooterView(view);
        homeFrameMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getContext(), NewsActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
