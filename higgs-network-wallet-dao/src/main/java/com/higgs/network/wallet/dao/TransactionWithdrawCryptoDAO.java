package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.TransactionWithdrawCrypto;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TransactionWithdrawCryptoDAO {
    //提币

    @Select("select * from transaction_withdraw_crypto where id = #{id}")
    List<TransactionWithdrawCrypto> getTransactionWithdrawCryptoById(@Param("id") Integer id);

    @Select("select * from transaction_withdraw_crypto where status=#{status}")
    List<TransactionWithdrawCrypto> getTransactionWithdrawCryptoByStatus(@Param("status") Integer status);

    @Select("select * from transaction_withdraw_crypto where partner_id = #{partnerid}")
    List<TransactionWithdrawCrypto> getTransactionWithdrawCryptoByPartnerId(@Param("partnerid") Integer partnerid);

    @Select("select * from transaction_withdraw_crypto where partner_orderNo = #{porderno}")
    TransactionWithdrawCrypto getTransactionWithdrawCryptoByPartnerOrderNo(@Param("porderno") String porderno);

    @Insert("insert into transaction_withdraw_crypto (partner_id,partner_orderNo,symbol,amount,fee,created_at,address_to,confirmations,status,audit_type,request_count) " +
            "values " +
            "(#{order.partnerId},#{order.partnerOrderNo},#{order.symbol},#{order.amount},#{order.fee},NOW(),#{order.addressTo},#{order.confirmations},#{order.status},#{order.auditType},#{order.requestCount})")
    Integer insert( @Param("order") TransactionWithdrawCrypto order);

    @Select("select * from transaction_withdraw_crypto where status=#{status} and request_count>=#{begin} and request_count<=#{end} and partner_id in (${partners})")
    List<TransactionWithdrawCrypto> getWithdrawOrderListByPartnerIDListAndRequestCount(@Param("partners") String partners,@Param("status") Integer status, @Param("begin")Integer begin,@Param("end") Integer end);

    @Update("update transaction_withdraw_crypto set request_count=request_count+1,status=#{status} where txid=#{txid} and (status=5 or status=7)")
    int updateOrderRequestCountByTxid(@Param("txid") String txid,@Param("status") Integer status);

    @Update("update transaction_withdraw_crypto set request_count=request_count+1,status=#{status} where id=#{id} and (status=5 or status=7)")
    int updateOrderRequestCountByOrderId(@Param("id") Integer id,@Param("status") Integer status);

    //所有成功状态的订单,5=提币成功,7=订单成功通知合作方失败,8=订单成功通知完成
    @Select("select sum(amount) from transaction_withdraw_crypto where partner_id=#{partnerid} and symbol=#{symbol} and (status=5 or status=7 or status=8) and sendbox=0 ")
    BigDecimal getSumAmountByPartnerID(@Param("partnerid") Integer partnerid,@Param("symbol") String symbol);
}
