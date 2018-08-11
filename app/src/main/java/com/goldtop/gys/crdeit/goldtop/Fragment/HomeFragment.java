package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.HomeBankAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddCard01Activity;
import com.goldtop.gys.crdeit.goldtop.acticity.MyCardActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.NewsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.ReceivablesActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.VipActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WebUtilActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.HttpsDialogView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    @Bind(R.id.home_frame_list)
    ListView homeFrameList;
    private View view;
    HomeBankAdapter adapter;
    HttpsDialogView dialog;

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
        adapter = new HomeBankAdapter(getContext(),array);
        homeFrameList.setAdapter(adapter);
        View hview = LayoutInflater.from(getContext()).inflate(R.layout.item_home_top,null);
        hview.findViewById(R.id.home_frame_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebUtilActivity.InWeb(getContext(),"https://h5.blackfish.cn/bill/credit-card-manager/page-index?channel=RZnfboTz&ID=015","",null);
            }
        });
        hview.findViewById(R.id.home_frame_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
                startActivity(new Intent(getContext(),ReceivablesActivity.class));
            }
        });
        hview.findViewById(R.id.home_frame_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
            }
        });
        hview.findViewById(R.id.home_frame_btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),VipActivity.class));
            }
        });
        homeFrameList.addHeaderView(hview);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_fragm_bomm,null);
        view.findViewById(R.id.home_add_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
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
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
                getActivity().startActivity(new Intent(getContext(), NewsActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("custId", UserModel.custId);
        map.put("cardType","C");
        Log.d(Action.queryBankCard+"?custId="+UserModel.custId+"&cardType=C"+"==》","");
        if (dialog==null){
            dialog = new HttpsDialogView(getContext());
            dialog.show();
        }else if (!dialog.isShowing()){
            dialog.show();
        }
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.queryBankCard+"?custId="+UserModel.custId+"&cardType=C", map, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        JSONArray array = jsonObject.getJSONArray("data");
                        getcards(array);
                        //adapter.notifyDataSetChanged(array);
                        //Log.d("data==》",""+array.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
            }
        }));
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    public void getcards(final JSONArray a){
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.paymentSchedule+"?custId="+UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        Log.d("===>",jsonObject.toString());
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0;i<a.length();i++){
                            String number = a.getJSONObject(i).getString("accountCode");
                            for (int j = 0;j<array.length();j++){
                                if (array.getJSONArray(0).length()>0){
                                    JSONObject object = array.getJSONArray(0).getJSONObject(0);
                                    if (number.equals(object.getString("cardNo"))){
                                        a.getJSONObject(i).put("applyId",object.getString("applyId"));
                                        a.getJSONObject(i).put("balanceAmt",object.getString("balanceAmt"));
                                        a.getJSONObject(i).put("transFee",object.getString("transFee"));
                                        a.getJSONObject(i).put("totalTerm",object.getString("totalTerm"));
                                        a.getJSONObject(i).put("currPaymentAmt",object.getString("currPaymentAmt"));
                                        a.getJSONObject(i).put("deadline",object.getString("deadline"));
                                        a.getJSONObject(i).put("balanceTerm",object.getString("balanceTerm"));
                                        a.getJSONObject(i).put("applyAmt",object.getString("applyAmt"));
                                    }
                                }
                            }
                        }
                        adapter.notifyDataSetChanged(a);
                        //Log.d("data==》",""+array.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (dialog.isShowing())
                    dialog.dismiss();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        }));
    }
}
