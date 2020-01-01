package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.goldtop.gys.crdeit.goldtop.Adapters.HomeShpingAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.HomeShpingTopAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.ConfirmOrderActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.DetailedActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.SpInfoActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WebUtilActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.ButtomDialogView;
import com.goldtop.gys.crdeit.goldtop.view.HeaderGridView;
import com.goldtop.gys.crdeit.goldtop.view.HorizontalListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private String id;
    HomeShpingAdapter adapter;

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

        initHead();
        adapter = new HomeShpingAdapter(getContext(), array);
        bommView = LayoutInflater.from(getContext()).inflate(R.layout.item_home_shping_bomm, null);
        homeShpingGrid.setAdapter(new HeaderViewListAdapter(null, null, adapter));

        MyVolley.addRequest(new VolleyRequest("http://www.tuoluo718.com/product/list/point", new ArrayMap<String, String>(), new MyVolleyCallback(getContext()) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    adapter.notifyDataSetChanged(jsonObject.getJSONArray("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
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
                            //homeShpingGrid.addFooterView(bommView);
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
        homeShpingGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"积分余额不足",Toast.LENGTH_LONG).show();
            }
        });

        // homeShpingGrid.addView(view1,0);
    }

    private void initHead() {
        final View view1 = LayoutInflater.from(getContext()).inflate(R.layout.item_home_shping_top, null);
        List<Integer> ins = new ArrayList<>();
        ins.add(R.mipmap.sp_show07);
        ins.add(R.mipmap.shangc3);
        ins.add(R.mipmap.sp_show07);
        HorizontalListView hlist = view1.findViewById(R.id.hor_list);

        final HomeShpingTopAdapter adapter = new HomeShpingTopAdapter(getContext(),new JSONArray());
        hlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
                Intent intent = new Intent(getContext(), SpInfoActivity.class);
                intent.putExtra("itmeObject",adapter.getItemString(i));
                getActivity().startActivity(intent);
            }
        });
        hlist.setAdapter(adapter);
        MyVolley.addRequest(new VolleyRequest("http://www.tuoluo718.com/product/list/money", new HashMap<String, String>(), new MyVolleyCallback(getContext()) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    adapter.notifyDataSetChanged(jsonObject.getJSONArray("data"));
                    initQiangtui(view1,jsonObject.getJSONArray("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
        ConvenientBanner<Integer> convenientBanner = view1.findViewById(R.id.shping_f_t_img);
        convenientBanner.setPages(new CBViewHolderCreator<ImageViewHolder>() {
            @Override
            public ImageViewHolder createHolder() {
                return new ImageViewHolder();
            }
        },ins).setPageIndicator(new int[]  {R.drawable.button_r_c,R.drawable.button_r_f})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPointViewVisible(true)
                .startTurning(3000); //设置指示器的方向水平  居中-
        view1.findViewById(R.id.sp_top_jf).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().startActivity(new Intent(getContext(), DetailedActivity.class));
                if (id!=null){
                    DetailedActivity.inActivity(getContext(),"积分明细",4,id);
                }
            }
        });
        final TextView textView = view1.findViewById(R.id.shping_f_jf);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.bonus + "?custId=" + UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback(getContext()) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    textView.setText(jsonObject.getJSONObject("data").getString("sum"));
                    id = jsonObject.getJSONObject("data").getString("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
        view1.findViewById(R.id.sp_heard_gz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebUtilActivity.InWeb(getContext(),"http://47.106.103.104/app/guide.png?fileName=point","积分规则",null);
            }
        });
        homeShpingGrid.addHeaderView(view1);
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
    public class ImageViewHolder implements Holder<Integer> {
        private ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }
        @Override
        public void UpdateUI(Context context, int position, Integer data) {

            imageView.setImageResource(data);
        }
    }
    public void initQiangtui(View v, final JSONArray array){
        ImageView imageView1 = v.findViewById(R.id.top_list_img1);
        ImageView imageView2 = v.findViewById(R.id.top_list_img2);
        ImageView imageView3 = v.findViewById(R.id.top_list_img3);
        TextView textView1 = v.findViewById(R.id.top_list_name1);
        TextView textView2 = v.findViewById(R.id.top_list_name2);
        TextView textView3 = v.findViewById(R.id.top_list_name3);
        TextView textView4 = v.findViewById(R.id.top_list_money1);
        TextView textView5 = v.findViewById(R.id.top_list_money2);
        TextView textView6 = v.findViewById(R.id.top_list_money3);
        try {
            JSONObject object1 = array.getJSONObject(array.length()-3);
            MyVolley.getImage(object1.getString("productPic")+"&token="+ UserModel.token,imageView1);
            textView1.setText(object1.getString("productName"));
            textView4.setText("￥"+object1.getInt("price"));
            JSONObject object2 = array.getJSONObject(array.length()-2);
            MyVolley.getImage(object2.getString("productPic")+"&token="+ UserModel.token,imageView2);
            textView2.setText(object2.getString("productName"));
            textView5.setText("￥"+object2.getInt("price"));
            JSONObject object3 = array.getJSONObject(array.length()-1);
            MyVolley.getImage(object3.getString("productPic")+"&token="+ UserModel.token,imageView3);
            textView3.setText(object3.getString("productName"));
            textView6.setText("￥"+object3.getInt("price"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        v.findViewById(R.id.sp_sp_01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
                Intent intent = new Intent(getContext(), SpInfoActivity.class);
                try {
                    intent.putExtra("itmeObject",array.getJSONObject(array.length()-3).toString());
                    getActivity().startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        v.findViewById(R.id.sp_sp_02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
                Intent intent = new Intent(getContext(), SpInfoActivity.class);
                try {
                    intent.putExtra("itmeObject",array.getJSONObject(array.length()-2).toString());
                    getActivity().startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        v.findViewById(R.id.sp_sp_03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
                Intent intent = new Intent(getContext(), SpInfoActivity.class);
                try {
                    intent.putExtra("itmeObject",array.getJSONObject(array.length()-1).toString());
                    getActivity().startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
