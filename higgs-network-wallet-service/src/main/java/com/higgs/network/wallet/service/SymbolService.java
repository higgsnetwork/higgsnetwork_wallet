package com.higgs.network.wallet.service;

import com.higgs.network.wallet.domain.ConfigCoinSymbol;
import com.higgs.network.wallet.domain.PartnerSymbol;

import java.util.List;

public interface SymbolService {
    List<ConfigCoinSymbol> getAllSymbolList();
    List<ConfigCoinSymbol> getAllSymbolListIsOpen();
    ConfigCoinSymbol getConfigCoinSymbolBySymbol(String symbol);
    String getTokenBaseBySymbol(String symbol);
    List<PartnerSymbol> getPartnerSymbolListByPatnerId(Integer patnerId);
    List<PartnerSymbol> getPartnerSymbolListByPatnerIdAndIsOpen(Integer patnerId);
    PartnerSymbol getPartnerSymbolByPatnerId(Integer patnerId,String symbol);
    Boolean getSymbolDepositStatusByPatnerId(Integer patnerId,String symbol);
    Boolean getSymbolWithdrawStatusByPatnerId(Integer patnerId,String symbol);
}
