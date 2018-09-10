package com.goldtop.gys.crdeit.goldtop.Fragment;

/**
 * Created by 郭月森 on 2018/8/27.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Adapters.MyCardAdapter;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.acticity.AddCard01Activity;
import com.goldtop.gys.crdeit.goldtop.acticity.BankCardMessgeActivity;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.HttpsDialogView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 *
 * Created by lijuan on 2016/8/23.
 */
public class MycardTabFragment extends Fragment {
    private TextView textView;
    MyCardAdapter adapter;
    JSONArray array;
    String type;
    public static String p = "";
    ListView listView;

    public static MycardTabFragment newInstance(String type){
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        MycardTabFragment fragment = new MycardTabFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mycard, null);
        listView = view.findViewById(R.id.my_card_list);
        View em = view.findViewById(R.id.my_card_empty);
        TextView t1 = em.findViewById(R.id.my_card_empty_t1);
        TextView t2 = em.findViewById(R.id.my_card_empty_t2);
        TextView t3 = em.findViewById(R.id.my_card_footer_text);
        if (getArguments().getString("type").equals("C")){
            t1.setText("暂无信用卡");
            t2.setText("哎呀～您没有绑定信用卡哦！");
            t3.setText("添加信用卡");
        }else {
            t1.setText("暂无储蓄卡");
            t2.setText("哎呀～您没有绑定储蓄卡哦！");
            t3.setText("添加储蓄卡");
        }
        View btn = em.findViewById(R.id.my_card_empty_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments().getString("type").equals("C")){
                    AddCard01Activity.initActivity(getContext(),"CC");
                }else {
                    AddCard01Activity.initActivity(getContext(),"DC");
                }

            }
        });
        adapter = new MyCardAdapter(getContext(), new JSONArray());
        listView.setAdapter(adapter);
        listView.setEmptyView(em);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (type.equals("D")&&i == 0){
                    Toast.makeText(getContext(),"实名认证储蓄卡不可操作!",Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(getContext(), BankCardMessgeActivity.class);
                try {
                    intent.putExtra("bankobj",array.getJSONObject(i).toString());
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        type = getArguments().getString("type");
        getcard(type,listView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (p.equals(type)) {
            getcard(type,listView);
            p = "";
        }
    }
    View footer = null;
    public void getcard(final String t, final ListView listView) {
        final HttpsDialogView view = new HttpsDialogView(getContext());
        view.show();
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.queryBankCard + "?custId=" + UserModel.custId + "&cardType="+t, new HashMap<String, String>(), new MyVolleyCallback() {
            @Override
            public void CallBack(JSONObject jsonObject) {
                view.dismiss();
                try {
                    if (jsonObject.getString("code").equals("1")) {
                        JSONObject object = jsonObject.getJSONObject("data");
                        JSONArray array = object.getJSONArray("bankCardList");
                        MycardTabFragment.this.array = array;
                            //listView.setAdapter(new MyCardAdapter(getContext(), array));
                            adapter.notifyDataSetChanged(MycardTabFragment.this.array);
                            listView.removeFooterView(footer);
                            if (array.length()>0){
                                footer = LayoutInflater.from(getContext()).inflate(R.layout.my_card_footer,null);
                                TextView textView = footer.findViewById(R.id.my_card_footer_text);
                                if (t.equals("D")){
                                    textView.setText("添加储蓄卡");
                                }else {
                                    textView.setText("添加信用卡");
                                }
                                footer.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (getArguments().getString("type").equals("C")){
                                            AddCard01Activity.initActivity(getContext(),"CC");
                                        }else {
                                            AddCard01Activity.initActivity(getContext(),"DC");
                                        }
                                        p = "-1";
                                    }
                                });
                                listView.addFooterView(footer,null,false);
                            }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                view.dismiss();
            }
        }));
    }
}
