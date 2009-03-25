/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.criteria;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ScopeType;

/**
 * A criteria object to support the monitoring location listing screen.
 */
@Name("mlocSearchCriteria")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class MonitoringLocationSearchCriteria extends ResultCriteria {
	private String id;
	private String name;
	private String huc8;
	private String county;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}

	public String getHuc8() {
		return huc8;
	}
	
	public void setHuc8(String huc8) {
		this.huc8 = huc8;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isBlank() {
		return StringUtils.isBlank(id) && StringUtils.isBlank(name) &&
			StringUtils.isBlank(huc8) && StringUtils.isBlank(county);
	}
	
	@Override
	public void reset() {
		id = "";
		name = "";
		huc8 = "";
		county = "";
	}
}
