package org.preflight.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "reservation", uniqueConstraints = {
	@UniqueConstraint(columnNames = "number")
})
public class Reservation implements Serializable {

	private static final long serialVersionUID = -8488608476808076410L;

	private long id;
	private Passenger purchaser;
	private Date issueDate;
	private BigDecimal airfare;
	private BigDecimal total;
	private Airport origin;
	private Airport destination;
	private ItineraryType itineraryType;
	private String number;
	private ReservationStatus status;
	private Set<FlightSegment> flightSegments;
	private Set<Ticket> tickets;

	public Reservation() {
		super();
	}

	@Id
	@GeneratedValue
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = LAZY, optional = false)
	@JoinColumn(name = "purchaser_id")
	public Passenger getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(Passenger purchaser) {
		this.purchaser = purchaser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "issue_date", nullable = false)
	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public BigDecimal getAirfare() {
		return this.airfare;
	}

	public void setAirfare(BigDecimal airfare) {
		this.airfare = airfare;
	}
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@ManyToOne(fetch = LAZY)
	public Airport getOrigin() {
		return origin;
	}

	public void setOrigin(Airport origin) {
		this.origin = origin;
	}

	@ManyToOne(fetch = LAZY)
	public Airport getDestination() {
		return destination;
	}

	@Enumerated(STRING)
	@Column(name = "itinerary_type", nullable = false)
	public ItineraryType getItineraryType() {
		return itineraryType;
	}

	public void setItineraryType(ItineraryType itineraryType) {
		this.itineraryType = itineraryType;
	}

	@Column(name = "number", nullable = false)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Enumerated(STRING)
	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	@OneToMany(cascade = ALL, mappedBy = "reservation")
	@OrderBy("sequence asc, leg asc")
	public Set<FlightSegment> getFlightSegments() {
		return flightSegments;
	}

	public void setFlightSegments(Set<FlightSegment> flightSegments) {
		this.flightSegments = flightSegments;
	}
	
	@OneToMany(cascade = ALL, mappedBy = "reservation")
	public Set<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}
	
	@Transient
	public Set<Passenger> getPassengers() {
		Set<Passenger> passengers = new HashSet<Passenger>();
		for (Ticket ticket : tickets) {
			passengers.add(ticket.getPassenger());
		}
		return passengers;
	}
	
	@Transient
	public Set<Passenger> getOtherPassengers(Passenger passenger) {
		Set<Passenger> others = new HashSet<Passenger>(getPassengers());
		others.remove(passenger);
		return others;
	}

}
