package org.preflight.model;

import static javax.persistence.EnumType.STRING;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import org.hibernate.validator.Pattern;

@Embeddable
public class SeatColumnId implements Serializable {

	private static final long serialVersionUID = 9074307351775190040L;

	private long aircraftId;

	private char letter;

	private FareClass section;

	public SeatColumnId() {
		super();
	}

	public SeatColumnId(long aircraftId, char letter, FareClass section) {
		this.aircraftId = aircraftId;
		this.letter = letter;
		this.section = section;
	}
	
	@Column(name = "aircraft_id", nullable = false)
	public long getAircraftId() {
		return aircraftId;
	}

	public void setAircraftId(long aircraftId) {
		this.aircraftId = aircraftId;
	}

	@Column(nullable = false)
	@Pattern(regex = "[A-Z]", message = "{validator.upperCaseLetter}")
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (!(other instanceof SeatColumnId)) {
			return false;
		}
		SeatColumnId castOther = (SeatColumnId) other;

		return (getAircraftId() == castOther.getAircraftId())
				&& (getLetter() == castOther.getLetter())
				&& (getSection() == castOther.getSection());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) getAircraftId();
		result = 37 * result + Character.valueOf(letter).hashCode();
		result = 37 * result + (getSection() != null ? getSection() : "").hashCode();

		return result;
	}
}
