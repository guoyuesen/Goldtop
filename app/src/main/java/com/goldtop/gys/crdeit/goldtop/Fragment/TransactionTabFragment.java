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
import com.goldtop.gys.crdeit.goldtop.Adapters.TransactionAdapter;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddCard01Activity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.service.formRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by lijuan on 2016/8/23.
 */
public class TransactionTabFragment extends Fragment {
    private TextView textView;

    public static TransactionTabFragment newInstance(String type){
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        TransactionTabFragment fragment = new TransactionTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transaction, null);
        //textView = (TextView) view.findViewById(R.id.text);
        //textView.setText(String.valueOf((char) getArguments().getInt("index")));
        ListView listView = view.findViewById(R.id.statistics_list);
        View em = view.findViewById(R.id.transaction_empty);
        listView.setAdapter(new TransactionAdapter(getContext(),new JSONArray()));
        listView.setEmptyView(em);
        getcard(getArguments().getString("type"),listView);
        return view;
    }
    public void getcard(final String t, final ListView listView) {
        Map<String,String> mapd = new HashMap<String, String>();
        mapd.put("custId", UserModel.custId);
        mapd.put("analysisType",t);
        MyVolley.addRequest(new formRequest(Action.tradeDetail, mapd, new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getString("code").equals("1")){
                        listView.setAdapter(new TransactionAdapter(getContext(),jsonObject.getJSONArray("data")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
    }
}
