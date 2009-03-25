/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.session;

import com.tetratech.edas2.model.Characteristic;
import com.tetratech.edas2.model.Measurement;
import com.tetratech.edas2.model.MeasurementUnit;
import com.tetratech.edas2.model.Organization;
import com.tetratech.edas2.model.Result;
import com.tetratech.edas2.criteria.FieldMeasurementResultCriteria;

import com.tetratech.edas2.model.Activity;
import java.util.Collections;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidStateException;
import org.hibernate.validator.InvalidValue;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Redirect;

// FIXME: proper error handling and success messages
import org.jboss.seam.log.Log;
@Name("fieldMeasResultListEditor")
@Scope(ScopeType.CONVERSATION) // this could be page scoped
public class FieldMeasurementResultListEditor extends DataGridEditor<Result> {

	@Logger private Log log;

	@In	protected EntityManager entityManager;

	@In	protected FacesMessages facesMessages;

	@In(create = true) protected Redirect redirect;

	@In("fieldMeasResultSearchCriteria") protected FieldMeasurementResultCriteria searchCriteria;

	private String addPrimaryMeasurementMsUnitLabel = null;
	private String editPrimaryMeasurementMsUnitLabel = null;

	private String addElevationMsUnitLabel = null;
	private String editElevationMsUnitLabel = null;

	private String addCharacteristicLabel = null;
	private String editCharacteristicLabel = null;

	@RequestParameter private Long actUid;

	@RequestParameter private String actId;

	@RequestParameter private Long mlocUid;

	@RequestParameter private String mlocName;

	private String actIdLabel = null;
	private Long mlocUidLabel = null;
	private String mlocNameLabel = null;

	@Factory("newFieldMeasResult")
	public Result createResult() {
		Result r = new Result();
		return r;
	}

	@Override
	public void init(Component component) {
		super.init(component);
		//search based on actUid
		//searchCriteria.setActUid(actUid!=null?actUid:0L);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public void executeQuery() {
		try {
			long t1 = System.currentTimeMillis();
			setResults(
					applyCriteriaToQuery(entityManager.createNamedQuery("FieldMeasurement.findByCriteria"))
					.setFirstResult(getOffset())
					.setMaxResults(getPageSize())
					.getResultList());
			System.out.println("Last query took " + (System.currentTimeMillis() - t1) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"There was an error executing the search. Please try again.");
		}
	}

	@Override
	public void executeCountQuery() {
		try {
			long t1 = System.currentTimeMillis();
			setTotalRecordCount(((Long)
					applyCriteriaToQuery(entityManager.createNamedQuery("FieldMeasurement.findByCriteriaCount"))
					.getSingleResult()).longValue());
			System.out.println("Last query took " + (System.currentTimeMillis() - t1) + "ms");
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
		prepareLabelsForEditing(itemDetails);
	}

	@Override
	public void saveItemDetail() {
		try{
			processLabelsFromEditing(itemDetails);
			super.saveItemDetail();
		}catch(Exception e){
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while saving the result. Please try again.");
		}
	}

	@Override
	public void cancelEditItem() {
		super.cancelEditItem();
		editCharacteristicLabel = "";
		editPrimaryMeasurementMsUnitLabel = "";
	}

	@Override
	public void editItem() {
		super.editItem();
		prepareLabelsForEditing(selectedItem);
	}

	@Override
	public void addItem(Result item) {
		try {
			Characteristic c = resolveCharacteristic(addCharacteristicLabel);
			MeasurementUnit mum = resolveMeasurementUnit(this.addPrimaryMeasurementMsUnitLabel);
			MeasurementUnit mue = resolveMeasurementUnit(this.addElevationMsUnitLabel);
			
			boolean errors = false;
			if(c==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,
				"Couldn't find the characteristic you entered. Please try again.");
				errors = true;
			}
			if(mum==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,
				"Couldn't find the measurement unit you entered for measure. Please try again.");
				errors = true;
			}
			if(!StringUtils.isEmpty(this.addElevationMsUnitLabel)&&mue==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,
				"Couldn't find the measurement unit you entered for elevation. Please try again.");
				errors = true;
			}
			
			if(errors==true){
				return;
			}

			item.setOrganization(findReservedOrganization());
			item.setActivity(entityManager.find(Activity.class, searchCriteria.getActUid()));
			item.setCharacteristic(c);
			item.setPrimaryMeasurement(produceMeasurement(item.getPrimaryMeasurement().getValue(),mum));
			item.setElevation(produceMeasurement(item.getElevation().getValue(),mue));
			//processLabelsFromAdding(item);
			entityManager.persist(item);
			entityManager.flush();
			resetQuery();
			Contexts.removeFromAllContexts("newFieldMeasResult");
			resetAddFields();
			facesMessages.add("Field measurement has been successfully added.");
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while saving the new result. Please try again.");
		}
	}

	public void saveItem() {
		log.info("SAVING ITEM ");
		try {
			
			Characteristic c = resolveCharacteristic(editCharacteristicLabel);
			MeasurementUnit mum = resolveMeasurementUnit(editPrimaryMeasurementMsUnitLabel);
			MeasurementUnit mue = resolveMeasurementUnit(editElevationMsUnitLabel);
			
			boolean errors = false;
			if(c==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,
				"Couldn't find the characteristic you entered. Please try again.");
				errors = true;
			}
			if(mum==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,
				"Couldn't find the measurement unit you entered for measure. Please try again.");
				errors = true;
			}
			if(!StringUtils.isEmpty(this.addElevationMsUnitLabel)&&mue==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,
				"Couldn't find the measurement unit you entered for elevation. Please try again.");
				errors = true;
			}
			
			if(errors==true){
				return;
			}
			
			selectedItem.setOrganization(findReservedOrganization());
			selectedItem.setCharacteristic(c);
			selectedItem.setPrimaryMeasurement(produceMeasurement(selectedItem.getPrimaryMeasurement().getValue(),mum));
			selectedItem.setElevation(produceMeasurement(selectedItem.getElevation().getValue(),mue));
			//processLabelsFromEditing(selectedItem);
			entityManager.flush();
			leaveEditMode(selectedItem);
			resetEditFields();
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while saving the result. Please try again.");
		}
	}

	public void deleteItem() {
		log.info("DELETING ITEM " + selectedItem);
		try {
			entityManager.remove(selectedItem);
			entityManager.flush();
			resetQuery();
			facesMessages.add("Field measurement has been successfully deleted.");
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while deleting the result. Please try again.");
		}
	}

	private void processRequestParams() {
		log.info("ACT ID " + actId);
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

	private Query applyCriteriaToQuery(Query query) {
		processRequestParams();
		return query
		.setParameter("valueType",searchCriteria.getValueType())
		.setParameter("detectionCondition",searchCriteria.getDetectionCondition())
		.setParameter("measurementQualifier",searchCriteria.getMeasurementQualifier())
		.setParameter("status",searchCriteria.getStatus())
		.setParameter("characteristic",searchCriteria.getCharacteristicName())
		.setParameter("valueType2",searchCriteria.getValueType())
		.setParameter("detectionCondition2",searchCriteria.getDetectionCondition())
		.setParameter("measurementQualifier2",searchCriteria.getMeasurementQualifier())
		.setParameter("status2",searchCriteria.getStatus())
		.setParameter("actUid",searchCriteria.getActUid());
	}

	public String back() {
		//reset state of search
		this.resultsStale = true;
		this.countStale = true;
		//clear search criteria
		searchCriteria.reset();
		searchCriteria.setActUid(new Long(0));
		return "back";
	}

	public List<MeasurementUnit> filterMeasurementUnit(Object partialCode) {
		List<MeasurementUnit> matches = Collections.<MeasurementUnit>emptyList();
		try {
			matches = entityManager
			.createNamedQuery("MeasurementUnit.findFuzzyMatch")
			.setParameter("partialCode", partialCode.toString().toLowerCase())
			.getResultList();
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while processing your request. Please try again!");
			e.printStackTrace();
		}
		return matches;
	}

	public List<Characteristic> filterCharacteristic(Object partialName) {
		List<Characteristic> matches = Collections.<Characteristic>emptyList();
		try {
			matches = entityManager
			.createNamedQuery("Characteristic.findFuzzyMatch")
			.setParameter("partialName", partialName.toString().toLowerCase())
			.getResultList();
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while processing your request. Please try again!");
			e.printStackTrace();
		}
		return matches;
	}

	private Organization findReservedOrganization() {
		return (Organization) entityManager
		.createNamedQuery("Organization.getReserved")
		.getSingleResult();
	}

	private Measurement produceMeasurement(String value,MeasurementUnit mu) throws Exception{
		if (StringUtils.isEmpty(value) || mu == null) {
			return new Measurement();
		}
		else{
			return new Measurement(value, mu);
		}

	}
	
	private Measurement produceMeasurement(String value, String code) throws Exception{
		if (StringUtils.isEmpty(value) || StringUtils.isEmpty(code)) {
			return new Measurement();
		}

		try {
			MeasurementUnit mu = resolveMeasurementUnit(code);
			return mu!=null?new Measurement(value, mu):new Measurement();
		}
		catch (Exception e) {
			throw new Exception(e);
		}
	}

	private MeasurementUnit resolveMeasurementUnit(String code) throws Exception{
		if (StringUtils.isEmpty(code)) {
			return null;
		}

		try {
			List<MeasurementUnit> mu = entityManager
			.createNamedQuery("MeasurementUnit.findByCode")
			.setParameter("code", code)
			.getResultList();

			return mu.size()!=0?mu.get(0):null;
		}
		catch (Exception e) {
			throw new Exception(e);
		}
	}

	private Characteristic resolveCharacteristic(String name) throws Exception {
		if (StringUtils.isEmpty(name)) {
			return null;
		}

		try {
			List<Characteristic> cs = entityManager
			.createNamedQuery("Characteristic.findByName")
			.setParameter("name", name)
			.getResultList();

			return cs.size()!=0?cs.get(0):null;
		}		
		catch (Exception e) {
			throw new Exception(e);
		}
	}

	public String getAddPrimaryMeasurementMsUnitLabel() {
		return addPrimaryMeasurementMsUnitLabel;
	}

	public void setAddPrimaryMeasurementMsUnitLabel(String label) {
		this.addPrimaryMeasurementMsUnitLabel = label;
	}

	public String getEditPrimaryMeasurementMsUnitLabel() {
		return editPrimaryMeasurementMsUnitLabel;
	}

	public void setEditPrimaryMeasurementMsUnitLabel(String label) {
		this.editPrimaryMeasurementMsUnitLabel = label;
	}

	public String getAddElevationMsUnitLabel() {
		return addElevationMsUnitLabel;
	}

	public void setAddElevationMsUnitLabel(String label) {
		this.addElevationMsUnitLabel = label;
	}

	public String getEditElevationMsUnitLabel() {
		return editElevationMsUnitLabel;
	}

	public void setEditElevationMsUnitLabel(String label) {
		this.editElevationMsUnitLabel = label;
	}

	public String getAddCharacteristicLabel() {
		return addCharacteristicLabel;
	}

	public void setAddCharacteristicLabel(String label) {
		this.addCharacteristicLabel = label;
	}

	public String getEditCharacteristicLabel() {
		return editCharacteristicLabel;
	}

	public void setEditCharacteristicLabel(String label) {
		this.editCharacteristicLabel = label;
	}

	public String getActIdLabel() {
		return actIdLabel;
	}

	public void setActIdLabel(String actIdLabel) {
		this.actIdLabel = actIdLabel;
	}

	public String getMlocNameLabel() {
		return mlocNameLabel;
	}

	public void setMlocNameLabel(String mlocNameLabel) {
		this.mlocNameLabel = mlocNameLabel;
	}

	public Long getMlocUidLabel() {
		return mlocUidLabel;
	}

	private void prepareLabelsForEditing(Result record) {
		editCharacteristicLabel = null;
		if (record.getCharacteristic() != null) {
			editCharacteristicLabel = record.getCharacteristic().getName();
		}

		editPrimaryMeasurementMsUnitLabel = null;
		if (record.getPrimaryMeasurement() != null && record.getPrimaryMeasurement().getUnit() != null) {
			editPrimaryMeasurementMsUnitLabel = record.getPrimaryMeasurement().getUnit().getCode();
		}

		editElevationMsUnitLabel = null;
		if (record.getElevation() != null && record.getElevation().getUnit() != null) {
			editElevationMsUnitLabel = record.getElevation().getUnit().getCode();
		}
	}

	private void processLabelsFromEditing(Result record) throws Exception {
		if (!StringUtils.isEmpty(editCharacteristicLabel)) {
			record.setCharacteristic(resolveCharacteristic(editCharacteristicLabel));
		}
		else {
			record.setCharacteristic(null);
		}

		if (record.getPrimaryMeasurement().getValue() != null) {
			record.setPrimaryMeasurement(produceMeasurement(record.getPrimaryMeasurement().getValue(), editPrimaryMeasurementMsUnitLabel));
		}
		else {
			record.resetPrimaryMeasurement();
		}

		if (record.getElevation().getValue() != null) {
			record.setElevation(produceMeasurement(record.getElevation().getValue(), editElevationMsUnitLabel));
		}
		else {
			record.resetElevation();
		}

		resetEditFields();
	}

	private void resetEditFields(){
		editCharacteristicLabel = null;
		editPrimaryMeasurementMsUnitLabel = null;
		editElevationMsUnitLabel = null;
	}

	private void processLabelsFromAdding(Result record) throws Exception{
		if (!StringUtils.isEmpty(addCharacteristicLabel)) {
			record.setCharacteristic(resolveCharacteristic(addCharacteristicLabel));
		}

		if (record.getPrimaryMeasurement().getValue() != null) {
			record.setPrimaryMeasurement(produceMeasurement(record.getPrimaryMeasurement().getValue(), addPrimaryMeasurementMsUnitLabel));
		}
		else {
			record.resetPrimaryMeasurement();
		}

		record.setElevation(new Measurement());

		resetAddFields();
	}

	private void resetAddFields(){
		addCharacteristicLabel = null;
		addPrimaryMeasurementMsUnitLabel = null;
		addElevationMsUnitLabel = null;
	}

}
