package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Fragment.FindFragment;
import com.goldtop.gys.crdeit.goldtop.Fragment.HomeFragment;
import com.goldtop.gys.crdeit.goldtop.Fragment.MeFragment;
import com.goldtop.gys.crdeit.goldtop.Fragment.ShpingFragment;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.DialogClick;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/7/2.
 */

public class HomeActivity extends AppCompatActivity {
    @Bind(R.id.home_bottom_img1)
    ImageView homeBottomImg1;
    @Bind(R.id.home_bottom_text1)
    TextView homeBottomText1;
    @Bind(R.id.home_bottom_btn1)
    RelativeLayout homeBottomBtn1;
    @Bind(R.id.home_bottom_img2)
    ImageView homeBottomImg2;
    @Bind(R.id.home_bottom_text2)
    TextView homeBottomText2;
    @Bind(R.id.home_bottom_btn2)
    RelativeLayout homeBottomBtn2;
    @Bind(R.id.home_bottom_img3)
    ImageView homeBottomImg3;
    @Bind(R.id.home_bottom_text3)
    TextView homeBottomText3;
    @Bind(R.id.home_bottom_btn3)
    RelativeLayout homeBottomBtn3;
    @Bind(R.id.home_bottom_img4)
    ImageView homeBottomImg4;
    @Bind(R.id.home_bottom_text4)
    TextView homeBottomText4;
    @Bind(R.id.home_bottom_btn4)
    RelativeLayout homeBottomBtn4;

    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment fragment;
    private HomeFragment homeFragment;
    private FindFragment findFragment;
    private ShpingFragment shpingFragment;
    private MeFragment meFragment;
    private int v = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        //获取管理者
        supportFragmentManager = getSupportFragmentManager();
        //开启事务
        fragmentTransaction = supportFragmentManager.beginTransaction();
        //new TitleBuder(this).setTitleText("首页");
        homeFragment = new HomeFragment();
        fragment = homeFragment;
        fragmentTransaction.add(R.id.home_frame, homeFragment).commit();
    }

    @OnClick({R.id.home_bottom_btn1, R.id.home_bottom_btn2, R.id.home_bottom_btn3, R.id.home_bottom_btn4})
    public void onClick(View view) {

        //fragmentTransaction.setCustomAnimations(R.anim.activity_in,R.anim.activity_out);
        switch (view.getId()) {
            case R.id.home_bottom_btn1:
                Fragment(0);
                break;
            case R.id.home_bottom_btn2:
                Fragment(1);
                //startActivity(new Intent(this,RepaymentMsgActivity.class));
                break;
            case R.id.home_bottom_btn3:
                //startActivity(new Intent(this,LoginActivity.class));
                Fragment(2);
                break;
            case R.id.home_bottom_btn4:
                Fragment(3);
                break;
        }
    }

    private void Fragment(int a) {
        if (v == a) {
            return;
        }
        //获取管理者
        supportFragmentManager = getSupportFragmentManager();
        //开启事务
        fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.hide(fragment);
        switch (a) {
            case 0:
                if (homeFragment == null) {
                    //实例化fragment2
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.home_frame, homeFragment).commit();
                } else {
                    //有的话就显示
                    fragmentTransaction.show(homeFragment).commit();
                }
                //Animation animation = getResources().getAnimation(R.anim.img_click);
                fragment = homeFragment;
                homeBottomImg1.setImageResource(R.mipmap.activity_home_01_1);
                homeBottomImg1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.img_click));
                break;
            case 1:
                if (shpingFragment == null) {
                    //实例化fragment2
                    shpingFragment = new ShpingFragment();
                    fragmentTransaction.add(R.id.home_frame, shpingFragment).commit();
                } else {
                    //有的话就显示
                    fragmentTransaction.show(shpingFragment).commit();
                }
                fragment = shpingFragment;
                homeBottomImg2.setImageResource(R.mipmap.activity_home_02_1);
                homeBottomImg2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.img_click));
                break;
            case 2:
                if (findFragment == null) {
                    //实例化fragment2
                    findFragment = new FindFragment();
                    fragmentTransaction.add(R.id.home_frame, findFragment).commit();
                } else {
                    //有的话就显示
                    fragmentTransaction.show(findFragment).commit();
                }
                fragment = findFragment;
                //Animation animation = getResources().getAnimation(R.anim.img_click);
                homeBottomImg3.setImageResource(R.mipmap.activity_home_03_1);
                homeBottomImg3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.img_click));
                break;
            case 3:
                    if (meFragment == null) {
                        //实例化fragment2
                        meFragment = new MeFragment();
                        meFragment.setClick(new DialogClick() {
                            @Override
                            public void onClick(View v) {
                                Fragment(1);
                            }
                        });
                        fragmentTransaction.add(R.id.home_frame, meFragment).commit();
                    } else {
                        //有的话就显示
                        fragmentTransaction.show(meFragment).commit();
                    }
                    fragment = meFragment;
                    homeBottomImg4.setImageResource(R.mipmap.activity_home_04_1);
                    homeBottomImg4.startAnimation(AnimationUtils.loadAnimation(this, R.anim.img_click));
                break;
        }
        settup(v);
        v = a;
    }

    public void settup(int up) {
        switch (up){
            case 0:
                homeBottomImg1.setImageResource(R.mipmap.activity_home_01_0);
                break;
            case 1:
                homeBottomImg2.setImageResource(R.mipmap.activity_home_02_0);
                break;
            case 2:
                homeBottomImg3.setImageResource(R.mipmap.activity_home_03_0);
                break;
            case 3:
                homeBottomImg4.setImageResource(R.mipmap.activity_home_04_0);
                break;
        }
    }
}
