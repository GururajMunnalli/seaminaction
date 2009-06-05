package org.preflight.seating.action;

import java.io.Serializable;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.preflight.model.BoardingPass;
import org.preflight.seating.report.SeatMatrix;
import org.preflight.seating.report.SeatingChartReportQuery;
import org.preflight.seating.report.SeatMatrix.SeatSelection;
import org.preflight.seating.report.SeatMatrix.SeatSelectionStatus;

@Name("seatSelector")
@Scope(ScopeType.PAGE)
public class SeatSelector implements Serializable {
	
	@In SeatingChartReportQuery seatingChartReportQuery;
	
	@Out(required = false) BoardingPass selectedBoardingPass;

	@Out(required = false) SeatMatrix seatMatrix;
	
	public void viewOrChangeSeats(BoardingPass boardingPass) {
		selectedBoardingPass = boardingPass;
		seatMatrix = seatingChartReportQuery.getSeatMatrixWithSelections(boardingPass);
	}
	
	public void close() {
		seatMatrix = null;
		selectedBoardingPass = null;
	}
	
	public void select(SeatSelection selection) {
		for (SeatSelection candidate : seatMatrix.getSeatSelections()) {
			if (candidate.getStatus() == SeatSelectionStatus.SELF) {
				candidate.unselect();
				break;
			}
		}
		selection.select();
		selectedBoardingPass.setSeat(selection.getSeat());
	}
	
}
