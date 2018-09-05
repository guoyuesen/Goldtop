package com.goldtop.gys.crdeit.goldtop.service;

/**
 * Created by 郭月森 on 2018/7/11.
 */

public class Action {
    private static String url = "http://www.tuoluo718.com";
    //登录
    public static String login = url+"/login";
    //注册
    public static String register = url+"/cust/save";
    //修改密码
    public static String changePassword = url+"/cust/changePassword";
    //实名认证
    public static String authentication = url+"/cust/registry";
    //获取验证码
    public static String check = url+"/identify";
    //获取银行卡列表
    public static String queryBankCard = url+"/cust/queryBankCard";
    //查看还款详情
    public static String paymentSchedule = url+"/payment/paymentSchedule";
    //添加银行卡
    public static String addcard = url+"/payment/addCard";
    //同名卡开卡
    //public static String openCard = url+"/payment/openCard";
    //获取计划手续费
    public static String calcReserveMoney = url+"/payment/calcReserveMoney";
    //还款申请
    public static String paymentApply = url+"/apply/paymentApply";//"/payment/paymentApply";
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
    //钱包金额？
    public static String transfer=url+"/income/transfer";
    //手势密码登录
    public static String loginByType = url+ "/loginByType";
    //版本号
    public static String version = url+"/app/version";
    //查询银行
    public static String queryBank = url+"/queryBank";
    //联行号查询
    public static String getAnge = url+"/dictionarty/getAnge";//省不传 ， parentCode = code
    //开卡
    public static String openCard = url + "/bk/openCard";
    //获取开卡短信验证码
    public static String openSms = url+"/bk/openCardSms";
    //大额支付短信验证码
    public static String bigPaySms = url+"/txWithdraw/bigPaySms";
    //大额支付
    public static String bigPay = url+"/txWithdraw/bigPay";
    //修改付款行业
    public static String updateMcc = url+"/payment/updateMcc";
    //钱包提现
    public static String withdrawFromIncome= url+"/income/withdrawFromIncome";
    //红包明细
    public static String redPackDetail= url+"/income/redPackDetail";

}
