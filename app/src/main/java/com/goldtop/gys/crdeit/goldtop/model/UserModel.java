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
    public static String shiMrenz="REG_ING";//
public static void setInfo(JSONObject object){
    try {
        id=object.getString("id")==null?"":object.getString("id");
    custId=object.getString("custId")==null?"":object.getString("custId");
    custPassword=object.getString("custPassword")==null?"":object.getString("custPassword");
    custName=object.getString("custName")==null?"":object.getString("custName");
    custSex=object.getString("custSex")==null?"":object.getString("custSex");
    custAge=object.getString("custAge")==null?"":object.getString("custAge");
    custMobile=object.getString("custMobile")==null?"":object.getString("custMobile");
    custEmail=object.getString("custEmail")==null?"":object.getString("custEmail");
    idCardNo=object.getString("idCardNo")==null?"":object.getString("idCardNo");
    address=object.getString("address")==null?"":object.getString("address");
    custLevelSample=object.getString("custLevelSample")==null?"":object.getString("custLevelSample");
    //introducerId=object.getString("introducerId")==null?"":object.getString("introducerId");
    custStatus=object.getString("custStatus")==null?"":(object.getString("custStatus").equals("AUTH")?"REG_SUCCESS":"REG_ING");
    lastLogin=object.getString("lastLogin")==null?"":object.getString("lastLogin");
    createdTime=object.getString("createdTime")==null?"":object.getString("createdTime");
    bankCards=object.getString("bankCards")==null?"":object.getString("bankCards");
    identifyingCode=object.getString("identifyingCode")==null?"":object.getString("identifyingCode");
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
}
}
