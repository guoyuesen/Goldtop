package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldtop.gys.crdeit.goldtop.Base.AppUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/7/6.
 */

public class HomeShpingAdapter extends BaseAdapter {
    Context context;
    JSONArray array = new JSONArray();

    public HomeShpingAdapter(Context context, JSONArray array) {
        this.context = context;
        this.array = array;
    }

    @Override
    public int getCount() {
        return array.length();
    }

    @Override
    public Object getItem(int i) {
        try {
            return array.get(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void notifyDataSetChanged(JSONArray array) {
        this.array = array;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ThisItem item = null;
        if (view == null) {
            item = new ThisItem();
            view = LayoutInflater.from(context).inflate(R.layout.item_spf_list, null);
            item.img = view.findViewById(R.id.item_sp_img);
            item.text = view.findViewById(R.id.item_sp_text);
            item.jf = view.findViewById(R.id.item_sp_jf);
            //item.dh = view.findViewById(R.id.sp_dh);
            view.setTag(item);
        }else {
            item = (ThisItem) view.getTag();
        }
        try {
            JSONObject object = array.getJSONObject(i);
            MyVolley.getImage(object.getString("productPic")+"&token="+ UserModel.token,item.img);
            item.text.setText(object.getString("description"));
            item.jf.setText(""+object.getInt("price"));
            /*item.dh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (AppUtil.isLogin(context)){
                        Toast.makeText(context,"积分不足",Toast.LENGTH_LONG).show();
                    }
                }
            });*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
    class ThisItem{
        ImageView img;
        TextView text;
        TextView jf;
        View dh;

    }
}
