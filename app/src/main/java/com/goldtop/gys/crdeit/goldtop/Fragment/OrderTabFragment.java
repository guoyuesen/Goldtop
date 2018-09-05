package com.goldtop.gys.crdeit.goldtop.Fragment;

/**
 * Created by 郭月森 on 2018/8/27.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.MyCardAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.Orderdapter;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddCard01Activity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 *
 * Created by lijuan on 2016/8/23.
 */
public class OrderTabFragment extends Fragment {
    private TextView textView;

    public static OrderTabFragment newInstance(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        OrderTabFragment fragment = new OrderTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);
        ListView listView = view.findViewById(R.id.order_list);
        View em = view.findViewById(R.id.order_empty);
        listView.setAdapter(new Orderdapter(getContext(), new JSONArray()));
        listView.setEmptyView(em);
        return view;
    }

}
