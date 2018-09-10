package com.goldtop.gys.crdeit.goldtop.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldtop.gys.crdeit.goldtop.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/9/5.
 */

public class HistoryItem extends RelativeLayout {
    View view;
    public HistoryItem(Context context, JSONObject object) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.item_history,null);
        ImageView img1 = view.findViewById(R.id.item_history_img1);
        ImageView img2 = view.findViewById(R.id.item_history_img2);
        TextView text1 = view.findViewById(R.id.item_history_text1);
        TextView text2 = view.findViewById(R.id.item_history_text2);
        TextView text3 = view.findViewById(R.id.item_history_text3);
        TextView text4 = view.findViewById(R.id.item_history_text4);
        try {
            switch (object.getString("status")){
                case "PROCESSING":
                    img1.setImageResource(R.mipmap.history_02);
                    img2.setImageResource(R.mipmap.history_05);
                    text1.setTextColor(Color.parseColor("#DA3128"));
                    text2.setTextColor(Color.parseColor("#DA3128"));
                    break;
                case "SUCCESS":
                    img1.setImageResource(R.mipmap.history_01);
                    img2.setImageResource(R.mipmap.history_04);
                    text1.setTextColor(Color.parseColor("#15A282"));
                    text2.setTextColor(Color.parseColor("#15A282"));
                    break;
                case "FAIL":
                    img1.setImageResource(R.mipmap.history_03);
                    img2.setImageResource(R.mipmap.history_06);
                    text1.setTextColor(Color.parseColor("#D6D6D6"));
                    text2.setTextColor(Color.parseColor("#D6D6D6"));
                    break;
            }
            text1.setText("开始时间:"+object.getString("startDate"));
            text2.setText("结束时间:"+object.getString("endDate"));
            text3.setText(object.getString("term"));
            text4.setText(object.getString("paymentAmt"));
            setTag(object.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addView(view);
    }

    public HistoryItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HistoryItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
