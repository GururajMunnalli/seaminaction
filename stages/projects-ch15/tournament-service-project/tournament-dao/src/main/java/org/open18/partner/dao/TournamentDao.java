package org.open18.partner.dao;

import java.util.List;
import org.open18.partner.model.Tournament;

public interface TournamentDao {

	public List<Tournament> getAll();
	
	public List<Tournament> getUpcoming();
	
	public List<Tournament> queryForList(String searchPattern, String sortBy, int pageSize, int page);
	
	public Tournament get(Long id);

	public void save(Tournament tournament);

	public void remove(Long id);

	public boolean exists(Tournament tournament);
	
	public boolean contains(Tournament tournament);
}
