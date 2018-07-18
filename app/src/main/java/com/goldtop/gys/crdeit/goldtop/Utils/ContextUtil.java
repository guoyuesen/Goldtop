package com.goldtop.gys.crdeit.goldtop.Utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.goldtop.gys.crdeit.goldtop.service.ThisApplication;

/**
 * Created by 郭月森 on 2018/6/22.
 */

public class ContextUtil {
    private static int width = 0;
    private static int heigth = 0;

    private ContextUtil() {
        DisplayMetrics dm = ThisApplication.getContext().getResources().getDisplayMetrics();
        heigth = dm.heightPixels;
        width = dm.widthPixels;
    }

    /**
     * 获取屏幕宽
     * @return
     */
    public int getWidth(){
        if (width==0){
            new ContextUtil();
        }
        return width;
    }
    /**
     * 获取屏幕高
     * @return
     */
    public int getHeigth(){
        if (heigth == 0){
            new ContextUtil();
        }
        return heigth;
    }
}
