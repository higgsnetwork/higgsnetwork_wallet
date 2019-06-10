package com.higgs.network.wallet.domain;

import java.util.Date;

public class PartnerInfo {
    private Integer id;

    private String name;

    private String hash;

    private String url;

    private Integer status;

    private Integer sendbox;

    private Date createdate;

    private String key;

    private String depositCallbackUrl;

    private String withdrawCallbackUrl;

    private String transactionUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSendbox() {
        return sendbox;
    }

    public void setSendbox(Integer sendbox) {
        this.sendbox = sendbox;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDepositCallbackUrl() {
        return depositCallbackUrl;
    }

    public void setDepositCallbackUrl(String depositCallbackUrl) {
        this.depositCallbackUrl = depositCallbackUrl;
    }

    public String getWithdrawCallbackUrl() {
        return withdrawCallbackUrl;
    }

    public void setWithdrawCallbackUrl(String withdrawCallbackUrl) {
        this.withdrawCallbackUrl = withdrawCallbackUrl;
    }

    public String getTransactionUrl() {
        return transactionUrl;
    }

    public void setTransactionUrl(String transactionUrl) {
        this.transactionUrl = transactionUrl;
    }
}