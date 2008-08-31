package org.open18.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.framework.EntityController;
import org.open18.comparator.HoleComparator;
import org.open18.comparator.TeeComparator;
import org.open18.comparator.TeeSetComparator;
import org.open18.model.Course;
import org.open18.model.Hole;
import org.open18.model.Tee;
import org.open18.model.TeeSet;

@Name("scorecard")
public class Scorecard extends EntityController {

	private static final String JPQL =
		"select distinct c from Course c " +
		"join fetch c.facility join fetch c.holes " +
		"join fetch c.teeSets ts join fetch ts.tees " +
		"where c.id = #{scorecard.courseId}";
	
	// FIXME: holeNumbersOut and holeNumbersIn should go on some sort of helper class
	private List<Integer> holeNumbersOut = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
	private List<Integer> holeNumbersIn = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18);

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

	@RequestParameter private Long courseId;
	@Out private Course course;

	// NOTE: using @Create would avoid need to use page action
	//@Create
	public void load() {
		course = (Course) createQuery(JPQL).getSingleResult();
	}
	
	public Long getCourseId() {
		return courseId;
	}

	public List<TeeSet> getTeeSets() {
		List<TeeSet> teeSets = new ArrayList<TeeSet>();
		teeSets.addAll(course.getTeeSets());
		Collections.sort(teeSets, new TeeSetComparator());
		return teeSets;
	}
	
	public List<TeeSet> getMensAndUnisexTeeSets() {
		return getMensAndUnisexTeeSets(getLadiesTeeSets());
	}
	
	public List<TeeSet> getLadiesTeeSets() {
		List<TeeSet> teeSets = new ArrayList<TeeSet>();
		for (TeeSet teeSet : course.getTeeSets()) {
			if (teeSet.getLadiesCourseRating() != null && teeSet.getLadiesSlopeRating() != null &&
				(teeSet.getMensCourseRating() == null || teeSet.getMensSlopeRating() == null)) {
				teeSets.add(teeSet);
			}
		}

		Collections.sort(teeSets, new TeeSetComparator());
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

		// FIXME: hole should be self-aware that it is an out hole
		for (Hole hole : course.getHoles()) {
			if (hole.getNumber() <= 9) {
				holes.add(hole);
			}
		}

		Collections.sort(holes, new HoleComparator());
		return holes;
	}
	
	public List<Hole> getHolesIn() {
		List<Hole> holes = new ArrayList<Hole>(9);

		// FIXME: hole should be self-aware that it is an out hole
		for (Hole hole : course.getHoles()) {
			if (hole.getNumber() > 9) {
				holes.add(hole);
			}
		}

		Collections.sort(holes, new HoleComparator());
		return holes;
	}
	
	public List<Tee> getTeesOut(TeeSet teeSet) {
		List<Tee> tees = new ArrayList<Tee>(9);
		for (Tee tee : teeSet.getTees()) {
			if (tee.getHole().getNumber() <= 9) {
				tees.add(tee);
			}
		}

		Collections.sort(tees, new TeeComparator());
		return tees;
	}
	
	public List<Tee> getTeesIn(TeeSet teeSet) {
        List<Tee> tees = new ArrayList<Tee>(9);
        for (Tee tee : teeSet.getTees()) {
            if (tee.getHole().getNumber() > 9) {
                tees.add(tee);
            }
        }

        Collections.sort(tees, new TeeComparator());
        return tees;
	}
	
	@BypassInterceptors
	public String bgColor(String color) {
        if (allowedColors.contains(color)) {
            return color;
        }
        else if (colorSubstituteMap.containsKey(color)) {
            return colorSubstituteMap.get(color);
        }
        else {
            return "white";
        }
    }
	
	@BypassInterceptors
	public String fontColor(String color) {
        String bgColor = bgColor(color);
        if (fontColorMap.containsKey(bgColor)) {
            return fontColorMap.get(bgColor);
        }
        else {
            return "black";
        }
    }
	
	protected List<TeeSet> getMensAndUnisexTeeSets(Collection<TeeSet> ladiesTeeSets) {
        List<TeeSet> teeSets = new ArrayList<TeeSet>();
        for (TeeSet teeSet : course.getTeeSets()) {
            if (!ladiesTeeSets.contains(teeSet)) {
                teeSets.add(teeSet);
            }
        }

        Collections.sort(teeSets, new TeeSetComparator());
        return teeSets;
    }

}