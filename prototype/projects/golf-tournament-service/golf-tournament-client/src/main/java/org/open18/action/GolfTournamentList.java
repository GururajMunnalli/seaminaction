package org.open18.action;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.open18.partner.model.GolfTournament;
import org.open18.partner.service.GolfTournamentService;

import java.util.List;

@Name("golfTournamentList")
public class GolfTournamentList {
    @In
    private GolfTournamentService golfTournamentService;

    @Factory(value = "futureTournaments", scope = ScopeType.EVENT)
    public List<GolfTournament> initFutureTournaments() {
        return golfTournamentService.getFutureTournaments();
    }
}
