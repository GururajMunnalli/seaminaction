package org.open18.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.open18.partner.model.Tournament;
import org.open18.partner.service.TournamentService;

import java.util.List;

@Name("upcomingTournamentList")
public class UpcomingTournamentList {
    @In private TournamentService tournamentService;

    @Factory(value = "upcomingTournaments", scope = ScopeType.EVENT)
    public List<Tournament> initUpcomingTournaments() {
        return tournamentService.getUpcomingTournaments();
    }
}
