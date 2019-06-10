package com.higgs.network.wallet.domain;

import java.math.BigDecimal;

public class CreateWithdrawParameter {
    private String addressTo;
    private BigDecimal amount;
    private BigDecimal fee;
    private Integer partnerId;
    private String partnerOrderNo;
    private String sign;
    private String symbol;
    private Long timestamp;

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerOrderNo(String partnerOrderNo) {
        this.partnerOrderNo = partnerOrderNo;
    }

    public String getPartnerOrderNo() {
        return partnerOrderNo;
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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
