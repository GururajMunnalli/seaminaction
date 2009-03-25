/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.model;

import java.util.Date;

public interface AttributableRetire extends Attributable {
	public Date getRetiredDate();
	public void setRetiredDate(Date updatedDate);
	public Long getRetiredBy();
	public void setRetiredBy(Long userId);
}
