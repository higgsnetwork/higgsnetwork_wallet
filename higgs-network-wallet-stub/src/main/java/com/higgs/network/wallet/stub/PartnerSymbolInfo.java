package com.higgs.network.wallet.stub;

import java.math.BigDecimal;

public class PartnerSymbolInfo {
    private String symbol;
    private Boolean depositOpen;
    private Boolean withdrawOpen;
    private Integer contratPrecision;
    private Integer showPrecision;
    private String tokenBase;
    private String withdrawMax;
    private String withdrawMin;

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public void setDepositOpen(Boolean depositOpen) {
        this.depositOpen = depositOpen;
    }

    public Boolean getDepositOpen() {
        return depositOpen;
    }

    public void setWithdrawOpen(Boolean withdrawOpen) {
        this.withdrawOpen = withdrawOpen;
    }

    public Boolean getWithdrawOpen() {
        return withdrawOpen;
    }

    public void setContratPrecision(Integer contratPrecision) {
        this.contratPrecision = contratPrecision;
    }

    public Integer getContratPrecision() {
        return contratPrecision;
    }

    public void setShowPrecision(Integer showPrecision) {
        this.showPrecision = showPrecision;
    }

    public Integer getShowPrecision() {
        return showPrecision;
    }

    public void setTokenBase(String tokenBase) {
        this.tokenBase = tokenBase;
    }

    public String getTokenBase() {
        return tokenBase;
    }

    public String toString(){
        return "";
    }

    public void setWithdrawMin(String withdrawMin) {
        this.withdrawMin = withdrawMin;
    }

    public String getWithdrawMin() {
        return withdrawMin;
    }

    public void setWithdrawMax(String withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public String getWithdrawMax() {
        return withdrawMax;
    }
}
