package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.CardTransformer;
import com.goldtop.gys.crdeit.goldtop.Adapters.HomeShpingTopAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.ViewPagerScroller;
import com.goldtop.gys.crdeit.goldtop.acticity.CustomizedActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.PosActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RecommendedAwardsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.SpInfoActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WebUtilActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.HomeCardView;
import com.goldtop.gys.crdeit.goldtop.view.HorizontalListView;
import com.goldtop.gys.crdeit.goldtop.view.initPhoneDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/12/18.
 */

public class NewHomeFragment extends Fragment {
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.view_pager2)
    ViewPager viewPager2;
    @Bind(R.id.horizontal_list)
    HorizontalListView horizontalList;
    @Bind(R.id.new_home_list)
    LinearLayout newHomeList;
    private View view;
    JSONArray array;
    HomeShpingTopAdapter adapter;
    HomeShpingTopAdapter adapter2;
    android.os.Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.activity_new_home, container, false);
        }
        ButterKnife.bind(this, view);
        viewPager.setPageMargin(0);
        viewPager.setPageTransformer(false, new CardTransformer());
        viewPager.setOffscreenPageLimit(3);
        viewPager2.setPageMargin(0);
        viewPager2.setOffscreenPageLimit(3);
        ViewPagerScroller pagerScroller = new ViewPagerScroller(getActivity());
        pagerScroller.setScrollDuration(2000);//设置时间，时间越长，速度越慢
        pagerScroller.initViewPagerScroll(viewPager);
        ViewPagerScroller pagerScroller1 = new ViewPagerScroller(getActivity());
        pagerScroller1.setScrollDuration(2000);//设置时间，时间越长，速度越慢
        pagerScroller1.initViewPagerScroll(viewPager2);


        array = new JSONArray();
        adapter = new HomeShpingTopAdapter(getContext(), array);
        horizontalList.setAdapter(adapter);
        adapter2 = new HomeShpingTopAdapter(getContext(), array);
        initsh();
        initCard();
        return view;

    }

    private void initCard() {
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://www.tuoluo718.com/ads/list/C", new MyVolleyCallback(getContext()) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            newHomeList.addView(new HomeCardView(getContext(),array.getJSONObject(i)));
                        }
                        newHomeList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (UserModel.custMobile.isEmpty()){
                                    initPhoneDialog dialog = new initPhoneDialog(getContext(), "请输入手机号", new initPhoneDialog.phoneDialog() {
                                        @Override
                                        public void back(String phong) {
                                            startWeb(phong);
                                        }
                                    });
                                    dialog.show();
                                }else {
                                    startWeb(UserModel.custMobile);
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    private void initsh() {
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://www.tuoluo718.com/ads/list/H", new MyVolleyCallback(getContext()) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 1) {
                        final List<String> list = new ArrayList<>();
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            list.add(object.getString("href"));
                        }
                        viewPager.setAdapter(new TopAdapter(getContext(), list));
                        viewPager.setCurrentItem(3*list.size());
                        if (handler==null) {
                            handler = new android.os.Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(viewPager!=null) {
                                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                                        //viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                                        handler.postDelayed(this, 4000);
                                    }
                                }
                            }, 4000);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));

        HorizontalListView hlist = view.findViewById(R.id.horizontal_list);

        final HomeShpingTopAdapter adapter = new HomeShpingTopAdapter(getContext(), new JSONArray());
        hlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!AppUtil.isLogin(getContext())) {
                    return;
                }
                Intent intent = new Intent(getContext(), SpInfoActivity.class);
                intent.putExtra("itmeObject", adapter.getItemString(i));
                getActivity().startActivity(intent);
            }
        });
        hlist.setAdapter(adapter);
        MyVolley.addRequest(new VolleyRequest("http://www.tuoluo718.com/product/list/money", new HashMap<String, String>(), new MyVolleyCallback(getContext()) {
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
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://www.tuoluo718.com/ads/list/S", new MyVolleyCallback(getContext()) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 1) {
                        final List<String> list = new ArrayList<>();
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            list.add(object.getString("href"));
                        }
                        viewPager2.setAdapter(new CenoterpAdapter(getContext(), list));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.item_01, R.id.item_02, R.id.item_03, R.id.item_04,R.id.new_home_card,R.id.item_07})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_01:
                if (AppUtil.isLogin(getContext())) {
                    startActivity(new Intent(getContext(), HomeFragment.class));
                }
                break;
            case R.id.item_02:
                startActivity(new Intent(getContext(), PosActivity.class));
                break;
            case R.id.item_03:
                WebUtilActivity.InWeb(getContext(), "http://jtl.scjplaw.com", "", null);
                break;
            case R.id.item_04:
                startActivity(new Intent(getContext(), CustomizedActivity.class));
                break;
            case R.id.new_home_card:
                if (UserModel.custMobile.isEmpty()){
                    initPhoneDialog dialog = new initPhoneDialog(getContext(), "请输入手机号", new initPhoneDialog.phoneDialog() {
                        @Override
                        public void back(String phong) {
                            startWeb(phong);
                        }
                    });
                    dialog.show();
                }else {
                    startWeb(UserModel.custMobile);
                }
                break;
            case R.id.item_07:
                if (UserModel.custMobile.isEmpty()){
                    initPhoneDialog dialog = new initPhoneDialog(getContext(), "请输入手机号", new initPhoneDialog.phoneDialog() {
                        @Override
                        public void back(String phong) {
                            startWeb(phong);
                        }
                    });
                    dialog.show();
                }else {
                    startWeb(UserModel.custMobile);
                }
                break;
        }
    }
    public void startWeb(String phone){
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://www.tuoluo718.com/credit/login/"+phone, new MyVolleyCallback(getContext()) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code")==1) {
                        WebUtilActivity.InWeb(getContext(), jsonObject.getJSONObject("data").getString("redirect_url"), "", null);
                    }else {
                        Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }
    private class TopAdapter extends PagerAdapter {
        Context context;
        List<String> list;

        public TopAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_newhome_top, null);
            ImageView imageView = view.findViewById(R.id.new_hometop_img);
            MyVolley.getImage(list.get(position%list.size()), imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (position%list.size()){
                        case 0:
                            startActivity(new Intent(getContext(), CustomizedActivity.class));
                            break;
                        case 1:
                            getActivity().startActivity(new Intent(getContext(), RecommendedAwardsActivity.class));
                            break;
                        case 2:
                            if (AppUtil.isLogin(getContext())) {
                                startActivity(new Intent(getContext(), HomeFragment.class));
                            }
                            break;
                        case 3:
                            startActivity(new Intent(getContext(), PosActivity.class));
                            break;
                    }
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    private class CenoterpAdapter extends PagerAdapter {
        Context context;
        List<String> list;

        public CenoterpAdapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public float getPageWidth(int position) {
            return super.getPageWidth(position);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            MyVolley.getImage(list.get(position%list.size()), imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (position%list.size()) {
                        case 0:
                            getActivity().startActivity(new Intent(getContext(), RecommendedAwardsActivity.class));
                            break;
                        case 1:
                            WebUtilActivity.InWeb(getContext(), "http://jtl.scjplaw.com", "", null);
                            break;
                    }
                }
            });
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    class ThisAdapter extends BaseAdapter {
        Context context;
        JSONArray datas;

        public ThisAdapter(Context context, JSONArray datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.length();
        }

        @Override
        public Object getItem(int i) {
            try {
                return datas.getJSONObject(i);
            } catch (JSONException e) {
                return datas;
            }
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public void notifyDataSetChanged(JSONArray array) {
            datas = array;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ThisItem item = null;
            if (view == null) {
                item = new ThisItem();
                view = LayoutInflater.from(context).inflate(R.layout.item_sp_list, null);
                view.setTag(item);
            } else {
                item = (ThisItem) view.getTag();
            }

            return view;
        }
    }

    class ThisItem {

    }
}
