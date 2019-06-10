package com.higgs.network.wallet.domain;

public class AutoOnchainPartnerinfo {
    private Integer id;

    private Integer partnerId;

    private String onchainTokenBase;

    private Integer status;

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

    public String getOnchainTokenBase() {
        return onchainTokenBase;
    }

    public void setOnchainTokenBase(String onchainTokenBase) {
        this.onchainTokenBase = onchainTokenBase == null ? null : onchainTokenBase.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}