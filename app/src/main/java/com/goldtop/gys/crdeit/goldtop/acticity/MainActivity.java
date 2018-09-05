package com.goldtop.gys.crdeit.goldtop.acticity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.Tools;
import com.goldtop.gys.crdeit.goldtop.interfaces.DateButtonListener;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.updatedownload.DownFileHelper;
import com.goldtop.gys.crdeit.goldtop.updatedownload.InstallApk;
import com.goldtop.gys.crdeit.goldtop.updatedownload.PermissionHelper;
import com.goldtop.gys.crdeit.goldtop.view.CommonProgressDialog;
import com.goldtop.gys.crdeit.goldtop.view.DateButton;
import com.yanzhenjie.permission.AndPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {
    private CommonProgressDialog pBar;
    ImageView imageView;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    new InstallApk(MainActivity.this)
                            .installApk(new File(Environment.getExternalStorageDirectory(), "your_app_name.apk"));
                    break;
                case 1:

                    break;
            }

        }
    };
    // 要申请的权限
      //private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
      //private AlertDialog dialog1;
    boolean b = false;
    boolean c = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.Main_img);
        permissionsCheck();
        SharedPreferences sp = getSharedPreferences("SP_PEOPLE", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String peopleJson = sp.getString("KEY_LOGING_DATA","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if(peopleJson!="")  //防空判断
        {
            DateButton dateButton = findViewById(R.id.Main_btn);
            dateButton.setthisText("跳过");
            dateButton.setNum(3);
            dateButton.setOnClick(new DateButtonListener() {
                @Override
                public void onClick(View view) {
                    if (b)
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                }

                @Override
                public void onStart() {
                    getV();
                }

                @Override
                public void onStop() {
                    c = true;
                    tiaozhuan();
                }
            });
            dateButton.Start();
        }else {
            SharedPreferences.Editor editor = sp.edit();;
            editor.putString("KEY_LOGING_DATA", "-------") ; //存入json串
            editor.commit() ; //提交
            startActivity(new Intent(MainActivity.this,ExhibitionActivity.class));
            finish();
        }

    }
    public void getV(){
        MyVolley.addRequest(new VolleyRequest(Action.version, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    JSONObject data = jsonObject.getJSONObject("data");
                    Log.d("-----",data.getInt("code")+"----"+ Tools.getVersion(MainActivity.this));
                    if (data.getInt("code")> Tools.getVersion(MainActivity.this)){
                        AlertDialog builder = new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                                .setMessage("发现新版本，请更新!")
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        new DownFileHelper(MainActivity.this, handler)
                                                .downFile("http://www.tuoluo718.com/app/download");
                                        dialogInterface.dismiss();
                                        Toast.makeText(MainActivity.this,"更新包下载任务已在后台开启，请耐心等待",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                        finish();
                                    }
                                }).create();
                        builder.show();

                    }else {
                        b = true;
                        tiaozhuan();
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
    private void tiaozhuan() {
        if (b&&c){
            startActivity(new Intent(this,HomeActivity.class));
            finish();
        }
    }


    private void permissionsCheck() {
        PermissionHelper permissionHelper = new PermissionHelper(this);
        String[] mlist = {Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_NETWORK_STATE};
        if (!permissionHelper.checkPermissionAllGranted(mlist)) {
            permissionHelper.requestPermissionAllGranted(mlist, 1);
        } /*else {
            new DownFileHelper(MainActivity.this, handler)
                    .downFile("http://www.mchomes.cn/app-release.apk");
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new DownFileHelper(MainActivity.this, handler)
                            .downFile("http://www.mchomes.cn/app-release.apk");

                } else {
                    //不给读写权限处理
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new InstallApk(MainActivity.this)
                            .installApk(new File(Environment.getExternalStorageDirectory(), "your_app_name.apk"));
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    startActivityForResult(intent, 10012);
                }
                break;
        }
    }
}
