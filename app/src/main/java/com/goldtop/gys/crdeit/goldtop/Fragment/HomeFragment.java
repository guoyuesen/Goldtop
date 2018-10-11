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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.goldtop.gys.crdeit.goldtop.Adapters.HomeBankAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddCard01Activity;
import com.goldtop.gys.crdeit.goldtop.acticity.AuthenticationActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.HistoryPlanActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.MyCardActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.NewsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.OpenRedActivity;
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
    @Bind(R.id.home_f_gif)
    ImageView homeFGif;
    HomeBankAdapter adapter;
    JSONArray array;


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
        homeFGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开红包
                startActivity(new Intent(getContext(), OpenRedActivity.class));
            }
        });
        array = new JSONArray();
        adapter = new HomeBankAdapter(getContext(),array);
        homeFrameList.setAdapter(adapter);
        homeFrameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    HistoryPlanActivity.inActivity(getContext(),array.getJSONObject(i-1).getString("cardNo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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
                    }, new DialogClick() {
                        @Override
                        public void onClick(View v) {

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
            }, new DialogClick() {
                @Override
                public void onClick(View v) {

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
        dialogShow2(context,msg,listener,null);
    }
    public static void dialogShow2(Context context, String msg, final DialogClick listener,final DialogClick clistener) {
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
        if (clistener == null){
            btn_cancel.setVisibility(View.GONE);
        }else {
            btn_cancel.setVisibility(View.VISIBLE);
        }
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
            getcards();
            if (UserModel.shiMrenz.equals("REG_SUCCESS")){
                Glide.with(this).load(R.drawable.homegif).into(homeFGif);
            }
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
                        array = jsonObject.getJSONArray("data");
                        adapter.notifyDataSetChanged(array);
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
}
