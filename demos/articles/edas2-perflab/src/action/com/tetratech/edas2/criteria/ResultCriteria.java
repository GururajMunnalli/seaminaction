/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.criteria;

import com.tetratech.edas2.model.ResultStatus;

public class ResultCriteria extends SearchCriteria {
	
	private Long actUid = new Long(0);
	private ResultStatus status = null;
	
	public ResultCriteria(){
		
	}
	
	public Long getActUid() {
		return actUid;
	}

	public void setActUid(Long actUid) {
		this.actUid = actUid;
	}

	
	public ResultStatus getStatus() {
		return status;
	}

	public void setStatus(ResultStatus status) {
		this.status = status;
	}

	@Override
	public void reset() {
		status = null;
	}
	
}
