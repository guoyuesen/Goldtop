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
            context.startActivity(new Intent(context, LoginActivity.class));
            return false;
        }else {
            return true;
        }
    }
}
