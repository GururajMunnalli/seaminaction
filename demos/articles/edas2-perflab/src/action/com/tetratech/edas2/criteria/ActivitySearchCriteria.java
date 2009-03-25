/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.criteria;

import java.util.Date;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import com.tetratech.edas2.model.ActivityMedia;
import com.tetratech.edas2.model.ResultType;
import com.tetratech.edas2.model.SamplingCollectionEquipment;

/**
 * A criteria object to support the activity listing screen.
 */
@Name("activitySearchCriteria")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class ActivitySearchCriteria extends SearchCriteria {
	private String id;
	private String activityType;
	private String sampleCollectionMethod;
	private Date startDate;
	private Date endDate;
	private SamplingCollectionEquipment samplingCollectionEquipment = null;
	private ActivityMedia media = null;
	private ResultType resultType = null;
	private Long mlocUid = new Long(0);
	
	@Logger
	Log log;
	
	public ActivitySearchCriteria(){
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getSampleCollectionMethod() {
		return sampleCollectionMethod;
	}

	public void setSampleCollectionMethod(String sampleCollectionMethod) {
		this.sampleCollectionMethod = sampleCollectionMethod;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Long getMlocUid() {
		return mlocUid;
	}

	public void setMlocUid(Long mlocUid) {
		this.mlocUid = mlocUid;
	}
	
	
	public SamplingCollectionEquipment getSamplingCollectionEquipment() {
		return samplingCollectionEquipment;
	}

	public void setSamplingCollectionEquipment(SamplingCollectionEquipment samplingCollectionEquipment) {
		this.samplingCollectionEquipment = samplingCollectionEquipment;
	}

	public ActivityMedia getMedia() {
		return media;
	}

	public void setMedia(ActivityMedia media) {
		this.media = media;
	}
	
	public ResultType getResultType() {
		return resultType;
	}

	public void setResultType(ResultType resultType) {
		this.resultType = resultType;
	}

	@Override
	public void reset() {
		id="";
		activityType="";
		sampleCollectionMethod="";
		startDate=null;
		endDate=null;
		samplingCollectionEquipment=null;
		media=null;
		resultType=null;
		//mlocUid = new Long(0);
	}
}
