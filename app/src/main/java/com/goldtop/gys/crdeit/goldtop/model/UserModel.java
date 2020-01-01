package com.goldtop.gys.crdeit.goldtop.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭月森 on 2018/7/13.
 */

public class UserModel {
    public static String id="";//
    public static String custId="";//账号
    public static String custPassword="";//
    public static String custName="";//姓名
    public static String custSex="";//
    public static String custAge="";//
    public static String custMobile="";//
    public static String custEmail="";//
    public static String idCardNo="";//
    public static String address="";//
    public static String custLevelSample="";//
    public static String introducerId="";//
    public static String custStatus="";//
    public static String lastLogin="";//
    public static String createdTime="";//
    public static String bankCards="";//
    public static String op="";//
    public static String identifyingCode="";//
    public static String roleList="";//
    public static String menuList="";//
    public static String shiMrenz="";//
    public static String token="";
public static void setInfo(JSONObject object){
    try {
        id=object.getString("id").equals("null")?"":object.getString("id");
    custId=object.getString("custId").equals("null")?"":object.getString("custId");
    custPassword=object.getString("custPassword").equals("null")?"":object.getString("custPassword");
    custName=object.getString("custName").equals("null")?"暂未实名认证":object.getString("custName");
    custSex=object.getString("custSex").equals("null")?"":object.getString("custSex");
    custAge=object.getString("custAge").equals("null")?"":object.getString("custAge");
    custMobile=object.getString("custMobile").equals("null")?"":object.getString("custMobile");
    custEmail=object.getString("custEmail").equals("null")?"":object.getString("custEmail");
    idCardNo=object.getString("idCardNo").equals("null")?"":object.getString("idCardNo");
    address=object.getString("address").equals("null")?"":object.getString("address");
    custLevelSample=object.getString("custLevelSample").equals("null")?"":object.getString("custLevelSample");
    //introducerId=object.getString("introducerId").equals("null")?"":object.getString("introducerId");
    custStatus=object.getString("custStatus").equals("null")?"":(object.getString("custStatus").equals("AUTH")?"REG_SUCCESS":"INIT");
    shiMrenz = object.getString("custStatus").equals("null")?"":(object.getString("custStatus").equals("AUTH")?"REG_SUCCESS":"INIT");
    lastLogin=object.getString("lastLogin").equals("null")?"":object.getString("lastLogin");
    createdTime=object.getString("createdTime").equals("null")?"":object.getString("createdTime");
    bankCards=object.getString("bankCards").equals("null")?"":object.getString("bankCards");
    identifyingCode=object.getString("identifyingCode").equals("null")?"":object.getString("identifyingCode");
        token=object.getString("token").equals("null")?"":object.getString("token");
    } catch (JSONException e) {
        Log.d("","json格式错误");
    }
}
public static void remov(){
    id="";//
    custId="";//账号
    custPassword="";//
    custName="";//姓名
    custSex="";//
    custAge="";//
    custMobile="";//
    custEmail="";//
    idCardNo="";//
    address="";//
    custLevelSample="";//
    introducerId="";//
    custStatus="";//
    lastLogin="";//
    createdTime="";//
    bankCards="";//
    op="";//
    identifyingCode="";//
    roleList="";//
    menuList="";//
    token="";
}
}
