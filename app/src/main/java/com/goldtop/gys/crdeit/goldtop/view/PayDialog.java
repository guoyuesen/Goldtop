package com.goldtop.gys.crdeit.goldtop.view;

/**
 * Created by 郭月森 on 2018/7/9.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddAdderssActivity;

import org.json.JSONArray;


/**
 * 自定义底部弹出对话框
 * Created by zhaomac on 2017/9/8.
 */

public class PayDialog extends Dialog {
    DialogBack dialogBack;
    private View view;
    private Context context;
    RadioGroup radioGroup;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public PayDialog(Context context, final DialogBack dialogBack) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.dialogBack = dialogBack;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_pay,null);
        radioGroup = view.findViewById(R.id.rg);
        final RadioButton b1= view.findViewById(R.id.x0);
        final RadioButton b2= view.findViewById(R.id.x1);
        view.findViewById(R.id.pay_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.findViewById(R.id.zfb).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setChecked(true);
            }
        });
        view.findViewById(R.id.wx).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.setChecked(true);
            }
        });
        view.findViewById(R.id.pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBack.back(radioGroup.getCheckedRadioButtonId()==R.id.x0?0:1);
                dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
    public interface DialogBack{
        void back(int id);
    }
}

