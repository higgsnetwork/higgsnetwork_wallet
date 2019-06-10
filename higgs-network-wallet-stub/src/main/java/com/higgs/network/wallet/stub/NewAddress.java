package com.higgs.network.wallet.stub;

public class NewAddress {
    private String address;
    private String symbol;
    private String baseToken;

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setBaseToken(String baseToken) {
        this.baseToken = baseToken;
    }

    public String getBaseToken() {
        return baseToken;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
