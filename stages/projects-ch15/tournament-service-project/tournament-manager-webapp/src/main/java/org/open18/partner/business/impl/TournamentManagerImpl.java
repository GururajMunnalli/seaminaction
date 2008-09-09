package org.open18.partner.business.impl;

import java.io.Serializable;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;
import org.open18.partner.business.TournamentManager;
import org.open18.partner.dao.TournamentDao;
import org.open18.partner.model.Tournament;
import org.open18.partner.search.TournamentSearchCriteria;

public class TournamentManagerImpl implements TournamentManager, Serializable {

	// NOTE: currently, @Logger is not working with stateful Spring beans
	private static Log log = LogFactory.getLog(TournamentManager.class);
	
	private TournamentDao tournamentDao;
	private FacesMessages facesMessages;
	private Identity identity;

	public List<Tournament> find(TournamentSearchCriteria criteria) {
		if (criteria == null) {
			return tournamentDao.getAll();
		}
		else {
			return tournamentDao.queryForList(
				criteria.getSearchPattern(),
				criteria.getSortBy(),
				criteria.getPageSize(),
				criteria.getPage());
		}
	}
	
	public Tournament get(Long id) {
		return tournamentDao.get(id);
	}

	public void save(Tournament tournament) {
		tournamentDao.save(tournament);
		if (identity != null && identity.isLoggedIn()) {
			log.info("Save operation issued by " + identity.getUsername());
		}
		facesMessages.addFromResourceBundleOrDefault("Tournament_saved", "Successfully saved '#0'", tournament.getName());
	}
	
	public void remove(Long id) {
		tournamentDao.remove(id);
	}
	
	public boolean exists(Tournament tournament) {
		return tournamentDao.exists(tournament);
	}
	
	public boolean managed(Tournament tournament) {
		return tournamentDao.contains(tournament);
	}

	public void setTournamentDao(TournamentDao tournamentDao) {
		this.tournamentDao = tournamentDao;
	}

	public void setFacesMessages(FacesMessages facesMessages) {
		this.facesMessages = facesMessages;
	}

	public void setCurrentUser(Identity identity) {
		this.identity = identity;
	}
}
