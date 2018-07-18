package com.goldtop.gys.crdeit.goldtop.service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.goldtop.gys.crdeit.goldtop.model.VersionCodeBean;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * Created by 郭月森 on 2018/6/25.
 */

public class VersionCodeUpdate {
    private Context context;
    /**
     * 更新APK入口
     * @param context
     * @param updateCallback
     */
    public void updateVersionCode(Context context, final UpdateCallback updateCallback){
        this.context = context;
        final int versionCode = getVersionCode();
        StringRequest request = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /**
                 * 解析 获取服务器版本信息
                 */
                VersionCodeBean versionCodeBean = gson.fromJson(response, VersionCodeBean.class);
                if(Float.valueOf(versionCodeBean.getVersionCode())
                        == Float.valueOf(versionCode)){
                    //不需要更新
                    updateCallback.dontUpdate();
                }else if(Float.valueOf(versionCodeBean.getVersionCode())
                        > Float.valueOf(versionCode)){
                    //需要更新
                    infoToPersonUpdate(versionCodeBean);
                    updateCallback.newUpdate();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updateCallback.dontUpdate();
            }
        });
        MyVolley.addRequest(request);

    }

    /**
     * 更新提示Dialog
     * @param versionCodeBean
     */
    public void infoToPersonUpdate(final VersionCodeBean versionCodeBean){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("版本提示：");
        builder.setMessage("发现新版本：\n" + "版本号：" + versionCodeBean.getVersionCode() + "\n版本更新：内容" + versionCodeBean.getContent());
        builder.setCancelable(false);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //更新
            }
        });
        builder.setNegativeButton("不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //不更新
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 下载apk
     * @param versionCodeBean
     */
    private void downLoaderApk(VersionCodeBean versionCodeBean){
        //显示下载进度
        progressDialog();
        //下载apk
        new Thread(new Runnable() {
            @Override
            public void run() {
                File path = new File(Environment.getExternalStorageDirectory() + "/cache/app");
                File file = new File(Environment.getExternalStorageDirectory() + "/cache/app/apkDownLoad.apk");
                if(path == null || !path.exists()){
                    path.mkdirs();
                }
                if(file != null && file.exists()){
                    file.delete();
                }
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedOutputStream bufferedOutputStream = null;
                BufferedInputStream bufferedInputStream = null;
                HttpURLConnection httpURLConnection = null;
                try {
                    URL url = new URL("");
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
                    int contentLength = httpURLConnection.getContentLength();
                    bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                    byte[] bytes = new byte[1024];
                    int length;
                    int totalLength = 0;
                    while((length = bufferedInputStream.read(bytes)) != -1 ){
                        //下载中
                        bufferedOutputStream.write(bytes, 0, length);
                        totalLength += length;

                        Message message = handler.obtainMessage();
                        message.arg2 = totalLength / contentLength;
                        handler.sendMessage(message);
                    }
                    bufferedOutputStream.flush();
                    //下载完成
                    Message message = handler.obtainMessage();
                    message.arg2 = 200;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    //下载失败
                    Message message = handler.obtainMessage();
                    message.arg2 = 150;
                    handler.sendMessage(message);
                }finally {
                    if(httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                    if(bufferedInputStream != null){
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(bufferedOutputStream != null){
                        try {
                            bufferedOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }).start();
    }

    /**
     * 下载进度Dialog
     */
    private AlertDialog progressDialog;
    private void progressDialog(){
        AlertDialog.Builder  builder = new AlertDialog.Builder(context);
        builder.setMessage("正在为您更新...， 请不要断开网络！");
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //取消更新
                dialog.dismiss();
            }
        });
        progressDialog = builder.create();
        progressDialog.show();
    }

    /**
     * 获取本地版本号
     * @return
     */
    private int getVersionCode(){
        PackageManager packageManager = context.getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int arg2 = msg.arg2;
            if(arg2 == 200){
                //下载完成
                progressDialog.dismiss();
                installApk();
            }else if(arg2 == 150){
                //下载失败
                Toast.makeText(context, "更新失败！", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }else if(arg2 > 0 && arg2 < 101){
                /**
                 * TODO 下载中 更新进度条
                 */
            }
        }
    };

    /**
     * 安装apk
     */
    private void installApk(){
        File apkFile = new File(Environment.getExternalStorageDirectory() + "/cache/app/apkDownLoad.apk");
        if(! apkFile.exists()){
            Toast.makeText(context, "安装包不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
    public interface  UpdateCallback{
        void newUpdate();//需要更新
        void dontUpdate();//不需要更新
    }
}
