package com.goldtop.gys.crdeit.goldtop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 郭月森 on 2018/8/20.
 */

public class ArcProgressBar extends View {
    private int color = Color.parseColor("#FFE183");//默认颜色
    private int color1 = Color.parseColor("#ffffff");//进度颜色
    private int setbacks = 0;//进度（0-180）

    public ArcProgressBar(Context context) {
        super(context);
    }

    public ArcProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ArcProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }

    /**
     * 默认颜色
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
        invalidate();
    }
    /**
     * 进度颜色
     * @param color1
     */
    public void setColor1(int color1) {
        this.color1 = color1;
        invalidate();
    }

    /**
     * 进度
     * @param setbacks（0-180）
     */
    public void setSetbacks(int setbacks) {
        this.setbacks = Math.abs(setbacks)%180;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mpaint = new Paint();
        mpaint.setStrokeWidth(20);//线条宽度
        mpaint.setAntiAlias(true);//抗锯齿
        mpaint.setStyle(Paint.Style.STROKE);//空心
        RectF rectF = new RectF(10,10,getWidth()-10,(getWidth()-10));//外圈
        RectF rectF1 = new RectF(50,50,getWidth()-50,(getWidth()-50));//内圈
        LinearGradient lg=new LinearGradient(0,getHeight()/2,getWidth()/3,0,color1,color, Shader.TileMode.MIRROR);//外圈渐变
        mpaint.setShader(lg);//设置外圈完成进度渐变
        canvas.drawArc(rectF,180,setbacks,false,mpaint);//绘画已完成
        mpaint.setShader(null);//将画笔渐变置空
        mpaint.setColor(color);//设置未完成部分的默认颜色
        canvas.drawArc(rectF,180+setbacks,180-setbacks,false,mpaint);//绘制未完成
        //mpaint.setColor(color1);
        for (int i = 180 ;i < 360 ; i+=6){//内圈绘制
            if (i<180+setbacks){//判断完成状态
                int c1 = 0;
                c1 = Math.abs(color1-color)/setbacks;//计算颜色差值
                if (color1>color){//设置内圈完成颜色
                    mpaint.setColor(color1-(i-180)*c1);
                }else {
                    mpaint.setColor(color1+(i-180)*c1);
                }
            }else {//设置内圈未完成颜色
                mpaint.setColor(color);
            }
            canvas.drawArc(rectF1,i,1,false,mpaint);
        }
    }
}
