package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;
import com.goldtop.gys.crdeit.goldtop.acticity.OpenCardActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RepaymentInstallActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RepaymentMsgActivity;
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
 * Created by 郭月森 on 2018/7/3.
 */

public class HomeBankAdapter extends BaseAdapter {
    private Context context;
    private JSONArray array;

    public HomeBankAdapter(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    public void notifyDataSetChanged(JSONArray array) {
        this.array = array;
        //Log.d("==》",""+array.length());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return array.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThisItem item;

        if (view==null) {
            item = new ThisItem();
            view = LayoutInflater.from(context).inflate(R.layout.item_home_list, null);
            item.button = view.findViewById(R.id.repayment);
            item.bankName = view.findViewById(R.id.home_bank_name);
            item.userName = view.findViewById(R.id.home_card_name);
            item.bankNumber = view.findViewById(R.id.home_card_number);
            item.money1 = view.findViewById(R.id.home_card_money1);
            item.money2 = view.findViewById(R.id.home_card_money2);
            item.imageView = view.findViewById(R.id.home_card_icon);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }

        try {
            final JSONObject object = array.getJSONObject(i);
            MyVolley.getImage(object.getString("icon"),item.imageView);
            item.bankName.setText(object.getString("bankName"));
            item.userName.setText(object.getString("accountName"));
            item.bankNumber.setText(object.getString("accountCode").substring(object.getString("accountCode").length()-4));

            if (object.getString("openStatus").equals("INIT")){
                item.button.setText("银联认证");
                item.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //try {
                            //WebUtilActivity.InWeb(context,Action.openCard+"?custId="+UserModel.custId+"&cardId="+object.getString("id"),"",null);
                            OpenCardActivity.initActivity(context,object);
                        /*} catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                });

            }else if(object.getString("openStatus").equals("OPEN_FAIL")){
                item.button.setText("认证失败");
            }else{

                if (!"".equals(object.getString("applyId"))){
                    item.button.setText("查看详情");
                    item.button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent(context, RepaymentMsgActivity.class);
                                intent.putExtra("card", object.getString("accountCode"));
                                intent.putExtra("applyId", object.getString("applyId"));
                                intent.putExtra("jsono",object.toString());
                                context.startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }});
                }else{
                    item.button.setText("立即还款");
                    item.button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!UserModel.custStatus.equals("REG_SUCCESS")){
                                Toast.makeText(context,"您还未实名认证，请先进行实名认证",Toast.LENGTH_LONG).show();
                                return;
                            }
                            try {
                                if (object.getString("bindStatus").equals("INIT")){
                                    Toast.makeText(context,"绑卡信息有误，暂不能设置计划",Toast.LENGTH_LONG).show();
                                }else {
                                    Intent intent = new Intent(context, RepaymentInstallActivity.class);
                                    intent.putExtra("cardid", object.getString("accountCode"));
                                    context.startActivity(intent);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
            item.money1.setText(MoneyUtils.getShowMoney(""+object.getDouble("applyAmt")));
            item.money2.setText(MoneyUtils.getShowMoney(""+object.getDouble("balanceAmt")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }
    class ThisItem{
        Button button;
        ImageView imageView;
        TextView bankName;
        TextView userName;
        TextView bankNumber;
        TextView money1;
        TextView money2;

    }
}
