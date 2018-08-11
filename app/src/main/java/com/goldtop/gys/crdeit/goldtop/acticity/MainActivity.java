package com.goldtop.gys.crdeit.goldtop.acticity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.Utils.StatusBarUtil;
import com.goldtop.gys.crdeit.goldtop.interfaces.DateButtonListener;
import com.goldtop.gys.crdeit.goldtop.interfaces.GrapNotif;
import com.goldtop.gys.crdeit.goldtop.service.MyVolley;
import com.goldtop.gys.crdeit.goldtop.service.ThisApplication;
import com.goldtop.gys.crdeit.goldtop.view.DateButton;
import com.goldtop.gys.crdeit.goldtop.view.GraphicUnlocking;
import com.goldtop.gys.crdeit.goldtop.view.TitleBuder;

import org.json.JSONObject;

import java.util.Map;

import javax.xml.datatype.Duration;

public class MainActivity extends BaseActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.Main_img);
        //MyVolley.getImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530505201952&di=94c33304ac265b82d21fef0db81c19c8&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0149eb5907e5aca801214550866692.png%401280w_1l_2o_100sh.png",imageView);
        SharedPreferences sp = getSharedPreferences("SP_PEOPLE", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String peopleJson = sp.getString("KEY_LOGING_DATA","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if(peopleJson!="")  //防空判断
        {

            DateButton dateButton = findViewById(R.id.Main_btn);
            dateButton.setthisText("4S");
            dateButton.setNum(4);
            dateButton.setOnClick(new DateButtonListener() {
                @Override
                public void onClick(View view) {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onStop() {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));

                }
            });
            dateButton.Start();
        }else {
            SharedPreferences.Editor editor = sp.edit();;
            editor.putString("KEY_LOGING_DATA", "-------") ; //存入json串
            editor.commit() ; //提交
            startActivity(new Intent(MainActivity.this,ExhibitionActivity.class));
        }

    }
}
