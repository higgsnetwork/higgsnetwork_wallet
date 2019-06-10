package com.higgs.network.wallet.domain;

public class AutoOnchainSymbol {
    private Integer id;

    private String tokenBase;

    private String symbol;

    private String onchainTokenBase;

    private String onchainSymbol;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTokenBase() {
        return tokenBase;
    }

    public void setTokenBase(String tokenBase) {
        this.tokenBase = tokenBase == null ? null : tokenBase.trim();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public String getOnchainTokenBase() {
        return onchainTokenBase;
    }

    public void setOnchainTokenBase(String onchainTokenBase) {
        this.onchainTokenBase = onchainTokenBase == null ? null : onchainTokenBase.trim();
    }

    public String getOnchainSymbol() {
        return onchainSymbol;
    }

    public void setOnchainSymbol(String onchainSymbol) {
        this.onchainSymbol = onchainSymbol == null ? null : onchainSymbol.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}