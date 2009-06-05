package org.preflight.service;

import java.util.List;

import org.preflight.criteria.ReservationIdentifier;
import org.preflight.model.BoardingPass;
import org.preflight.model.Reservation;
import org.preflight.model.Seat;
import org.preflight.model.SeatMatrix;
import org.springframework.binding.message.MessageContext;

/**
 * A service for retrieving flight reservation information and to facilitate the
 * flight check-in process.
 * 
 * @author Dan Allen
 */
public interface CheckInService {

	public Reservation locateReservationForCheckIn(ReservationIdentifier reservationIdentifier, MessageContext messageContext);

	public Reservation fetchReservation(Long reservationId);

	public List<BoardingPass> locateBoardingPasses(Reservation reservation, boolean checkInGroup);

	public SeatMatrix buildSeatMatrixWithSelections(BoardingPass boardingPass);
	
	public void changeReservedSeat(BoardingPass boardingPass, Seat seat);
	
	public void checkIn(List<BoardingPass> boardingPasses);

}