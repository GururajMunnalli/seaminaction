package org.open18.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityController;
import org.open18.model.Course;
import org.open18.model.Hole;
import org.open18.model.Tee;
import org.open18.model.TeeSet;
import org.open18.model.Score;

@Name("scorecard")
public class Scorecard extends EntityController {

	private static final String JPQL =	"select distinct c from Course c " +
		"join fetch c.facility join fetch c.holes join fetch c.teeSets ts join fetch ts.tees " +
		"where c.id = #{scorecard.courseId}";

	static HoleComparator HOLE_COMPARATOR = new HoleComparator();
	static TeeSetComparator TEE_SET_COMPARATOR = new TeeSetComparator();
	static TeeComparator TEE_COMPARATOR = new TeeComparator();
	static ScoreComparator SCORE_COMPARATOR = new ScoreComparator();

	private Set<String> allowedColors = new HashSet<String>() {{
		addAll(Arrays.asList("white", "gray", "lightgray", "darkgray", "black", "red",
			"pink", "yellow", "green", "magenta", "cyan", "blue", "orange"));
	}};

	private HashMap<String, String> colorSubstituteMap = new HashMap<String, String>() {{
		put("gold", "yellow");
		put("silver", "lightgray");
		put("brown", "darkgray");
	}};

	private HashMap<String, String> fontColorMap = new HashMap<String, String>() {{
		put("black", "white");
		put("darkgray", "white");
		put("blue", "white");
	}};

	private Long courseId;

	@Out
	private Course course;

	@Out
	private List<TeeSet> teeSets;

	@Out
	private List<TeeSet> mensAndUnisexTeeSets;

	@Out
	private List<TeeSet> ladiesTeeSets;
	
	@Out
	private List<Hole> holesOut;

	@Out
	private List<Hole> holesIn;

	@Out
	private List<Integer> holeNumbersOut = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

	@Out
	private List<Integer> holeNumbersIn = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18);

	@BypassInterceptors
	public Long getCourseId() {
		return courseId;
	}

	@BypassInterceptors
	@RequestParameter
	public void setCourseId(Long courseId) {
		if (courseId != null) {
			this.courseId = courseId;
		}
	}

	@BypassInterceptors
	public Course getCourse() {
		return course;
	}

	@BypassInterceptors
	public void setCourse(Course c) {
		this.courseId = c.getId();
		this.course = c;
	}

	public void load() {
		course = (Course) createQuery(JPQL).getSingleResult();
		teeSets = getTeeSets();
		ladiesTeeSets = getLadiesTeeSets();
		mensAndUnisexTeeSets = getMensAndUnisexTeeSets(ladiesTeeSets);
		holesOut = getHolesOut();
		holesIn = getHolesIn();
	}

	public List<TeeSet> getTeeSets() {
		List<TeeSet> teeSets = new ArrayList<TeeSet>();
		teeSets.addAll(course.getTeeSets());
		Collections.sort(teeSets, TEE_SET_COMPARATOR);
		return teeSets;
	}

	public List<TeeSet> getMensAndUnisexTeeSets() {
		return getMensAndUnisexTeeSets(getLadiesTeeSets());
	}

	public List<TeeSet> getMensAndUnisexTeeSets(Collection<TeeSet> ladiesTeeSets) {
		List<TeeSet> teeSets = new ArrayList<TeeSet>();
		for (TeeSet teeSet : course.getTeeSets()) {
			if (!ladiesTeeSets.contains(teeSet)) {
				teeSets.add(teeSet);
			}
		}

		Collections.sort(teeSets, TEE_SET_COMPARATOR);
		return teeSets;
	}

	public List<TeeSet> getLadiesTeeSets() {
        List<TeeSet> teeSets = new ArrayList<TeeSet>();
        for (TeeSet teeSet : course.getTeeSets()) {
            if (teeSet.getLadiesCourseRating() != null && teeSet.getLadiesSlopeRating() != null &&
				(teeSet.getMensCourseRating() == null || teeSet.getMensSlopeRating() == null)) {
				teeSets.add(teeSet);
			}
        }

		Collections.sort(teeSets, TEE_SET_COMPARATOR);
		return teeSets;
    }

	public List<Integer> getHoleNumbersOut() {
		return holeNumbersOut;
	}

	public List<Integer> getHoleNumbersIn() {
		return holeNumbersIn;
	}

	public List<Hole> getHolesOut() {
		List<Hole> holes = new ArrayList<Hole>(9);

		for (Hole hole : course.getHoles()) {
			if (hole.getNumber() <= 9) {
				holes.add(hole);
			}
		}

		Collections.sort(holes, HOLE_COMPARATOR);
		return holes;
	}

	public List<Hole> getHolesIn() {
		 List<Hole> holes = new ArrayList<Hole>(9);

		for (Hole hole : course.getHoles()) {
			if (hole.getNumber() > 9) {
				holes.add(hole);
			}
		}

		Collections.sort(holes, HOLE_COMPARATOR);
		return holes;
	}

	public List<Tee> getTeesOut(TeeSet teeSet) {
		List<Tee> tees = new ArrayList<Tee>(9);
		for (Tee tee : teeSet.getTees()) {
			if (tee.getHole().getNumber() <= 9) {
				tees.add(tee);
			}
		}

		Collections.sort(tees, TEE_COMPARATOR);
		return tees;
	}

	public List<Tee> getTeesIn(TeeSet teeSet) {
		List<Tee> tees = new ArrayList<Tee>(9);
		for (Tee tee : teeSet.getTees()) {
			if (tee.getHole().getNumber() > 9) {
				tees.add(tee);
			}
		}

		Collections.sort(tees, TEE_COMPARATOR);
		return tees;
	}

	public String bgColor(String teeSetColor) {
		return fillColor(teeSetColor);
	}

	public String fillColor(String teeSetColor) {
		if (allowedColors.contains(teeSetColor)) {
			return teeSetColor;
		}
		else if (colorSubstituteMap.containsKey(teeSetColor)) {
			return colorSubstituteMap.get(teeSetColor);
		}
		else {
			return "white";
		}
	}

	public String fontColor(String teeSetColor) {
		String bgColor = fillColor(teeSetColor);
		if (fontColorMap.containsKey(bgColor)) {
			return fontColorMap.get(bgColor);
		}
		else {
			return "black";
		}

	}

	public static class HoleComparator implements Comparator<Hole> {
		public int compare(Hole a, Hole b) {
			return Integer.valueOf(a.getNumber()).compareTo(Integer.valueOf(b.getNumber()));
		}
	}

	public static class TeeSetComparator implements Comparator<TeeSet> {
		public int compare(TeeSet a, TeeSet b) {
			return a.getPosition() == null ||
				b.getPosition() == null ? 0 : a.getPosition().compareTo(b.getPosition());
		}
	}

	public static class TeeComparator implements Comparator<Tee> {
		public int compare(Tee a, Tee b) {
			return Integer.valueOf(a.getHole().getNumber()).compareTo(Integer.valueOf(b.getHole().getNumber()));
		}
	}

	public static class ScoreComparator implements Comparator<Score> {
		public int compare(Score a, Score b) {
			return Integer.valueOf(a.getHole().getNumber()).compareTo(Integer.valueOf(b.getHole().getNumber()));
		}
	}

	@BypassInterceptors
	public String toString() {
		return super.toString();
	}

}
