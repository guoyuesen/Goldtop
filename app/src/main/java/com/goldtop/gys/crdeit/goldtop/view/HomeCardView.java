package com.goldtop.gys.crdeit.goldtop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/12/28.
 */

public class HomeCardView extends RelativeLayout {


    public HomeCardView(Context context, JSONObject object) {
        super(context);
        initView(object);
    }

    public HomeCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(JSONObject object) {
        try {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.home_card_list, null);
            ImageView imageView = inflate.findViewById(R.id.img);
            MyVolley.getImage(object.getString("href"),imageView);
            TextView name = inflate.findViewById(R.id.name);
            TextView msg = inflate.findViewById(R.id.msg);
            TextView text1 = inflate.findViewById(R.id.text1);
            TextView text2 = inflate.findViewById(R.id.text2);
            name.setText(object.getString("title"));
            msg.setText(object.getString("content"));
            String string = object.getString("items");
            String [] stringArr= string.split(",");
            text1.setText(stringArr[0]);
            text2.setText(stringArr[1]);
            inflate.findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getContext(),"申请",Toast.LENGTH_LONG).show();
                }
            });
            addView(inflate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
