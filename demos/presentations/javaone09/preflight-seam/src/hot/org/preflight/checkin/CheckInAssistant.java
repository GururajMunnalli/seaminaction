package org.preflight.checkin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.FlushModeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.international.StatusMessages;
import org.preflight.model.BoardingPass;
import org.preflight.model.Reservation;
import org.preflight.reservation.criteria.ReservationIdentifier;
import org.preflight.reservation.service.ReservationLocator;
import org.preflight.ui.SelectableItem;

@Name("checkInAssistant")
@Scope(ScopeType.CONVERSATION)
public class CheckInAssistant implements Serializable {
	
	private static final long serialVersionUID = 4642447418609006752L;

	@In StatusMessages statusMessages;
	
	@In EntityManager entityManager;
	
	@In ReservationLocator reservationLocator;
	
	@In ReservationIdentifier reservationIdentifier;
	
	@Out(required = false) Reservation reservation;
	
	@DataModel List<SelectableItem<BoardingPass>> candidateBoardingPasses;
	
	@DataModel List<BoardingPass> boardingPasses;
	
	@Begin(pageflow = "check-in", flushMode = FlushModeType.MANUAL)
	public String locateReservationForCheckIn() {
		reservation = reservationLocator.findActiveReservation(reservationIdentifier);
		if (reservation == null) {
			statusMessages.add(StatusMessage.Severity.ERROR, "We were unable to locate your booking. Either your reference number or last name were entered incorrectly or you don't have an active reservation.");
			return null;
		}
		
		boardingPasses = reservationLocator.findBoardingPassesPendingCheckIn(reservation, getCheckInWindowDuration());
		if (boardingPasses.size() == 0) {
			statusMessages.add(StatusMessage.Severity.INFO, "No flights available for check-in.");
			return null;
		}
		
		candidateBoardingPasses = new ArrayList<SelectableItem<BoardingPass>>();
		for (BoardingPass boardingPass : boardingPasses) {
			candidateBoardingPasses.add(new SelectableItem<BoardingPass>(boardingPass));
		}
		boardingPasses.clear();
		
		return "begin";
	}
	
	public void selectBoardingPasses() {
		for (SelectableItem<BoardingPass> candidate : candidateBoardingPasses) {
			if (candidate.isSelected()) {
				boardingPasses.add(candidate.getItem());
			}
		}
		
		if (boardingPasses.size() == 0) {
			statusMessages.add(StatusMessage.Severity.WARN, "You must select at least one passenger's flight to proceed with check in.");
		}
		else {
			candidateBoardingPasses.clear();
		}
	}
	
	public void checkIn() {
		for (BoardingPass boardingPass : boardingPasses) {
			boardingPass.setCheckedIn(true);
		}
	}
	
	public boolean commit() {
		entityManager.flush();
		statusMessages.add("Check in complete!");
		return true;
	}
	
	protected int getCheckInWindowDuration() {
		return 24;
//		Calendar nextFlightDate = Calendar.getInstance();
//		nextFlightDate.set(2009, 5, 1, 0, 0, 0);
//
//		Calendar today = Calendar.getInstance();
//		today.set(Calendar.HOUR_OF_DAY, 0);
//		today.set(Calendar.MINUTE, 0);
//		today.set(Calendar.SECOND, 0);
//
//		long differenceInMillis = nextFlightDate.getTimeInMillis() - today.getTimeInMillis();
//		return ((int) (differenceInMillis / ReservationLocator.MILLISECONDS_PER_HOUR)) + 24;
	}
	
}
