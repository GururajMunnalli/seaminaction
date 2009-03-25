/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.session;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Redirect;

import com.tetratech.edas2.criteria.BenthicMeasurementResultCriteria;
import com.tetratech.edas2.model.Activity;
import com.tetratech.edas2.model.FrequencyClassDescriptor;
import com.tetratech.edas2.model.Measurement;
import com.tetratech.edas2.model.MeasurementUnit;
import com.tetratech.edas2.model.Organization;
import com.tetratech.edas2.model.Result;
import com.tetratech.edas2.model.ResultFrequencyClass;
import com.tetratech.edas2.model.ResultStatus;
import com.tetratech.edas2.model.Taxon;
import com.tetratech.edas2.util.Stopwatch;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.log.Log;

// FIXME: proper error handling and success messages
@Name("benthicMsmntEditorPhase2")
@Scope(ScopeType.CONVERSATION)
public class BenthicMsmntEditorPhase2 extends DataGridEditor<Result> {
	@Logger private Log log;
	
	@In	protected EntityManager entityManager;

	@In	protected FacesMessages facesMessages;

	@In(create = true) protected Redirect redirect;

	@In("benthicMeasResultSearchCriteria") protected BenthicMeasurementResultCriteria searchCriteria;

	private String addTaxonLabel = null;
	private String editTaxonLabel = null;
	
	private FrequencyClassDescriptor addFreqClassDesc;
	private FrequencyClassDescriptor editFreqClassDesc;

	// FIXME: think of a cleaner way of tracking the following state
	@RequestParameter private Long actUid;

	@RequestParameter private String actId;

	@RequestParameter private Long mlocUid;

	@RequestParameter private String mlocName;

	@Out(required = false) private String actIdLabel = null;
	@Out(required = false) private Long mlocUidLabel = null;
	@Out(required = false) private String mlocNameLabel = null;

	private List<Taxon> taxonList;
	private List<ResultStatus> statusList;

	@Override
	public void init(Component component) {
		super.init(component);
		//taxonList = entityManager.createQuery("select t from Taxon t").getResultList();
		//statusList = entityManager.createQuery("select s from ResultStatus s").getResultList();
	}
	
	@Override
	public void prepareResults() {
		applyInitialCriteria();
		super.prepareResults();
	}

	@Factory("newBenthicMsmntPhase2")
	public Result createResult() {
		Result a = new Result();
		return a;
	}

	public void executeQuery() {
		try {
			Stopwatch stopWatch = new Stopwatch().start();
			setResults(
					applyCriteriaToQuery(entityManager.createNamedQuery("BenthicMeasurement.findByCriteria"))
					.setFirstResult(getOffset())
					.setMaxResults(getPageSize())
					.getResultList());
			log.debug("Query => BenthicMeasurement.findByCriteria; Elapsed => " + stopWatch.stop().getElapsedWithUnits());
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
					"There was an error executing the search. Please try again.");
		}
	}

	public void executeCountQuery() {
		try {
			Stopwatch stopWatch = new Stopwatch().start();
			setTotalRecordCount(((Long)
					applyCriteriaToQuery(entityManager.createNamedQuery("BenthicMeasurement.findByCriteriaCount"))
					.getSingleResult()).longValue());
			log.debug("Query => BenthicMeasurement.findByCriteriaCount; Elapsed => " + stopWatch.stop().getElapsedWithUnits());
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
					"There was an error executing the search. Please try again.");
		}
	}
	
	@Override
	public void executeQueryCounts() {
	}
	
	public void resetFilter() {
		searchCriteria.reset();
		applyFilter();
	}

	@Override
	public void editItemDetail() {
		super.editItemDetail();
		prepareSelectionsForEditing(itemDetails);
	}
		
	@Override
	public void saveItemDetail() {
		processSelectionsFromEditing(itemDetails);
		super.saveItemDetail();
	}
	
	@Override
	public void editItem() {
		super.editItem();
		prepareSelectionsForEditing(selectedItem);
	}

	public void saveItem() {
		try {
			
			Taxon t = resolveTaxon(editTaxonLabel);
			if(t==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,"The taxon entered doesn't exist. Please try again.");
				return;
			}
			
			selectedItem.setOrganization(findReservedOrganization());
			selectedItem.setTaxon(t);
			selectedItem.setPrimaryMeasurement(findMeasurement(selectedItem.getPrimaryMeasurement().getValue(),"count"));
			selectedItem.setResultFrequencyClass(mapFrequencyClassDescriptor(selectedItem,editFreqClassDesc));
			entityManager.flush();
			leaveEditMode(selectedItem);
			editTaxonLabel="";
			editFreqClassDesc=null;
			facesMessages.add("Benthic measurement has been successfully saved.");
		}catch(Exception e){
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
					"An error occured while saving the result. Please try again.");
		}
	}
	
	public void addItem(Result newBenthicMeasResult) {
		try {
			Taxon t = resolveTaxon(addTaxonLabel);
			if(t==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR, "The taxon entered doesn't exist. Please try again.");
				return;
			}
			
			newBenthicMeasResult.setOrganization(findReservedOrganization());
			newBenthicMeasResult.setActivity(entityManager.find(Activity.class, searchCriteria.getActUid()));
			newBenthicMeasResult.setTaxon(t);
			newBenthicMeasResult.setPrimaryMeasurement(findMeasurement(newBenthicMeasResult.getPrimaryMeasurement().getValue(),"count"));
			newBenthicMeasResult.setResultFrequencyClass(mapFrequencyClassDescriptor(newBenthicMeasResult,addFreqClassDesc));
			newBenthicMeasResult.setElevation(null);
			newBenthicMeasResult.setGroupSummaryCountWeight(null);
			entityManager.persist(newBenthicMeasResult);
			entityManager.flush();
			resetQuery();
			Contexts.removeFromAllContexts("newBenthicMeasResult");
			addTaxonLabel="";
			addFreqClassDesc=null;
			facesMessages.add("Benthic measurement has been successfully added.");
		} catch (InvalidStateException e) {
			InvalidValue[] values = e.getInvalidValues();
			for(int i=0;i<values.length;i++)
				System.out.println(values[i].getPropertyName()+" "+values[i].getMessage());
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while saving the new result. Please try again.");
		}
	}

	public void deleteItem() {
		try{
			ResultFrequencyClass rfc = selectedItem.getResultFrequencyClass();
			entityManager.remove(selectedItem);
			entityManager.flush();
			resetQuery();
			facesMessages.add("Benthic measurement has been successfully deleted.");
		}catch(Exception e){
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
					"An error occured while deleting the result. Please try again.");
		}
	}
	
	private void applyInitialCriteria() {
		//update actUid to search the records
		//for as long as the user is on the screen
		if (actUid != null && searchCriteria.getActUid() == 0) {
			searchCriteria.setActUid(actUid);
			//set the act Name label
			actIdLabel = actId;
			mlocUidLabel = mlocUid;
			mlocNameLabel = mlocName;
		}
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	private Query applyCriteriaToQuery(Query query) {
		return query
		.setParameter("status", searchCriteria.getStatus())
		.setParameter("status2", searchCriteria.getStatus())
		.setParameter("taxon",!StringUtils.isEmpty(searchCriteria.getTaxonomyName())?searchCriteria.getTaxonomyName():null)
		.setParameter("freqClassDesc",searchCriteria.getFreqClassDesc())
		.setParameter("freqClassDesc2",searchCriteria.getFreqClassDesc())
		.setParameter("actUid",searchCriteria.getActUid());
	}

	public String back(){
		//reset state of search
		this.resultsStale = true;
		this.countStale = true;
		//clear search criteria
		searchCriteria.reset();
		searchCriteria.setActUid(new Long(0));
		return "back";
	}

	private ResultFrequencyClass mapFrequencyClassDescriptor(Result r, FrequencyClassDescriptor desc){
		ResultFrequencyClass rfc = r.getResultFrequencyClass();
		if (desc != null) {
			if (rfc == null) {
				rfc = new ResultFrequencyClass();
				rfc.setResult(r);
				rfc.setOrganization(findReservedOrganization());
			}
			
			rfc.setFrequencyClassDescriptor(desc);
		}
		else if (rfc != null && entityManager.contains(rfc)) {
			entityManager.remove(rfc);
			rfc = null;
		}
		return rfc;
	}
	
	public List<MeasurementUnit> filterMeasurementUnit(Object event) {
		List<MeasurementUnit> matches = null;
		try {
			String pref = event.toString();
			matches = entityManager
			.createQuery("select mu from MeasurementUnit mu " +
					"where " +
			"lower(mu.code) like concat(concat('%',lower(:code)),'%') ")
			.setParameter("code", pref)
			.getResultList();
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
					"An error occured while processing your request. Please try again!");
			e.printStackTrace();
		}
		return matches;
	}

	public List<Taxon> filterTaxon(Object event) {
		long t1 = System.currentTimeMillis();
		List<Taxon> matches = null;
		try {
			String pref = event.toString();
			matches = entityManager
			.createQuery("select t from Taxon t " +
					"where " +
			"lower(t.name) like concat(concat(lower(:name)),'%') ")
			.setParameter("name", pref.trim())
			.getResultList();
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
					"An error occured while processing your request. Please try again!");
			e.printStackTrace();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2-t1+" ms");
		System.out.println("MATCHES: "+matches.size()+" "+event.toString());
		return matches;
	}


	private Organization findReservedOrganization() {
		return (Organization) entityManager
			.createNamedQuery("Organization.getReserved")
			.getSingleResult();
	}

	private Measurement findMeasurement(String value, String code){
		if(StringUtils.isEmpty(value)||StringUtils.isEmpty(code))
			return new Measurement();

		List<MeasurementUnit> mu = entityManager.createQuery("select mu from MeasurementUnit mu where mu.code = :code")
		.setParameter("code", code)
		.getResultList();

		return mu.size()!=0?new Measurement(value,mu.get(0)):new Measurement();
	}

	private Taxon resolveTaxon(String name){
		if(StringUtils.isEmpty(name))
			return null;

		List<Taxon> c = entityManager.createQuery("select t from Taxon t where t.name = :name")
		.setParameter("name", name)
		.getResultList();

		return c.size()!=0?c.get(0):null;
	}

	public String getAddTaxonLabel() {
		return addTaxonLabel;
	}

	public void setAddTaxonLabel(String addTaxonLabel) {
		this.addTaxonLabel = addTaxonLabel;
	}

	public String getEditTaxonLabel() {
		return editTaxonLabel;
	}

	public void setEditTaxonLabel(String editTaxonLabel) {
		this.editTaxonLabel = editTaxonLabel;
	}

	public String getActIdLabel() {
		return actIdLabel;
	}

	public void setActIdLabel(String actIdLabel) {
		this.actIdLabel = actIdLabel;
	}

	public Long getMlocUidLabel() {
		return mlocUidLabel;
	}

	public void setMlocUidLabel(Long mlocUidLabel) {
		this.mlocUidLabel = mlocUidLabel;
	}

	public String getMlocNameLabel() {
		return mlocNameLabel;
	}

	public void setMlocNameLabel(String mlocNameLabel) {
		this.mlocNameLabel = mlocNameLabel;
	}

	public FrequencyClassDescriptor getAddFreqClassDesc() {
		return addFreqClassDesc;
	}

	public void setAddFreqClassDesc(FrequencyClassDescriptor addFreqClassDesc) {
		this.addFreqClassDesc = addFreqClassDesc;
	}

	public FrequencyClassDescriptor getEditFreqClassDesc() {
		return editFreqClassDesc;
	}

	public void setEditFreqClassDesc(FrequencyClassDescriptor editFreqClassDesc) {
		this.editFreqClassDesc = editFreqClassDesc;
	}
	
	private void prepareSelectionsForEditing(Result record) {
		editTaxonLabel = null;
		if (record.getTaxon() != null) {
			editTaxonLabel = record.getTaxon().getName();
		}
		
		editFreqClassDesc = null;
		if (record.getResultFrequencyClass() != null) {
			editFreqClassDesc = record.getResultFrequencyClass().getFrequencyClassDescriptor();
		}
	}

	private void processSelectionsFromEditing(Result record) {
		if (!StringUtils.isEmpty(editTaxonLabel)) {
			record.setTaxon(resolveTaxon(editTaxonLabel));
		}
		else {
			record.setTaxon(null);
		}
		
		if (record.getPrimaryMeasurement().getValue() != null) {
			record.setPrimaryMeasurement(findMeasurement(record.getPrimaryMeasurement().getValue(), "count"));
		}
		else {
			record.resetPrimaryMeasurement();
		}
		
		editTaxonLabel = null;
		editFreqClassDesc = null;
	}
	
	private void processLabelsFromAdding(Result record) {
		if (!StringUtils.isEmpty(addTaxonLabel)) {
			record.setTaxon(resolveTaxon(addTaxonLabel));
		}
		
		if (record.getPrimaryMeasurement().getValue() != null) {
			record.setPrimaryMeasurement(findMeasurement(record.getPrimaryMeasurement().getValue(), "count"));
		}
		else {
			record.resetPrimaryMeasurement();
		}
		
		record.setElevation(new Measurement());
		
		addTaxonLabel = null;
		addFreqClassDesc = null;
	}
}

