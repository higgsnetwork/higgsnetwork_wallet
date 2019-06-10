package com.higgs.network.wallet.domain;

import java.util.Date;

public class AddressBindInfo {
    private Integer id;

    private Integer partnerId;

    private String symbol;

    private String address;

    private Integer type;

    private Integer status;

    private Date ctime;

    private String tokenBase;

    private String partnerUid;

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public void setPartnerUid(String partnerUid) {
        this.partnerUid = partnerUid;
    }

    public String getPartnerUid() {
        return partnerUid;
    }

    public void setTokenBase(String tokenBase) {
        this.tokenBase = tokenBase;
    }

    public String getTokenBase() {
        return tokenBase;
    }
}