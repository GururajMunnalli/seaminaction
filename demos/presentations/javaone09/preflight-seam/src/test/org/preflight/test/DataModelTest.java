package org.preflight.test;

import javax.persistence.EntityManager;

import org.jboss.seam.mock.SeamTest;
import org.preflight.model.BoardingPass;
import org.preflight.model.BoardingPassId;
import org.testng.annotations.Test;

public class DataModelTest extends SeamTest {

	@Test
	public void retrieveSeatStatus() throws Exception {
		new FacesRequest() {
			@Override
			protected void invokeApplication() {
				EntityManager em = (EntityManager) getValue("#{entityManager}");
				BoardingPass bp = em.find(BoardingPass.class, new BoardingPassId(1l, 1l));
				//SeatingChartReportQuery query = (SeatingChartReportQuery) getValue("#{seatingChartReportQuery}");
				//SeatMatrixReport seatMatrix = query.getSeatMatrixWithSelections(bp);
				//System.out.println(seatMatrix.getSections());
				/*List<Object[]> results = (List<Object[]>) em.createQuery("select s, b from Seat s join fetch s.row join fetch s.column left join s.boardingPasses b left join fetch b.ticket t left join fetch t.passenger where s.aircraft.model = 'A319' order by s.row asc, s.letter desc").getResultList();
				for (Object[] result : results) {
					System.out.println(((Seat) result[0]).getNumber() + ", " + (result[1] == null ? null : ((BoardingPass) result[1]).getTicket().getPassenger().getName()));
				}*/
			}
		}.run();
	}
}
