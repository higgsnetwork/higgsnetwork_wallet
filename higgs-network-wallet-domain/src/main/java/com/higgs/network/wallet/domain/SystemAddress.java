package com.higgs.network.wallet.domain;

public class SystemAddress {
    private Integer id;
    private String baseToken;
    private String address;
    private Integer status;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setBaseToken(String baseToken) {
        this.baseToken = baseToken;
    }

    public String getBaseToken() {
        return baseToken;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}
