package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.MyVolleyCallback;
import com.goldtop.gys.crdeit.goldtop.service.Action;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.VolleyRequest;
import com.goldtop.gys.crdeit.goldtop.view.initPhoneDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 郭月森 on 2018/12/20.
 */

public class CustomizedActivity extends BaseActivity {
    @Bind(R.id.customized)
    LinearLayout customized;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.img3)
    ImageView img3;
    @Bind(R.id.img4)
    ImageView img4;
    @Bind(R.id.img5)
    ImageView img5;
    @Bind(R.id.img6)
    ImageView img6;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customized);
        hiedBar(this);
        ButterKnife.bind(this);
        final List<ImageView> list = new ArrayList<>();
        list.add(img1);
        list.add(img2);
        list.add(img3);
        list.add(img4);
        list.add(img5);
        list.add(img6);
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, "http://www.tuoluo718.com/ads/list/B", new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code") == 1) {
                        JSONArray array = jsonObject.getJSONArray("data");
                        /*for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            ImageView imageView = new ImageView(CustomizedActivity.this);
                            imageView.setLayoutParams(getLayoutLayoutParams(375, 257));
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            MyVolley.getImage(object.getString("href"), imageView);
                            customized.addView(imageView);
                        }*/
                        list.get(0).setLayoutParams(getLayoutLayoutParams(list.get(0).getLayoutParams(), 375, 257));
                        MyVolley.getImage(array.getJSONObject(0).getString("href"), list.get(0));
                        list.get(1).setLayoutParams(getLayoutLayoutParams(list.get(1).getLayoutParams(), 360, 117));
                        MyVolley.getImage(array.getJSONObject(1).getString("href"), list.get(1));
                        list.get(2).setLayoutParams(getLayoutLayoutParams(list.get(2).getLayoutParams(), 351, 380));
                        MyVolley.getImage(array.getJSONObject(2).getString("href"), list.get(2));
                        list.get(3).setLayoutParams(getLayoutLayoutParams(list.get(3).getLayoutParams(), 327, 321));
                        MyVolley.getImage(array.getJSONObject(3).getString("href"), list.get(3));
                        list.get(4).setLayoutParams(getLayoutLayoutParams(list.get(4).getLayoutParams(), 351, 343));
                        MyVolley.getImage(array.getJSONObject(4).getString("href"), list.get(4));
                        list.get(5).setLayoutParams(getLayoutLayoutParams(list.get(5).getLayoutParams(), 375, 418));
                        MyVolley.getImage(array.getJSONObject(5).getString("href"), list.get(5));
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


    private ViewGroup.LayoutParams getLayoutLayoutParams(ViewGroup.LayoutParams params, float w, int h) {
        params.height = (int) (ContextUtil.getX(CustomizedActivity.this) / w * h);
        return params;
    }

    @OnClick({R.id.customized_submit, R.id.back_to})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.customized_submit:
                initPhoneDialog dialog = new initPhoneDialog(this, "请输入联系信息", "", new initPhoneDialog.initDialog() {
                    @Override
                    public void back(String name, String phong) {
                        submit(name,phong,"app");
                    }
                });
                dialog.show();
                break;
            case R.id.back_to:
                finish();
                break;
        }
    }
    private void submit(String name,String phone,String type){
        MyVolley.addRequest(new VolleyRequest(Request.Method.GET, Action.business+"type="+type+"&contactName="+name+"&telephone="+phone, new MyVolleyCallback(this) {
            @Override
            public void CallBack(JSONObject jsonObject) {
                try {
                    if (jsonObject.getInt("code")==1){
                        Toast.makeText(CustomizedActivity.this,"提交申请成功，我们将尽快联系您，请保持手机畅通！",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(CustomizedActivity.this,jsonObject.getString("msg"),Toast.LENGTH_LONG).show();
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
