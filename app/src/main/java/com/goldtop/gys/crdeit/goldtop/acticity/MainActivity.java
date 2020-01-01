package com.goldtop.gys.crdeit.goldtop.acticity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;
import com.goldtop.gys.crdeit.goldtop.interfaces.DateButtonListener;
import com.goldtop.gys.crdeit.goldtop.view.DateButton;

public class MainActivity extends BaseActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseActivity.hiedBar(this);
        setContentView(R.layout.activity_main);
        if(!isTaskRoot()){
            finish();
            return;
        }
        if (getIntent() != null) {
            if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
                finish();
                return;
            }
        }
        imageView = findViewById(R.id.Main_img);
        SharedPreferences sp = getSharedPreferences("SP_PEOPLE", Activity.MODE_PRIVATE);//创建sp对象,如果有key为"SP_PEOPLE"的sp就取出
        String peopleJson = sp.getString("KEY_LOGING_DATA","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
        if(peopleJson!="")  //防空判断
        {
            DateButton dateButton = findViewById(R.id.Main_btn);
            dateButton.setthisText("跳过");
            dateButton.setNum(3);
            dateButton.setOnClick(new DateButtonListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onStop() {
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                }
            });
            dateButton.Start();
        }else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("KEY_LOGING_DATA", "-------") ; //存入json串
            editor.commit() ; //提交
            startActivity(new Intent(MainActivity.this,ExhibitionActivity.class));
            finish();
        }

    }

}
