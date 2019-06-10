package com.higgs.network.wallet.service;

import com.higgs.network.wallet.domain.AddressBindInfo;
import com.higgs.network.wallet.domain.SystemAddress;
import com.higgs.network.wallet.stub.SymbolInfo;

import java.util.List;

public interface AddressService {
//    List<SymbolInfo> getSymbolListInfo();
    String getAddressIsNotUse(String token_base);
    Boolean bindAddressToPartner(Integer parterid,String parteruid,String address,String coin_symbol,String token_base);
    int updateAddressIsUse(String token_base,String address);
    Boolean checkAddressByPartnerID(Integer parterid,String address);
    AddressBindInfo getAddressBindInfoByUidAndTokenBase(Integer parterid,String parteruid,String tokenbase);
    boolean checkAddressInHiggs(String address);
    String BindNewAddressByPartnerUid(Integer partnerId,String partnerUid, String token_base,String symbol);
    AddressBindInfo getAddressBindInfoByPartnerUidAndTokenbase(Integer partnerid, String partneruid, String tokenBase);
    SystemAddress getSystemAddressByBasetoken(String basetoken);
    AddressBindInfo getAddressInfo(String address);
}
