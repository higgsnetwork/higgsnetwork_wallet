package com.higgs.network.wallet.common.domain;


/**
 * 分页模型，也是所有实体类的基类
 *
 */
public class PushPagerModel extends PagerModel{
	private int unreadTotal;

    /**
     * Get unread total int.
     *
     * @return the int
     */
    public int getUnreadTotal() {
		return unreadTotal;
	}

    /**
     * Set unread total.
     *
     * @param unreadTotal the unread total
     */
    public void setUnreadTotal(int unreadTotal) {
		this.unreadTotal = unreadTotal;
	}
}
