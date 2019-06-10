package com.higgs.network.wallet.common.stub;

/**
 * @author : Alex Wu
 * @date : 2018-04-11
 */
public class OrderStatusCode {
    public static final Integer WITHDRAW_ORDER_STATUS_UNREVIEWED=0;//未审核
    public static final Integer WITHDRAW_ORDER_STATUS_APPROVE=1;//审核通过
    public static final Integer WITHDRAW_ORDER_STATUS_REJECT=2;//审核拒绝
    public static final Integer WITHDRAW_ORDER_STATUS_PAYING=3;//支付中已经打币
    public static final Integer WITHDRAW_ORDER_STATUS_FAILED=4;//支付失败
    public static final Integer WITHDRAW_ORDER_STATUS_SUCCESS=5;//已完成
    public static final Integer WITHDRAW_ORDER_STATUS_REVOCATION=6;//已撤销
    public static final Integer WITHDRAW_ORDER_STATUS_CALLBACK_FAILED=7;//通知失败
    public static final Integer WITHDRAW_ORDER_STATUS_CALLBACK_SUCCESS=8;//通知完成

    public static final Integer DEPOSIT_ORDER_STATUS_UNCONFIRMED=0;//未确认
    public static final Integer DEPOSIT_ORDER_STATUS_SUCCESS=1;//已完成
    public static final Integer DEPOSIT_ORDER_STATUS_ERROR=2;//异常
    public static final Integer DEPOSIT_ORDER_STATUS_CALLBACK_FAILED=7;//通知失败
    public static final Integer DEPOSIT_ORDER_STATUS_CALLBACK_SUCCESS=8;//通知完成
}
