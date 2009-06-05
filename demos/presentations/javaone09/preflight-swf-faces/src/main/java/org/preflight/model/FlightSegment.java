package org.preflight.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "flight_segment", uniqueConstraints = 
	@UniqueConstraint(columnNames = {"reservation_id", "leg", "sequence"})
)
public class FlightSegment implements Serializable {

	private static final long serialVersionUID = 7816028537626353408L;

	private long id;
	private Reservation reservation;
	private int leg;
	private int sequence;
	private Flight flight;

	public FlightSegment() {
		super();
	}

	@Id @GeneratedValue
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	public Reservation getReservation() {
		return this.reservation;
	}
	
	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	public int getLeg() {
		return leg;
	}

	public void setLeg(int leg) {
		this.leg = leg;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	public Flight getFlight() {
		return this.flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

}
