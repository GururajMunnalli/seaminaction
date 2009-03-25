/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.session;

import com.tetratech.edas2.model.County;
import com.tetratech.edas2.model.HorizontalCollectionMethod;
import com.tetratech.edas2.model.HorizontalReferenceDatum;
import com.tetratech.edas2.model.MonitoringLocation;
import com.tetratech.edas2.model.MonitoringLocationType;
import com.tetratech.edas2.model.Organization;
import com.tetratech.edas2.criteria.MonitoringLocationSearchCriteria;
import com.tetratech.edas2.util.Stopwatch;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.contexts.Contexts;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.Redirect;

// FIXME: proper error handling and success messages
import org.jboss.seam.log.Log;
@Name("mlocListEditor")
@Scope(ScopeType.CONVERSATION)
public class MonitoringLocationListEditor extends DataGridEditor<MonitoringLocation> {

	@Logger private Log log;

	@In	protected EntityManager entityManager;

	@In	protected FacesMessages facesMessages;

	@In(create = true) protected Redirect redirect;

	@In("mlocSearchCriteria") protected MonitoringLocationSearchCriteria searchCriteria;

	private String addCountyLabel;
	private String editCountyLabel;

	@Factory("newMonitoringLocation")
	public MonitoringLocation createMonitoringLocation() {
		MonitoringLocation mloc = new MonitoringLocation();
		mloc.setOrganization(findReservedOrganization());
		return mloc;
	}

	@Override
	public void init(Component component) {
		super.init(component);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void executeQuery() {
		try {
			Stopwatch stopWatch = new Stopwatch().start();
			setResults(
					applyCriteriaToQuery(entityManager.createNamedQuery("MonitoringLocation.findByCriteria"))
					.setFirstResult(getOffset())
					.setMaxResults(getPageSize())
					.getResultList());
			log.debug("Query => MonitoringLocation.findByCriteria; Elapsed => " + stopWatch.stop().getElapsedWithUnits());
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"There was an error executing the search. Please try again.");
		}
	}

	public void executeCountQuery() {
		try {
			Stopwatch stopWatch = new Stopwatch().start();
			setTotalRecordCount(((Long)
					applyCriteriaToQuery(entityManager.createNamedQuery("MonitoringLocation.findByCriteriaCount"))
					.setFirstResult(getOffset())
					.setMaxResults(getPageSize())
					.getSingleResult()).longValue());
			log.debug("Query => MonitoringLocation.findByCriteriaCount; Elapsed => " + stopWatch.stop().getElapsedWithUnits());
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"There was an error executing the search. Please try again.");
		}
	}

	@Override
	public void executeQueryCounts() {
		try {
			Stopwatch stopWatch = new Stopwatch().start();
			this.setResultChildCounts(
					applyCriteriaToQuery(entityManager.createNamedQuery("MonitoringLocation.getActivityCounts"))
					.setFirstResult(getOffset())
					.setMaxResults(getPageSize())
					.getResultList());
			log.debug("Query => MonitoringLocation.getActivityCounts; Elapsed => " + stopWatch.stop().getElapsedWithUnits());
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"There was an error executing the search. Please try again.");
		}
	}

	public void resetFilter() {
		searchCriteria.reset();
		applyFilter();
	}

	@Override
	public void editItemDetail() {
		super.editItemDetail();
		editCountyLabel = buildCountyStateLabel(itemDetails);
	}

	@Override
	public void cancelEditItemDetail() {
		super.cancelEditItemDetail();
		editCountyLabel = "";
	}

	@Override
	public void saveItemDetail() {
		itemDetails.setCounty(findCounty(editCountyLabel));
		itemDetails.setState(itemDetails.getCounty().getState());
		editCountyLabel = "";
		super.saveItemDetail();
	}

	@Override
	public void editItem() {
		super.editItem();
		editCountyLabel = buildCountyStateLabel(selectedItem);
	}

	@Override
	public void cancelEditItem() {
		super.cancelEditItem();
		editCountyLabel = "";
	}

	public void saveItem() {
		try{
			County c = findCounty(editCountyLabel);
			if(c==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,"Couldn't find the county you entered.");
				return;
			}

			selectedItem.setCounty(c);
			selectedItem.setState(selectedItem.getCounty()!=null?selectedItem.getCounty().getState():null);
			editCountyLabel = "";
			entityManager.flush();
			leaveEditMode(selectedItem);
			facesMessages.add("The monitoring location " + selectedItem.getName() + " has been successfully saved.");
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while saving the monitoring location. Please try again.");
		}
	}

	public void addItem(MonitoringLocation mloc) {
		try {
			County c = findCounty(addCountyLabel);
			if(c==null){
				facesMessages.add(FacesMessage.SEVERITY_ERROR,"Couldn't find the county you entered.");
				return;
			}

			mloc.setOrganization(findReservedOrganization());
			mloc.setCounty(c);
			if (mloc.getCounty() != null) {
				mloc.setState(mloc.getCounty().getState());
			}
			mloc.setType(findMonitoringLocationType());
			mloc.setHorizontalCollectionMethod(findHorizontalCollectionMethod());
			mloc.setHorizontalReferenceDatum(findHorizontalReferenceDatum());
			entityManager.persist(mloc);
			entityManager.flush();
			resetQuery();
			facesMessages.add("The monitoring location " + mloc.getName() + " has been successfully added.");
			Contexts.removeFromAllContexts("newMonitoringLocation");
			addCountyLabel = "";
		} catch (Exception e) {
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while saving the new monitoring location. Please try again.");
		}
	}

	public void deleteItem() {
		try{
			entityManager.remove(selectedItem);
			entityManager.flush();
			resetQuery();
			facesMessages.add("The monitoring location " + selectedItem.getName() + " has been successfully deleted.");
		}catch(Exception e){
			e.printStackTrace();
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while deleting the monitoring location. Please try again.");
		}
	}


	public String forwardToActivities(){
		resetState();
		return "activities";
	}
	
	private String buildCountyStateLabel(MonitoringLocation mloc) {
		if (mloc.getCounty() == null) {
			return null;
		}
		return mloc.getCounty().getName() + " - " + mloc.getCounty().getState().getAbbreviation();
	}

	private Query applyCriteriaToQuery(Query query) {
		String county=null;
		String state=null;
		String countystate = searchCriteria.getCounty();
		if(!StringUtils.isEmpty(countystate)){
			String[] parts = countystate.split("-");
			county = parts[0].trim();
			state = parts.length==2?parts[1].trim():null;
		}

		return query
		.setParameter("id", searchCriteria.getId())
		.setParameter("name", searchCriteria.getName())
		.setParameter("huc8", searchCriteria.getHuc8())
		.setParameter("county", county)
		.setParameter("state", state);
	}

	public List<County> filterCounty(Object event) {
		List<County> matches = null;
		try {
			String partial = event.toString().replaceFirst(" - .*$", "");
			matches = entityManager
			.createQuery("select c from County c join fetch c.state where c.retiredBy is null and lower(c.name) like '" + partial.
					trim().toLowerCase() + "%' order by c.name asc, c.state.abbreviation asc")
					.getResultList();
		} catch (Exception e) {
			facesMessages.add(FacesMessage.SEVERITY_WARN,
			"An error occured while processing your request. Please try again!");
			e.printStackTrace();
		}
		return matches;
	}

	private County findCounty(String countyName) {
		if (countyName == null || countyName.trim().equals("")) {
			return null;
		}
		String[] countyParts = countyName.trim().split(" - ");
		//check string
		if(countyParts.length!=2)
			return null;

		List<County> cs = entityManager
		.createQuery("select c from County c where c.name = :county and c.state.abbreviation = :state")
		.setParameter("county", countyParts[0])
		.setParameter("state", countyParts[1])
		.getResultList();

		return cs.size()!=0?cs.get(0):null;
	}

	private Organization findReservedOrganization() {
		return (Organization) entityManager
		.createNamedQuery("Organization.getReserved")
		.getSingleResult();
	}

	private MonitoringLocationType findMonitoringLocationType(){
		MonitoringLocationType mlType = entityManager.find(MonitoringLocationType.class, new Long(32));
		return mlType;
	}

	private HorizontalCollectionMethod findHorizontalCollectionMethod(){
		HorizontalCollectionMethod hcm = entityManager.find(HorizontalCollectionMethod.class, new Long(28));
		return hcm;
	}

	private HorizontalReferenceDatum findHorizontalReferenceDatum(){
		HorizontalReferenceDatum hrd = entityManager.find(HorizontalReferenceDatum.class, new Long(2));
		return hrd;
	}

	public String getAddCountyLabel() {
		return addCountyLabel;
	}

	public void setAddCountyLabel(String addCountyLabel) {
		this.addCountyLabel = addCountyLabel;
	}

	public String getEditCountyLabel() {
		return editCountyLabel;
	}

	public void setEditCountyLabel(String editCountyLabel) {
		this.editCountyLabel = editCountyLabel;
	}


}
