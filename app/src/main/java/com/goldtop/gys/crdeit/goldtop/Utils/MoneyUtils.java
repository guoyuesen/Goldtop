package com.goldtop.gys.crdeit.goldtop.Utils;

import java.text.DecimalFormat;

/**
 * Created by 郭月森 on 2018/8/24.
 */

public class MoneyUtils {
    public static String getShowMoney(double m){
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(m);
    }
    public static String getShowMoney(String m){
        double d = Double.parseDouble(m);
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(d);
    }
}
