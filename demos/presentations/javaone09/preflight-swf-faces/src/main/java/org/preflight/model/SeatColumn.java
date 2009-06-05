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
@Table(name = "seat_column")
public class SeatColumn implements Serializable {

	private static final long serialVersionUID = 123245234L;
	
	private SeatColumnId id;
	private Aircraft aircraft;
	private String letter;
	private FareClass section;
	private boolean aisleOnLeft;
	private boolean aisleOnRight;
	
	@EmbeddedId
	public SeatColumnId getId() {
		return id;
	}

	public void setId(SeatColumnId id) {
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
	public String getLetter() {
		return letter;
	}
	
	public void setLetter(String letter) {
		this.letter = letter;
	}

	@Enumerated(STRING)
	@Column(insertable = false, updatable = false)
	public FareClass getSection() {
		return section;
	}
	
	public void setSection(FareClass section) {
		this.section = section;
	}
	
	@Column(name = "aisle_left")
	public boolean isAisleOnLeft() {
		return aisleOnLeft;
	}

	public void setAisleOnLeft(boolean aisleOnLeft) {
		this.aisleOnLeft = aisleOnLeft;
	}

	@Column(name = "aisle_right")
	public boolean isAisleOnRight() {
		return aisleOnRight;
	}

	public void setAisleOnRight(boolean aisleOnRight) {
		this.aisleOnRight = aisleOnRight;
	}

 }
