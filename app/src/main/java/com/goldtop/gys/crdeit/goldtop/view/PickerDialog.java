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
import android.widget.ScrollView;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddAdderssActivity;
import com.goldtop.gys.crdeit.goldtop.model.Pickers;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义底部弹出对话框
 * Created by zhaomac on 2017/9/8.
 */

public class PickerDialog extends Dialog {
    private View view;
    private Context context;
    List<List<Pickers>> lists;
    String[] strings;
    List<PickerScrollView> pickers;
    List<View> views;
    List<TextView> textViews;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public PickerDialog(Context context, final List<List<Pickers>> lists,String[] strings, final PickerBack pickerBack) {
        super(context, R.style.MyDialog);
        this.context = context;
        this.lists = lists;
        this.strings = strings;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_rolling_choice,null);
        pickers = new ArrayList<PickerScrollView>();
        pickers.add((PickerScrollView) view.findViewById(R.id.picker_scroll_picker0));
        pickers.add((PickerScrollView) view.findViewById(R.id.picker_scroll_picker1));
        pickers.add((PickerScrollView) view.findViewById(R.id.picker_scroll_picker2));
        views = new ArrayList<View>();
        views.add(view.findViewById(R.id.picker_scroll_layout0));
        views.add(view.findViewById(R.id.picker_scroll_layout1));
        views.add(view.findViewById(R.id.picker_scroll_layout2));
        textViews = new ArrayList<TextView>();
        textViews.add((TextView) view.findViewById(R.id.picker_scroll_text0));
        textViews.add((TextView) view.findViewById(R.id.picker_scroll_text1));
        textViews.add((TextView) view.findViewById(R.id.picker_scroll_text2));
        for (int i = 0;i<lists.size();i++){
            views.get(i).setVisibility(View.VISIBLE);
            textViews.get(i).setText(strings[i]);
            pickers.get(i).setData(lists.get(i));
            pickers.get(i).setSelected(0);
        }
        view.findViewById(R.id.picker_scroll_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        view.findViewById(R.id.picker_scroll_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] ids = new String[lists.size()];
                for (int i = 0;i<lists.size();i++){
                    ids[i] = pickers.get(i).getData().getShowId();
                }
                pickerBack.back(ids);
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
    public interface PickerBack{
        public void back(String[] choices);
    }
}

