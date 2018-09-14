package com.goldtop.gys.crdeit.goldtop.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 郭月森 on 2018/7/5.
 */

public class CalendarChoice extends View implements View.OnTouchListener{
    DateDialogView.DateBack back;
    int bw;
    int item[][][]= new int[14][7][6];
    public CalendarChoice(Context context) {
        super(context);
    }

    public CalendarChoice(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarChoice(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setOnTouchListener(this);
    }

    public void setBack(DateDialogView.DateBack back) {
        this.back = back;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(ContextUtil.dip2px(getContext(),15));
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        for (int i = 0;i<item.length;i++){
            int d[][] = item[i];
            for (int j = 0;j < d.length;j++){
                int dn[] = d[j];
                int baseLineY = (int) ((dn[2]+(bw/2)) - top/2 - bottom/2);//基线中间点的y轴计算公式
                if (dn[5]==0){//没有日期的空白
                    if (i==0&&j==3){//第一个月份
                        paint.setColor(Color.BLACK);
                        canvas.drawText(getCurrentYear()+"年"+dn[4]+"月",dn[1]+(bw/2),baseLineY,paint);
                    }else if (i==7&&j==3){//第二个月份
                        paint.setColor(Color.BLACK);
                        canvas.drawText(getCurrentYear()+"年"+(dn[4]==13?1:(dn[4]))+"月",dn[1]+(bw/2),baseLineY,paint);
                    }
                }else {//日期
                    if (dn[5] == 2){//已过日期背景及字的颜色
                        p.setColor(Color.parseColor("#ffffff"));
                        paint.setColor(Color.parseColor("#dddddd"));
                    }else {//未过日期及字的颜色
                        if (dn[0] == 1) {//选中日期字背景的颜色
                            p.setColor(Color.parseColor("#FECE00"));
                            paint.setColor(Color.WHITE);
                        } else {//未选中日期字背景的颜色
                            p.setColor(Color.WHITE);
                            if (j == 0 || j == 6) {//周末字的颜色
                                paint.setColor(Color.parseColor("#FF3D00"));
                            } else {//周一至周五字的颜色
                                paint.setColor(Color.BLACK);
                            }
                        }
                    }
                    RectF rf = new RectF(dn[1]+15,dn[2]+15,dn[1]+bw-15,dn[2]+bw-15);//背景范围
                    canvas.drawRoundRect(rf,10,10,p);//绘画背景圆角10px
                    if (dn[0]==1){//绘制选中状态
                        canvas.drawText(dn[3]+"",dn[1]+(bw/2),baseLineY-20,paint);
                        paint.setTextSize(ContextUtil.dip2px(getContext(),11));
                        canvas.drawText("还款",dn[1]+(bw/2),baseLineY+20,paint);
                        paint.setTextSize(ContextUtil.dip2px(getContext(),15));
                    }else {//绘制未选中状态
                        canvas.drawText(dn[3]+"",dn[1]+(bw/2),baseLineY,paint);
                    }
                }
            }
        }
        //绘画两月之间的分割线
        paint.setColor(Color.parseColor("#dddddd"));
        canvas.drawLine(20,getWidth(),getWidth()-20,getWidth(),paint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                int i = (int)(motionEvent.getX()-getPaddingLeft())/bw;
                int j = (int)(motionEvent.getY()-getPaddingTop())/bw;
                if (j>14||i>6||item[j][i][5]!=1)//判断点击是否有效
                    break;
                if (item[j][i][0]==1){
                    item[j][i][0]=0;
                }else {
                    item[j][i][0]=1;
                }
                invalidate();//刷新绘画
                if (back!=null){
                    back.callback("");
                }
                break;
        }
        return true;
    }

    /**
     * 获取当前年分
     * @return
     */
    public static String getCurrentYear(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }
    /**
     * 取得当月天数
     * */
    public int getCurrentMonthLastDay()
    {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);//把日期设置为当月第一天
        a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    /**
     * 取得当天号数
     * */
    public int getCurrentDay()
    {
        Calendar a = Calendar.getInstance();
        int maxDate = a.get(Calendar.DAY_OF_MONTH);
        return maxDate;
    }
    /**
     * 取得月份
     * */
    public int getCurrent()
    {
        Calendar calendar=Calendar.getInstance();
        return calendar.get(Calendar.MONTH)+1;
    }

    /**
     * 获取当月1日星期几
     * @return
     */

    public int v(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        SimpleDateFormat format = new SimpleDateFormat("E");
        //System.out.println("本月第一天是：" + format.format(calendar.getTime()));
        //如果你要得到一个数字的话就是：
        return calendar.get(Calendar.DAY_OF_WEEK)-1;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获取显示宽度
        int w = getWidth()-getPaddingLeft()-getPaddingRight();
        //获取单格宽高
        bw = w/7;
        //开始X坐标
        int startx = getPaddingLeft();
        int d=0;
        for (int i=0;i<14;i++){
            //获取当前月数
            int month = i>6 ? getCurrent() + 1 : getCurrent();
            //开始Y坐标
            int ty = getPaddingTop()+i*bw;
            for (int j=0;j<7;j++){
                item[i][j][4] = month;
                int tx=startx+j*bw;
                item[i][j][0] = 0;//选中状态：0,未选中1选中
                item[i][j][1] = tx;//矩形左上角X坐标
                item[i][j][2] = ty;//矩形左上角Y坐标
                if (i ==0 || i == 7){//预留空白
                    item[i][j][5] = 0;//不显示日期或内容 0不显示或显示月份星期 1显示日期并可点击 2显示日期不可点击
                }else {
                    if (i==1&&j<v()){//按号当日星期几开始对应显示
                        item[i][j][5] = 0;
                    }else if (i==8&&j<((getCurrentMonthLastDay()%7+v())<7?(getCurrentMonthLastDay()%7+v()):(getCurrentMonthLastDay()%7+v())-7)){//第二月开始显示位置
                        item[i][j][5] = 0;
                    }else {
                        d++;//日期增加
                        if (i<8&&d>getCurrentMonthLastDay()){//两月之间空白处
                            item[i][j][5] = 0;
                            d--;
                        }else if(d > getCurrentMonthLastDay()*2){
                            item[i][j][5] = 0;
                            d--;
                        }else {
                            item[i][j][3] = d > getCurrentMonthLastDay() ?  d - getCurrentMonthLastDay():d;//存入日期
                            if (d < getCurrentDay() && month == getCurrent()) {//当月结尾空白
                                item[i][j][5] = 2;
                            } else {
                                item[i][j][5] = 1;
                            }
                        }
                    }
                }
            }
        }
        setMinimumHeight(bw*14);
    }

    public String getContent(){
        String y = getCurrentYear();
        DecimalFormat df=new DecimalFormat("00");
        StringBuffer con = new StringBuffer("");
        for (int[][] a : item){
            for (int [] b:a){
                if (b[0]==1)
                    con.append(y).append("-").append(df.format(b[4])).append("-").append(df.format(b[3])).append(",");
            }
        }
        if (con.length()>3)
            con.deleteCharAt(con.length()-1);
        return con.toString();
    }
}
