package com.higgs.network.wallet.common.domain;

import java.io.Serializable;
import java.util.Date;

public class BaseModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Base model.
	 */
	public BaseModel()
	{

	}

	/**
	 * 创建人ID
	 */
	private String createdBy;
	/**
	 * 创建时间   
	 */
	private Date createdTime;
	/**
	 * 修改人ID   
	 */
	private String updatedBy;
	/**
	 * 修改时间  
	 */   
	private Date updatedTime;

	private int offset; // 偏移量
	private int pageSize;// 总页数

	/**
	 * Get created by string.
	 *
	 * @return the string
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set created by.
	 *
	 * @param createdBy the created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Get created time date.
	 *
	 * @return the date
	 */
	public Date getCreatedTime() {
		return createdTime;
	}

	/**
	 * Set created time.
	 *
	 * @param createdTime the created time
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * Get updated by string.
	 *
	 * @return the string
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Set updated by.
	 *
	 * @param updatedBy the updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Get updated time date.
	 *
	 * @return the date
	 */
	public Date getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * Set updated time.
	 *
	 * @param updatedTime the updated time
	 */
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * Get offset int.
	 *
	 * @return the int
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Set offset.
	 *
	 * @param offset the offset
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * Get page size int.
	 *
	 * @return the int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Set page size.
	 *
	 * @param pageSize the page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
