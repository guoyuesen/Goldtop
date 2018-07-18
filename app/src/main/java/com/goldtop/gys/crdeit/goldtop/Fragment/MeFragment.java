package com.goldtop.gys.crdeit.goldtop.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddressActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.AuthenticationActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.LoginActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.MyCardActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.OrderActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RecommendedAwardsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.RedEnvelopesActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.SettionsActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.VipActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.WalletActivity;

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
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_me, container, false);
            ButterKnife.bind(this, view);
            return view;
        }
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.me_f_wallet_l, R.id.me_f_red_l, R.id.me_f_integral_l, R.id.me_f_authentication_l, R.id.me_f_card_l, R.id.me_f_vip_l, R.id.me_f_invite_l, R.id.me_f_tema_l, R.id.me_f_process_l, R.id.me_f_order_l, R.id.me_f_address_l,R.id.fragment_mi_settiongs})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_f_wallet_l://钱包
                getActivity().startActivity(new Intent(getContext(), WalletActivity.class));
                break;
            case R.id.me_f_red_l://红包
                getActivity().startActivity(new Intent(getContext(), RedEnvelopesActivity.class));
                break;
            case R.id.me_f_integral_l://积分
                break;
            case R.id.me_f_authentication_l://实名认证
                getActivity().startActivity(new Intent(getContext(), AuthenticationActivity.class));
                break;
            case R.id.me_f_card_l://我的银行卡
                getActivity().startActivity(new Intent(getContext(), MyCardActivity.class));
                break;
            case R.id.me_f_vip_l://vip
                getActivity().startActivity(new Intent(getContext(), VipActivity.class));
                break;
            case R.id.me_f_invite_l://邀请有奖
                getActivity().startActivity(new Intent(getContext(), RecommendedAwardsActivity.class));
                break;
            case R.id.me_f_tema_l://团队业绩
                if (AppUtil.isLogin(getContext())){
                break;
                }
                break;
            case R.id.me_f_process_l://新手指南
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
        }
    }


}
