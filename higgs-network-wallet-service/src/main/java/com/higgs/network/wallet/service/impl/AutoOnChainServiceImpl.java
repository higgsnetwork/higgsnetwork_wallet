package com.higgs.network.wallet.service.impl;

import com.higgs.network.wallet.common.util.HttpUtil;
import com.higgs.network.wallet.common.util.MD5;
import com.higgs.network.wallet.common.util.VerifySign;
import com.higgs.network.wallet.dao.AutoOnchainOrderinfoDAO;
import com.higgs.network.wallet.dao.AutoOnchainPartnerOrderinfoDAO;
import com.higgs.network.wallet.dao.AutoOnchainPartnerinfoDAO;
import com.higgs.network.wallet.dao.AutoOnchainSymbolDAO;
import com.higgs.network.wallet.domain.*;
import com.higgs.network.wallet.service.AutoOnChainService;
import com.higgs.network.wallet.service.PartnerService;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("AutoOnChainService")
public class AutoOnChainServiceImpl implements AutoOnChainService {
    private static final Logger logger = LoggerFactory.getLogger(AutoOnChainServiceImpl.class);
    @Autowired
    private AutoOnchainPartnerinfoDAO autoOnchainPartnerinfoDAO;
    @Autowired
    private AutoOnchainPartnerOrderinfoDAO autoOnchainPartnerOrderinfoDAO;
    @Autowired
    private AutoOnchainOrderinfoDAO autoOnchainOrderinfoDAO;
    @Autowired
    private AutoOnchainSymbolDAO autoOnchainSymbolDAO;
    @Autowired
    private PartnerService partnerService;

    @Override
    public Map<Integer,String> openPartnerMap(){
        Map<Integer,String> urlMap = new HashMap<>();
        List<AutoOnchainPartnerinfo> openPartnerList = getAutoOnchainPartnerinfoIsOpen();
        if(openPartnerList!=null&&openPartnerList.size()>0){
            logger.info("AutoOnChainService - PartnerinfoIsOpenCount {} ", openPartnerList.size());
            //循环配置表获得合作方ID和刷交易的主链名称
            for (AutoOnchainPartnerinfo openPartneritem:openPartnerList) {

                //通过配置列表读取合作方信息
                PartnerInfo partnerInfo = partnerService.getPartnerInfoByID(openPartneritem.getPartnerId());
                if(partnerInfo==null || StringUtils.isBlank(partnerInfo.getTransactionUrl())) {
                    //合作方信息不存在或读交易接口地址不为空
                    logger.info("AutoOnChainService partnerInfo=null or TransactionUrl=null by PartnerId={}",openPartneritem.getPartnerId());
                }
                else {
                    //所有地址写入urlMap
                    if (urlMap.size() == 0 || urlMap.get(openPartneritem.getPartnerId())==null) {
                        //不插入重复数据
                        urlMap.put(openPartneritem.getPartnerId(),partnerInfo.getTransactionUrl());
                    }
                }

                logger.info("AutoOnChainService TransactionUrl Map "+urlMap.toString());
            }
        }
        return urlMap;
    }

    @Override
    public Integer[] openPartnerID(){
        return autoOnchainPartnerinfoDAO.openPartnerID();
    }

    @Override
    public List<AutoOnchainPartnerinfo> getAutoOnchainPartnerinfoIsOpen(){
        return autoOnchainPartnerinfoDAO.getAutoOnchainPartnerinfoListByIsOpen();
    }

    @Override
    public List<AutoOnchainPartnerinfo> getAutoOnchainPartnerinfoListByPartnerId(Integer PartnerId){
        return autoOnchainPartnerinfoDAO.getAutoOnchainPartnerinfoListByPartnerId(PartnerId);
    }

    /**
     * 扫描交易
     * @param lastorderno
     * @param url
     * @param salt
     */
    @Override
    public String ScanTransactionUrl(String lastorderno, String url, String salt,Integer count){
        List<AutoOnchainPartnerOrderinfo> autoOnchainPartnerOrderinfos=null;
        try {
            if (!StringUtils.isBlank(url)) {
                Long time = System.currentTimeMillis();
                //组装参数进行签名
                Map<String, Object> map = new HashMap<>();
                map.put("lastorderno",lastorderno);
                map.put("count",count);
                map.put("timestamp",time);
                String sign = MD5.getMD5(VerifySign.getVerifySignString(map, salt));

                JSONObject params = new JSONObject(map);
                params.put("sign", sign);
                logger.info("ScanTransactionUrl url={} params={}", url, params);
                String responseData = HttpUtil.sendPostJson(url, params, "utf-8");
                return responseData;
            }
            else{
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("AutoOnChainScanTaskImpl Scan catch {}" , e.getMessage());
            return null;
        }
    }

    @Override
    public List<AutoOnchainPartnerOrderinfo> getAutoOnchainPartnerOrderinfoByPartneridAndStatus(Integer partnerid, Integer status){
        return autoOnchainPartnerOrderinfoDAO.getAutoOnchainPartnerOrderinfoByPartneridAndStatus(partnerid,status);
    }

    @Override
    public List<AutoOnchainPartnerOrderinfo> getAutoOnchainPartnerOrderinfoByStatus(Integer status){
        return autoOnchainPartnerOrderinfoDAO.getAutoOnchainPartnerOrderinfoByStatus(status);
    }

    @Override
    public AutoOnchainPartnerOrderinfo getAutoOnchainPartnerOrderinfoByPartnerIdAndPartnerOrderno(Integer partnerid, String partnerOrderno){
        return autoOnchainPartnerOrderinfoDAO.getAutoOnchainPartnerOrderinfoByPartnerIdAndPartnerOrderno(partnerid,partnerOrderno);
    }

    @Override
    public List<AutoOnchainOrderinfo> getAutoOnchainOrderinfoByPartnerOrderno(Integer partnerid,String partnerOrderno){
        return autoOnchainOrderinfoDAO.getAutoOnchainOrderinfoByPartnerOrderno(partnerid,partnerOrderno);
    }

    @Override
    public List<AutoOnchainOrderinfo> getAutoOnchainOrderinfoListByStatus(Integer status){
        return autoOnchainOrderinfoDAO.getAutoOnchainOrderinfoListByStatus(status);
    }

    @Override
    public AutoOnchainOrderinfo getAutoOnchainOrderinfoByPartnerOrdernoAndTokenbaseAndSymbol(Integer partnerid,String partnerOrderno,String tokenbase,String Symbol){
        return autoOnchainOrderinfoDAO.getAutoOnchainOrderinfoByPartnerOrdernoAndTokenbaseAndSymbol(partnerid,partnerOrderno,tokenbase,Symbol);
    }

    @Override
    public Integer addAutoOnchainOrderinfo(AutoOnchainOrderinfo order){
        return autoOnchainOrderinfoDAO.addAutoOnchainOrderinfo(order);
    }

    @Override
    public AutoOnchainSymbol getAutoOnchainSymbolByTokenBaseAndSymbol(String tokenBase,String symbol){
        return autoOnchainSymbolDAO.getAutoOnchainSymbolListByTokenbaseAndSymbol(tokenBase,symbol);
    }

    @Override
    public List<AutoOnchainSymbol> getAutoOnchainSymbolListByOnchainSymbol(String onchainSymbol){
        return autoOnchainSymbolDAO.getAutoOnchainSymbolListByOnchainSymbol(onchainSymbol);
    }

    @Override
    public Integer updateAutoOnchainOrderinfoByWithdrawAndOrderNo(AutoOnchainOrderinfo order){
        return autoOnchainOrderinfoDAO.updateAutoOnchainOrderinfoByWithdrawAndOrderNo(order);
    }

    @Override
    public Integer updateAutoOnchainOrderinfoByDepositAndTxid(AutoOnchainOrderinfo order){
        return autoOnchainOrderinfoDAO.updateAutoOnchainOrderinfoByDepositAndTxid(order);
    }

    @Override
    public Integer updatePartnerOrderinfoStatusByPartnerIdAndPartnerOrderNo(Integer partnerId,String partnerOrderno,Integer status){
        return autoOnchainPartnerOrderinfoDAO.updatePartnerOrderinfoStatusByPartnerIdAndPartnerOrderNo(partnerId,partnerOrderno,status);
    }

    @Override
    public List<PartnerInfo> getScanTransactionPartnerList(){
        List<PartnerInfo> partnerInfos = new ArrayList<PartnerInfo>() ;
        List<AutoOnchainPartnerinfo> autoOnchainPartnerOrderinfos = autoOnchainPartnerinfoDAO.getAutoOnchainPartnerinfoListByIsOpen();
        if(autoOnchainPartnerOrderinfos!=null) {
            for (AutoOnchainPartnerinfo item:autoOnchainPartnerOrderinfos) {
                partnerInfos.add(partnerService.getPartnerInfoByID(item.getPartnerId()));
            }
        }
        return partnerInfos;
    }

    public Integer addAutoOnchainPartnerOrderinfo(AutoOnchainPartnerOrderinfo info){
        return autoOnchainPartnerOrderinfoDAO.add(info);
    }

    public String getLastPartnerOrderno(Integer PartnerId){
        String lastPartnerOrderNo = autoOnchainOrderinfoDAO.getLastPartnerOrderno(PartnerId);
        if(StringUtils.isBlank(lastPartnerOrderNo)){
            return "";
        }else{
            return lastPartnerOrderNo;
        }
    }
}
