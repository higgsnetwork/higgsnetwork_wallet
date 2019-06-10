package com.higgs.network.wallet.service.impl;

import com.higgs.network.wallet.dao.ConfigCoinSymbolDAO;
import com.higgs.network.wallet.dao.PartnerSymbolDAO;
import com.higgs.network.wallet.domain.ConfigCoinSymbol;
import com.higgs.network.wallet.domain.PartnerSymbol;
import com.higgs.network.wallet.service.SymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("SymbolService")
public class SymbolServiceImpl implements SymbolService {

    @Autowired
    private ConfigCoinSymbolDAO configCoinSymbolDAO;

    @Autowired
    private PartnerSymbolDAO partnerSymbolDAO;

    @Override
    public List<ConfigCoinSymbol> getAllSymbolList(){
        return configCoinSymbolDAO.getAllConfigCoinSymbolList();
    }

    @Override
    public List<ConfigCoinSymbol> getAllSymbolListIsOpen(){
        return configCoinSymbolDAO.getAllConfigCoinSymbolListIsOpen();
    }

    @Override
    public ConfigCoinSymbol getConfigCoinSymbolBySymbol(String symbol){
        return configCoinSymbolDAO.getConfigCoinSymbolBySymbol(symbol);
    }
    @Override
    public String getTokenBaseBySymbol(String symbol){
        ConfigCoinSymbol configCoinSymbol = getConfigCoinSymbolBySymbol(symbol);
        if(configCoinSymbol!=null){
            return configCoinSymbol.getTokenBase();
        }
        return "";
    }

    @Override
    public List<PartnerSymbol> getPartnerSymbolListByPatnerId(Integer patnerId){
        return partnerSymbolDAO.getPartnerSymbolListByPartnerid(patnerId);
    }

    @Override
    public List<PartnerSymbol> getPartnerSymbolListByPatnerIdAndIsOpen(Integer patnerId){
        return partnerSymbolDAO.getPartnerSymbolListByPartneridAndIsOpen(patnerId);
    }

    @Override
    public PartnerSymbol getPartnerSymbolByPatnerId(Integer patnerId,String symbol){
        return partnerSymbolDAO.getPartnerSymbolByPartneridAndSymbol(patnerId,symbol);
    }

    @Override
    public Boolean getSymbolDepositStatusByPatnerId(Integer patnerId,String symbol){
        PartnerSymbol partnerSymbol = getPartnerSymbolByPatnerId(patnerId,symbol);
        if(partnerSymbol!=null){
            if(partnerSymbol.getDepositOpen()==1){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean getSymbolWithdrawStatusByPatnerId(Integer patnerId,String symbol){
        PartnerSymbol partnerSymbol = getPartnerSymbolByPatnerId(patnerId,symbol);
        if(partnerSymbol!=null){
            if(partnerSymbol.getWithdrawOpen()==1){
                return true;
            }
        }
        return false;
    }
}
