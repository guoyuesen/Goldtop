package com.goldtop.gys.crdeit.goldtop.acticity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/9.
 */

public class VipActivity extends BaseActivity {

    @Bind(R.id.vip_text)
    TextView vipText;
    @Bind(R.id.vip_layout_hy)
    LinearLayout vipLayoutHy;
    @Bind(R.id.vip_img)
    ImageView vipImg;
    @Bind(R.id.vip_feilv_img)
    ImageView vipFeilvImg;
    @Bind(R.id.vip_feilv_text)
    TextView vipFeilvText;
    @Bind(R.id.vip_ffanyong_img)
    ImageView vipFfanyongImg;
    @Bind(R.id.vip_ffanyong_text)
    TextView vipFfanyongText;
    @Bind(R.id.vip_jifen_img)
    ImageView vipJifenImg;
    @Bind(R.id.vip_jifen_text)
    TextView vipJifenText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        new TitleBuder(this).setTitleText("会员特权").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setBackgrund(Color.parseColor("#ffffff"));
        switch (UserModel.custLevelSample) {
            case "MEMBER":
                vipText.setText("尊敬的会员用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您将享受以下专属权益");
                vipImg.setImageResource(R.mipmap.vip__02);
                vipFeilvImg.setImageResource(R.mipmap.vip_vip_fl);
                vipFfanyongImg.setImageResource(R.mipmap.vip_vip_fy);
                vipJifenImg.setImageResource(R.mipmap.vip_vip_jf);
                vipFeilvText.setText("服务费率0.68%");
                vipFfanyongText.setText("分享返佣0.10%");
                vipJifenText.setText("积分赠送88积分");
                break;
            case "MANAGER":
                vipText.setText("尊敬的经理" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您将享受以下专属权益");
                vipImg.setImageResource(R.mipmap.vip__04);
                vipFeilvImg.setImageResource(R.mipmap.vip_hy_fl);
                vipFfanyongImg.setImageResource(R.mipmap.vip_hy_fy);
                vipJifenImg.setImageResource(R.mipmap.vip_hy_jf);
                vipFeilvText.setText("服务费率0.62%");
                vipFfanyongText.setText("分享返佣0.16%");
                vipJifenText.setText("积分赠送588积分");
                break;
            case "CHIEF":
                vipText.setText("尊敬的总监" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您将享受以下专属权益");
                vipImg.setImageResource(R.mipmap.vip__05);
                vipFeilvImg.setImageResource(R.mipmap.vip_zj_fl);
                vipFfanyongImg.setImageResource(R.mipmap.vip_zj_fy);
                vipJifenImg.setImageResource(R.mipmap.vip_zj_jf);
                vipFeilvText.setText("服务费率0.56%");
                vipFfanyongText.setText("分享返佣0.22%");
                vipJifenText.setText("积分赠送1288积分");
                break;
            case "AGENT":
                vipText.setText("尊敬的合伙人用户" + UserModel.custMobile.substring(0, 3) + "****" + UserModel.custMobile.substring(UserModel.custMobile.length() - 4, UserModel.custMobile.length()) + "，您将享受以下专属权益");
                vipImg.setImageResource(R.mipmap.vip__06);
                vipFeilvImg.setImageResource(R.mipmap.vip_qy_fl);
                vipFfanyongImg.setImageResource(R.mipmap.vip_qy_fy);
                vipJifenImg.setImageResource(R.mipmap.vip_qy_jf);
                vipFeilvText.setText("服务费率0.52%");
                vipFfanyongText.setText("分享返佣0.26%");
                vipJifenText.setText("积分赠送2688积分");
                break;
        }

    }
}
