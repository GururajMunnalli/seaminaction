package org.open18.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.NoResultException;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Transactional;
import org.jboss.seam.annotations.security.Restrict;
import org.jboss.seam.framework.EntityHome;
import org.open18.model.Golfer;
import org.open18.model.Hole;
import org.open18.model.Round;
import org.open18.model.Score;
import org.open18.model.TeeSet;

@Name("roundHome")
public class RoundHome extends EntityHome<Round> {

	@In(required = false)
	private Golfer currentGolfer;

	@In(create = true)
	private TeeSetHome teeSetHome;

	@In(required = false)
	@Out(required = false)
	private List<Score> scores;

	// As an alternative to a page parameter, the courseId request
	// parameter can be injected using the @RequestParameter annotation.
	//@RequestParameter
	public void setRoundId(Long id) {
		setId(id);
	}

	public Long getRoundId() {
		return (Long) getId();
	}
	
	@Factory(value = "round", scope = ScopeType.EVENT)
	@Override
	public Round getInstance() {
		return super.getInstance();
	}

	@Override
	protected Round createInstance() {
		Round round = super.createInstance();
		if (round.getGolfer() == null) {
			round.setGolfer(currentGolfer);
		}
		round.setDate(new java.sql.Date(System.currentTimeMillis()));
		return round;
	}
	
	/**
	 * For now just grab the scores...might be good to reach out to the golfer and course,
	 * but really if we could like holes to score then we can just go grab the holes.
	 */
	@Override
	protected Round loadInstance() {
		try {
		return (Round) getEntityManager()
			.createQuery("select distinct r from Round r join fetch r.scores where r.id = #{roundHome.id}")
			.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void wire() {
		TeeSet teeSet = teeSetHome.getDefinedInstance();
		if (teeSet != null && !teeSet.equals(getInstance().getTeeSet())) {
			getInstance().setTeeSet(teeSet);
			scores = new ArrayList<Score>();
			List<Hole> holes = new ArrayList<Hole>(teeSet.getCourse().getHoles());
			Collections.sort(holes, Scorecard.HOLE_COMPARATOR);
			for (Hole hole : holes) {
				Score score = new Score();
				score.setRound(getInstance());
				score.setHole(hole);
				scores.add(score);
			}
		}
		
		if (isManaged() && scores == null) {
			scores = new ArrayList<Score>(getInstance().getScores());
		}
	}

	public boolean isWired() {
		if (getInstance().getTeeSet() == null) {
			return false;
		}
		return true;
	}
	
	@Transactional
	public String revert() {
		getEntityManager().refresh(getInstance());
		teeSetHome.clearInstance();
		return "reverted";
	}
	
	@Override
	@Restrict("#{s:hasPermission('roundHome', 'remove', round)}")
	public String remove() {
		return super.remove();
	}

	@Override
	@Restrict("#{s:hasPermission('roundHome', 'update', round)}")
	public String update() {
		getInstance().setScores(new LinkedHashSet<Score>(scores));
		getInstance().updateTotalScore();
		return super.update();
	}

	@Override
	public String persist() {
		getInstance().setScores(new LinkedHashSet<Score>(scores));
		getInstance().updateTotalScore();
		return super.persist();
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
		Collections.sort(scores, Scorecard.SCORE_COMPARATOR);
		return scores;
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
}
