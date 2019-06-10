package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.AutoOnchainOrderinfo;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AutoOnchainOrderinfoDAO {

    //获得合作方的所有订单
    @Select("select * from auto_onchain_orderinfo where partner_id=#{pid}")
    List<AutoOnchainOrderinfo> getAutoOnchainOrderinfoListByPid();

    //获得某个状态的所有订单
    @Select("select * from auto_onchain_orderinfo where partner_id=#{pid} and status=#{status}")
    List<AutoOnchainOrderinfo> getAutoOnchainOrderinfoListByPidAndStatus(@Param("pid") Integer pid, @Param("status") Integer status);

    //获得某个状态的所有订单
    @Select("select * from auto_onchain_orderinfo where status=#{status}")
    List<AutoOnchainOrderinfo> getAutoOnchainOrderinfoListByStatus(@Param("status") Integer status);


    //查询某一个合作方订单信息（开放多个链刷交易会有多条数据）
    @Select("select* from auto_onchain_orderinfo where partner_id=#{pid} and partner_orderno = #{partnerOrderno}")
    List<AutoOnchainOrderinfo> getAutoOnchainOrderinfoByPartnerOrderno(@Param("pid") Integer pid, @Param("partnerOrderno") String partnerOrderno);

    //查询某一个合作方订单信息（开放多个链刷交易会有多条数据）
    @Select("select* from auto_onchain_orderinfo where partner_id=#{pid} and partner_orderno = #{partnerOrderno} and onchain_token_base=#{onchainTokenBase} and onchain_symbol=#{onchainSymbol}")
    AutoOnchainOrderinfo getAutoOnchainOrderinfoByPartnerOrdernoAndTokenbaseAndSymbol(@Param("pid") Integer pid, @Param("partnerOrderno") String partnerOrderno,@Param("onchainTokenBase") String onchainTokenBase,@Param("onchainSymbol") String onchainSymbol);

    //查询某一个订单信息
    @Select("select* from auto_onchain_orderinfo where orderno = #{orderno}")
    AutoOnchainOrderinfo getAutoOnchainOrderInfoByOrderno(@Param("orderno") String orderno);

    //获得最后一条订单编号
    @Select("select partner_orderno from auto_onchain_orderinfo where partner_id=#{pid} order by id desc limit 1")
    String getLastPartnerOrderno(@Param("pid") Integer pid);

    @Insert("INSERT INTO `auto_onchain_orderinfo` " +
            "(`partner_id`, `partner_orderno`, `orderno`, `onchain_token_base`, `onchain_symbol`, `amount`, `from_address`, `from_partner_uid`, `withdraw_status`, `withdraw_time`, `to_address`, `to_partner_uid`, `deposit_status`, `deposit_time`, `txid`, `status`, `create_time`)" +
            " VALUES " +
            "(#{order.partnerId},#{order.partnerOrderno},#{order.orderno},#{order.onchainTokenBase},#{order.onchainSymbol},#{order.amount},#{order.fromAddress},#{order.fromPartnerUid},#{order.withdrawStatus},#{order.withdrawTime},#{order.toAddress},#{order.toPartnerUid},#{order.depositStatus},#{order.depositTime},#{order.txid},#{order.status},NOW())"
    )
    Integer addAutoOnchainOrderinfo(@Param("order") AutoOnchainOrderinfo order);

    //更新订单
    @Update("update auto_onchain_orderinfo set " +
            "withdraw_status=#{order.withdrawStatus}, " +
            "withdraw_time=#{order.withdrawTime}, " +
            "txid=#{order.txid}, " +
            "status=#{order.status} " +
            "where orderno=#{order.orderno}")
    Integer updateAutoOnchainOrderinfoByWithdrawAndOrderNo(@Param("order") AutoOnchainOrderinfo order);

    //更新订单
    @Update("update auto_onchain_orderinfo set " +
            "deposit_status=#{order.depositStatus}, " +
            "deposit_time=#{order.depositTime}, " +
            "status=#{order.status} " +
            "where txid=#{order.txid}")
    Integer updateAutoOnchainOrderinfoByDepositAndTxid(@Param("order") AutoOnchainOrderinfo order);
}
