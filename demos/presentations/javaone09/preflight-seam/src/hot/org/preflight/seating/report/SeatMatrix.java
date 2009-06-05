package org.preflight.seating.report;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.preflight.model.BoardingPass;
import org.preflight.model.FareClass;
import org.preflight.model.Passenger;
import org.preflight.model.Seat;
import org.preflight.model.SeatColumn;
import org.preflight.model.SeatRow;

public class SeatMatrix implements Serializable {
	
	private List<Section> sections;
	
	private List<SeatSelection> seatSelections;
	
	// we really want to pass in the Aircraft but the getter for seats isn't right yet
	public SeatMatrix(List<Seat> seats, Map<Seat, BoardingPass> boardingPassForSeat, BoardingPass currentBoardingPass) {

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
		Section currentSection = null;
		for (SeatRow row : seatsByRow.keySet()) {
			if (currentSection == null || currentSection.getFareClass() != row.getSection()) {
				currentSection = new Section(row.getSection());
				ColumnGroup currentColumnGroup = new ColumnGroup();
				for (Seat seatInRow : seatsByRow.get(row)) {
					Column column = new Column(seatInRow.getColumn().getLetter());
					for (Seat seatInColumn : seatsByColumn.get(seatInRow.getColumn())) {
						if (seatInColumn.getRow().getSection() == currentSection.getFareClass()) {
							SeatSelection seatSelection = new SeatSelection(
									seatInColumn,
									boardingPassForSeat.get(seatInColumn),
									currentBoardingPass.getFareClass() == currentSection.getFareClass(),
									currentPassenger, passengersInGroup);
							seatSelections.add(seatSelection);
							column.getSeatSelections().add(seatSelection);
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
			// add seats if iterating by row
			for (Seat seatInRow : seatsByRow.get(row)) {
				r.getSeatSelections().add(new SeatSelection(
					seatInRow,
					boardingPassForSeat.get(seatInRow),
					currentBoardingPass.getFareClass() == currentSection.getFareClass(),
					currentPassenger, passengersInGroup));
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
	
	public class Section {
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
	
	public class ColumnGroup {
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
	
	public class Column {
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
	
	public class Row {
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
	
	public class SeatSelection {
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
		
		public void select() {
			this.status = SeatSelectionStatus.SELF;
		}
		
		public void unselect() {
			this.status = SeatSelectionStatus.AVAILABLE;
		}
	}
}
