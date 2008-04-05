package org.open18.partner.service.impl;

import org.open18.partner.model.GolfTournament;
import org.open18.partner.service.GolfTournamentService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class GolfTournamentServiceImpl implements GolfTournamentService {
    public List<GolfTournament> getFutureTournaments() {
        List<GolfTournament> tournaments = new ArrayList();
        GolfTournament tournament1 = new GolfTournament();
        tournament1.setName("Seam Golf Classic");
        Calendar cal = Calendar.getInstance();
        cal.set(2008, 4, 6);
        tournament1.setStartDate(cal.getTime());
        cal.set(2008, 4, 9);
        tournament1.setEndDate(cal.getTime());
        cal.set(2008, 3, 30);
        tournament1.setEntryDeadline(cal.getTime());
        tournament1.setHostFacilityName("Golden Gate Park Golf Course");
        tournament1.setHostFacilityLocation("San Francisco, CA");
        tournament1.setSponser(Arrays.asList( new String[]{ "Sun Microsystems" }));
        tournament1.setSummary("Java developers, put your executive skills to work!");
        tournaments.add(tournament1);
        return tournaments;
    }
}
