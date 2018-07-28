package com.goldtop.gys.crdeit.goldtop.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;

/**
 * Created by 郭月森 on 2018/7/25.
 */

public class DateDialogView extends Dialog {
    private View view;
    DateBack back;
    public DateDialogView(@NonNull Context context,DateBack back) {
        super(context,R.style.MyDialog);
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_date,null);
        this.back = back;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view.findViewById(R.id.date_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.findViewById(R.id.date_dialog_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarChoice choice = view.findViewById(R.id.date_dialog_choice);
                back.callback(choice.getContent());
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
    public interface DateBack{
        void callback(String str);
    }
}
