package com.higgs.network.wallet.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDepositCrypto {
    private Integer id;

    private Integer partnerId;

    private String symbol;

    private BigDecimal amount;

    private BigDecimal fee;

    private BigDecimal realFee;

    private Date createdAt;

    private Date updatedAt;

    private String addressTo;

    private String txid;

    private Integer confirmations;

    private Byte isMining;

    private Byte status;

    private String encrypt;

    private Integer requestCount;

    private Integer sendbox;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getRealFee() {
        return realFee;
    }

    public void setRealFee(BigDecimal realFee) {
        this.realFee = realFee;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(String addressTo) {
        this.addressTo = addressTo == null ? null : addressTo.trim();
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid == null ? null : txid.trim();
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Byte getIsMining() {
        return isMining;
    }

    public void setIsMining(Byte isMining) {
        this.isMining = isMining;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt == null ? null : encrypt.trim();
    }

    public void setRequestCount(Integer requestCount) {
        this.requestCount = requestCount;
    }

    public Integer getRequestCount() {
        return requestCount;
    }

    public void setSendbox(Integer sendbox) {
        this.sendbox = sendbox;
    }

    public Integer getSendbox() {
        return sendbox;
    }
}