package com.higgs.network.wallet.service.impl;

import com.higgs.network.wallet.common.stub.Result;
import com.higgs.network.wallet.common.stub.ResultCode;
import com.higgs.network.wallet.common.stub.ResultMessage;
import com.higgs.network.wallet.dao.AddressBindInfoDAO;
import com.higgs.network.wallet.dao.CryptoAddressDAO;
import com.higgs.network.wallet.dao.SystemAddressDAO;
import com.higgs.network.wallet.domain.AddressBindInfo;
import com.higgs.network.wallet.domain.CryptoAddress;
import com.higgs.network.wallet.domain.SystemAddress;
import com.higgs.network.wallet.service.AddressService;
import com.higgs.network.wallet.stub.SymbolInfo;
import net.minidev.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("AddressService")
public class AddressServiceImpl implements AddressService {
    @Autowired
    private CryptoAddressDAO cryptoAddressDAO;

    @Autowired
    private AddressBindInfoDAO addressBindInfoDAO;

    @Autowired
    private SystemAddressDAO systemAddressDAO;

//    @Override
//    public List<SymbolInfo> getSymbolListInfo(){
//
//    }
    @Override
    public String getAddressIsNotUse(String token_base){
        CryptoAddress cryptoAddress = cryptoAddressDAO.getAddressIsNotUse(token_base.toLowerCase());
        if(cryptoAddress!=null){
            return cryptoAddress.getAddress();
        }else {
            //已没有空地址，发送系统警告给技术部门。
            return "";
        }
    }

    /**
     * 把地址和合作方信息绑定，此地址仅归绑定的合作方使用
     * @param parterid
     * @param address
     * @param coin_symbol
     * @param token_base
     * @return
     */
    @Override
    public Boolean bindAddressToPartner(Integer parterid,String parteruid,String address,String coin_symbol,String token_base){
        if(addressBindInfoDAO.getAddressBindInfoByAddress(address)==null){
            return bindAddress(parterid,parteruid,address,coin_symbol,token_base);
        }
        return false;
    }

    @Override
    public Boolean checkAddressByPartnerID(Integer parterid,String address){
        AddressBindInfo addressBindInfo = addressBindInfoDAO.getAddressBindInfoByPartnerIDAndAddress(parterid,address);
        if(addressBindInfo!=null && addressBindInfo.getStatus()==1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 更新地址为已使用
     * @param token_base
     * @param address
     * @return
     */
    @Override
    public int updateAddressIsUse(String token_base,String address){
        return cryptoAddressDAO.updateAddressIsUse(token_base.toLowerCase(),address);
    }

    @Transactional
    private boolean bindAddress(Integer parterid,String parteruid,String address,String symbol,String token_base){
        AddressBindInfo addressBindInfo = new AddressBindInfo();
        addressBindInfo.setPartnerId(parterid);
        addressBindInfo.setAddress(address);
        addressBindInfo.setSymbol(symbol);
        addressBindInfo.setType(0);
        addressBindInfo.setStatus(1);
        addressBindInfo.setTokenBase(token_base);
        addressBindInfo.setPartnerUid(parteruid);
        if(cryptoAddressDAO.updateAddressIsUse(token_base.toLowerCase(),address)>0 && addressBindInfoDAO.insertAddressBindInfo(addressBindInfo)>0){
            //更新地址为已使用并绑定合作方信息
            return true;
        }
        return false;
    }

    @Override
    public AddressBindInfo getAddressBindInfoByUidAndTokenBase(Integer parterid,String parteruid,String tokenbase){
        AddressBindInfo abi = addressBindInfoDAO.getAddressBindInfoByPartnerUidAndTokenbase(parterid,parteruid,tokenbase);
        return abi;
    }

    @Override
    public boolean checkAddressInHiggs(String address){
        if(!StringUtils.isBlank(address)) {
            AddressBindInfo addressBindInfo = addressBindInfoDAO.getAddressBindInfoByAddress(address);
            if(addressBindInfo==null){
                return false;
            }
        }
        else{
            return false;
        }
        return true;
    }

    @Override
    public String BindNewAddressByPartnerUid(Integer partnerId,String partnerUid, String token_base,String symbol){
        String address = getAddressIsNotUse(token_base);
        if (!StringUtils.isBlank(address)) {
            //得到一个未使用的地址，进行合作方信息绑定
            if (bindAddressToPartner(partnerId,partnerUid, address, symbol, token_base)) {
                return address;
            }
        }
        return "";
    }

    @Override
    public AddressBindInfo getAddressBindInfoByPartnerUidAndTokenbase(Integer partnerid, String partneruid, String tokenBase){
        return addressBindInfoDAO.getAddressBindInfoByPartnerUidAndTokenbase(partnerid, partneruid, tokenBase);
    }

    @Override
    public SystemAddress getSystemAddressByBasetoken(String basetoken){
        return systemAddressDAO.getSystemAddressByBasetoken(basetoken);
    }

    @Override
    public AddressBindInfo getAddressInfo(String address){
        AddressBindInfo addressBindInfo = null;
        if(!StringUtils.isBlank(address)) {
            addressBindInfo = addressBindInfoDAO.getAddressBindInfoByAddress(address);
        }
        return addressBindInfo;
    }
}
