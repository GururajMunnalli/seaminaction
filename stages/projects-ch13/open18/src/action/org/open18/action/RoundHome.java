package org.open18.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.persistence.NoResultException;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.framework.EntityHome;
import org.open18.comparator.ScoreComparator;
import org.open18.model.Golfer;
import org.open18.model.Hole;
import org.open18.model.Round;
import org.open18.model.Score;
import org.open18.model.TeeSet;
import org.open18.model.enums.Weather;

@Name("roundHome")
public class RoundHome extends EntityHome<Round> {

	@In(required = false)
	private Golfer currentGolfer;

	@In(create = true)
	private TeeSetHome teeSetHome;

	// As an alternative to a page parameter, the courseId request
	// parameter can be injected using the @RequestParameter annotation.
	//@RequestParameter
	//public void setRoundId(Long id) {
	//	setId(id);
	//}

	@Factory(value = "round", scope = ScopeType.EVENT)
	@Override
	public Round getInstance() {
		return super.getInstance();
	}

	@Factory(value = "weatherCategories", scope = ScopeType.CONVERSATION)
	public Weather[] getWeatherCategories() {
		return Weather.values();
	}
	
	// As an alternative to XML-based configuration, the round prototype can
	// be configured here
	//@Override
	//protected Round createInstance() {
	//	Round round = super.createInstance();
	//	if (round.getGolfer() == null) {
	//		round.setGolfer(currentGolfer);
	//	}
	//	round.setDate(new java.sql.Date(System.currentTimeMillis()));
	//	return round;
	//}
	
	/**
	 * Eagerly fetch associations needed on view/edit page.
	 */
	@Override
	protected Round loadInstance() {
		try {
			return (Round) getEntityManager()
				.createQuery(
					"select r from Round r " +
					"join fetch r.golfer g " +
					"join fetch r.teeSet ts " +
					"join fetch ts.course c " +
					"where r.id = #{roundHome.id}")
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void wire() {
		TeeSet teeSet = teeSetHome.getDefinedInstance();
		if (teeSet == null) {
			if (isManaged()) {
				teeSet = getInstance().getTeeSet();
			}
		} // only set if the tee set does not share object identity (hence a real change)
		else if (teeSet != null && !teeSet.equals(getInstance().getTeeSet())) {
			getInstance().setTeeSet(teeSet);
			updateScores();
		}

		if (teeSet != null && getInstance().getScores().isEmpty()) {
			seedScores();
		}
	}

	public boolean isWired() {
		if (getInstance().getTeeSet() == null) {
			return false;
		}
		return true;
	}

	@Override
	public String persist() {
		if (!validateScoreInput()) {
			return null;
		}
		updateScores();
		return super.persist();
	}

	@Override
	public String update() {
		if (!validateScoreInput()) {
			return null;
		}
		updateScores();
		return super.update();
	}
	
	@Transactional
	public String revert() {
		// NOTE: collections with transient entities must be cleared before a refresh
		getInstance().getScores().clear();
		getEntityManager().refresh(getInstance());
		teeSetHome.clearInstance();
		return "reverted";
	}

	protected void seedScores() {
		Set<Score> scores = new LinkedHashSet<Score>();
		List<Hole> holes = new ArrayList<Hole>(getInstance().getTeeSet().getCourse().getHoles());
		for (Hole hole : holes) {
			Score score = new Score();
			score.setRound(getInstance());
			score.setHole(hole);
			scores.add(score);
		}
		getInstance().setScores(scores);
	}
	
	/**
	 * <p>Order the scores according to the hole number.</p>
	 * <p>This ordering cannot be handled by JPA since it is not possible to
	 * reach into an associated entity to get a sort value.</p>
	 */
	public List<Score> getScores() {
		if (getInstance() == null) {
			return null;
		}

		List<Score> scores = new ArrayList<Score>(getInstance().getScores());
		if (!scores.isEmpty()) {
			Collections.sort(scores, new ScoreComparator());
		}
		return scores;
	}
	
	/**
	 * Convenience method to make it possible to perform inline date formatting.
	 */
	public String getDate(String pattern) {
		if (getInstance() == null || getInstance().getDate() == null) {
			return null;
		}
		DateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(getInstance().getDate());
	}
	
	protected void updateScores() {
		if (getInstance().getScores().isEmpty()) {
			return;
		}
		Set<Hole> holes = getInstance().getTeeSet().getCourse().getHoles();
		for (Score score : getInstance().getScores()) {
			for (Hole hole : holes) {
				if (score.getHole().getNumber() == hole.getNumber()) {
					score.setHole(hole);
				}
			}
		}
	}
	
	protected boolean validateScoreInput() {
		if (getInstance().getScores().isEmpty()) {
			if (getInstance().getTotalScore() == null) {
				getFacesMessages().addToControl("round:totalScore", FacesMessage.SEVERITY_ERROR,
					"You must provide a total score or scores per hole.");
				return false;
			}
			
			return true;
		}
		else {
			int totalStrokes = 0;
			boolean strokesEntered = false;
			List<Integer> missingScores = new ArrayList<Integer>();
			for (Score score : getInstance().getScores()) {
				if (score.getStrokes() == null) {
					missingScores.add(score.getHole().getNumber());
				} else {
					strokesEntered = true;
					totalStrokes += score.getStrokes();
				}
			}
			
			if (strokesEntered) {
				if (!missingScores.isEmpty()) {
					getFacesMessages().add(FacesMessage.SEVERITY_ERROR,
						"You are missing a stroke count for holes {0}.", join(missingScores, ", "));
					return false;
				}
				getInstance().setTotalScore(totalStrokes);
			}
			else {
				if (getInstance().getTotalScore() == null) {
					getFacesMessages().addToControl("round:totalScore", FacesMessage.SEVERITY_ERROR,
						"You must provide a total score or scores per hole.");
					return false;
				}
				getInstance().getScores().clear();
			}
			
			return true;
		}
	}
	
	private <T> String join(Collection<T> c, String delimiter) {
		if (c.isEmpty()) {
			return "";
		}
		Iterator<T> iter = c.iterator();
		StringBuffer buffer = new StringBuffer(stringValue(iter.next()));
		while (iter.hasNext()) {
			buffer.append(delimiter).append(stringValue(iter.next()));
		}
		return buffer.toString();
	}

	private String stringValue(Object o) {
		return o != null ? o.toString() : "";
	}
}
