package com.goldtop.gys.crdeit.goldtop.service;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.goldtop.gys.crdeit.goldtop.R;
import com.mob.MobSDK;

/**
 * Created by Administrator on 2018/6/20.
 */

public class ThisApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        MyVolley.init(getApplicationContext());
        context = getApplicationContext();
        MobSDK.init(this);

    }
    public static Context getContext(){
        return context;
    }
    public static String getVersion()//获取版本号
    {
        try {
            PackageInfo pi=getContext().getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "0.0.0";
        }
    }
}
