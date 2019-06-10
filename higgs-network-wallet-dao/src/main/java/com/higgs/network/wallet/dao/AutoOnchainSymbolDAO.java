package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.AutoOnchainSymbol;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface AutoOnchainSymbolDAO {
    //获得币种上链对应所有币对配置信息
    @Select("select * from auto_onchain_symbol where token_base=#{tokenbase} and symbol=#{symbol} and status=1")
    AutoOnchainSymbol getAutoOnchainSymbolListByTokenbaseAndSymbol(@Param("tokenbase") String tokenbase,@Param("symbol") String symbol);

    //获得币种上链对应所有币对配置信息
    @Select("select * from auto_onchain_symbol where onchain_symbol=#{onchainsymbol}")
    List<AutoOnchainSymbol> getAutoOnchainSymbolListByOnchainSymbol(@Param("onchainsymbol") String onchainsymbol);

    //获得币种上链对应某一个链的比对配置信息
    @Select("select * from auto_onchain_symbol where partner_id=#{pid} and status=#{status} and onchain_token_base=#{onchainTokenBase}")
    List<AutoOnchainSymbol> getAutoOnchainSymbolListByPartnerIdAndSymbolAndOnchainTokenBase(@Param("tokenbase") String tokenbase,@Param("symbol") String symbol,@Param("onchainTokenBase") String onchainTokenBase);

    //获得上链比对信息
    @Select("select partner_orderno from auto_onchain_symbol where onchain_token_base=#{onchainTokenBase} and onchain_symbol=#{onchainSymbol}")
    String getLastPartnerOrderno(@Param("onchainTokenBase") String onchainTokenBase,@Param("onchainSymbol") String onchainSymbol);

    //新增一条配置信息
    @Insert("insert into auto_onchain_symbol " +
            "(token_base,symbol,onchain_token_base,onchain_symbol,status)" +
            " values " +
            "(#{info.tokenBase},#{info.symbol},#{info.onchainTokenBase},#{info.onchainSymbol},#{info.status})")
    Integer addAutoOnchainSymbol(@Param("info") AutoOnchainSymbol info);


}
