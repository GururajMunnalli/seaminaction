package org.open18.action;

import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.ScopeType;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Log;
import org.open18.model.Round;

@Name("roundList")
@Scope(ScopeType.CONVERSATION)
public class RoundList extends EntityQuery {
	
	@Logger Log log;
	
	private static final String[] RESTRICTIONS = {
		"lower(golfer.lastName) like concat(lower(#{roundList.round.golfer.lastName}),'%')",
        "lower(golfer.firstName)like concat(lower(#{roundList.round.golfer.firstName}),'%')",
 		"lower(teeSet.color) = lower(#{roundExample.teeSet.color})",
 		"lower(r.weather) = lower(#{roundExample.weather})",
 		"r.date >= #{roundExample.date}",
        "r.totalScore <= #{roundExample.totalScore}",};

	@In(value="#{roundExample}")
    private Round round;
	
	//for sorting
	private String sortColumn="date";
	private String descending="desc";
	

	//for determining total number of records in this table
	private Long roundCount;
	
	private static final String EJBQL="select r from Round r join fetch r.golfer golfer"+
	          " join fetch r.teeSet teeSet"+
	          " join fetch teeSet.course course";
	
	@Override
	public String getEjbql() {
		return EJBQL;
	}
	
	@Override
	public Integer getMaxResults() {
		return 50;
	}
	
	@Override
	public String getOrder(){
		return sortColumn +" "+descending;
	}

	@Override
	public List<String> getRestrictions() {
		return Arrays.asList(RESTRICTIONS);
	}

	public Round getround(){
		return round;
	}

	public Long getRoundCount(){
	   Object o = this.getEntityManager().createQuery("select count(*) from Round as r").getSingleResult();
	   return (Long)o;
	}
	public String getSortColumn() {
		return this.sortColumn;
	}

	public void setSortColumn(String sort) {
		log.info("setting sortcolum to "+sort);
		if (!sort.equals(this.sortColumn)){
			this.sortColumn = sort;
			this.sort(this.sortColumn,descending.equals("asc"));
		}
	}
	
	public boolean isDescending() {
		return (descending.equals("desc"));
	}

	public void setDescending(boolean desc) {
		log.info("setting Descending to "+desc);
		if (this.descending.equals("asc")){
			this.descending="desc";
		}
			else this.descending = "asc"; 
		this.sort(this.sortColumn,this.descending.equals("asc"));
	}
	
	public void sort(final String column, final boolean descending){
		log.info("setting sort to "+sortColumn + " desc="+descending);
		setOrder(sortColumn+" "+this.descending);
		this.setEjbql(EJBQL);
	}
	

	
}
