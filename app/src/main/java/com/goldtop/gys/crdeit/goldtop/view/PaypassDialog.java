package com.goldtop.gys.crdeit.goldtop.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.R;


/**
 * Created by 郭月森 on 2018/12/6.
 */

public class PaypassDialog extends AlertDialog {
    String title;
    String btn;
    Paypass paypass;

    public PaypassDialog(Context context, String title, String btn, Paypass paypass) {
        super(context);
        this.title = title;
        this.btn = btn;
        this.paypass = paypass;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_msg);
        TextView textView = findViewById(R.id.paypass_title);
        final TextView editText = findViewById(R.id.msg);
        TextView btns = findViewById(R.id.paypass_submit);
        textView.setText(title);
        editText.setText(btn);
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                paypass.back(s);
                dismiss();
            }
        });
        findViewById(R.id.paypass_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public interface Paypass{
        void back(String pass);
    }
}
