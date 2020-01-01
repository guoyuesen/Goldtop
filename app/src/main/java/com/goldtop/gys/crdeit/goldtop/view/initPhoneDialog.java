package com.goldtop.gys.crdeit.goldtop.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldtop.gys.crdeit.goldtop.R;

/**
 * Created by 郭月森 on 2018/12/28.
 */

public class initPhoneDialog extends Dialog {
    public initPhoneDialog(@NonNull Context context,@NonNull String title,@NonNull final phoneDialog back) {
        super(context);
        View d = LayoutInflater.from(getContext()).inflate(R.layout.dialog_init_phone, null);
        final EditText editText = d.findViewById(R.id.dialog_phone);
        TextView textView = d.findViewById(R.id.init_dialog_title);
        textView.setText(title);
        d.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        d.findViewById(R.id.dialog_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().length()!=11){
                    Toast.makeText(getContext(),"请输入手机号",Toast.LENGTH_LONG).show();
                }else {
                    back.back(editText.getText().toString());
                    dismiss();
                }
            }
        });
        setContentView(d);
    }
    public initPhoneDialog(@NonNull Context context, String title, String text, final initDialog back) {
        super(context);
        View d = LayoutInflater.from(getContext()).inflate(R.layout.dialog_init_phone, null);
        final EditText editText = d.findViewById(R.id.dialog_phone);
        TextView textView = d.findViewById(R.id.init_dialog_title);
        textView.setText(title);
        TextView msg = d.findViewById(R.id.dialog_text);
        msg.setVisibility(View.VISIBLE);
        msg.setText(text);
        final EditText name = d.findViewById(R.id.dialog_name);
        name.setVisibility(View.VISIBLE);
        d.findViewById(R.id.dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        d.findViewById(R.id.dialog_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().isEmpty()){
                    Toast.makeText(getContext(),"请输入姓名",Toast.LENGTH_LONG).show();
                    return;
                }
                if (editText.getText().toString().length()!=11){
                    Toast.makeText(getContext(),"请输入手机号",Toast.LENGTH_LONG).show();
                }else {
                    back.back(name.getText().toString(),editText.getText().toString());
                    //startWeb(editText.getText().toString());
                    dismiss();
                }
            }
        });
        setContentView(d);
    }
    public interface phoneDialog{
        void back(String phong);
    }
    public interface initDialog{
        void back(String name,String phong);
    }
}
