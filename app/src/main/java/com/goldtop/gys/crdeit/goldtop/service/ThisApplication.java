package com.goldtop.gys.crdeit.goldtop.service;

import android.app.Application;
import android.content.Context;

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
}
