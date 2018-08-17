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
    //查看还款详情
    public static String paymentSchedule = url+"/payment/paymentSchedule";
    //添加银行卡
    public static String addcard = url+"/payment/addCard";
    //同名卡开卡
    public static String openCard = url+"/payment/openCard";
    //获取计划手续费
    public static String calcReserveMoney = url+"/payment/calcReserveMoney";
    //还款申请
    public static String paymentApply = url+"/payment/paymentApply";
    //查看计划
    public static String paymentPlanListByDate = url+"/payment/paymentPlanListByDate";
    //取消计划
    public static String closePlan = url+"/payment/cancelPaymentPlan";
    //实名认证状态
    public static String smrz = url+"/app/registryStatus/";
    //收款
    public static String withdraw = url+"/tx/withdraw";
    //钱包
    public static String totalIncome = url+"/userProfit/totalIncome/";
    //钱包
    public static String tradeDetail = url+"/userProfit/tradeDetail";
    //钱包
    public static String customerAnalysis = url+"/userProfit/customerAnalysis?custId=";
    //支付宝创建订单
    public static String createOrder=url+"/alipay/createOrder";
    //设置手势密码
    public static String createPatternPassword=url+"/createPatternPassword";
    //红包
    public static String myRedpack=url+"/income/myRedpack";
    //红包
    public static String transfer=url+"/income/transfer";
    //手势密码登录
    public static String loginByType = url+ "/loginByType";

}
