package org.open18.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TimeZone;

import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.web.RequestParameter;
//import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.framework.EntityHome;
import org.jboss.seam.log.Log;
import org.open18.model.Gender;
import org.open18.model.Golfer;
import org.open18.model.Hole;
import org.open18.model.Round;
import org.open18.model.Score;
import org.open18.model.TeeSet;

import org.jboss.seam.core.Events;

@Name("roundHome")
@Scope(ScopeType.CONVERSATION)
public class RoundHome extends EntityHome<Round> {

	@Logger Log log;
	
	@In(required = false)
	private Golfer currentGolfer;

	@In(create = true)
	private TeeSetHome teeSetHome;

	
	@Out(required = false)
	private List<Score> scores;

	@In(create=true)
	FacesMessages facesMessages;
	
	private String holeCheck="";
	private boolean roundValid=false;
	private int totalScore=0;
	
//	@In(required=false)
	//@RequestParameter
	private String inputMode;

	private Long teeSetId;
	private boolean readonly=false;
	
	private DataModel rowdm; 
	private boolean male = true;
	
	private boolean initialised = false;
	
	
//	// As an alternative to a page parameter, the courseId request
//	// parameter can be injected using the @RequestParameter annotation.
//	//@RequestParameter
	public void setRoundId(Long id) {
		log.info("setting ID to "+id);
		setId(id);
	}
	

 	@Factory(value="round", scope= ScopeType.CONVERSATION)
//	@Factory(value="round")
	@Override
	public Round getInstance() {
	//	log.info("getInstance");
		if (!initialised){
			init();
			this.initialised=true;
		}
		return super.getInstance();
	}

	protected void init(){
		log.info("init() before setting up datamodel");
		initDataModel();
		if (this.inputMode!=null) if (this.inputMode.equals("View"))readonly=true;
		else readonly=false;
		if (currentGolfer !=null)this.male=this.currentGolfer.getGender().equals(Gender.MALE);
		else log.info("currentGolfer==null!!!"); 
	}
	
	private void initDataModel(){
		log.info("!!!!!! initDataModel !!!!!!!");
		ArrayList<String> rowDmVals = new ArrayList<String>();		
		rowDmVals.add("Par");
		rowDmVals.add("Score");
		rowDmVals.add("Fairway");
		rowDmVals.add("Green In Regulation");
		rowDmVals.add("Putts");
		this.rowdm = new ListDataModel(rowDmVals);
	}
 
	public List getTestData(){
		ArrayList<String> rowDmVals = new ArrayList<String>();		
		rowDmVals.add("Par");
		rowDmVals.add("Score");
		rowDmVals.add("Fairway");
		rowDmVals.add("Green In Regulation");
		rowDmVals.add("Putts");
		return rowDmVals;
	}
	
	public DataModel getRowdm(){
		if (rowdm==null)log.info("rowDMVal is null!!!!");
		else log.info("getting rowdm rowcount="+rowdm.getRowCount());
		return this.rowdm;
	}

	public int getRowIndex(){
		if (rowdm!=null)log.info("returning rowIndex = "+this.rowdm.getRowIndex());
		else log.info("rowDM is NULL!!!!!!!!!!!!");
		return this.rowdm.getRowIndex();
	}
	@Override
	protected Round createInstance() {
		log.info("createInstance version="+this);
		Round round = super.createInstance();
		if (round.getGolfer() == null) {
			round.setGolfer(currentGolfer);
		}
		readonly=false;
		round.setDate(new java.sql.Date(System.currentTimeMillis()));
		return round;
	}
	
	/**
	 * Grab the rounds as well as scores and associated holes. That should limit what we need to a single query.
	 */

	@Override
	 protected Round loadInstance() {
		log.info("loadInstance()");
	 	try {
	 		
	 		Round currentRound = (Round) getEntityManager().createQuery(
	 		"select distinct r from Round r join fetch r.scores s join fetch s.hole where r.id = #{roundHome.id}")
	 			.getSingleResult();
		 		setScores(new ArrayList<Score>(currentRound.getScores()));
		 		this.totalScore=currentRound.getTotalScore();
		 		log.info("totalScore is="+this.totalScore+" round has score="+currentRound.getTotalScore());
		 		if (!initialised){
		 			init();
			 		this.male=currentRound.getGolfer().getGender().equals(Gender.MALE);		 			
	 		     }
	 		return currentRound;
	 	} catch (NoResultException e) {
	 		return null;
	 	}
	}

	public void wire() {

		if (!this.initialised){
			log.info("wire");
		TeeSet teeSet = teeSetHome.getDefinedInstance();
		if (teeSet != null && !teeSet.equals(getInstance().getTeeSet())) {
				log.info("building Scores list");
			getInstance().setTeeSet(teeSet);
			scores = new ArrayList<Score>();
			List<Hole> holes = new ArrayList<Hole>(teeSet.getCourse().getHoles());
			Collections.sort(holes, Scorecard.HOLE_COMPARATOR);
				log.info("after sorting holes wire() number of holes for this teeSet = "+holes.size());
			for (Hole hole : holes) {
				Score score = new Score();
				score.setRound(getInstance());
				score.setHole(hole);
				scores.add(score);
			}
				this.getInstance().setScores(scores);
		}
		}
		
		
//		if (isManaged() && scores == null) {
//			log.info("getting the instance back");
//			this.refreshScores();
//			scores = getScores();
//		}
		
		}

	public boolean isWired() {
		if (getInstance().getTeeSet() == null) {
			return false;
		}
		return true;
	}
	
	@Transactional
	@End(beforeRedirect=true)
	public String revert() {
 		log.info("revert and flush mode="+this.getEntityManager().getFlushMode());
		getEntityManager().refresh(getInstance());
		teeSetHome.clearInstance();
		this.totalScore=0;
		this.scores=new ArrayList<Score>();
		this.clearInstance();		
		return "reverted";
	}
	
	@Override
	@End
	public String remove() {
		try{
			String remove=super.remove();
//need to insert something here to check to see if person is on leaderboard and needs to be removed!			
			getEntityManager().flush();
			return remove;
		}catch(Exception e){
			log.info("problems deleting record");
			return "notremoved";
		}
	}

	@Override
	public String update() {
		log.info("update");
		 getInstance().setScores(new LinkedHashSet<Score>(scores));
		 getInstance().updateTotalScore();
		if (checkScores()){
			this.setScores(scores);
			try{
				String update = super.update();
				Events.instance().raiseEvent("checkTopTen", this.getInstance());
			    this.readonly=true;
			    this.clearInstance();
			    return update;
			}catch (Exception e){
				facesMessages.add("problem updating this record");
				return "unsuccessful";
			}
		}
		else {
			return "unsuccessful";
		}		
	}

	@Override
	public String persist() {
		//check to see that scores are all there and not equal null
		log.info("persist!!");
		if (checkScores()){
			log.info("checkscores is good");
			this.roundValid=true;
			getInstance().setScores(new LinkedHashSet<Score>(scores));
			getInstance().updateTotalScore();
			String persist= super.persist();
			this.getEntityManager().flush();
			log.info("Can persist without problems persist="+persist);
			if (persist.equals("persisted")){
				//check with LeaderBoardManager to see this round makes top ten
				Events.instance().raiseEvent("checkTopTen", this.getInstance());
			}
			this.readonly=true;
			return persist;
		}
		else {
		 	FacesMessages.instance().add("Round Not Persisted:- Problems with Data--check that all scores are entered");
			return "";
	}
	}


	/**
	 * <p>Order the scores according to the hole number.</p>
	 * <p>This ordering cannot be handled by JPA since it is not possible to
	 * reach into an associated entity to get a sort value.</p>
	 */
 //	@BypassInterceptors  Don't use that here!!!
	public List<Score> getScores() {
		if (scores == null) {
			log.info("scores is null");
		}
		return scores;
	}
	
	@BypassInterceptors
	protected void setScores(List<Score> scores){
		log.info("setScores");
		this.scores = scores;
		Collections.sort(scores,Scorecard.SCORE_COMPARATOR);
		}

	protected void refreshScores(){
		log.info("refreshing scores list & sorting!!");
	 	scores = new ArrayList<Score>(getInstance().getScores());
		Collections.sort(scores, Scorecard.SCORE_COMPARATOR);
	}

	public double getAverageScore(long par) {
		double total = 0;
		int num = 0;
		
		for (Score score : getScores()) {
			if (score.getHole().getPar(getInstance().getGolfer().getGender()) == par) {
				total += score.getStrokes();
				num++;
			}
		}

		return num > 0 ? total / num : 0;
	}

	public double getAveragePutts() {
		double total = 0;
		int num = 0;
		
		for (Score score : getScores()) {
			if (score.getPutts() != null) {
				total += score.getPutts();
				num++;
			}
		}

		return num > 0 ? total / num : 0;
	}

	public double getFairwayPercentage() {
		double total = 0;
		int num = 0;
		
		for (Score score : getScores()) {
			if (!score.getHole().isPar3(getInstance().getGolfer().getGender())) {
				num++;
				if (Boolean.TRUE.equals(score.getFairway())) {
					total++;
				}
			}
		}

		return num > 0 ? Math.round((total / num) * 100) : 0;
	}

	public double getGirPercentage() {
		double total = 0;
		int num = 0;
		
		for (Score score : getScores()) {
			num++;
			if (Boolean.TRUE.equals(score.getGreenInRegulation())) {
				total++;
			}
		}

		return num > 0 ? Math.round((total / num) * 100) : 0;
	}

	public double getGirPercentage(long par) {
		double total = 0;
		int num = 0;
		
		for (Score score : getScores()) {
			if (score.getHole().getPar(getInstance().getGolfer().getGender()) == par) {
				num++;
				if (Boolean.TRUE.equals(score.getGreenInRegulation())) {
					total++;
				}
			}
		}

		return num > 0 ? (total / num) * 100 : 0;
	}

	public java.util.Map<Integer, Integer> getPuttFrequencies() {
		java.util.Map<Integer, Integer> freqs = new java.util.HashMap<Integer, Integer>();
		for (Score score : getScores()) {
			if (score.getPutts() != null) {
				Integer currentValue = freqs.containsKey(score.getPutts()) ? freqs.get(score.getPutts()) : 0;
				freqs.put(score.getPutts(), ++currentValue);
			}
		}
		return freqs;
	}
	
	public void calculateTotalScore(ValueChangeEvent event){
		log.info("valueChangeListener fired");
		if (event.getPhaseId()!=PhaseId.INVOKE_APPLICATION){
			event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			event.queue();
		}else{
 			if (event.getNewValue()!=null)this.totalScore+=(Integer)event.getNewValue();
 			if (event.getOldValue()!=null)this.totalScore-=(Integer)event.getOldValue();
			log.info("totalScore is"+this.totalScore);
		}

	}

	/**
	 * for now, just using a basic validation that the holes are entered.  Other validation
	 * can go into this method as well and persistence and redirection will only 
	 * proceed if this validation is met
	 * @return
	 */
	public boolean checkScores(){
		log.info("checkScores");
		for (Score score : scores){
			if (score.getStrokes()==null){
 				FacesMessages.instance().add("Missing value for hole="+score.getHole().getNumber());
				this.holeCheck="Missing value for hole="+score.getHole().getNumber();
				return false;
			}
		}return true;
	}
	public String getHoleCheck(){
		return this.holeCheck;
	}

	@BypassInterceptors
	public boolean getReadonly(){
		return this.readonly;
	}

	@BypassInterceptors
	public int getTotalScore(){
		return this.totalScore;
	}
	
	
	public void setTeeSetHome(TeeSetHome teeSetHome){this.teeSetHome=teeSetHome;}
	public TeeSetHome getTeeSetHome(){return this.teeSetHome;}
	
	public void setTeeSetId(Long id){
		log.info("setting teeSetId to id="+id);
		this.teeSetId = id;
		}
	
	public Long getTeeSetId(){return this.teeSetId;}
	
//don't bypass interceptors when you are using request parameters or injected ones
	public String getInputMode(){return this.inputMode;}

 	@In(required=false)
 	public void setInputMode(String iMode){
 		this.inputMode=iMode;
 	}
	
//	public Round getDefinedInstance() {
//		log.info("getDefinedInstance and isIdDefined() = "+isIdDefined());
//		return isIdDefined() ? loadInstance() : null;
//	}
	@BypassInterceptors
	public boolean isMale(){
		return this.male;
	}
	//@BypassInterceptors
//	public String switchToEditMode(ActionEvent event){
	public String switchToEditMode(){
		log.info("switching to Edit mode");
		setInputMode("Edit");
		this.readonly=false;
		return "";
	}
	@BypassInterceptors
    public TimeZone getTimeZone() {
        return java.util.TimeZone.getDefault();
    }
	public boolean isRoundValid(){return this.roundValid;}

	@Destroy
	public void destroy(){
		log.info("roundHome instance destroyed");
	}
}
