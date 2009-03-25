/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.model;

import java.util.Date;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AttributableUpdateRetireEntity
		implements
			AttributableUpdate,
			AttributableRetire {

	private Date updatedDate;
	private Long updatedBy;
	private Date retiredDate;
	private Long retiredBy;

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long userId) {
		this.updatedBy = userId;
	}

	public Date getRetiredDate() {
		return retiredDate;
	}

	public void setRetiredDate(Date retiredDate) {
		this.retiredDate = retiredDate;
	}

	public Long getRetiredBy() {
		return retiredBy;
	}

	public void setRetiredBy(Long userId) {
		this.retiredBy = userId;
	}

}
