package com.higgs.network.wallet.domain;

import java.math.BigDecimal;
import java.util.Date;

public class AutoOnchainPartnerOrderinfo {
    private Integer id;

    private Integer partnerId;

    private String partnerOrderno;

    private String tokenBase;

    private String symbol;

    private BigDecimal amount;

    private String fromPartneruid;

    private String toPartneruid;

    private Integer status;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public String getPartnerOrderno() {
        return partnerOrderno;
    }

    public void setPartnerOrderno(String partnerOrderno) {
        this.partnerOrderno = partnerOrderno == null ? null : partnerOrderno.trim();
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromPartneruid() {
        return fromPartneruid;
    }

    public void setFromPartneruid(String fromPartneruid) {
        this.fromPartneruid = fromPartneruid == null ? null : fromPartneruid.trim();
    }

    public String getToPartneruid() {
        return toPartneruid;
    }

    public void setToPartneruid(String toPartneruid) {
        this.toPartneruid = toPartneruid == null ? null : toPartneruid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}