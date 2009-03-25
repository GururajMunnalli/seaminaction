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
public class AttributableUpdateEntity implements AttributableUpdate {

	private Date updatedDate;
	private Long updatedBy;

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

}
