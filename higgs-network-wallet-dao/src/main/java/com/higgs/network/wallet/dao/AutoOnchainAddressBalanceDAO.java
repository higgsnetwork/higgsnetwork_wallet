package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.AutoOnchainAddressBalance;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AutoOnchainAddressBalanceDAO {

    @Select("select * from auto_onchain_address_balance where partner_id=#{partnerId} and partner_uid=#{partnerUid}")
    List<AutoOnchainAddressBalance> getAutoOnchainAddressBalanceListByPartnerIdAndPartnerUid(@Param("partnerId") String partnerId, @Param("partnerUid") String partnerUid);

    @Select("select * from auto_onchain_address_balance where partner_id=#{partnerId} and partner_uid=#{partnerUid} and token_base=#{tokenBase} and symbol=#{symbol}")
    AutoOnchainAddressBalance getAutoOnchainAddressBalanceByPartnerIdAndPartnerUid(@Param("partnerId") String partnerId, @Param("partnerUid") String partnerUid,@Param("tokenBase") String tokenBase,@Param("symbol") String symbol);

    @Insert("insert into auto_onchain_address_balance (partner_id,partner_uid,address,token_base,symbol,balance,frozen_balance)" +
            " values " +
            "(#{info.partnerId},#{info.partnerUid},#{info.address},#{info.tokenBase},#{info.symbol},#{info.balance},#{info.frozen_balance})")
    Integer insert(@Param("info") AutoOnchainAddressBalance info);

    @Update("update auto_onchain_address_balance set balance=#{info.balance},frozen_balance=#{info.frozenBalance} where partner_id=#{info.partnerId} and partner_uid=#{info.partnerUid} and token_base=#{info.tokenBase} and symbol=#{symbol}")
    Integer update(@Param("info") AutoOnchainAddressBalance info);
}
