package org.preflight.model;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Enumerated;

@Entity
@Table(name = "seat", uniqueConstraints = 
	@UniqueConstraint(columnNames = {"aircraft_id", "row_number", "letter"})
)
// aircraft, rowNumber and section have to be mapped in twice order there is no way to insert
// that's because to override the column names, the insertable/updatable have to be set to false
public class Seat implements Serializable {
	
	private static final long serialVersionUID = 1396773068506754852L;
	
	private long id;
	private SeatRow row;
	private SeatColumn column;
	private int rowNumber;
	private char letter;
	private FareClass section;
	private Aircraft aircraft;
	private Set<BoardingPass> boardingPasses = new HashSet<BoardingPass>();

	@Id @GeneratedValue	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumns({
		@JoinColumn(name = "aircraft_id", referencedColumnName = "aircraft_id", insertable = false, updatable = false),
		@JoinColumn(name = "row_number", referencedColumnName = "number", insertable = false, updatable = false),
		@JoinColumn(name = "section", referencedColumnName = "section", insertable = false, updatable = false)
	})
	public SeatRow getRow() {
		return row;
	}
	
	public void setRow(SeatRow row) {
		this.row = row;
	}

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumns({
		@JoinColumn(name = "aircraft_id", referencedColumnName = "aircraft_id", insertable = false, updatable = false),
		@JoinColumn(name = "letter", referencedColumnName = "letter", insertable = false, updatable = false),
		@JoinColumn(name = "section", referencedColumnName = "section", insertable = false, updatable = false)
	})
	public SeatColumn getColumn() {
		return column;
	}

	public void setColumn(SeatColumn column) {
		this.column = column;
	}

	@Column(name = "row_number", nullable = false)
	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	@Column(nullable = false)
	public char getLetter() {
		return letter;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	@Enumerated(STRING)
	@Column(nullable = false)
	public FareClass getSection() {
		return section;
	}
	
	public void setSection(FareClass section) {
		this.section = section;
	}
	
	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "aircraft_id")
	public Aircraft getAircraft() {
		return aircraft;
	}
	
    public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	@OneToMany(mappedBy = "seat")
    public Set<BoardingPass> getBoardingPasses() {
        return boardingPasses;
    }

    public void setBoardingPasses(Set<BoardingPass> boardingPasses) {
        this.boardingPasses = boardingPasses;
    }
	
	@Transient
	public String getNumber() {
		return String.valueOf(row.getNumber()) + column.getLetter();
	}

}
