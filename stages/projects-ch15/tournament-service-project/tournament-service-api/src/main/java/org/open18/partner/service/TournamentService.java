package org.open18.partner.service;

import org.open18.partner.model.Tournament;

import java.util.List;

public interface TournamentService {
    List<Tournament> getUpcomingTournaments();
}
