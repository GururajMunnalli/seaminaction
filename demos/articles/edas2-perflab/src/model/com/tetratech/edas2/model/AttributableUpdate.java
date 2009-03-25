/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.model;

import java.util.Date;

public interface AttributableUpdate extends Attributable {
	public Date getUpdatedDate();
	public void setUpdatedDate(Date updatedDate);
	public Long getUpdatedBy();
	public void setUpdatedBy(Long userId);
}
