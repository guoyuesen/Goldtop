package com.goldtop.gys.crdeit.goldtop.view;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.goldtop.gys.crdeit.goldtop.R;

/**
 * Created by 郭月森 on 2018/7/25.
 */

public class MyCardDialogView extends Dialog {
    private View view;
    DateDialogView.DateBack back;
    CalendarChoice choice;
    Activity activity;
    public MyCardDialogView(@NonNull Activity context, DateDialogView.DateBack back) {
        super(context,R.style.MyDialog);
        activity = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_mycard_clear,null);
        this.back = back;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view.findViewById(R.id.card_dialog_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("~~~~~~~~~~~~~~~~~","~~~~~~~~~~~~~~~~~~~~~~");
                back.callback("");
            }
        });
        view.findViewById(R.id.card_dialog_d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setContentView(view);//这行一定要写在前面
        //setCancelable(iscancelable);//点击外部不可dismiss
        //setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;//ContextUtil.dip2px(getContext(),300);
        window.setAttributes(params);
    }

}
