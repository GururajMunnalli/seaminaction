package org.preflight.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

import org.preflight.faces.FacesHelper;

public class SeatMatrix implements Serializable {
	
	private List<Section> sections;
	
	private List<SeatSelection> seatSelections;
	
	private SeatSelection seatSelection;
	
	// we really want to pass in the Aircraft but the getter for seats isn't right yet
	public SeatMatrix(List<Seat> seats, Map<Seat, BoardingPass> reservedSeats, BoardingPass currentBoardingPass) {

		Passenger currentPassenger = currentBoardingPass.getPassenger();
		Set<Passenger> passengersInGroup = currentBoardingPass.getReservation().getPassengers();
		
		Map<SeatRow, List<Seat>> seatsByRow = new LinkedHashMap<SeatRow, List<Seat>>();
		for (Seat seat : seats) {
			if (!seatsByRow.containsKey(seat.getRow())) {
				seatsByRow.put(seat.getRow(), new ArrayList<Seat>());
			}
			seatsByRow.get(seat.getRow()).add(seat); // TODO order by seat letter (reversed)
		}
		
		Map<SeatColumn, List<Seat>> seatsByColumn = new HashMap<SeatColumn, List<Seat>>();
		for (Seat seat : seats) {
			if (!seatsByColumn.containsKey(seat.getColumn())) {
				seatsByColumn.put(seat.getColumn(), new ArrayList<Seat>());
			}
			seatsByColumn.get(seat.getColumn()).add(seat); // TODO order by seat row
		}
		
		sections = new ArrayList<Section>();
		seatSelections = new ArrayList<SeatSelection>();
		Map<Seat, SeatSelection> seatSelectionBySeat = new HashMap<Seat, SeatSelection>();
		Section currentSection = null;
		for (SeatRow row : seatsByRow.keySet()) {
			if (currentSection == null || currentSection.getFareClass() != row.getSection()) {
				currentSection = new Section(row.getSection());
				ColumnGroup currentColumnGroup = new ColumnGroup();
				for (Seat seatInRow : seatsByRow.get(row)) {
					Column column = new Column(seatInRow.getColumn().getLetter());
					for (Seat seatInColumn : seatsByColumn.get(seatInRow.getColumn())) {
						if (seatInColumn.getRow().getSection() == currentSection.getFareClass()) {
							SeatSelection selection = new SeatSelection(
									seatInColumn,
									reservedSeats.get(seatInColumn),
									currentBoardingPass.getFareClass() == currentSection.getFareClass(),
									currentPassenger, passengersInGroup);
							seatSelections.add(selection);
							seatSelectionBySeat.put(seatInColumn, selection);
							column.getSeatSelections().add(selection);
							if (selection.getStatus() == SeatSelectionStatus.SELF) {
								seatSelection = selection;
							}
						}
					}
					currentColumnGroup.getColumns().add(column);
					if (seatInRow.getColumn().isAisleOnLeft()) {
						currentColumnGroup.setLast(false);
						currentSection.getColumnGroups().add(currentColumnGroup);
						currentColumnGroup = new ColumnGroup();
					}
				}
				if (currentColumnGroup.getColumns().size() > 0) {
					currentSection.getColumnGroups().add(currentColumnGroup);
				}
				sections.add(currentSection);
			}
			Row r = new Row(row.getNumber());
			// add seats for when UI is iterating by row
			List<Seat> seatsInRow = seatsByRow.get(row);
			// need to reverse since query brings them back in reverse order
			Collections.reverse(seatsInRow);
			for (Seat seatInRow : seatsInRow) {
				r.getSeatSelections().add(seatSelectionBySeat.get(seatInRow));
			}
			currentSection.getRows().add(r);
		}
	}
	
	public List<Section> getSections() {
		return sections;
	}
	
	public List<Row> getRows() {
		List<Row> rows = new ArrayList<Row>();
		for (Section section : sections) {
			rows.addAll(section.getRows());
		}
		return rows;
	}
	
	public List<SeatSelection> getSeatSelections() {
		return seatSelections;
	}
	
	public Seat getSelectedSeat() {
		return seatSelection != null ? seatSelection.getSeat() : null;
	}
	
	/*
	public void select(BoardingPass boardingPass, SeatSelection selection) {
		if (seatSelection != null) {
			seatSelection.unselect();
		}
		
//		for (SeatSelection candidate : seatSelections) {
//			if (candidate.getStatus() == SeatSelectionStatus.SELF) {
//				candidate.unselect();
//				break;
//			}
//		}
		
		selection.select();
		boardingPass.setSeat(selection.getSeat());
		seatSelection = selection;
		currentSelection = null;
	}*/
	
	/**
	 * A JSF ActionListener method that captures the value of the current iteration variable
	 * and stores it until it can be processed. This is a workaround for not being able
	 * to use a parameterized method expression in the view template.
	 */
	public void select(ActionEvent e) throws AbortProcessingException {
		SeatSelection selection = FacesHelper.resolveContextVariable("_seatSelection", SeatSelection.class);
		if (seatSelection != null) {
			seatSelection.unselect();
		}
		
		selection.select();
		seatSelection = selection;
	}
	

	public class Section implements Serializable {
		private FareClass fareClass;
		private List<ColumnGroup> columnGroups;
		private List<Row> rows;
		
		public Section(FareClass fareClass) {
			this.fareClass = fareClass;
			this.columnGroups = new ArrayList<ColumnGroup>();
			this.rows = new ArrayList<Row>();
		}
		
		public List<ColumnGroup> getColumnGroups() {
			return columnGroups;
		}
		
		public List<Row> getRows() {
			return rows;
		}
		
		public FareClass getFareClass() {
			return fareClass;
		}
	}
	
	
	public class ColumnGroup implements Serializable {
		private boolean last;
		private List<Column> columns;

		public ColumnGroup() {
			last = true;
			columns = new ArrayList<Column>();
		}

		public boolean isLast() {
			return last;
		}

		public void setLast(boolean last) {
			this.last = last;
		}

		public List<Column> getColumns() {
			return columns;
		}
	}
	
	public class Column implements Serializable {
		private String letter;
		private List<SeatSelection> seatSelections;
		
		public Column(String letter) {
			this.letter = letter;
			this.seatSelections = new ArrayList<SeatSelection>();
		}
		
		public String getLetter() {
			return letter;
		}
		
		public List<SeatSelection> getSeatSelections() {
			return seatSelections;
		}
	}
	
	public class Row implements Serializable {
		private int number;
		private List<SeatSelection> seatSelections;
		
		public Row(int number) {
			this.number = number;
			this.seatSelections = new ArrayList<SeatSelection>();
		}
		
		public int getNumber() {
			return number;
		}
		
		public List<SeatSelection> getSeatSelections() {
			return seatSelections;
		}
	}

	public class SeatSelection implements Serializable {
		private Seat seat;
		private SeatSelectionStatus status;
		
		public SeatSelection(Seat seat, BoardingPass boardingPass, boolean seatInFareClass, Passenger passenger, Set<Passenger> passengersInGroup) {
			this.seat = seat;
			if (!seatInFareClass) {
				status = SeatSelectionStatus.UNAVAILABLE;
			}
			else if (boardingPass == null) {
				status = SeatSelectionStatus.AVAILABLE;
			}
			else if (boardingPass.getTicket().getPassenger() == passenger) {
				status = SeatSelectionStatus.SELF;
			}
			else if (passengersInGroup.contains(boardingPass.getPassenger())) {
				status = SeatSelectionStatus.OTHER_IN_GROUP;
			}
			else {
				status = SeatSelectionStatus.UNAVAILABLE;
			}
		}
		
		public Seat getSeat() {
			return seat;
		}
		
		public SeatSelectionStatus getStatus() {
			return status;
		}

		public boolean isAvailable() {
			return status == SeatSelectionStatus.AVAILABLE;
		}
		
		public boolean isSelected() {
			return status == SeatSelectionStatus.SELF;
		}
		
		public void select() {
			status = SeatSelectionStatus.SELF;
		}
		
		public void unselect() {
			status = SeatSelectionStatus.AVAILABLE;
		}
	}
	
	public enum SeatSelectionStatus {
		AVAILABLE('A', "available"),
		UNAVAILABLE('U', "unavailable"),
		SELF('S', "self"),
		OTHER_IN_GROUP('G', "group"),
		NO_SEAT('X', "blank");
		
		private char abbreviation;
		private String key;
		
		private SeatSelectionStatus(char abbreviation, String key) {
			this.abbreviation = abbreviation;
			this.key = key;
		}
		
		public char getAbbreviation() {
			return abbreviation;
		}
		
		public String getKey() {
			return key;
		}
	}
}
