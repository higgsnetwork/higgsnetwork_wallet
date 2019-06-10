package com.higgs.network.wallet.domain;

import java.math.BigDecimal;
import java.util.Date;

public class AutoOnchainOrderinfo {
    private Integer id;

    private Integer partnerId;

    private String partnerOrderno;

    private String orderno;

    private String onchainTokenBase;

    private String onchainSymbol;

    private BigDecimal amount;

    private String fromAddress;

    private String fromPartnerUid;

    private Integer withdrawStatus;

    private Date withdrawTime;

    private String toAddress;

    private String toPartnerUid;

    private Integer depositStatus;

    private Date depositTime;

    private String txid;

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

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno == null ? null : orderno.trim();
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress == null ? null : fromAddress.trim();
    }

    public String getFromPartnerUid() {
        return fromPartnerUid;
    }

    public void setFromPartnerUid(String fromPartnerUid) {
        this.fromPartnerUid = fromPartnerUid == null ? null : fromPartnerUid.trim();
    }

    public Integer getWithdrawStatus() {
        return withdrawStatus;
    }

    public void setWithdrawStatus(Integer withdrawStatus) {
        this.withdrawStatus = withdrawStatus;
    }

    public Date getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(Date withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress == null ? null : toAddress.trim();
    }

    public String getToPartnerUid() {
        return toPartnerUid;
    }

    public void setToPartnerUid(String toPartnerUid) {
        this.toPartnerUid = toPartnerUid == null ? null : toPartnerUid.trim();
    }

    public Integer getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(Integer depositStatus) {
        this.depositStatus = depositStatus;
    }

    public Date getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(Date depositTime) {
        this.depositTime = depositTime;
    }

    public String getTxid() {
        return txid;
    }

    public void setTxid(String txid) {
        this.txid = txid == null ? null : txid.trim();
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