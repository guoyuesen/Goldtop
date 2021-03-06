package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddressActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.AuthenticationActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.HomeActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.LoginActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.MyCardActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.OrderActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RatesActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RecommendedAwardsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RedEnvelopesActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RegisterActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.SettionsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.ShareListActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.VipActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WalletActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WebUtilActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.DialogClick;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.RImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/2.
 */

public class MeFragment extends Fragment {
    @Bind(R.id.fragment_me_icon)
    ImageView fragmentMeIcon;
    @Bind(R.id.me_f_money)
    TextView meFMoney;
    @Bind(R.id.me_f_red)
    TextView meFRed;
    @Bind(R.id.me_f_integral)
    TextView meFIntegral;
    @Bind(R.id.fragment_me_name)
    LinearLayout fragmentMeName;
    @Bind(R.id.fragment_me_login)
    LinearLayout fragmentMeLogin;
    @Bind(R.id.me_f_name)
    TextView name;
    @Bind(R.id.me_f_phone)
    TextView phone;
    @Bind(R.id.me_f_rz)
    TextView me_f_rz;
    private View view;
    DialogClick click;

    public void setClick(DialogClick click) {
        this.click = click;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_me, container, false);
            ButterKnife.bind(this, view);
            RImageView imageView = view.findViewById(R.id.fragment_me_icon);
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.fragment_me_01);
            imageView.setStroke(Color.parseColor("#ffffff"),3).setImageBitmap(bm);
            return view;
        }
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (UserModel.id.isEmpty()){
            fragmentMeName.setVisibility(View.GONE);
            fragmentMeLogin.setVisibility(View.VISIBLE);
        }else {
            fragmentMeName.setVisibility(View.VISIBLE);
            fragmentMeLogin.setVisibility(View.GONE);
            name.setText(UserModel.custName);
            phone.setText(UserModel.custMobile);
        }
        switch (UserModel.shiMrenz){
            case "REG_SUCCESS":
                me_f_rz.setText("已认证");
                break;
            case "REG_ING":
                me_f_rz.setText("审核中");
                break;
            case "INIT":
                me_f_rz.setText("未认证");
                break;
        }
        if (UserModel.shiMrenz.equals("")||UserModel.shiMrenz.equals("REG_ING")){
            MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.smrz+UserModel.custId, new HashMap<String, String>(), new MyVolleyCallback(getContext()) {
                @Override
                public void CallBack(JSONObject jsonObject) {
                    try {
                        if (jsonObject.getString("code").equals("1")){
                            JSONObject object = jsonObject.getJSONObject("data");
                            UserModel.shiMrenz = object.getString("desciption");
                            UserModel.custStatus = object.getString("desciption");
                            switch (UserModel.shiMrenz){
                                case "REG_SUCCESS":
                                    me_f_rz.setText("已认证");
                                    break;
                                case "REG_ING":
                                    me_f_rz.setText("审核中");
                                    break;
                                case "INIT":
                                    me_f_rz.setText("未认证");
                                    break;
                            }
                        }else {
                            UserModel.shiMrenz = "INIT";
                            me_f_rz.setText("未认证");
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.me_f_wallet_l, R.id.me_f_red_l, R.id.me_f_integral_l, R.id.me_f_authentication_l, R.id.me_f_card_l,
            R.id.me_f_vip_l, R.id.me_f_invite_l, R.id.me_f_tema_l, R.id.me_f_process_l, R.id.me_f_order_l,
            R.id.me_f_address_l, R.id.fragment_mi_settiongs,R.id.me_f_login,R.id.me_f_register,
            R.id.me_f_process_l1,R.id.me_f_process_l0,R.id.me_f_flee_l,R.id.me_f_qrcode_l})
    public void onClick(View view) {
        if (!AppUtil.isLogin(getContext())){
            return;
        }
        switch (view.getId()) {
            case R.id.me_f_wallet_l://钱包
                if (HomeFragment.smrzShow(getActivity()))
                getActivity().startActivity(new Intent(getContext(), WalletActivity.class));
                break;
            case R.id.me_f_red_l://红包
                if (HomeFragment.smrzShow(getActivity()))
                getActivity().startActivity(new Intent(getContext(), RedEnvelopesActivity.class));
                break;
            case R.id.me_f_integral_l://积分
                if (click!=null){
                click.onClick(null);
                }
                break;
            case R.id.me_f_authentication_l://实名认证
                if (UserModel.shiMrenz.equals("INIT")){
                    getActivity().startActivity(new Intent(getContext(), AuthenticationActivity.class));
                }
                break;
            case R.id.me_f_card_l://我的银行卡
                if (HomeFragment.smrzShow(getActivity()))
                getActivity().startActivity(new Intent(getContext(), MyCardActivity.class));
                break;
            case R.id.me_f_vip_l://vip
                if (HomeFragment.smrzShow(getActivity())) {
                    if (UserModel.custLevelSample.indexOf("NORMAL") != -1){
                        final Dialog dialog = new Dialog(getContext(),R.style.MyDialog);
                        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.dialog_purchase,null);
                        TextView textView = view1.findViewById(R.id.purchase_dialog_text);
                        textView.setText("尊敬的用户"+UserModel.custMobile.substring(0,3)+"****"+UserModel.custMobile.substring(7,11)+"，您还不是会员 在商城任意购买价值满399元的产品");
                        view1.findViewById(R.id.purchase_dialog_btn).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (click!=null){
                                    click.onClick(null);
                                }
                                dialog.dismiss();
                            }
                        });
                        dialog.setContentView(view1);
                        dialog.show();
                    }else
                    {
                        getActivity().startActivity(new Intent(getContext(), RatesActivity.class));
                        //getActivity().startActivity(new Intent(getContext(), VipActivity.class));
                    }
                }
                break;
            case R.id.me_f_invite_l://邀请有奖
                getActivity().startActivity(new Intent(getContext(), RecommendedAwardsActivity.class));
                break;
            case R.id.me_f_tema_l://团队业绩
                break;
            case R.id.me_f_process_l://新手指南
                WebUtilActivity.InWeb(getContext(),"http://47.106.103.104/app/guide.png?fileName=guide","新手指南",null);
                break;
            case R.id.me_f_order_l://我的订单
                getActivity().startActivity(new Intent(getContext(), OrderActivity.class));
                break;
            case R.id.me_f_address_l://地址管理
                getActivity().startActivity(new Intent(getContext(), AddressActivity.class));
                break;
            case R.id.fragment_mi_settiongs://设置
                getActivity().startActivity(new Intent(getContext(), SettionsActivity.class));
                break;
            case R.id.me_f_login://登录
                getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.me_f_register://注册
                getActivity().startActivity(new Intent(getContext(), RegisterActivity.class));
                break;
            case R.id.me_f_process_l1://设置
                getActivity().startActivity(new Intent(getContext(), SettionsActivity.class));
                break;
            case R.id.me_f_flee_l://费率
                getActivity().startActivity(new Intent(getContext(), RatesActivity.class));
                break;
            case R.id.me_f_qrcode_l://邀请码
                getActivity().startActivity(new Intent(getContext(), ShareListActivity.class));
                break;
            case R.id.me_f_process_l0://我的客服
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setTitle("温馨提示");
                builder.setMessage("您的专属客服号：02885002704");
                builder.setPositiveButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.setNegativeButton("拨打电话", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE);

                        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(
                                    getActivity(),
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    123);
                        } else {
                            startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:02885002704")));
                        }
                    }
                });
                AlertDialog dialog=builder.create();
                dialog.show();

                break;
        }
    }


}
