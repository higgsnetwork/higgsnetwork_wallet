package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.CryptoAddress;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CryptoAddressDAO {

    @Select("select * from crypto_address_${token_base} where is_used=0 limit 1")
    CryptoAddress getAddressIsNotUse(@Param("token_base") String token_base);

    @Update("update crypto_address_${token_base} set is_used=1 where address=#{address}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int updateAddressIsUse(@Param("token_base") String token_base,@Param("address") String address);
}
