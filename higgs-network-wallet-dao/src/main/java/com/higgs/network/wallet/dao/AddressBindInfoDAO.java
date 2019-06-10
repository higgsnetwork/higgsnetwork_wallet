package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.AddressBindInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBindInfoDAO {

    @Select("select * from address_bind_info where partner_id=#{partnerid} and status=1")
    List<AddressBindInfo> getAddressBindInfoByPartnerID(@Param("partnerid") Integer partnerid);

    @Select("select * from address_bind_info where address=#{address}")
    AddressBindInfo getAddressBindInfoByAddress(@Param("address") String address);

    @Select("select * from address_bind_info where partner_id=#{partnerid} and address=#{address}")
    AddressBindInfo getAddressBindInfoByPartnerIDAndAddress(@Param("partnerid") Integer partnerid,@Param("address") String address);

    @Select("select * from address_bind_info where partner_id=#{partnerid} and partner_uid=#{partneruid} and token_base=#{tokenBase}")
    AddressBindInfo getAddressBindInfoByPartnerUidAndTokenbase(@Param("partnerid") Integer partnerid,@Param("partneruid") String partneruid,@Param("tokenBase") String tokenBase);

    @Insert("INSERT INTO address_bind_info (partner_id,symbol,address,type,status,ctime,token_base,partner_uid) VALUES (#{info.partnerId},#{info.symbol},#{info.address},#{info.type},#{info.status},NOW(),#{info.tokenBase},#{info.partnerUid})")
    int insertAddressBindInfo(@Param("info") AddressBindInfo info);
}
