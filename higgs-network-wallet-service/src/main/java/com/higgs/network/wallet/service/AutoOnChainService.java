package com.higgs.network.wallet.service;

import com.higgs.network.wallet.domain.*;

import java.util.List;
import java.util.Map;

public interface AutoOnChainService {

    List<AutoOnchainPartnerinfo> getAutoOnchainPartnerinfoIsOpen();

    Integer[] openPartnerID();

    List<AutoOnchainPartnerinfo> getAutoOnchainPartnerinfoListByPartnerId(Integer PartnerId);

    String ScanTransactionUrl(String lastorderno, String url, String salt,Integer count);

    List<AutoOnchainPartnerOrderinfo> getAutoOnchainPartnerOrderinfoByPartneridAndStatus(Integer partnerid, Integer status);

    List<AutoOnchainPartnerOrderinfo> getAutoOnchainPartnerOrderinfoByStatus(Integer status);

    AutoOnchainPartnerOrderinfo getAutoOnchainPartnerOrderinfoByPartnerIdAndPartnerOrderno(Integer partnerid, String partnerOrderno);

    Map<Integer,String> openPartnerMap();

    List<AutoOnchainOrderinfo> getAutoOnchainOrderinfoByPartnerOrderno(Integer partnerid, String partnerOrderno);

    List<AutoOnchainOrderinfo> getAutoOnchainOrderinfoListByStatus(Integer status);

    AutoOnchainOrderinfo getAutoOnchainOrderinfoByPartnerOrdernoAndTokenbaseAndSymbol(Integer partnerid,String partnerOrderno,String tokenbase,String Symbol);

    Integer addAutoOnchainOrderinfo(AutoOnchainOrderinfo order);

    AutoOnchainSymbol getAutoOnchainSymbolByTokenBaseAndSymbol(String tokenBase, String symbol);

    List<AutoOnchainSymbol> getAutoOnchainSymbolListByOnchainSymbol(String onchainSymbol);

    Integer updateAutoOnchainOrderinfoByWithdrawAndOrderNo(AutoOnchainOrderinfo order);

    Integer updateAutoOnchainOrderinfoByDepositAndTxid(AutoOnchainOrderinfo order);

    Integer updatePartnerOrderinfoStatusByPartnerIdAndPartnerOrderNo(Integer partnerId,String partnerOrderno,Integer status);

    List<PartnerInfo> getScanTransactionPartnerList();

    Integer addAutoOnchainPartnerOrderinfo(AutoOnchainPartnerOrderinfo info);

    String getLastPartnerOrderno(Integer PartnerId);
}
