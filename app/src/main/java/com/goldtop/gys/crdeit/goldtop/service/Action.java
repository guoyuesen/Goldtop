package com.goldtop.gys.crdeit.goldtop.service;

/**
 * Created by 郭月森 on 2018/7/11.
 */

public class Action {
    private static String url = "http://47.106.103.104";
    //登录
    public static String login = url+"/login";
    //注册
    public static String register = url+"/cust/save";
    //修改密码
    public static String changePassword = url+"/cust/changePassword";
    //实名认证
    public static String authentication = url+"/cust/cert";
    //获取验证码
    public static String check = url+"/identify";
    //获取银行卡列表
    public static String queryBankCard = url+"/cust/queryBankCard";
    //添加银行卡
    public static String addcard = url+"/payment/addCard";
}
