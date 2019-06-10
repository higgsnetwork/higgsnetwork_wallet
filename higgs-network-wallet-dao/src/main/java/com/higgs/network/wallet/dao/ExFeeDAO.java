package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.ExFee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ExFeeDAO {

    @Select("select * from ex_fee where symbol = #{symbol}")
    ExFee getExFeeBySymbol(@Param("symbol") String symbol);
}
