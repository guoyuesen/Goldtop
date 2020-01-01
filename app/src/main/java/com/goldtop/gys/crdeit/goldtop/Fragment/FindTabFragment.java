package com.goldtop.gys.crdeit.goldtop.Fragment;

/**
 * Created by 郭月森 on 2018/8/27.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.FindAdapter;
import com.goldtop.gys.crdeit.goldtop.Adapters.Orderdapter;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.WebUtilActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
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
public class FindTabFragment extends Fragment {
    JSONArray array;

    public static FindTabFragment newInstance(int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        FindTabFragment fragment = new FindTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_findtab, null);
        final ListView listView = view.findViewById(R.id.fragment_find_tab);
        View em = view.findViewById(R.id.order_empty);
        array = new JSONArray();
        listView.setAdapter(new FindAdapter(getContext(), array));
        listView.setEmptyView(em);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    WebUtilActivity.InWeb(getContext(),"http://120.79.172.84:8080/UEditorMe/Index?id="+array.getJSONObject(i).getString("id"),"",null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://120.79.172.84:8080/UEditorMe/GetList?type="+getArguments().getInt("type"), new HashMap<String, String>(), new MyVolleyCallback(getContext()) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    array = jsonObject.getJSONArray("data");
                    listView.setAdapter(new FindAdapter(getContext(), array));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }));
        return view;
    }

}
