package org.preflight.model;

import static javax.persistence.EnumType.STRING;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

@Embeddable
public class SeatRowId implements Serializable {

	private static final long serialVersionUID = -3275228518883783399L;

	private long aircraftId;

	private int number;
	
	private FareClass section;

	public SeatRowId() {
		super();
	}

	public SeatRowId(long aircraftId, int number, FareClass section) {
		this.aircraftId = aircraftId;
		this.number = number;
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
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
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
		if (!(other instanceof SeatRowId)) {
			return false;
		}
		SeatRowId castOther = (SeatRowId) other;

		return (getAircraftId() == castOther.getAircraftId())
				&& (getNumber() == castOther.getNumber())
				&& (getSection() == castOther.getSection());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) getAircraftId();
		result = 37 * result + getNumber();
		result = 37 * result + (getSection() != null ? getSection() : "").hashCode();

		return result;
	}
}
