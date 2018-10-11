package com.goldtop.gys.crdeit.goldtop.acticity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.QRCode;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by 郭月森 on 2018/9/26.
 */

public class ShareActivity extends BaseActivity {
    @Bind(R.id.share_bg)
    ImageView shareBg;
    @Bind(R.id.share_qrcode)
    ImageView shareQrcode;
    @Bind(R.id.share_qrcode2)
    ImageView shareQrcode2;
    @Bind(R.id.share_view)
    RelativeLayout shareView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        hiedBar(this);
        ButterKnife.bind(this);
        new TitleBuder(this).setLeftImage(R.mipmap.back_to).setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }).setBackgrund(Color.parseColor("#00000000"));

        //MyVolley.getImage(getIntent().getStringExtra("imgurl"),shareBg);
        RelativeLayout layout = findViewById(R.id.share_imgview2);
        if(getIntent().getStringExtra("type").equals("1")){
            layout.setVisibility(View.GONE);
        }else {
            shareQrcode.setVisibility(View.GONE);
        }
        ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
        layoutParams.width = ContextUtil.getX(this);
        layoutParams.height = (int)(ContextUtil.getX(this)*0.925);//
        layout.setLayoutParams(layoutParams);
        //setViewFullScreen(layout);
        setViewFullScreen(shareQrcode);
        setViewFullScreen(shareQrcode2);
        Glide.with(this)
                .load(getIntent().getStringExtra("imgurl")).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {

                        shareBg.setImageBitmap(bitmap);
                    }
                });
        shareQrcode.setImageBitmap(QRCode.createQRCodeWithLogo("http://www.tuoluo718.com/toRegist?inviteMobile="+ UserModel.custMobile, (int) (ContextUtil.getX(this) * 0.280), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher1)));
        shareQrcode2.setImageBitmap(QRCode.createQRCodeWithLogo("http://www.tuoluo718.com/toRegist?inviteMobile="+ UserModel.custMobile, (int) (ContextUtil.getX(this) * 0.280), BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher1)));
    }

    private void setViewFullScreen(ImageView view) {
        ViewGroup.LayoutParams layoutParams1 = view.getLayoutParams();
        layoutParams1.width = (int) (ContextUtil.getX(this) * 0.280);
        layoutParams1.height = (int) (ContextUtil.getX(this) * 0.280);
        view.setLayoutParams(layoutParams1);
    }

    public static Bitmap getBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        // Draw background
        Drawable bgDrawable = v.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(c);
        else
            c.drawColor(Color.WHITE);
        // Draw view to canvas
        v.draw(c);
        return b;
    }

    private void showShare(Bitmap bitmap) {
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
        //oks.setImageUrl("http://www.mchomes.cn/jtlfx.png");
        oks.setImageData(bitmap);
        // url在微信、微博，Facebook等平台中使用
        // oks.setUrl("http://www.tuoluo718.com/toRegist?inviteMobile="+ UserModel.custMobile);//http:www.tuoluo718.com/toRegist?inviteMobile=17760523716&from=singlemessage&isappinstalled=0
        // comment是我对这条分享的评论，仅在人人网使用
        //oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

    @OnClick(R.id.share_fx)
    public void onClick() {
        showShare(getBitmapFromView(shareView));
    }
}
