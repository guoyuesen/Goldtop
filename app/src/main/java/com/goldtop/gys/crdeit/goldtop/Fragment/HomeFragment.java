package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.HomeBankAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddCard01Activity;
import com.goldtop.gys.crdeit.goldtop.acticity.AuthenticationActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.MyCardActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.NewsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.ReceivablesActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RecommendedAwardsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.VipActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WebUtilActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.DialogClick;
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
                AddCard01Activity.initActivity(getContext(),"CC");
            }
        });
        JSONArray array = new JSONArray();
        adapter = new HomeBankAdapter(getContext(),array);
        homeFrameList.setAdapter(adapter);
        View hview = LayoutInflater.from(getContext()).inflate(R.layout.item_home_top,null);
        hview.findViewById(R.id.home_frame_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebUtilActivity.InWeb(getContext(),"https://h5.blackfish.cn/bill/credit-card-manager/page-index?channel=RZnfboTz&ID=015","信用卡办理",null);
            }
        });
        hview.findViewById(R.id.home_frame_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
                if (HomeFragment.smrzShow(getActivity()))
                startActivity(new Intent(getContext(),ReceivablesActivity.class));
            }
        });
        hview.findViewById(R.id.home_frame_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(getContext())){
                    return;
                }
                startActivity(new Intent(getContext(),RecommendedAwardsActivity.class));
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
                }if (UserModel.shiMrenz.equals("REG_SUCCESS")){
                    AddCard01Activity.initActivity(getContext(),"CC");
                }else if (UserModel.shiMrenz.equals("INIT")||UserModel.shiMrenz.equals("")){
                    dialogShow2(getContext(), "您尚未进行实名认证，请前往认证！", new DialogClick() {
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(new Intent(getContext(), AuthenticationActivity.class));
                        }
                    });
                }else {
                    dialogShow2(getContext(), "实名认证审核中，请耐心等待！", new DialogClick() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }

            }
        });
        view.findViewById(R.id.home_baoxian).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WebUtilActivity.InWeb(getContext(),"https://m.zhongan.com/p/85132614","",null);
            }
        });
        homeFrameList.addFooterView(view,null,false);
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
    public static boolean smrzShow(final Context context){
        if (UserModel.shiMrenz.equals("REG_SUCCESS")){
            return true;
        }else if (UserModel.shiMrenz.equals("INIT")||UserModel.shiMrenz.equals("")){
            dialogShow2(context, "您尚未进行实名认证，请前往认证！", new DialogClick() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, AuthenticationActivity.class));
                }
            });
            return false;
        }else {
            dialogShow2(context, "实名认证审核中，请耐心等待！", new DialogClick() {
                @Override
                public void onClick(View v) {

                }
            });
            return false;
        }
    }
    public static void dialogShow2(Context context, String msg, final DialogClick listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.update_manage_dialog, null);
        TextView content = (TextView) v.findViewById(R.id.dialog_content);
        content.setText(msg);
        Button btn_sure = (Button) v.findViewById(R.id.dialog_btn_sure);
        Button btn_cancel = (Button) v.findViewById(R.id.dialog_btn_cancel);
        //builer.setView(v);//这里如果使用builer.setView(v)，自定义布局只会覆盖title和button之间的那部分
        final Dialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置
        btn_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                listener.onClick(v);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();

            }
        });
    }
    @Override
    public void onStart() {
        if (!UserModel.custId.isEmpty()) {
            /*Map<String, String> map = new HashMap<String, String>();
            map.put("custId", UserModel.custId);
            map.put("cardType", "C");
            Log.d(Action.queryBankCard + "?custId=" + UserModel.custId + "&cardType=C" + "==》", "");
            MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.queryBankCard + "?custId=" + UserModel.custId + "&cardType=C", map, new MyVolleyCallback() {
                @Override
                public void CallBack(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getString("code").equals("1")) {
                            JSONObject object = jsonObject.getJSONObject("data");
                            JSONArray array = object.getJSONArray("bankCardList");
                            getcards(object);
                            //adapter.notifyDataSetChanged(array);
                            //Log.d("data==》",""+array.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    //dialog.dismiss();
                }
            }));
        */
            getcards();
        }
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    public void getcards(){

        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.paymentSchedule+"?custId="+UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        Log.d("===>",jsonObject.toString());
                        JSONArray array = jsonObject.getJSONArray("data");
                        /*for (int i = 0;i<a.getJSONArray("bankCardList").length();i++){
                            String number = a.getJSONArray("bankCardList").getJSONObject(i).getString("accountCode");
                            boolean p = true;
                            for (int j = 0;j<array.length();j++){
                                    JSONObject object = array.getJSONObject(j);
                                    if (number.equals(object.getString("cardNo"))){
                                        a.getJSONArray("bankCardList").getJSONObject(i).put("applyId",object.getString("applyId"));
                                        a.getJSONArray("bankCardList").getJSONObject(i).put("balanceAmt",object.getString("balanceAmt"));
                                        a.getJSONArray("bankCardList").getJSONObject(i).put("transFee",object.getString("transFee"));
                                        a.getJSONArray("bankCardList").getJSONObject(i).put("totalTerm",object.getString("totalTerm"));
                                        a.getJSONArray("bankCardList").getJSONObject(i).put("currPaymentAmt",object.getString("currPaymentAmt"));
                                        a.getJSONArray("bankCardList").getJSONObject(i).put("deadline",object.getString("deadline"));
                                        a.getJSONArray("bankCardList").getJSONObject(i).put("balanceTerm",object.getString("balanceTerm"));
                                        a.getJSONArray("bankCardList").getJSONObject(i).put("applyAmt",object.getString("applyAmt"));
                                        a.getJSONArray("bankCardList").getJSONObject(i).put("idCardNo",a.getString("idCardNo"));
                                        p = false;
                                    }
                            }
                            if (p){
                                Log.d("-----","----");
                                a.getJSONArray("bankCardList").getJSONObject(i).put("applyId","0");
                                a.getJSONArray("bankCardList").getJSONObject(i).put("balanceAmt","0");
                                a.getJSONArray("bankCardList").getJSONObject(i).put("transFee","0");
                                a.getJSONArray("bankCardList").getJSONObject(i).put("totalTerm","0");
                                a.getJSONArray("bankCardList").getJSONObject(i).put("currPaymentAmt","0");
                                a.getJSONArray("bankCardList").getJSONObject(i).put("deadline","0");
                                a.getJSONArray("bankCardList").getJSONObject(i).put("balanceTerm","0");
                                a.getJSONArray("bankCardList").getJSONObject(i).put("applyAmt","0");
                                a.getJSONArray("bankCardList").getJSONObject(i).put("idCardNo","0");
                            }
                        }*/
                        adapter.notifyDataSetChanged(array);
                        //Log.d("data==》",""+array.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
               /* if (dialog.isShowing())
                    dialog.dismiss();*/
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                /*if (dialog.isShowing())
                    dialog.dismiss();*/
            }
        }));
    }
}
