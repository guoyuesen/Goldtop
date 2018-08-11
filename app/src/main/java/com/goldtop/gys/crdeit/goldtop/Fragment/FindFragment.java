package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.FindAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.WebUtilActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

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
    TextView textView;
    FindAdapter a1;
    FindAdapter a2;
    FindAdapter a3;
    FindAdapter a4;
    JSONArray array1=new JSONArray();
    JSONArray array2=new JSONArray();
    JSONArray array3=new JSONArray();
    JSONArray array4=new JSONArray();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_find, container, false);
            textView = view.findViewById(R.id.fragment_find_tabhost1);
            ButterKnife.bind(this,view);
            fragmentFindTab1.setVisibility(View.VISIBLE);
            a1 = new FindAdapter(getActivity(), array1);
            a2 = new FindAdapter(getActivity(), array2);
            a3 = new FindAdapter(getActivity(), array3);
            a4 = new FindAdapter(getActivity(), array4);
            fragmentFindTab1.setAdapter(a1);
            fragmentFindTab2.setAdapter(a2);
            fragmentFindTab3.setAdapter(a3);
            fragmentFindTab4.setAdapter(a4);
            fragmentFindTab1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        WebUtilActivity.InWeb(getContext(),"http://120.79.172.84:8080/UEditorMe/Index?id="+array1.getJSONObject(i).getString("id"),"",null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            fragmentFindTab2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        WebUtilActivity.InWeb(getContext(),"http://120.79.172.84:8080/UEditorMe/Index?id="+array2.getJSONObject(i).getString("id"),"",null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            fragmentFindTab3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        WebUtilActivity.InWeb(getContext(),"http://120.79.172.84:8080/UEditorMe/Index?id="+array3.getJSONObject(i).getString("id"),"",null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            fragmentFindTab4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        WebUtilActivity.InWeb(getContext(),"http://120.79.172.84:8080/UEditorMe/Index?id="+array4.getJSONObject(i).getString("id"),"",null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            listView = fragmentFindTab1;
            Vx = (int) fragmentFindTabb.getX();
            Vy = (int) fragmentFindTabb.getY();
            W = ContextUtil.getX(getActivity())/8+ContextUtil.dip2px(getContext(),20);
            fragmentFindTabb.setTranslationX(W);
            MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://120.79.172.84:8080/UEditorMe/GetList?type=1", new HashMap<String, String>(), new MyVolleyCallback() {
                @Override
                public void CallBack(JSONObject jsonObject) {
                    try {
                        array1 = jsonObject.getJSONArray("data");
                        a1.notifyDataSetChanged(array1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));
            MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://120.79.172.84:8080/UEditorMe/GetList?type=2", new HashMap<String, String>(), new MyVolleyCallback() {
                @Override
                public void CallBack(JSONObject jsonObject) {
                    try {
                        array2 = jsonObject.getJSONArray("data");
                        a2.notifyDataSetChanged(array2);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));
            MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://120.79.172.84:8080/UEditorMe/GetList?type=3", new HashMap<String, String>(), new MyVolleyCallback() {
                @Override
                public void CallBack(JSONObject jsonObject) {
                    try {
                        array3 = jsonObject.getJSONArray("data");
                        a3.notifyDataSetChanged(array3);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));
            MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://120.79.172.84:8080/UEditorMe/GetList?type=4", new HashMap<String, String>(), new MyVolleyCallback() {
                @Override
                public void CallBack(JSONObject jsonObject) {
                    try {
                        array4 = jsonObject.getJSONArray("data");
                        a4.notifyDataSetChanged(array4);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }));
            return view;
        }

    }
    @OnClick({R.id.fragment_find_tabhost1, R.id.fragment_find_tabhost2, R.id.fragment_find_tabhost3, R.id.fragment_find_tabhost4})
    public void onClick(View view) {
        if (view.getId() == textView.getId()){
            return;
        }
        textView.setTextColor(Color.parseColor("#Ab9100"));
        textView = (TextView) view;
        textView.setTextColor(Color.parseColor("#ff000000"));
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
