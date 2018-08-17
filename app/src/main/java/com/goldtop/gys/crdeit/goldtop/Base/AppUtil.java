package com.goldtop.gys.crdeit.goldtop.Base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.goldtop.gys.crdeit.goldtop.acticity.GraphicActivity;
import com.goldtop.gys.crdeit.goldtop.acticity.LoginActivity;
import com.goldtop.gys.crdeit.goldtop.model.UserModel;

/**
 * Created by 郭月森 on 2018/7/13.
 */

public class AppUtil {
    public static boolean isLogin(Context context){
        if (UserModel.id.isEmpty()) {
            /*SharedPreferences sp = context.getSharedPreferences("SP_PEOPLE", Activity.MODE_PRIVATE);
            String peopleJson = sp.getString("KEY_LOGING_PASS","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
            String people = sp.getString("KEY_LOGING_PHONE","");  //取出key为"KEY_PEOPLE_DATA"的值，如果值为空，则将第二个参数作为默认值赋值
            if(peopleJson!=""&&people!="")  //防空判断
            {
                GraphicActivity.isLogin = true;
                context.startActivity(new Intent(context, GraphicActivity.class));
            }else*/
            context.startActivity(new Intent(context, LoginActivity.class));
            return false;
        }else {
            return true;
        }
    }
}
