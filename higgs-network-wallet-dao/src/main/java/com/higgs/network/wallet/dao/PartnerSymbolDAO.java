package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.PartnerSymbol;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PartnerSymbolDAO {
    @Select("select * from partner_symbol where partner_id=#{pid} and symbol=#{symbol}")
    PartnerSymbol getPartnerSymbolByPartneridAndSymbol(@Param("pid") Integer pid,@Param("symbol") String symbol);

    @Select("select * from partner_symbol where partner_id=#{pid} and symbol=#{symbol}")
    List<PartnerSymbol> getPartnerSymbolListByPartnerid(@Param("pid") Integer pid);

    @Select("select * from partner_symbol where partner_id=#{pid} and is_open=1")
    List<PartnerSymbol> getPartnerSymbolListByPartneridAndIsOpen(@Param("pid") Integer pid);
}
