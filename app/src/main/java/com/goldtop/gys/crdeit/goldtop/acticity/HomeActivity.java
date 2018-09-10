package com.goldtop.gys.crdeit.goldtop.acticity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Fragment.FindFragment;
import com.goldtop.gys.crdeit.goldtop.Fragment.HomeFragment;
import com.goldtop.gys.crdeit.goldtop.Fragment.MeFragment;
import com.goldtop.gys.crdeit.goldtop.Fragment.ShpingFragment;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.Tools;
import com.goldtop.gys.crdeit.goldtop.interfaces.DialogClick;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.updatedownload.DownFileHelper;
import com.goldtop.gys.crdeit.goldtop.updatedownload.InstallApk;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

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
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (Build.VERSION.SDK_INT >= 26) {
                        boolean b = HomeActivity.this.getPackageManager().canRequestPackageInstalls();
                        if (b) {
                            new InstallApk(HomeActivity.this)
                                    .installApk(new File(Environment.getExternalStorageDirectory(), "your_app_name.apk"));
                        } else {
                            //请求安装未知应用来源的权限
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, 10102);
                        }
                    } else {
                        new InstallApk(HomeActivity.this)
                                .installApk(new File(Environment.getExternalStorageDirectory(), "your_app_name.apk"));
                    }

                    break;
                case 1:

                    break;
            }

        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        /*XXPermissions.with(this)
                //.permission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,})
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll){
                            Toast.makeText(HomeActivity.this,"金陀螺感谢您的支持",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {

                    }
                });*/
        getV();
        //获取管理者
        supportFragmentManager = getSupportFragmentManager();
        //开启事务
        fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.activity_in, R.anim.activity_out,
                R.anim.activity_in, R.anim.activity_out
        );
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
        fragmentTransaction.setCustomAnimations(
                R.anim.activity_in, R.anim.activity_out,
                R.anim.activity_in, R.anim.activity_out
        );
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

    public void getV(){
        MyVolley.addRequest(new VolleyRequest(Action.version, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    final JSONObject data = jsonObject.getJSONObject("data");
                    Log.d("-----",data.getInt("code")+"----"+ Tools.getVersion(HomeActivity.this));
                    if (data.getInt("code")> Tools.getVersion(HomeActivity.this)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this).setTitle("提示")
                                .setMessage("发现新版本，请更新!")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                            XXPermissions.with(HomeActivity.this)
                                                    .permission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,})
                                                    .request(new OnPermission() {

                                                        @Override
                                                        public void hasPermission(List<String> granted, boolean isAll) {
                                                            if (isAll){
                                                                try {
                                                                    new DownFileHelper(HomeActivity.this, handler)
                                                                            .downFile("http://www.tuoluo718.com/app/download",data.getString("updateNote"));
                                                                    Toast.makeText(HomeActivity.this,"更新包下载任务已开启，请耐心等待",Toast.LENGTH_LONG).show();
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void noPermission(List<String> denied, boolean quick) {

                                                        }
                                                    });

                                        dialogInterface.dismiss();

                                    }
                                });
                        if (data.getInt("flag") == 0) {
                            builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                        }
                        builder.create().show();

                    }else {

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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10102:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new InstallApk(HomeActivity.this)
                            .installApk(new File(Environment.getExternalStorageDirectory(), "your_app_name.apk"));
                    Log.d(">_<","0");
                } else {
                    AlertDialog dialog = new AlertDialog.Builder(this).setMessage("为了您的信息安全，我们的安装需要您的授权！")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(HomeActivity.this,"更新失败，请前往官网重新下载或授权安装",Toast.LENGTH_LONG).show();
                                    dialogInterface.dismiss();
                                }
                            }).setNeutralButton("去设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                                    startActivityForResult(intent, 10103);
                                    Log.d(">_<","1");
                                    dialogInterface.dismiss();
                                }
                            }).create();
                    dialog.show();
                }

                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10103){
            handler.sendEmptyMessage(0);
            Log.d(">_<","2");
        }
    }
}
