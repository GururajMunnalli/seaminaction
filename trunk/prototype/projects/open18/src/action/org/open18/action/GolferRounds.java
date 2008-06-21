package org.open18.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import org.open18.model.Hole;
import org.open18.model.Round;
import org.open18.model.Score;
import org.open18.model.Golfer;

@Name("golferRounds")
public class GolferRounds extends EntityQuery<Round> {
    private static final String JPQL =
        "select r from Round r join fetch r.scores";
    
    private static final List<String> RESTRICTIONS = Arrays.asList(
        "r.golfer = #{roundHome.managed ? roundHome.instance.golfer : null}",
        "r.golfer = #{selectedGolfer}"
    );
    
    private static final String ORDER = "r.date asc";
    
    @In(required = false)
    public RoundHome roundHome;
    
	@In(required = false)
	public Golfer selectedGolfer;

    public GolferRounds() {
        setEjbql(JPQL);
        setRestrictions(RESTRICTIONS);
        setOrder(ORDER);
    }
    
    @Override
    public void validate() {
        super.validate();
        if (selectedGolfer == null && (roundHome == null || (roundHome != null && !roundHome.isManaged()))) {
            throw new IllegalArgumentException("No golfer selected.");
        }
    }

    public double getAverageScore(long par) {
        double total = 0;
        int num = 0;

        for (Round round : getResultList()) {
            List<Score> scores = new ArrayList<Score>(round.getScores());
            Collections.sort(scores, Scorecard.SCORE_COMPARATOR);
            for (Score score : scores) {
				if (score.getHole().getPar(round.getGolfer().getGender()) == par) {
                    total += score.getStrokes();
                    num++;
                }
            }
        }

        return num > 0 ? total / num : 0;
    }
}
