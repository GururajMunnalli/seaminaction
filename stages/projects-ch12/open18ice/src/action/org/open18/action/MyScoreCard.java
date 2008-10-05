package org.open18.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.event.ActionEvent;


import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;
import org.open18.model.Course;
import org.open18.model.Golfer;
import org.open18.model.Round;
import org.open18.model.TeeSet;
import org.jboss.seam.core.Events;

import org.open18.action.MyRoundList;
import org.jboss.seam.Component;

import com.icesoft.faces.component.ext.RowSelectorEvent;

@Name("myscorecard")
@Scope(ScopeType.CONVERSATION)
public class MyScoreCard extends MyRoundList{
	@Logger Log log;

	public boolean expanded = false;
	public boolean showAddButton = false;
	
	private TeeSet teeSet;
	private Course course;
	Long courseId=null;
	
	Long teeSetId=null;
	
	@BypassInterceptors
	public void addRoundVisible(){
		this.expanded=true;
	}
	@BypassInterceptors
	public boolean isExpanded(){
		return this.expanded;
	}
//	@BypassInterceptors
	public void setTeeSet(TeeSet teeset){
		this.teeSet=teeset;
		this.teeSetId = teeSet.getId();
		if (teeSet !=null)showAddButton=true;
		log.info("set teeSet to id="+teeSetId+" for colour="+teeSet.getColor());
	}
	@BypassInterceptors
	public List getTeeSets(){
		return new ArrayList<TeeSet>(course.getTeeSets());
	}

	@BypassInterceptors
	public void cancelAdd(){
		teeSet=null;
		this.expanded=false;
		this.showAddButton=false;
	}

	public TeeSet getTeeSet(){return this.teeSet;}
	
	@BypassInterceptors
	public void setExpanded(boolean addround){this.expanded=addround;}

	@BypassInterceptors
	public boolean getShowAddButton(){
		return this.showAddButton;
	}
	

}
