package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.AutoOnchainPartnerinfo;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface AutoOnchainPartnerinfoDAO {

    @Select("select * from auto_onchain_partnerinfo where status=1")
    List<AutoOnchainPartnerinfo> getAutoOnchainPartnerinfoListByIsOpen();

    @Select("select partner_id from auto_onchain_partnerinfo where status=1 group by partner_id")
    Integer[] openPartnerID();

    @Select("select * from auto_onchain_partnerinfo where partner_id=#{PartnerId}")
    List<AutoOnchainPartnerinfo> getAutoOnchainPartnerinfoListByPartnerId(@Param("PartnerId") Integer PartnerId);

    @Select("select * from auto_onchain_partnerinfo where partner_id=#{partnerId} and onchain_token_base=#{onchainTokenBase}")
    AutoOnchainPartnerinfo getAutoOnchainPartnerinfoByPartnerId(@Param("partnerId") Integer PartnerId,@Param("onchainTokenBase") Integer onchainTokenBase);

    @Insert("insert info auto_onchain_partnerinfo " +
            "(partner_id,onchain_token_base,status)" +
            " values " +
            "(#{info.partnerId},#{info.onchainTokenBase},#{info.status})")
    Integer insert(@Param("info") AutoOnchainPartnerinfo info);

    @Update("update auto_onchain_partnerinfo set onchain_token_base=#{info.onchainTokenBase},status=#{info.status} where partner_id=#{info.partnerId}")
    Integer update(@Param("info") AutoOnchainPartnerinfo info);
}
