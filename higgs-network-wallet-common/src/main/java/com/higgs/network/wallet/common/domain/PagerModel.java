package com.higgs.network.wallet.common.domain;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class PagerModel extends BaseModel{
	private int total; // 总数
	private List list = new ArrayList(); // 分页集合列表
	private int pagerSize;// 总页数


    /**
     * Get pager size int.
     *
     * @return the int
     */
    public int getPagerSize() {
		return pagerSize;
	}

    /**
     * Set pager size.
     *
     * @param pagerSize the pager size
     */
    public void setPagerSize(int pagerSize) {
		this.pagerSize = pagerSize;
	}

    /**
     * Get total int.
     *
     * @return the int
     */
    public int getTotal() {
		return total;
	}

    /**
     * Set total.
     *
     * @param total the total
     */
    public void setTotal(int total) {
		this.total = total;
	}

    /**
     * Get list list.
     *
     * @return the list
     */
    public List getList() {
		return list == null ? new LinkedList() : list;
	}

    /**
     * Set list.
     *
     * @param list the list
     */
    public void setList(List list) {
		this.list = list;
	}
}
