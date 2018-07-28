package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebBackForwardList;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

/**
 * Created by 郭月森 on 2018/6/26.
 */

public class WebUtilActivity extends BaseActivity {
    private static String title = "";
    private static String url = "";
    private WebView view;
    String mHtml="";
    public static void InWeb(@NonNull Context context,String url, String title, Bundle bundle){
        WebUtilActivity.title = title;
        WebUtilActivity.url = url;
        Log.d("url地址：==》",url);
        Intent intent = new Intent(context,WebUtilActivity.class);
        if (bundle!=null){
            intent.putExtra("bundle",bundle);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_util);
        if (!title.equals("")){
            new TitleBuder(this).setLeftImage(R.mipmap.ic_launcher).setTitleText(title).setLeftListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        view = findViewById(R.id.util_web);
        view.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                WebBackForwardList webBackForwardList=view.copyBackForwardList();        //       获取WebView 加载的历史数据
                int index = webBackForwardList.getSize();                                    //       获取堆栈中的历史数据长度
                if(view.getUrl().equals("about:blank")&&index>1&&!TextUtils.isEmpty(mHtml)) {  //       满足上述bug的条件
                    view.clearHistory();                                                     //       恢复WebView到初始状态
                    view.loadDataWithBaseURL(null, mHtml, "text/html", "utf-8", null);
                }
                }
        });
        if (getIntent().getBundleExtra("bundle")!=null){
            mHtml = getIntent().getBundleExtra("bundle").getString("html");
            view.loadDataWithBaseURL(null, mHtml, "text/html", "utf-8",null);
        }else {
            view.loadUrl(url);
        }
        initActivity();
    }

    private void initActivity() {
        WebSettings settings = view.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&view.canGoBack()){
            view.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
