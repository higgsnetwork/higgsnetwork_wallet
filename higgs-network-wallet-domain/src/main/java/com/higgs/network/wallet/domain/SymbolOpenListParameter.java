package com.higgs.network.wallet.domain;

public class SymbolOpenListParameter {
    private Integer partnerId;
    private Long timestamp;
    private String sign;

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign() {
        return sign;
    }
}
