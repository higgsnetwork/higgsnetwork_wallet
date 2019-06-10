package com.higgs.network.wallet.domain;

import java.math.BigDecimal;
import java.util.Date;

public class ConfigCoinSymbol {
    private Integer id;

    private String coinSymbol;

    private String contractAddress;

    private Integer contractPrecision;

    private Integer showPrecision;

    private Byte isOpen;

    private Byte depositOpen;

    private Byte withdrawOpen;

    private String tokenBase;

    private String chainAddress;

    private String chainTx;

    private Integer depositConfirm;

    private Integer miningDepositConfirm;

    private BigDecimal withdrawMax;

    private BigDecimal withdrawMin;

    private String name;

    private String icon;

    private String link;

    private Integer sort;

    private Byte releaseStatus;

    private Date ctime;

    private Date mtime;

    private Byte isRelease;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoinSymbol() {
        return coinSymbol;
    }

    public void setCoinSymbol(String coinSymbol) {
        this.coinSymbol = coinSymbol == null ? null : coinSymbol.trim();
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress == null ? null : contractAddress.trim();
    }

    public Integer getContractPrecision() {
        return contractPrecision;
    }

    public void setContractPrecision(Integer contractPrecision) {
        this.contractPrecision = contractPrecision;
    }

    public Integer getShowPrecision() {
        return showPrecision;
    }

    public void setShowPrecision(Integer showPrecision) {
        this.showPrecision = showPrecision;
    }

    public Byte getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Byte isOpen) {
        this.isOpen = isOpen;
    }

    public Byte getDepositOpen() {
        return depositOpen;
    }

    public void setDepositOpen(Byte depositOpen) {
        this.depositOpen = depositOpen;
    }

    public Byte getWithdrawOpen() {
        return withdrawOpen;
    }

    public void setWithdrawOpen(Byte withdrawOpen) {
        this.withdrawOpen = withdrawOpen;
    }

    public String getTokenBase() {
        return tokenBase;
    }

    public void setTokenBase(String tokenBase) {
        this.tokenBase = tokenBase == null ? null : tokenBase.trim();
    }

    public String getChainAddress() {
        return chainAddress;
    }

    public void setChainAddress(String chainAddress) {
        this.chainAddress = chainAddress == null ? null : chainAddress.trim();
    }

    public String getChainTx() {
        return chainTx;
    }

    public void setChainTx(String chainTx) {
        this.chainTx = chainTx == null ? null : chainTx.trim();
    }

    public Integer getDepositConfirm() {
        return depositConfirm;
    }

    public void setDepositConfirm(Integer depositConfirm) {
        this.depositConfirm = depositConfirm;
    }

    public Integer getMiningDepositConfirm() {
        return miningDepositConfirm;
    }

    public void setMiningDepositConfirm(Integer miningDepositConfirm) {
        this.miningDepositConfirm = miningDepositConfirm;
    }

    public BigDecimal getWithdrawMax() {
        return withdrawMax;
    }

    public void setWithdrawMax(BigDecimal withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public BigDecimal getWithdrawMin() {
        return withdrawMin;
    }

    public void setWithdrawMin(BigDecimal withdrawMin) {
        this.withdrawMin = withdrawMin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Byte releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getMtime() {
        return mtime;
    }

    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    public Byte getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(Byte isRelease) {
        this.isRelease = isRelease;
    }
}