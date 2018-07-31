package com.goldtop.gys.crdeit.goldtop.view;

/**
 * Created by 郭月森 on 2018/7/9.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddAdderssActivity;

import org.json.JSONArray;


/**
 * 自定义底部弹出对话框
 * Created by zhaomac on 2017/9/8.
 */

public class ButtomDialogView extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    private ScrollView scrollView;
    private LinearLayout linearLayout;
    private JSONArray jsonArray;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public ButtomDialogView(Context context, JSONArray array) {
        super(context, R.style.MyDialog);
        this.jsonArray = array;
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_address,null);
        view.findViewById(R.id.sp_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        scrollView = view.findViewById(R.id.dialog_address_scroll);
        linearLayout = view.findViewById(R.id.dialog_address_layout);
        //this.iscancelable = isCancelable;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (jsonArray.length()==1){
            scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ContextUtil.dip2px(context,75)));
        }else if(jsonArray.length()>1){
            scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ContextUtil.dip2px(context,150)));
        }
        for (int i = 0;i<jsonArray.length();i++){
            View lview = LayoutInflater.from(context).inflate(R.layout.item_dialog_address,null);
            linearLayout.addView(lview);
        }
        //Log.d("<=======>",""+jsonArray.length());
        view.findViewById(R.id.add_adderss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), AddAdderssActivity.class));
            }
        });
        setContentView(view);//这行一定要写在前面
        //setCancelable(iscancelable);//点击外部不可dismiss
        //setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }
}

