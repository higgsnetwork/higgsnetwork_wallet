package com.higgs.network.wallet.common.stub;

/**
 * @author :
 * @date :
 */
public class ResultCode  {

    public static final int SUCCESS = 200;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int TIMEOUT = 408;
    public static final int SERVER_ERROR = 500;
    public static final int SERVICE_UNAVAILABLE = 503;

    public static final int Error = 9999;//未确定的错误
    public static final int FAILED = 10000;

    public static final int IPError = 10001;//IP受限
    public static final int ParameterNull = 10002;//参数不全
    public static final int ParameterError = 10003;//参数异常
    public static final int SignError = 10004;//效验失败
    public static final int ProtocolError = 10005;//协议不支持
    public static final int ExceptionError = 10006;//异常抛出
    public static final int PartnerNotOpen = 10007;//合作方未开放
    public static final int AmountError = 10008;//金额不合法
    public static final int SymbolError = 10009;//代币类型
    public static final int OrderProcessing = 10010;//订单处理中
    public static final int OrderExist = 10011;//订单已存在
    public static final int OrderFail = 10012;//订单已失败
    public static final int OrderFinish = 10013;//订单已完成
    public static final int FeesError = 10014;//手续费异常
    public static final int RequestError = 10015;//请求失败
    public static final int RequestTimeOut = 10016;//请求超时
//
//    public static final int GameNotOpen = 993;//游戏未开放
//    public static final int BalanceError = 990;//金币不足
//    public static final int ExUserNotExist = 899;//交易所用户不存在
//    public static final int NotBindError = 898;//游戏用户与交易所账户不是绑定关系
//    public static final int VerifyCodeTimeOut = 897;//验证码超时
//    public static final int AccountFreezing = 896;//账户冻结无法操作
//    public static final int VerifyCodeFail = 895;//验证码错误
//    public static final int UserNotExist = 894;//游戏中心用户不存在
//    public static final int BindBitAccountError = 889;//绑定失败
//    public static final int UnbindAccount = -888;//请先解除绑定
//    public static final int GoogleCodeFall = -887;//GoogleCode验证失败
//    public static final int GoogleCodeNotBind = 886;//Google验证器未绑定

    private ResultCode() {
        //do nothing
    }
}
