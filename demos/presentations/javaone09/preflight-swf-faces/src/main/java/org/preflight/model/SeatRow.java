package org.preflight.model;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seat_row")
public class SeatRow implements Serializable {
	
	private static final long serialVersionUID = -8488608429408076410L;
	
	private SeatRowId id;
	private Aircraft aircraft;
	private int number;
	private FareClass section;
	private boolean exitRow;

	@EmbeddedId
	public SeatRowId getId() {
		return id;
	}

	public void setId(SeatRowId id) {
		this.id = id;
	}

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(insertable = false, updatable = false)
	public Aircraft getAircraft() {
		return aircraft;
	}
	
	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	@Column(insertable = false, updatable = false)
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}

	@Enumerated(STRING)
	@Column(insertable = false, updatable = false)
	public FareClass getSection() {
		return section;
	}
	
	public void setSection(FareClass section) {
		this.section = section;
	}

	@Column(name = "exit_row")
	public boolean isExitRow() {
		return exitRow;
	}

	public void setExitRow(boolean exitRow) {
		this.exitRow = exitRow;
	}
}