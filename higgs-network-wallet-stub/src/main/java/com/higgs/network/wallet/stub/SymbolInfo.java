package com.higgs.network.wallet.stub;

public class SymbolInfo {
    private String coinSymbol;
    private String tokenBase;

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol;
    }

    public String getCoin_symbol() {
        return coinSymbol;
    }

    public void setTokenBase(String tokenBase) {
        this.tokenBase = tokenBase;
    }

    public String getToken_base() {
        return tokenBase;
    }
}
