package org.preflight.reservation.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.preflight.model.BoardingPass;
import org.preflight.model.Reservation;
import org.preflight.model.ReservationStatus;
import org.preflight.reservation.criteria.ReservationIdentifier;

@Name("reservationLocator")
@AutoCreate
public class ReservationLocator {
	
	public static final long MILLISECONDS_PER_HOUR = 60 * 60 * 1000;
	
	@In private EntityManager entityManager;
	
	public Reservation findActiveReservation(ReservationIdentifier reservationIdentifier) {
		try {
			return (Reservation) entityManager.createQuery(
				"select t.reservation from Ticket t where t.reservation.number = :number and t.passenger.lastName = :name and t.reservation.status = :status")
				.setParameter("number", reservationIdentifier.getReservationNumber())
				.setParameter("name", reservationIdentifier.getPassengerLastName())
				.setParameter("status", ReservationStatus.RESERVED)
				.getSingleResult();
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<BoardingPass> findBoardingPassesPendingCheckIn(Reservation reservation, int maxHoursUntilDeparture) {
		return (List<BoardingPass>) entityManager.createQuery(
				"select b from BoardingPass b join fetch b.ticket t join fetch t.passenger p join fetch b.flightSegment s join fetch s.flight f " +
				"where b.checkedIn = false and b.ticket.reservation = :reservation " +
				"and f.departureTime between current_timestamp() and :threshold " +
				"order by f.departureTime asc, p.lastName asc, p.firstName asc")
			.setParameter("reservation", reservation)
			.setParameter("threshold", new Date(new Date().getTime() + (maxHoursUntilDeparture * MILLISECONDS_PER_HOUR)))
			.getResultList();
	}
}