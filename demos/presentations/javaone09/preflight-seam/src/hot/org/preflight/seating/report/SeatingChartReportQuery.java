package org.preflight.seating.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.preflight.model.BoardingPass;
import org.preflight.model.Seat;

@Name("seatingChartReportQuery")
@AutoCreate
public class SeatingChartReportQuery {
	
	@In private EntityManager entityManager;
	
	public SeatMatrix getSeatMatrixWithSelections(BoardingPass boardingPass) {
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
	
}
