package org.open18.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;
import org.jboss.seam.ScopeType;
import org.open18.model.TeeSet;
import org.open18.model.Round;
import org.icefaces.x.core.push.SessionRenderer;
/**
 * This bean is responsible for checking each entry for each course to 
 * see if current entries will change the top 10 list for each course for
 * each tee set.  Depending on number of users, the lists could be maintained
 * in memory here rather than hitting the database each time.  For now
 * we will just keep the 10th score from each list.  As each user enters
 * their round and it is safely persisted, then check to see if they are
 * in the top 10.  If they are then push the render for whichever sessions
 * are registered for that tee's leaderboard.
 * @author jguglielmin
 *
 */
@Name("leaderBoardManager")
@Scope(ScopeType.APPLICATION)
@AutoCreate
public class LeaderBoardManager {
	@Logger Log log;
	
	@In("#{teeSets}")
	List<TeeSet> teeSets;
	
	@In
	EntityManager entityManager;
	
	private Map roundsLookup;
	private Map minLookup;
	
	@Create
	public void init(){
		log.info("CREATING LEADERBOARDMANAGER");
		roundsLookup = new ConcurrentHashMap(teeSets.size());
		minLookup = new ConcurrentHashMap(teeSets.size());
		//each teeSet has id which is key in HashMap that returns
		//the minimum value of the top 10 rounds.
		for (TeeSet teeSet : teeSets){
			//find top 10 rounds  & place in hashmap with teeset id as key
			roundsLookup.put(teeSet.getId(),getTeeSetRounds(teeSet));		
		}
	}
	
	public List<Round> getTeeSetRounds(TeeSet teeSet){
		List<Round> resultList = entityManager.createQuery("select distinct r from Round r join fetch r.golfer golfer"+
		          " join fetch r.teeSet teeSet"+
		          " join fetch teeSet.course course where r.teeSet = :pattern1 order by r.totalScore")
		          .setParameter("pattern1",teeSet)
		          .setMaxResults(10)
		          .getResultList();
		log.info("for teeSet:-"+teeSet.getId()+" have "+resultList.size()+" values");
		if (resultList.size()>0){
			Round r = (Round)resultList.get(resultList.size()-1);
			log.info("setting min value to:-"+r.getTotalScore()+" by user:-"+r.getGolfer().getUsername());
			minLookup.put(teeSet.getId(), r.getTotalScore());
		}else if (resultList.isEmpty()){
			minLookup.put(teeSet.getId(), 99999);
		}else log.info("What else could it be?");
		return resultList;
	}
	
	
	/**
	 * first check to see if the list has to change
	 */
	@Observer("checkTopTen")
	public synchronized void checkRound(Round round){
		TeeSet teeSet = round.getTeeSet();
		log.info("new round has score="+round.getTotalScore());
		log.info("original min of top ten ="+minLookup.get(teeSet.getId()));
		if (round.getTotalScore() < (Integer)minLookup.get(teeSet.getId())){
			log.info("have to update the list and insert the round in the list");	
			//next line changes the maps
			this.roundsLookup.put(teeSet.getId(), this.getTeeSetRounds(teeSet));
			log.info("size of minLookup is "+minLookup.size());
			log.info("size of roundsLookup is "+roundsLookup.size());
			SessionRenderer.render(teeSet.getId().toString());
		}
				else log.info("don't have to update the list or insert the round");		
	}

	public Map getRoundsLookup() {
		return Collections.unmodifiableMap(roundsLookup);
	}

	protected void setRoundsLookup(Map roundsLookup) {
		this.roundsLookup = roundsLookup;
	}

	protected Map getMinLookup() {
		return minLookup;
	}

	protected void setMinLookup(Map minLookup) {
		this.minLookup = minLookup;
	}
	

	
}
