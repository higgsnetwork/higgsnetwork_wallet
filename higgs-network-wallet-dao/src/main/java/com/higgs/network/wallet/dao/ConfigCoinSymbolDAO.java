package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.ConfigCoinSymbol;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ConfigCoinSymbolDAO {
    @Select("SELECT * from config_coin_symbol")
    List<ConfigCoinSymbol> getAllConfigCoinSymbolList();

    @Select("SELECT * from config_coin_symbol where is_open=1")
    List<ConfigCoinSymbol> getAllConfigCoinSymbolListIsOpen();


    @Select("SELECT * from config_coin_symbol where coin_symbol=#{symbol}")
    ConfigCoinSymbol getConfigCoinSymbolBySymbol(@Param("symbol") String symbol);


}
