package com.goldtop.gys.crdeit.goldtop.Adapters;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by 郭月森 on 2018/9/25.
 */

public class CardTransformer implements ViewPager.PageTransformer {
    String TAG = "CardTransformer";
    private float MAX_SCALE = 1.0f;
    private float MIN_SCALE = 0.65f;//0.85f
    private ObjectAnimator animator=null;
    @Override
    public void transformPage(@NonNull View page, float position) {
//设置了内间距  有0.15的偏差
        float pos=position -0.15f;

        if ( pos <= 1) {
            float scaleFactor = MIN_SCALE + (1 - Math.abs(pos)) * (MAX_SCALE - MIN_SCALE);
            page.setScaleX(scaleFactor);
            //page.scaleX = scaleFactor; //缩放效果
            if (pos > 0) {
                //page.translationX = -scaleFactor * 2;
                page.setTranslationX(-scaleFactor * 2);
            } else if (pos < 0 && pos > -1) {
                //page.translationX = scaleFactor * 2;
                page.setTranslationX(scaleFactor * 2);
            }
            page.setScaleY(scaleFactor);


        } else {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
