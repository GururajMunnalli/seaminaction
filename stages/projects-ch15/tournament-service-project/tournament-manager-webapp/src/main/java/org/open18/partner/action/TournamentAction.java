package org.open18.partner.action;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.log.Log;
import org.open18.partner.business.TournamentManager;
import org.open18.partner.model.Tournament;

@Name("tournamentAction")
@Scope(ScopeType.CONVERSATION)
public class TournamentAction implements Serializable {

	@Logger Log log;
	
	@In	private EntityManager entityManager;

	@In	private FacesMessages facesMessages;
	
	@In	private TournamentManager tournamentManager;
	
	@Out private Tournament tournament;
	
	@Out private boolean managed;

	@Begin
	public String create() {
		tournament = new Tournament();
		return "/tournamentEditor.xhtml";
	}

	@Begin(nested = true, flushMode = FlushModeType.MANUAL)
	public String edit(Tournament selectedTournament) {
		tournament = tournamentManager.get(selectedTournament.getId());
		managed = true;
		return "/tournamentEditor.xhtml";
	}

	public String view(Tournament selectedTournament) {
		tournament = tournamentManager.get(selectedTournament.getId());
		return "/tournament.xhtml";
	}

	@End
	public String save() {
		if (tournamentManager.exists(tournament)) {
			facesMessages.add(FacesMessage.SEVERITY_ERROR,
				"A tournament with that name, date, and location " +
				"already exists in the database.");
			return null;
		}

		tournamentManager.save(tournament);
		try {
            log.info("Transaction active after call to business layer? " +
				org.jboss.seam.transaction.Transaction.instance().isActive());
        } catch (Exception e) {}
		return "/tournament.xhtml";
	}

	@End
	public String delete() {
		tournamentManager.remove(tournament.getId());
		return returnToList();
	}

	@End
	public String cancel() {
		return tournament.getId() != null ? "/tournament.xhtml" : returnToList();
	}
	
	public String returnToList() {
		return "/tournaments.xhtml";
	}

	public boolean isManagedAccordingToSeam() {
		return entityManager.contains(tournament);
	}
	
	public boolean isManagedAccordingToSpring() {
		return tournamentManager.managed(tournament);
	}
}
