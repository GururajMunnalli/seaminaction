package org.open18.action;



import java.util.Arrays;
import java.util.List;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.core.Events;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.framework.EntityQuery;
import org.jboss.seam.log.Log;
import org.open18.model.Round;

import com.icesoft.faces.component.ext.RowSelectorEvent;

//@Name("myRoundsList")
@Scope(ScopeType.CONVERSATION)
public class MyRoundList extends EntityQuery {
	
	private static final String[] RESTRICTIONS = {
		"lower(golfer.lastName) like concat(lower(#{roundList.round.golfer.lastName}),'%')",
        "lower(golfer.firstName)like concat(lower(#{roundList.round.golfer.firstName}),'%')",
 		"lower(teeSet.color) = lower(#{roundExample.teeSet.color})",
 		"lower(r.weather) = lower(#{roundExample.weather})",
 		"r.date >= #{roundExample.date}",
        "r.totalScore <= #{roundExample.totalScore}",};

	@Logger Log log;

	private static final String EJBQL= "select r from Round r join fetch r.golfer golfer"+
	          " join fetch r.teeSet teeSet"+
	          " join fetch teeSet.course course"+
	  		" where r.golfer.id = #{currentGolfer.id}";
	
	@In(value="#{roundExample}")
    private Round round;
	
	private Round selectedRound;

	//for sorting
	private String sortColumn="r.date";
	private String descending="desc";
	

	//for determining total number of records in this table
	private Long roundCount;

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

	public Round getRound(){
		return round;
	}
			
	public Long getRoundCount(){
		   Object o = this.getEntityManager().createQuery("select count(*) from Round as r"+
				   " join fetch r.golfer golfer").getSingleResult();
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
	public void setSelectedRound(Round round){
		this.selectedRound = round;
	}
	public Round getSelectedRound(){return this.selectedRound;}

	//when another round is added, need to update the list
	@Observer("updateScorecardList")
	public void getFreshList(){
		log.info("getting new list");
		this.setEjbql(EJBQL);
	}
}
