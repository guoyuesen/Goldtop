package com.goldtop.gys.crdeit.goldtop.service;


import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.goldtop.gys.crdeit.goldtop.acticity.LoginActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by 郭月森 on 2018/6/21.
 */

public class VolleyRequest extends Request<String>{
    private MyVolleyCallback callback=null;
    private Map<String,String> smap;
    private String url;

    public VolleyRequest(String url1,Map<String, String> smap,MyVolleyCallback callback) {
        super(Method.POST, url1.indexOf("?")==-1?(url1+"?token="+UserModel.token):(url1+"&token="+UserModel.token), callback);
        this.callback = callback;
        this.smap = smap;
        this.url = url1;
        Log.d(url+"请求参数：==》",smap.toString());
    }
    public VolleyRequest(int method,String url1,Map<String, String> smap,MyVolleyCallback callback) {
        super(method, url1.indexOf("?")==-1?(url1+"?token="+UserModel.token):(url1+"&token="+UserModel.token), callback);
        this.callback = callback;
        this.smap = smap;
        this.url = url1;
        Log.d(url+"请求参数：==》",smap.toString());
    }

    public VolleyRequest(int method, String url, MyVolleyCallback listener) {
        super(method, url.indexOf("?")==-1?(url+"?token="+UserModel.token):(url+"&token="+UserModel.token), listener);
        this.url = url;
        this.callback = listener;
        //Log.d(url+"请求参数：==》",smap?.toString());
    }
    @Override
    protected void onFinish() {
        super.onFinish();
        callback = null;
    }

    @Override
    protected void deliverResponse(String response) {
        Log.d(url.substring(url.length()-10)+"返回结果：==》",response);
        JSONObject object;
        if (callback != null) {
            if (response.isEmpty()){}else {
                try {
                    object = new JSONObject(response);
                    if (object.has("msg")){
                        callback.context.startActivity(new Intent(callback.context, LoginActivity.class));
                    }else {
                        callback.CallBack(object);
                    }
                } catch (JSONException e) {
                    object = new JSONObject();
                    callback.onErrorResponse(new VolleyError("json转换错误"));
                }
            }
        }
    }
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {

        return smap;
    }
}
