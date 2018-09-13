package com.goldtop.gys.crdeit.goldtop.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 郭月森 on 2018/9/13.
 */

public class PayIndustryUtills {
     public static Map<String,String> allmap;
     public static String Creat(String s){
         if (allmap==null) {
             allmap = new HashMap<String, String>();
             allmap.put("百货商超", "M001");
             allmap.put("酒吧", "M010");
             allmap.put("酒店", "M011");
             allmap.put("电影院", "M012");
             allmap.put("餐饮", "M002");
             allmap.put("珠宝/首饰", "M003");
             allmap.put("服饰", "M004");
             allmap.put("化妆品", "M005");
             allmap.put("健身", "M006");
             allmap.put("美容/SPA", "M007");
             allmap.put("洗浴/按摩", "M008");
             allmap.put("加油站", "M009");
         }
         return allmap.get(s);
     }
    public static boolean isBetween(Date paymentTime,String s,String s1 ) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date start = df1.parse(df.format(paymentTime) + " " + s);
            Date end = df1.parse(df.format(paymentTime) + " " + s1);
            return paymentTime.getTime() >= start.getTime() && paymentTime.getTime() <= end.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String[] getkeys(Date paymentTime){
        String[] array;

        // TODO Auto-generated method stub
        if (isBetween(paymentTime, "09:00", "11:00")) {

            array = new String[]{"百货商超","珠宝/首饰","服饰","化妆品","健身","加油站","酒店"};
        } else if (isBetween( paymentTime,"11:00", "13:00")) {

            array = new String[]{"百货商超","餐饮","珠宝/首饰","服饰","化妆品","健身","加油站","酒店","电影院"};
        } else if (isBetween( paymentTime,"13:00", "17:00")) {

            array = new String[]{"百货商超","餐饮","珠宝/首饰","服饰","化妆品","健身","美容/SPA","加油站","酒店","电影院"};
        } else if (isBetween( paymentTime,"17:00", "19:00")) {

            array = new String[]{"百货商超","餐饮","服饰","化妆品","健身","加油站","酒店","电影院"};
        } else if (isBetween( paymentTime,"19:00", "22:00")) {

            array = new String[]{"百货商超","餐饮","珠宝/首饰","服饰","化妆品","健身","美容/SPA","加油站","酒店","电影院"};
        } else {
            array = new String[0];
        }
        return array;
    }
}
