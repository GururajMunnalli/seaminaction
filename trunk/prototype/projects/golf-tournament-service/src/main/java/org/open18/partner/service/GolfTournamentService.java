package org.open18.partner.service;

import org.open18.partner.model.GolfTournament;

import java.util.List;

public interface GolfTournamentService {
    List<GolfTournament> getFutureTournaments();
}
