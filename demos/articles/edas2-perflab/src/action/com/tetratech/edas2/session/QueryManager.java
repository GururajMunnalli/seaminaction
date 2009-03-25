/*
 * EDAS2 - TetraTech, Inc.
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 */
package com.tetratech.edas2.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.log.Log;

// TODO: might be nice to have the concept of the entity class being managed
// TODO: should probably make SearchCriteria object part of class
public abstract class QueryManager<T> {
	@Logger private Log log;
	private int pageSize = 50;
	private int page = 0;
	private long recordCount = 0;
	private long totalRecordCount = 0;
	protected boolean resultsStale = true;
	protected boolean countStale = true;
	@DataModel protected List<T> results = new ArrayList<T>();
	@DataModelSelection protected T selectedItem;
	@Out(required = false, scope = ScopeType.PAGE) private PaginationState paginationState;

	protected Map<Long, Long> resultChildCounts = new HashMap<Long,Long>();
	
	private String componentName;
	
	@Create
	public void init(Component component) {
		componentName = component.getName();
	}

	public void prepareResults() {
		if (log.isDebugEnabled()) {
			log.debug("Preparing results for #0", componentName);
		}
		if (resultsStale) {
			if (log.isDebugEnabled()) {
				log.debug("Refetching result list for #0", componentName);
			}
			executeQuery();
			executeQueryCounts();
			resultsStale = false;
		}
		if (countStale) {
			if (log.isDebugEnabled()) {
				log.debug("Refetching result count for #0", componentName);
			}
			executeCountQuery();
			countStale = false;
		}
		paginationState = new PaginationState();
		paginationState.setStart(getStart());
		paginationState.setEnd(getEnd());
		paginationState.setPreviousAvailable(isPreviousAvailable());
		paginationState.setNextAvailable(isNextAvailable());
		paginationState.setTotalRecordCount(getTotalRecordCount());
	}
	
	public abstract void executeQuery();
	
	public abstract void executeCountQuery();
	
	public abstract void executeQueryCounts();
	
	public abstract void resetFilter();
	
	public abstract EntityManager getEntityManager();
	
	public void applyFilter() {
		resetQuery();
	}
	
	public void next() {
		page++;
		resultsStale = true;
	}
	
	public void previous() {
		if (page > 0) {
			page--;
			resultsStale = true;
		}
	}
	
	public void first() {
		if (page > 0) {
			page = 0;
			resultsStale = true;
		}
	}
	
	public boolean isNextAvailable() {
		return ((page + 1) * pageSize) < totalRecordCount;
	}
	
	public boolean isPreviousAvailable() {
		return totalRecordCount > 0 && page > 0;
	}
	
	public void resetQuery() {
		first();
		resultsStale = true;
		countStale = true;
	}
	
	public void resetState(){
		resultsStale = true;
		countStale = true;
	}
	
	public long getStart() {
		return totalRecordCount != 0 ? (page * pageSize + 1) : 0;
	}
	
	public long getEnd() {
		return page * pageSize + recordCount;
	}
		
	public int getOffset() {
		return page * pageSize;
	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if (this.page != page) {
			this.page = page;
			resultsStale = true;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if (this.pageSize != pageSize) {
			this.pageSize = pageSize;
			resultsStale = true;
		}
	}

	public long getRecordCount() {
		return recordCount;
	}

	public long getTotalRecordCount() {
		return totalRecordCount;
	}

	protected void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

	public List<T> getResults() {
		return results;
	}
	
	protected void setResults(List<T> results) {
		this.results = results;
		this.recordCount = results.size();
	}

	public T getSelectedItem() {
		return selectedItem;
	}
	
	public int getIndexOfSelectedItem() {
		return results.indexOf(selectedItem);
	}

	public void pageSizeChanged(ValueChangeEvent event) {
		if (log.isDebugEnabled()) {
			log.debug("Detected page size change from #0 to #1", event.getOldValue(), event.getNewValue());
		}
		if (event.getNewValue() != null) {
			setPageSize((Integer)event.getNewValue());
			first();
		}
	}

	public Map<Long, Long> getResultChildCounts() {
		return resultChildCounts;
	}
	
	public Long getResultChildCount(Long key){
		return resultChildCounts.get(key);
	}

	public void setResultChildCounts(List list) {
		resultChildCounts = new HashMap<Long, Long>();
		for (int i = 0; i < list.size(); i++) {
			Object[] o = (Object[]) list.get(i);
			Long key = (Long) o[0];
			Long value = (Long) o[1];
			resultChildCounts.put(key, value);
		}
	}
	
}
