package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.goldtop.gys.crdeit.goldtop.Adapters.HomeBankAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddCard01Activity;
import com.goldtop.gys.crdeit.goldtop.acticity.AuthenticationActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.HistoryPlanActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.NewsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.OpenRedActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RatesActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.ReceivablesActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RecommendedAwardsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.VipActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WalletActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WebUtilActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.DialogClick;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.initPhoneDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/2.
 */

public class HomeFragment extends BaseActivity {
    @Bind(R.id.home_frame_add)
    ImageButton homeFrameAdd;
    @Bind(R.id.home_frame_msg)
    ImageButton homeFrameMsg;
    @Bind(R.id.home_frame_list)
    ListView homeFrameList;
    @Bind(R.id.home_f_gif)
    ImageView homeFGif;
    HomeBankAdapter adapter;
    JSONArray array;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        hiedBar(this);
        ButterKnife.bind(this);
        initActivity();
    }

    private void initActivity() {
        homeFrameAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCard01Activity.initActivity(HomeFragment.this, "CC");
            }
        });
        homeFGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开红包
                startActivity(new Intent(HomeFragment.this, OpenRedActivity.class));
            }
        });
        array = new JSONArray();
        adapter = new HomeBankAdapter(HomeFragment.this, array);
        homeFrameList.setAdapter(adapter);
        homeFrameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    HistoryPlanActivity.inActivity(HomeFragment.this, array.getJSONObject(i - 1).getString("cardNo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        View hview = LayoutInflater.from(HomeFragment.this).inflate(R.layout.item_home_top, null);
        hview.findViewById(R.id.home_frame_btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* WebUtilActivity.InWeb(HomeFragment.this, "https://h5.blackfish.cn/bill/credit-card-manager/page-index?channel=RZnfboTz&ID=015", "信用卡办理", null);*/
                   if (UserModel.custMobile.isEmpty()){
                       initPhoneDialog dialog = new initPhoneDialog(HomeFragment.this, "请输入手机号", new initPhoneDialog.phoneDialog() {
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
        hview.findViewById(R.id.home_frame_btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(HomeFragment.this)) {
                    return;
                }
                startActivity(new Intent(HomeFragment.this, ReceivablesActivity.class));
            }
        });
        hview.findViewById(R.id.home_frame_btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(HomeFragment.this)) {
                    return;
                }
                startActivity(new Intent(HomeFragment.this, WalletActivity.class));
            }
        });
        hview.findViewById(R.id.home_frame_btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().startActivity(new Intent(getContext(), RatesActivity.class));
                startActivity(new Intent(HomeFragment.this, RatesActivity.class));
            }
        });
        homeFrameList.addHeaderView(hview);
        View view = LayoutInflater.from(HomeFragment.this).inflate(R.layout.item_home_fragm_bomm, null);
        view.findViewById(R.id.home_add_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(HomeFragment.this)) {
                    return;
                }
                if (UserModel.shiMrenz.equals("REG_SUCCESS")) {
                    AddCard01Activity.initActivity(HomeFragment.this, "CC");
                } else if (UserModel.shiMrenz.equals("INIT") || UserModel.shiMrenz.equals("")) {
                    dialogShow2(HomeFragment.this, "您尚未进行实名认证，请前往认证！", new DialogClick() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(HomeFragment.this, AuthenticationActivity.class));
                        }
                    }, new DialogClick() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                } else {
                    dialogShow2(HomeFragment.this, "实名认证审核中，请耐心等待！", new DialogClick() {
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
                WebUtilActivity.InWeb(HomeFragment.this, "https://m.zhongan.com/p/85132614", "", null);
            }
        });
        homeFrameList.addFooterView(view, null, false);
        homeFrameMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AppUtil.isLogin(HomeFragment.this)) {
                    return;
                }
                startActivity(new Intent(HomeFragment.this, NewsActivity.class));
            }
        });
    }
    public void startWeb(String phone){
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://www.tuoluo718.com/credit/login/"+phone, new MyVolleyCallback(HomeFragment.this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code")==1) {
                        WebUtilActivity.InWeb(HomeFragment.this, jsonObject.getJSONObject("data").getString("redirect_url"), "", null);
                    }else {
                        Toast.makeText(HomeFragment.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
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
    public static boolean smrzShow(final Context context) {
        if (UserModel.shiMrenz.equals("REG_SUCCESS")) {
            return true;
        } else if (UserModel.shiMrenz.equals("INIT") || UserModel.shiMrenz.equals("")) {
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
        } else {
            dialogShow2(context, "实名认证审核中，请耐心等待！", new DialogClick() {
                @Override
                public void onClick(View v) {

                }
            });
            return false;
        }
    }

    public static void dialogShow2(Context context, String msg, final DialogClick listener) {
        dialogShow2(context, msg, listener, null);
    }

    public static void dialogShow2(Context context, String msg, final DialogClick listener, final DialogClick clistener) {
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
        if (clistener == null) {
            btn_cancel.setVisibility(View.GONE);
        } else {
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
            if (UserModel.shiMrenz.equals("REG_SUCCESS")) {
                Glide.with(this).load(R.drawable.homegif).into(homeFGif);
            }
        }
        super.onStart();
    }

    public void getcards() {
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.paymentSchedule + "?custId=" + UserModel.custId + "&token=" + UserModel.token, new HashMap<String, String>(), new MyVolleyCallback(HomeFragment.this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        Log.d("===>", jsonObject.toString());
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

    @OnClick(R.id.title_left_img)
    public void onViewClicked() {
        finish();
    }
}
