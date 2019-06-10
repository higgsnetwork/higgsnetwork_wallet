package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.AddressBindInfo;
import com.higgs.network.wallet.domain.SystemAddress;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SystemAddressDAO {

    @Select("select * from system_address where status=1")
    List<SystemAddress> getSystemAddressListByOpen();

    @Select("select * from system_address where base_token=#{basetoken} and status=1")
    SystemAddress getSystemAddressByBasetoken(@Param("basetoken") String basetoken);

    @Update("update system_address set base_token=#{item.baseToken},address=#{item.address},status=#{item.status} where id=#{item.id}")
    Integer update(@Param("item") SystemAddress item);

    @Insert("INSERT INTO system_address (id,base_token,address,status) VALUES (#{item.id},#{info.baseToken},#{info.address},#{item.status})")
    Integer add(@Param("item") SystemAddress item);
}
