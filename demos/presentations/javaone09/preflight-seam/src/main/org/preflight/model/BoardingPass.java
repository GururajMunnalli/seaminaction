package org.preflight.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.EnumType.STRING;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "boarding_pass", uniqueConstraints = 
	@UniqueConstraint(columnNames = {"ticket_id", "flight_segment_id", "seat_id"})
)
public class BoardingPass implements Serializable {

	private static final long serialVersionUID = -586693271293486727L;

	private BoardingPassId id;
	private Ticket ticket;
	private FlightSegment flightSegment;
	private FareClass fareClass;
	private Seat seat;
	private Integer bags;
	private boolean checkedIn;

	public BoardingPass() {
		super();
	}

	@EmbeddedId
	public BoardingPassId getId() {
		return id;
	}

	public void setId(BoardingPassId id) {
		this.id = id;
	}

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(nullable = false, insertable = false, updatable = false)
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "flight_segment_id", nullable = false, insertable = false, updatable = false)
	public FlightSegment getFlightSegment() {
		return flightSegment;
	}

	public void setFlightSegment(FlightSegment flightSegment) {
		this.flightSegment = flightSegment;
	}

	@ManyToOne(fetch = LAZY, optional = true)
	public Seat getSeat() {
		return this.seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}
	
	@Column(name = "bags")
	public Integer getBags() {
		return bags;
	}
	
	public void setBags(Integer bags) {
		this.bags = bags;
	}

	@Enumerated(STRING)
	@Column(name = "fare_class", nullable = false)
	public FareClass getFareClass() {
		return fareClass;
	}

	public void setFareClass(FareClass fareClass) {
		this.fareClass = fareClass;
	}

	@Column(name = "checked_in", nullable = false)
	public boolean getCheckedIn() {
		return this.checkedIn;
	}

	public void setCheckedIn(boolean checkedIn) {
		this.checkedIn = checkedIn;
	}
	
	@Transient
	public Passenger getPassenger() {
		return ticket.getPassenger();
	}
	
	@Transient
	public Reservation getReservation() {
		return ticket.getReservation();
	}

}
