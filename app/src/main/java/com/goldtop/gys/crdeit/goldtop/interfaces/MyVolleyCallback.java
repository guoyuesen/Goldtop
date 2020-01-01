package com.goldtop.gys.crdeit.goldtop.interfaces;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/6/21.
 */

public abstract class MyVolleyCallback implements Response.ErrorListener{
    public Context context;
    public MyVolleyCallback(Context context){
        this.context = context;
    }
    public abstract void CallBack( JSONObject jsonObject);
}
