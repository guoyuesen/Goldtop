package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;

import com.goldtop.gys.crdeit.goldtop.Adapters.HomeShpingAdapter;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.DetailedActivity;
import com.goldtop.gys.crdeit.goldtop.view.ButtomDialogView;
import com.goldtop.gys.crdeit.goldtop.view.HeaderGridView;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class ShpingFragment extends Fragment {
    @Bind(R.id.home_shping_grid)
    HeaderGridView homeShpingGrid;
    private View view;
    private View bommView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_home_shping, container, false);
            ButterKnife.bind(this, view);
            intActivity();
            return view;
        }
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void intActivity() {
        JSONArray array = new JSONArray();
        array.put(0);
        array.put(0);
        array.put(0);
        array.put(0);
        array.put(0);
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_home_shping_top, null);
        bommView = LayoutInflater.from(getContext()).inflate(R.layout.item_home_shping_bomm, null);
        homeShpingGrid.addHeaderView(view1);
        homeShpingGrid.setAdapter(new HeaderViewListAdapter(null, null, new HomeShpingAdapter(getContext(), array)));
        homeShpingGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JSONArray array1 = new JSONArray();
                for (int j = 0; j < i; j++) {
                    array1.put(0);
                }
                ButtomDialogView dialogView = new ButtomDialogView(getContext(), array1);
                dialogView.show();
            }
        });
        homeShpingGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int getLastVisiblePosition = 0, lastVisiblePositionY = 0;
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //滚动到底部
                    if (absListView.getLastVisiblePosition() == (absListView.getCount() - 1)) {
                        View v = (View) absListView.getChildAt(absListView.getChildCount() - 1);
                        int[] location = new int[2];
                        v.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
                        int y = location[1];

                       // MyLog.d("x" + location[0], "y" + location[1]);
                        if (absListView.getLastVisiblePosition() != getLastVisiblePosition && lastVisiblePositionY != y)//第一次拖至底部
                        {
                            //Toast.makeText(view.getContext(), "已经拖动至底部，再次拖动即可翻页", 500).show();
                            getLastVisiblePosition = absListView.getLastVisiblePosition();
                            lastVisiblePositionY = y;
                            return;
                        } else if (absListView.getLastVisiblePosition() == getLastVisiblePosition && lastVisiblePositionY == y)//第二次拖至底部
                        {
                            //mCallback.execute();
                            homeShpingGrid.addFooterView(bommView);
                        }
                    }

                    //未滚动到底部，第二次拖至底部都初始化
                    getLastVisiblePosition = 0;
                    lastVisiblePositionY = 0;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });

        // homeShpingGrid.addView(view1,0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.jf_mx)
    public void onClick() {
        getActivity().startActivity(new Intent(getContext(), DetailedActivity.class));
    }
}
