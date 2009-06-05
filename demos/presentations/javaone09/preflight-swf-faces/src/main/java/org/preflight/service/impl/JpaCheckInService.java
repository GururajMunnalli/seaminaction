package org.preflight.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.preflight.criteria.ReservationIdentifier;
import org.preflight.model.BoardingPass;
import org.preflight.model.Reservation;
import org.preflight.model.ReservationStatus;
import org.preflight.model.Seat;
import org.preflight.model.SeatMatrix;
import org.preflight.service.CheckInService;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("checkInService")
@Repository
public class JpaCheckInService implements CheckInService {
	
	public static final long MILLISECONDS_PER_HOUR = 60 * 60 * 1000;

	@PersistenceContext EntityManager entityManager;

	@Transactional(readOnly = true)
	public Reservation locateReservationForCheckIn(ReservationIdentifier reservationIdentifier, MessageContext messageContext) {
		try {
			return (Reservation) entityManager.createQuery(
				"select t.reservation from Ticket t where t.reservation.number = :number and t.passenger.lastName = :name and t.reservation.status = :status")
				.setParameter("number", reservationIdentifier.getReservationNumber())
				.setParameter("name", reservationIdentifier.getPassengerLastName())
				.setParameter("status", ReservationStatus.RESERVED)
				.getSingleResult();
		}
		catch (NoResultException e) {
			messageContext.addMessage(
				new MessageBuilder().warning().code("booking.not.found").defaultText(
					"We were unable to locate your booking. Either your reference number or last name were entered incorrectly or you don't have an active reservation.").build());
			return null;
		}
	}
   
	@Transactional(readOnly = true)
	public Reservation fetchReservation(Long reservationId) {
		return entityManager.find(Reservation.class, reservationId);
	}

	@Transactional(readOnly = true)
	public List<BoardingPass> locateBoardingPasses(Reservation reservation, boolean checkInGroup) {
		@SuppressWarnings("unchecked")
		List<BoardingPass> results = entityManager.createQuery(
			"select b from BoardingPass b join fetch b.ticket t join fetch t.passenger p join fetch b.flightSegment s join fetch s.flight f " +
			"where b.checkedIn = false and b.ticket.reservation = :reservation " +
			"and f.departureTime between current_timestamp() and :threshold " +
			"order by f.departureTime asc, p.lastName asc, p.firstName asc")
			.setParameter("reservation", reservation)
			.setParameter("threshold", new Date(new Date().getTime() + (24 * MILLISECONDS_PER_HOUR)))
			.getResultList();
		System.out.println("Found " + results.size() + " boarding pass(es) for reservation number " + reservation.getNumber());
		return results;
	}
   
	@Transactional(readOnly = true)
	public SeatMatrix buildSeatMatrixWithSelections(BoardingPass boardingPass) {
		@SuppressWarnings("unchecked")
		List<Seat> seats = entityManager.createQuery(
			"select s from Seat s join fetch s.row join fetch s.column " +
			"where s.aircraft = :aircraft order by s.row.number asc, s.column.letter desc")
			.setParameter("aircraft", boardingPass.getFlightSegment().getFlight().getAircraft())
			.getResultList();
		@SuppressWarnings("unchecked")
		List<BoardingPass> boardingPasses = entityManager.createQuery(
			"select b from BoardingPass b join fetch b.ticket t join fetch t.passenger " +
			"where b.flightSegment = :flightSegment")
			.setParameter("flightSegment", boardingPass.getFlightSegment())
			.getResultList();
		Map<Seat, BoardingPass> reservedSeats = new HashMap<Seat, BoardingPass>();
		for (BoardingPass bp : boardingPasses) {
			if (bp.getSeat() != null) {
				reservedSeats.put(bp.getSeat(), bp);
			}
		}
		return new SeatMatrix(seats, reservedSeats, boardingPass);
	}


	@Transactional(readOnly = true)
	public void changeReservedSeat(BoardingPass boardingPass, Seat seat) {
		boardingPass.setSeat(seat);

		//entityManager.find(BoardingPass.class, boardingPass.getId()).setSeat(entityManager.find(Seat.class, seat.getId()));
	}

	public void checkIn(List<BoardingPass> boardingPasses) {
		for (BoardingPass boardingPass : boardingPasses) {
			boardingPass.setCheckedIn(true);
		}
	}

}