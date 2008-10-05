package org.open18.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;

import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;
import org.open18.model.Course;
import org.open18.model.Round;
import org.open18.model.TeeSet;
import org.icefaces.x.core.push.SessionRenderer;
import org.jboss.seam.core.Manager;

@Name("leaderBoard")
@Scope(ScopeType.SESSION)
public class LeaderBoard implements Serializable{
	@Logger Log log;
	public boolean visible = true;
	public boolean renderPush=false;
	public boolean teeSetIdEmpty=true;
	
	@In
	EntityManager entityManager;
	
	@In(required=false)
	private String teeSetId;
	
	private TeeSet teeSet;
	private Course course;
	
	
	private List<Round> resultList;
	
	@Create
	public void init(){
	   	log.info("creating leaderboard version="+this+" visible="+visible);
	   	if (teeSetId!=null){
	   		log.info("teeSetId injected in =" +teeSetId);
	   		this.teeSetIdEmpty=false;
	   		this.visible=false;
	   	}
	   	else this.visible = true;
	}
	
//	@Begin(join=true)
	public void create(){
		this.renderPush=true;
		if (!Manager.instance().isLongRunningConversation())
			log.info("didn't start conversation yet!!");
		log.info("settingSessionRenderer");
		if (teeSet!=null){
			this.teeSetIdEmpty=false;
			SessionRenderer.addCurrentSession(teeSet.getId().toString());
		}
		else {
			log.info("can't start SessionRenderer since teeSet is null!!");
			this.teeSetIdEmpty=true;
		}
		visible=false;
	}
	
	public boolean isVisible(){
		return this.visible;
	}
	public void setTeeSet(TeeSet teeset){
		this.teeSet=teeset;
		this.course = teeSet.getCourse();
		log.info("set teeSet to id="+teeSet.getId()+" for colour="+teeSet.getColor());
	}
	public void setCourse(Course course){
		this.course = course;
		log.info("set course to id="+course.getId()+" for course="+course.getName());
	}

	@Factory(scope=ScopeType.STATELESS)
	public List<Round> getResultList(){
		buildList();
		return this.resultList;
	}
	
	protected void buildList(){
		//can't build the list if no teeSet is selected
		if (teeSet !=null){
			resultList = entityManager.createQuery("select distinct r from Round r join fetch r.golfer golfer"+
	          " join fetch r.teeSet teeSet"+
	          " join fetch teeSet.course course where r.teeSet = :pattern1 order by r.totalScore")
	          .setParameter("pattern1",teeSet)
	          .setMaxResults(10)
	          .getResultList();			
		}else if (teeSetId!=null){
			resultList = entityManager.createQuery("select distinct r from Round r join fetch r.golfer golfer"+
			          " join fetch r.teeSet teeSet"+
			          " join fetch teeSet.course course where r.teeSet.id = :pattern1 order by r.totalScore")
			          .setParameter("pattern1",teeSetId)
			          .setMaxResults(10)
			          .getResultList();				
		}else log.info("they are both null???");
	}
	
	public Course getCourse(){return this.course;}
	public TeeSet getTeeSet(){return this.teeSet;}
	public void setVisible(boolean addround){this.visible=addround;}
	public boolean isRenderPush(){return this.renderPush;}
	public void setRenderPush(boolean push){this.renderPush = push;}
	
	@Destroy
	public void destroy(){
		if (teeSet!=null){
			SessionRenderer.removeCurrentSession(teeSet.getId().toString());
			teeSet=null;
			visible = true;
			renderPush=false;
			teeSetIdEmpty=true;
			log.info("removed this session from SessionRenderer");
		}
	}
	
	public void setTeeSetId(String teeSetId){
		if (teeSetId==null)this.teeSetId=teeSetId;
	}
	public String getTeeSetId(){
		if (teeSet!=null)return teeSet.getId().toString();
		else return teeSetId;
	}

	public boolean isTeeSetIdEmpty() {
		return teeSetIdEmpty;
	}

	public void setTeeSetIdEmpty(boolean teeSetIdEmpty) {
		this.teeSetIdEmpty = teeSetIdEmpty;
	}
}
