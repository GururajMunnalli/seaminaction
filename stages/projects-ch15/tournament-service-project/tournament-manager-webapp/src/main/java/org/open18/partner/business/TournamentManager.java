package org.open18.partner.business;

import java.util.List;
import org.open18.partner.model.Tournament;
import org.open18.partner.search.TournamentSearchCriteria;

public interface TournamentManager {

	public List<Tournament> find(TournamentSearchCriteria criteria);
	
	public Tournament get(Long id);

	public void save(Tournament tournament);

	public void remove(Long id);

	public boolean exists(Tournament tournament);
	
	public boolean managed(Tournament tournament);
}
