package com.higgs.network.wallet.domain;

import java.util.List;

public class ScanTransaction {
    private String status;
    private Integer orderCount;
    private List<ScanTransactionOrderItem> date;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public List<ScanTransactionOrderItem> getDate() {
        return date;
    }

    public void setDate(List<ScanTransactionOrderItem> date) {
        this.date = date;
    }
}
