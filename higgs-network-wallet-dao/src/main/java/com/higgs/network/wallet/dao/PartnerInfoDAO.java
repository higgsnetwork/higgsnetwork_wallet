package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.PartnerInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PartnerInfoDAO {

    @Select("select * from partner_info")
    List<PartnerInfo> getAllPartnerInfoList();

    @Select("select * from partner_info where status=1")
    List<PartnerInfo> getAllPartnerInfoListByIsOpen();

    @Select("select * from partner_info where id = #{id}")
    PartnerInfo getPartnerInfoByID(@Param("id") Integer id);

    @Select("select * from partner_info where hash = #{hash}")
    PartnerInfo getPartnerInfoByHash(@Param("hash") String hash);
}
