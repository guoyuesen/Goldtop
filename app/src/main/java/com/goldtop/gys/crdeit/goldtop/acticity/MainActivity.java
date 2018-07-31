package com.goldtop.gys.crdeit.goldtop.acticity;

import android.content.Intent;
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
                startActivity(new Intent(MainActivity.this,ExhibitionActivity.class));
            }
        });
        dateButton.Start();
    }
}
