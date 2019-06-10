package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.TransactionDepositCrypto;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TransactionDepositCryptoDAO {
    //充值

    @Select("select * from transaction_deposit_crypto where id = #{id}")
    List<TransactionDepositCrypto> getTransactionDepositCryptoById(@Param("id") Integer id);

    @Select("select * from transaction_deposit_crypto where status=#{status}")
    List<TransactionDepositCrypto> getTransactionDepositCryptoByStatus(@Param("status") Integer status);

    @Select("select * from transaction_deposit_crypto where partner_id = #{partnerid}")
    List<TransactionDepositCrypto> getTransactionDepositCryptoByPartnerId(@Param("partnerid") Integer partnerid);

    @Select("select * from transaction_deposit_crypto where status=#{status} and request_count>=#{begin} and request_count<=#{end} and partner_id in (${partners})")
    List<TransactionDepositCrypto> getDepositOrderListByPartnerIDListAndRequestCount(@Param("partners") String partners,@Param("status") Integer status, @Param("begin")Integer begin,@Param("end") Integer end);

    @Update("update transaction_deposit_crypto set request_count=request_count+1,status=#{status} where txid=#{txid} and (status=1 or status=7)")
    Insert updateOrderRequestCountByTxid(@Param("txid") String txid,@Param("status") Integer status);

    @Update("update transaction_deposit_crypto set request_count=request_count+1,status=#{status} where id=#{id} and (status=1 or status=7)")
    Integer updateOrderRequestCountByOrderId(@Param("id") Integer id,@Param("status") Integer status);

    //所有成功状态的订单,1=充值成功,7=通知合作方失败,8=通知合作方成功
    @Select("select sum(amount) from transaction_deposit_crypto where partner_id=#{partnerid} and symbol=#{symbol}  and (status=1 or status=7 or status=8) and status<>6 ")
    BigDecimal getSumAmountByPartnerID(@Param("partnerid") Integer partnerid, @Param("symbol") String symbol);

    @Insert("INSERT INTO transaction_deposit_crypto " +
            "(partner_id, symbol,amount,fee,real_fee,created_at,updated_at,address_to,txid,confirmations,is_mining,status,encrypt,request_count,sendbox) " +
            "VALUES " +
            "(#{order.partnerId},#{order.symbol},#{order.amount},#{order.fee},#{order.realFee},#{order.createdAt},#{order.updatedAt},#{order.addressTo},#{order.txid},#{order.confirmations},#{order.isMining},#{order.status},#{order.encrypt},#{order.requestCount},#{order.sendbox})")
    Integer insert(@Param("order") TransactionDepositCrypto order);

    @Select("select * from transaction_deposit_crypto where partner_id=#{partnerid} and symbol=#{symbol} and txid=#{txid}")
    TransactionDepositCrypto getTransactionDepositCryptoByTxid(@Param("partnerid") Integer partnerid, @Param("symbol") String symbol,@Param("txid") String txid);

    @Update("update transaction_deposit_crypto set partner_id=#{partnerId} where id=#{id}")
    Integer updateOrderPartnerIdByOrderId(@Param("id") Integer id,@Param("partnerId") Integer partnerId);
}
