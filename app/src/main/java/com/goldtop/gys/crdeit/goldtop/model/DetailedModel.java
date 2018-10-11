package com.goldtop.gys.crdeit.goldtop.model;

import com.goldtop.gys.crdeit.goldtop.Base.ContextUtil;
import com.goldtop.gys.crdeit.goldtop.Utils.MoneyUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭月森 on 2018/9/18.
 */

public class DetailedModel {
    public List<Detailed> list;
    public DetailedModel(JSONArray array,int type) {
        list = new ArrayList<Detailed>();
        for (int i=0;i<array.length();i++){
            try {
                list.add(setmode(array.getJSONObject(i),type));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private Detailed setmode(JSONObject object,int type){
        Detailed d = null;
        try {
        switch (type){
            case 0:
                    d = new Detailed(object.getString("note"),"+"+ MoneyUtils.getShowMoney(object.getInt("sum")/100.00d)+"元", ContextUtil.dataTostr(object.getLong("createTime"),"yyyy-MM-dd HH:mm"));
                break;
            case 1:
                String m = object.getString("money");
                String money;
                if (m.substring(0,1).equals("-")){
                    money = "-"+MoneyUtils.getShowMoney(Double.parseDouble(m.substring(1,m.length()))/100.00d);
                }else {
                    money = "+"+MoneyUtils.getShowMoney(Double.parseDouble(m.substring(1,m.length()))/100.00d);
                }
                d = new Detailed(object.getString("note"),money+"元",ContextUtil.dataTostr(object.getLong("tradeTime"),"yyyy-MM-dd HH:mm"));
                break;
            case 2:
                Double mn = object.getDouble("incomeAmt");
                String my;
                if (mn>0){
                    my = "+"+MoneyUtils.getShowMoney(object.getDouble("incomeAmt")/100.00d);
                }else {
                    my = MoneyUtils.getShowMoney(object.getDouble("incomeAmt")/100.00d);
                }
                d = new Detailed(object.getString("description"),my+"元",ContextUtil.dataTostr(object.getLong("createdTime"),"yyyy-MM-dd HH:mm"));
                break;
            case 3:
                Double mn2 = object.getDouble("incomeAmt");
                String my2;
                if (mn2>0){
                    my2 = "+"+MoneyUtils.getShowMoney(object.getDouble("incomeAmt")/100.00d);
                }else {
                    my2 = MoneyUtils.getShowMoney(object.getDouble("incomeAmt")/100.00d);
                }
                d = new Detailed(object.getString("description"),my2+"元",ContextUtil.dataTostr(object.getLong("createdTime"),"yyyy-MM-dd HH:mm"));
                break;
            case 4:
                d = new Detailed(object.getString("note"),"+"+object.getString("sum")+"积分",ContextUtil.dataTostr(object.getLong("createTime"),"yyyy-MM-dd HH:mm"));
                break;
            case 5:
                d = new Detailed(object.getString("custMobile").substring(0,3)+"****"+object.getString("custMobile").substring(7,11),
                        getVipType(object.getString("custLevelSample")),ContextUtil.dataTostr(object.getLong("createdTime"),"yyyy-MM-dd HH:mm"));
                break;
            case 6:
                d = new Detailed(object.getString("custMobile").substring(0,3)+"****"+object.getString("custMobile").substring(7,11),
                        getVipType(object.getString("custLevelSample")),ContextUtil.dataTostr(object.getLong("createdTime"),"yyyy-MM-dd HH:mm"));
                break;
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (d==null)
            return new Detailed();
        else return d;
    }

    public class Detailed{
        String name;
        //String symbol;
        String number;
        String time;

        public Detailed() {
        }

        public Detailed(String name, String number, String time) {
            this.name = name;
            this.number = number;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
    public static String getVipType(String code){
        String type = "";
        switch (code) {
            case "AGENT":
                type= "企业账号";
                break;
            case "MEMBER":
                type= "会员";
                break;
            case "VIP":
                type= "VIP";
                break;
            case "NORMAL":
                type= "普通用户";
                break;
        }
        return type;
    }
}
