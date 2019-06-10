package com.higgs.network.wallet.dao;

import com.higgs.network.wallet.domain.AutoOnchainPartnerOrderinfo;
import org.apache.ibatis.annotations.*;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Mapper
public interface AutoOnchainPartnerOrderinfoDAO {

    //查询最后写入的一条需要上链的交易
    @Select("select partner_orderno from auto_onchain_partner_orderinfo where partner_id=#{partnerId} order by id desc limit 1")
    String getLastPartnerOrderNo(@Param("partnerId") String partnerId);

    //根据合作方订单号查询上链数据
    @Select("select * from auto_onchain_partner_orderinfo where partner_id=#{partnerId} and partner_orderno=#{partnerOrderno}")
    AutoOnchainPartnerOrderinfo getAutoOnchainPartnerOrderinfoByPartnerIdAndPartnerOrderno(@Param("partnerId") Integer partnerId,@Param("partnerOrderno") String partnerOrderno);

    //根据本地订单号查询订单
    @Select("select * from auto_onchain_partner_orderinfo where partner_id=#{partnerId} and orderno=#{orderno}")
    AutoOnchainPartnerOrderinfo getAutoOnchainPartnerOrderinfoByPartnerIdAndOrderno(@Param("partnerId") Integer partnerId,@Param("orderno") String orderno);

    //根据合作方ID和status获得订单列表
    @Select("select * from auto_onchain_partner_orderinfo where partner_id=#{partnerId} and status=#{status}")
    List<AutoOnchainPartnerOrderinfo> getAutoOnchainPartnerOrderinfoByPartneridAndStatus(@Param("partnerId") Integer partnerId,@Param("status") Integer status);


    //根据合作方ID和status获得订单列表
    @Select("select * from auto_onchain_partner_orderinfo where status=#{status}")
    List<AutoOnchainPartnerOrderinfo> getAutoOnchainPartnerOrderinfoByStatus(@Param("status") Integer status);

    @Update("update auto_onchain_partner_orderinfo set status=#{status} where partner_id=#{partnerId} and partner_orderno=#{partnerOrderno}")
    Integer updatePartnerOrderinfoStatusByPartnerIdAndPartnerOrderNo(@Param("partnerId") Integer partnerId,@Param("partnerOrderno") String partnerOrderno,@Param("status") Integer status);

    //新增数据
    @Insert("insert into auto_onchain_partner_orderinfo " +
            "(partner_id,partner_orderno,token_base,symbol,amount,from_partneruid,to_partneruid,status,create_time)" +
            " values " +
            "(#{info.partnerId},#{info.partnerOrderno},#{info.tokenBase},#{info.symbol},#{info.amount},#{info.fromPartneruid},#{info.toPartneruid},#{info.status},#{info.createTime})")
    Integer add(@Param("info") AutoOnchainPartnerOrderinfo info);

    //批量添加数据
    @InsertProvider(type = CyQuestionDetailProvider.class, method = "batchInsertQuestionDetail")
    Integer batchInsert(@Param("list")List<AutoOnchainPartnerOrderinfo> records);

    class CyQuestionDetailProvider {

        public String batchInsertQuestionDetail(Map<String, List<AutoOnchainPartnerOrderinfo>> map) {
            List<AutoOnchainPartnerOrderinfo> list = map.get("list");
            StringBuilder stringBuilder = new StringBuilder(256);
            stringBuilder.append("insert into auto_onchain_partner_orderinfo(partner_id,partner_orderno,orderno,token_base,symbol,amount,from_partneruid,to_partneruid,status,create_time) values ");
            MessageFormat messageFormat = new MessageFormat("(#'{'list[{0}].partnerId,jdbcType=INTEGER}, " +
                    "#'{'list[{0}].partnerOrderno,jdbcType=VARCHAR}, #'{'list[{0}].orderno,jdbcType=VARCHAR}, " +
                    "#'{'list[{0}].tokenBase,jdbcType=VARCHAR}, #'{'list[{0}].symbol,jdbcType=VARCHAR}, " +
                    "#'{'list[{0}].amount,jdbcType=DECIMAL}, " +
                    "#'{'list[{0}].frompPartneruid,jdbcType=VARCHAR}, #'{'list[{0}].toPartneruid,jdbcType=VARCHAR}, " +
                    "#'{'list[{0}].status,jdbcType=INTEGER}, #'{'list[{0}].toPartneruid,jdbcType=VARCHAR},CURRENT_TIMESTAMP()) ");
            for (int i = 0; i < list.size(); i++) {
                stringBuilder.append(messageFormat.format(new Integer[]{i}));
                stringBuilder.append(",");
            }
            stringBuilder.setLength(stringBuilder.length() - 1);
            return stringBuilder.toString();
        }

    }
}
