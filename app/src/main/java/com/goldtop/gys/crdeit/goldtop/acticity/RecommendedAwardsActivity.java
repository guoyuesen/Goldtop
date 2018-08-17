package com.goldtop.gys.crdeit.goldtop.acticity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 郭月森 on 2018/7/12.
 */

public class RecommendedAwardsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_awards);
        ButterKnife.bind(this);
        hiedBar(this);
        new TitleBuder(this).setTitleText("邀请有奖").setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setBackgrund(Color.parseColor("#20ffffff"));
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("【金陀螺】为您而设计");
        // titleUrl QQ和QQ空间跳转链接
        //oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("信用卡的规则您真的懂吗？让我们带您进入卡的世界。");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl("http://47.106.103.104/UEditorMe/image/20180809/1533797169567080019.png");
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://47.106.103.104/toRegist?inviteMobile="+ UserModel.custMobile);//http:www.tuoluo718.com/toRegist?inviteMobile=17760523716&from=singlemessage&isappinstalled=0
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

    @OnClick(R.id.fenxiangceshi)
    public void onClick() {
        showShare();
    }

}
