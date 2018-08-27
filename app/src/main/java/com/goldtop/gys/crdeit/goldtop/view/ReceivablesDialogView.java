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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddAdderssActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.AddCard01Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 自定义底部弹出对话框
 * Created by zhaomac on 2017/9/8.
 */

public class ReceivablesDialogView extends Dialog {
    private backTo back;
    private String T="C";
    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    private ListView linearLayout;
    private JSONArray jsonArray;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public ReceivablesDialogView(Context context, JSONArray array,backTo back,String t) {
        super(context, R.style.MyDialog);
        this.jsonArray = array;
        this.context = context;
        this.back = back;
        this.T = t;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_receivables,null);
        view.findViewById(R.id.receivables_dialog_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.findViewById(R.id.receivables_dialog_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (T.equals("C")){
                    AddCard01Activity.initActivity(getContext(),"CC");
                }else {
                    AddCard01Activity.initActivity(getContext(),"DC");
                }
            }
        });
        view.findViewById(R.id.receivables_dialog_addt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (T.equals("C")){
                    AddCard01Activity.initActivity(getContext(),"CC");
                }else {
                    AddCard01Activity.initActivity(getContext(),"DC");
                }
            }
        });

        linearLayout = view.findViewById(R.id.receivables_dialog_layout);

        //this.iscancelable = isCancelable;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (jsonArray.length()==1){
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ContextUtil.dip2px(context,75)));
        }else if(jsonArray.length()>1){
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ContextUtil.dip2px(context,150)));
        }
        linearLayout.setAdapter(new thisAdapter());
        linearLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    dismiss();
                    back.sercsse(T,jsonArray.getJSONObject(i));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        /*for (int i = 0;i<jsonArray.length();i++){
            View lview = LayoutInflater.from(context).inflate(R.layout.item_dialog_address,null);
            linearLayout.addView(lview);
        }*/
        //Log.d("<=======>",""+jsonArray.length());
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
    private class thisAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return jsonArray.length();
        }

        @Override
        public Object getItem(int i) {
            return jsonArray;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ThisItem item;
            if(view == null){
                item = new ThisItem();
                view = LayoutInflater.from(getContext()).inflate(R.layout.item_receivables_dialog,null);
                item.i1 = view.findViewById(R.id.d_i_img);
                item.i2 = view.findViewById(R.id.d_i_tf);
                item.t1 = view.findViewById(R.id.d_i_bankname);
                item.t2 = view.findViewById(R.id.d_i_number);
                item.t3 = view.findViewById(R.id.d_i_type);
                view.setTag(item);
            }else {
                item = (ThisItem) view.getTag();
            }
            JSONObject object = null;
            try {
                object = jsonArray.getJSONObject(i);
                item.t1.setText(object.getString("bankName"));
                item.t2.setText("尾号"+object.getString("accountCode").substring(object.getString("accountCode").length()-4));
                if(object.getString("ioType").equals("IO")){
                    item.t3.setText("信");
                }else {
                    item.t3.setText("储");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ContextUtil.dip2px(getContext(),25)));
            return view;
        }
    }
    class ThisItem{
        TextView t1;
        TextView t2;
        TextView t3;
        ImageView i1;
        ImageView i2;
    }
    public interface backTo{
        void sercsse(String T,JSONObject object);
    }
}

