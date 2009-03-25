package com.tetratech.edas2.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

import com.tetratech.edas2.model.Characteristic;
import com.tetratech.edas2.model.MonitoringLocation;
import com.tetratech.edas2.model.ResultStatus;

@Name("reportSearchCriteria")
@AutoCreate
@Scope(ScopeType.CONVERSATION)
public class ReportCriteria extends SearchCriteria{
	
	@Logger
	Log log;
	
	private Date startDate;
	private Date endDate;
	private List<Characteristic> characteristics = new ArrayList<Characteristic>();
	private List<ResultStatus> resultStatuses = new ArrayList<ResultStatus>(); 
	private List<MonitoringLocation> monitoringLocations = new ArrayList<MonitoringLocation>();
	private String exclude;
	
	
	
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



	public List<Characteristic> getCharacteristics() {
		return characteristics;
	}



	public void setCharacteristics(List<Characteristic> characteristics) {
		this.characteristics = characteristics;
	}



	public List<ResultStatus> getResultStatuses() {
		return resultStatuses;
	}



	public void setResultStatuses(List<ResultStatus> resultStatuses) {
		this.resultStatuses = resultStatuses;
	}

	
	public String getExclude() {
		return exclude;
	}


	public void setExclude(String exclude) {
		this.exclude = exclude;
	}


	public List<MonitoringLocation> getMonitoringLocations() {
		return monitoringLocations;
	}


	public void setMonitoringLocations(List<MonitoringLocation> monitoringLocations) {
		this.monitoringLocations = monitoringLocations;
	}
	
	/*** Utility Methods ***/
	public void addMonitoringLocation(MonitoringLocation ml){
		monitoringLocations.add(ml);
	}
	
	public void removeMonitoringLocation(MonitoringLocation ml){
		monitoringLocations.remove(ml);
	}
	
	public boolean containsMonitoringLocation(MonitoringLocation ml){
		return monitoringLocations.contains(ml);
	}

	public void clearMonitoringLocations(){
		monitoringLocations.clear();
	}
	
	public void addCharacteristic(Characteristic c){
		characteristics.add(c);
	}
	
	public void removeCharacteristic(Characteristic c){
		characteristics.remove(c);
	}
	
	public boolean containsCharacteristic(Characteristic c){
		return characteristics.contains(c);
	}

	public void clearCharacteristics(){
		characteristics.clear();
	}


	@Override
	public void reset() {
		startDate = null;
		endDate = null;
		monitoringLocations.clear();
		characteristics.clear();
		resultStatuses.clear();
		exclude = null;
	}
	
	
}
