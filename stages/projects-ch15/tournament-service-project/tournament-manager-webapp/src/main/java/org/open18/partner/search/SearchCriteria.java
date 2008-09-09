package org.open18.partner.search;

import java.io.Serializable;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Scope;

@Scope(ScopeType.CONVERSATION)
public class SearchCriteria implements Serializable {

	private String searchString;
	private int pageSize = 5;
	private int page = 0;
	private String sortBy = "name";

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSearchPattern() {
		return buildLikePattern(searchString);
	}
	
	public void first() {
		page = 0;
	}
	
	public void previous() {
		page--;
	}
	
	public void next() {
		page++;
	}
	
	public boolean isPreviousExists() {
		return page > 0;
	}
	
	public boolean isNextExists(Integer numResults) {
		return numResults != null && numResults.equals(pageSize);
	}
	
	public boolean isPaginationNeeded(Integer numResults) {
		return isPreviousExists() || isNextExists(numResults);
	}
	
	private String buildLikePattern(String search) {
		if (search == null || search.trim().length() == 0) {
			return "%";
		} else {
			return "%".concat(search.replace('*', '%').toLowerCase()).concat("%");
		}
	}
}
