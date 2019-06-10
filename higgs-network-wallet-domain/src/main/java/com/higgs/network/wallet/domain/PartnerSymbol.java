package com.higgs.network.wallet.domain;

import javax.xml.crypto.Data;
import java.util.Date;

public class PartnerSymbol {
    private Integer partnerId;
    private String symbol;
    private Integer isOpen;
    private Integer depositOpen;
    private Integer withdrawOpen;
    private Date updateTime;

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getDepositOpen() {
        return depositOpen;
    }

    public void setDepositOpen(Integer depositOpen) {
        this.depositOpen = depositOpen;
    }

    public Integer getWithdrawOpen() {
        return withdrawOpen;
    }

    public void setWithdrawOpen(Integer withdrawOpen) {
        this.withdrawOpen = withdrawOpen;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
