/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.model;

import java.util.Date;

public interface AttributableCreate extends Attributable {
	public Date getCreatedDate();
	public void setCreatedDate(Date updatedDate);
	public Long getCreatedBy();
	public void setCreatedBy(Long userId);
}
