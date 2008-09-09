package org.open18.partner.service.impl;

import org.open18.partner.model.Tournament;
import org.open18.partner.service.TournamentService;
import org.open18.partner.dao.TournamentDao;

import java.util.List;

public class TournamentServiceImpl implements TournamentService {
	private TournamentDao tournamentDao;
	
    public List<Tournament> getUpcomingTournaments() {
       return tournamentDao.getUpcoming();
    }

	public void setTournamentDao(TournamentDao tournamentDao) {
		this.tournamentDao = tournamentDao;
	}
}
