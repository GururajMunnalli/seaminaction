/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.session;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Redirect;

import com.tetratech.edas2.criteria.ActivitySearchCriteria;
import com.tetratech.edas2.model.Activity;
import com.tetratech.edas2.model.ActivityType;
import com.tetratech.edas2.model.Assemblage;
import com.tetratech.edas2.model.MonitoringLocation;
import com.tetratech.edas2.model.Organization;
import com.tetratech.edas2.model.ResultType;


// FIXME: proper error handling and success messages
import com.tetratech.edas2.util.Stopwatch;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.log.Log;
@Name("activityListEditor")
@Scope(ScopeType.CONVERSATION)
public class ActivityListEditor extends DataGridEditor<Activity> {

	@Logger private Log log;
	
	@In protected EntityManager entityManager;

	@In	protected FacesMessages facesMessages;

	@In(create = true) protected Redirect redirect;

	@In("activitySearchCriteria") protected ActivitySearchCriteria searchCriteria;

	private String addActivityTypeLabel;
	private String editActivityTypeLabel;

	private static final int FIELD_MEASUREMENT_RESULT_TYPE = 1;
	private static final int BENTHIC_MEASUREMENT_RESULT_TYPE = 2;

	private int resultType;

	@RequestParameter private Long mlocUid;

	@RequestParameter private String mlocName;

	private String mlocNameLabel;

	@Factory("newActivity")
	public Activity createActivity() {
		Activity a = new Activity();
		return a;
	}

	@Override
	public void init(Component component) {
		super.init(component);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@Override
	public void executeQuery() {
		try {
			Stopwatch stopWatch = new Stopwatch().start();
			setResults(
					applyCriteriaToQuery(entityManager.createNamedQuery("Activity.findByCriteria"))
					.setFirstResult(getOffset())
					.setMaxResults(getPageSize())
					.getResultList());
			log.debug("Query => Activity.findByCriteria; Elapsed => " + stopWatch.stop().getElapsedWithUnits());
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"There was an error executing the search. Please try again.");
		}
	}

	@Override
	public void executeCountQuery() {
		try {
			Stopwatch stopWatch = new Stopwatch().start();
			setTotalRecordCount(((Long)
					applyCriteriaToQuery(entityManager.createNamedQuery("Activity.findByCriteriaCount"))
					.getSingleResult()).longValue());
			log.debug("Query => Activity.findByCriteriaCount; Elapsed => " + stopWatch.stop().getElapsedWithUnits());
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"There was an error executing the search. Please try again.");
		}
	}
	
	@Override
	public void executeQueryCounts() {
		try {
			Stopwatch stopWatch = new Stopwatch().start();
			this.setResultChildCounts(
					applyCriteriaToQuery(entityManager.createNamedQuery("Activity.getResultCounts"))
					.setFirstResult(getOffset())
					.setMaxResults(getPageSize())
					.getResultList());
			log.debug("Query => Activity.getResultCounts; Elapsed => " + stopWatch.stop().getElapsedWithUnits());
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"There was an error executing the search. Please try again.");
		}
	}
	
	public void resetFilter() {
		searchCriteria.reset();
		applyFilter();
	}

	@Override
	public void editItem() {
		super.editItem();
		editActivityTypeLabel = selectedItem.getType() != null ? selectedItem.getType().getCode() : "";
	}

	@Override
	public void cancelEditItem() {
		super.cancelEditItem();
		editActivityTypeLabel = "";
	}

	public void saveItem() {
		try{
			selectedItem.setOrganization(findOrganization());
			//fix date issue
			selectedItem.setStartTime(selectedItem.getStartDate());
			selectedItem.setEndTime(selectedItem.getEndDate());
			//save activity
			entityManager.flush();
			leaveEditMode(selectedItem);
			editActivityTypeLabel = "";
			facesMessages.add("The activity " + selectedItem.getId() + " has been successfully saved.");
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while saving the Activity. Please try again.");
		}
	}

	public void addItem(Activity act) {
		try {
			//check validations
			boolean errors = false;
			ResultType rt = act.getResultType();
			if(rt==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,
				"Please select a result type.");
				errors = true;
			}
			if(act.getMedia()==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,
				"Please select a media.");
				errors = true;
			}
			
			if(errors==true){
				return;
			}
			
			act.setOrganization(findOrganization());
			act.setMonitoringLocation(findMonitoringLocation(searchCriteria.getMlocUid()));
			
			//set act type and assemblage based on result type
			Long uid = rt.getUid();
			if(uid == FIELD_MEASUREMENT_RESULT_TYPE){
				act.setType(findActivityType(new Long(1)));
				act.setAssemblage(null);
			}else if(uid == BENTHIC_MEASUREMENT_RESULT_TYPE){
				act.setType(findActivityType(new Long(36)));
				act.setAssemblage(findAssemblage(new Long(4)));
			}else
				throw new RuntimeException("Invalid Result Type");
			//fix date issue
			act.setStartTime(act.getStartDate());
			act.setEndTime(act.getEndDate());
			//save activity
			entityManager.persist(act);
			entityManager.flush();
			resetQuery();
			facesMessages.add("The activity " + act.getId() + " has been successfully added");
			Contexts.removeFromAllContexts("newActivity");
			addActivityTypeLabel = "";
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while saving the new Activity. Please try again.");
		}
	}

	public void deleteItem() {
		try{
			entityManager.remove(selectedItem);
			entityManager.flush();
			resetQuery();
			facesMessages.add("The activity " + selectedItem.getId() + " has been successfully deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while deleting the Activity. Please try again.");
		}
	}

	private void processRequestParams(){
		//update mlocUid to search the records
		//for as long as the user is on the screen
		if(mlocUid!=null && searchCriteria.getMlocUid()==0){
			searchCriteria.setMlocUid(mlocUid);
			//set the mloc Name label
			mlocNameLabel = mlocName;
		}
	}

	private Query applyCriteriaToQuery(Query query) {
		processRequestParams();
		return query
		.setParameter("id", searchCriteria.getId())
		.setParameter("samplingCollectionMethodName", searchCriteria.getSampleCollectionMethod())
		.setParameter("startDate", searchCriteria.getStartDate())
		.setParameter("startDate2", searchCriteria.getStartDate())
		.setParameter("endDate", searchCriteria.getEndDate())
		.setParameter("endDate2", searchCriteria.getEndDate())
		.setParameter("monitoringLocationUid",searchCriteria.getMlocUid())
		.setParameter("monitoringLocationUid2",searchCriteria.getMlocUid())
		.setParameter("sampleCollEquip",searchCriteria.getSamplingCollectionEquipment())
		.setParameter("sampleCollEquip2",searchCriteria.getSamplingCollectionEquipment())
		.setParameter("media",searchCriteria.getMedia())
		.setParameter("media2",searchCriteria.getMedia())
		.setParameter("resultType",searchCriteria.getResultType())
		.setParameter("resultType2",searchCriteria.getResultType());
	}

	private ActivityType findActivityType(Long actUid){
		ActivityType c = (ActivityType)entityManager.find(ActivityType.class,actUid);
		return c;
	}

	private Assemblage findAssemblage(Long assemUid){
		Assemblage a = (Assemblage)entityManager.find(Assemblage.class, assemUid);
		return a;
	}

	private Organization findOrganization(){
		return (Organization) entityManager
		.createNamedQuery("Organization.getReserved")
		.getSingleResult();
	}

	private MonitoringLocation findMonitoringLocation(Long mlocUid){
		MonitoringLocation ml = (MonitoringLocation)entityManager.find(MonitoringLocation.class,mlocUid);
		return ml;
	}

	public String forwardToResults(Activity a){
		ResultType rt = a.getResultType();
		if(rt!=null){
			Long uid = rt.getUid();
			if(uid == FIELD_MEASUREMENT_RESULT_TYPE){
				log.debug("forwarding to field measurement result screen ->");
				resetState();
				return "field";
			}else if(uid ==BENTHIC_MEASUREMENT_RESULT_TYPE){
				log.debug("forwarding to benthic measurement result screen ->");
				resetState();
				return "benthic";
			}else
				return null;
		}else
			return null;
	}

	public String back(){
		//reset state of search
		this.resultsStale = true;
		this.countStale = true;
		//clear search criteria
		searchCriteria.reset();
		searchCriteria.setMlocUid(new Long(0));
		return "back";
	}

	public String getAddActivityTypeLabel() {
		return addActivityTypeLabel;
	}

	public void setAddActivityTypeLabel(String addActivityTypeLabel) {
		this.addActivityTypeLabel = addActivityTypeLabel;
	}

	public String getEditActivityTypeLabel() {
		return editActivityTypeLabel;
	}

	public void setEditActivityTypeLabel(String editActivityTypeLabel) {
		this.editActivityTypeLabel = editActivityTypeLabel;
	}

	public int getResultType() {
		return resultType;
	}

	public void setResultType(int resultType) {
		this.resultType = resultType;
	}

	public String getMlocNameLabel() {
		return mlocNameLabel;
	}

	public void setMlocNameLabel(String mlocNameLabel) {
		this.mlocNameLabel = mlocNameLabel;
	}

}
