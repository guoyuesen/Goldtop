package com.goldtop.gys.crdeit.goldtop.view;

/**
 * Created by 郭月森 on 2018/7/9.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.ZhxzAdapter;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AuthenticationActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 自定义底部弹出对话框
 * Created by zhaomac on 2017/9/8.
 */

public class CSxzDialogView extends Dialog {
    ImageView csxzDialogClose;
    TextView csxzDialogT1;
    TextView csxzDialogT2;
    View viewSheng;
    View viewShi;
    ListView csxzDialogSs;
    ProgressBar pbLoad;
    TextView pbText;
    LinearLayout csxzDialogEm;
    LinearLayout csxzDialogNr;
    JSONArray array1 = new JSONArray();
    JSONArray array2 = new JSONArray();
    private View view;
    Context context;
    int index = 0;
    int a0=0;
    CsBack back;

    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public CSxzDialogView(Context context,CsBack back) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.back = back;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_csxz, null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        csxzDialogClose = view.findViewById(R.id.csxz_dialog_close);
        csxzDialogT1  = view.findViewById(R.id.csxz_dialog_t1);
         csxzDialogT2  = view.findViewById(R.id.csxz_dialog_t2);
        viewSheng  = view.findViewById(R.id.view_sheng);
        viewShi  = view.findViewById(R.id.view_shi);
        csxzDialogSs  = view.findViewById(R.id.csxz_dialog_ss);
        pbLoad  = view.findViewById(R.id.pb_load);
        pbText  = view.findViewById(R.id.pb_text);
        csxzDialogEm  = view.findViewById(R.id.csxz_dialog_em);
        csxzDialogNr  = view.findViewById(R.id.csxz_dialog_nr);
        csxzDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        csxzDialogSs.setEmptyView(csxzDialogEm);
        csxzDialogT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                csxzDialogSs.setAdapter(new ZhxzAdapter(context,array1));
                index = 0;
            }
        });
        getList();
        csxzDialogSs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                if (index == 0){

                        csxzDialogT1.setText(array1.getJSONObject(i).getString("name"));
                        index++;
                        a0 = i;
                    getList();
                }else {
                    csxzDialogT2.setText(array2.getJSONObject(i).getString("name"));
                    back.Back(array1.getJSONObject(a0).getString("name"),array2.getJSONObject(i).getString("name"),array1.getJSONObject(a0).getString("code"),array2.getJSONObject(i).getString("code"));
                    dismiss();
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    public void getList() {
        try {
            final String url;
            Map<String,String> map = new HashMap<>();
            if (index == 1) {
                url = Action.getAnge;// + "?parentCode=" + array1.getJSONObject(a0).getString("code");
                map.put("parentCode",array1.getJSONObject(a0).getString("code"));
            } else {
                url = Action.getAnge;
            }
            Log.d("<=====>", url);
            pbLoad.setVisibility(View.VISIBLE);
            pbText.setVisibility(View.GONE);
            csxzDialogSs.setAdapter(new ZhxzAdapter(context,new JSONArray()));
            MyVolley.addRequest(new formRequest(url, map, new MyVolleyCallback(context) {
                @Override
                public void CallBack(JSONObject jsonObject) {
                    pbLoad.setVisibility(View.GONE);
                    pbText.setVisibility(View.VISIBLE);
                    try {
                        if (jsonObject.getString("code").equals("1")) {
                            if (index == 0) {
                                array1 = jsonObject.getJSONArray("data");
                                csxzDialogSs.setAdapter(new ZhxzAdapter(context,array1));
                            } else if (index == 1) {
                                array2 = jsonObject.getJSONArray("data");
                                csxzDialogSs.setAdapter(new ZhxzAdapter(context,array2));
                            }

                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    pbLoad.setVisibility(View.GONE);
                    pbText.setVisibility(View.VISIBLE);
                    pbText.setText("网络请求失败");
                }
            }));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public interface CsBack{
        void Back(String c,String C,String S,String s);
    }
}

