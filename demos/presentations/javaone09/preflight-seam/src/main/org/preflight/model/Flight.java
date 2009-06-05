package org.preflight.model;

import static javax.persistence.EnumType.STRING;
import java.io.Serializable;
import java.lang.String;
import java.util.Date;
import javax.persistence.*;
import org.preflight.model.Airport;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "flight")
public class Flight implements Serializable {

	private static final long serialVersionUID = 6846582918837812509L;

	private long id;
	private int number;
	private Airport origin;
	private Airport destination;
	private Date departureTime;
	private Date arrivalTime;
	private String departureGate;
	private String arrivalGate;
	private FlightStatus status;
	private Aircraft aircraft;
	private Airline airline;

	public Flight() {
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

	@Column(nullable = false)
	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@ManyToOne(fetch = LAZY, optional = false)
	public Airport getOrigin() {
		return this.origin;
	}

	public void setOrigin(Airport origin) {
		this.origin = origin;
	}

	@ManyToOne(fetch = LAZY, optional = false)
	public Airport getDestination() {
		return this.destination;
	}

	public void setDestination(Airport destination) {
		this.destination = destination;
	}

	@Column(name = "departure_time", nullable = false)
	public Date getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	@Column(name = "arrival_time", nullable = false)
	public Date getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	@Column(name = "departure_gate")
	public String getDepartureGate() {
		return this.departureGate;
	}

	public void setDepartureGate(String departureGate) {
		this.departureGate = departureGate;
	}

	@Column(name = "arrival_gate")
	public String getArrivalGate() {
		return this.arrivalGate;
	}

	public void setArrivalGate(String arrivalGate) {
		this.arrivalGate = arrivalGate;
	}

	@Enumerated(STRING)
	public FlightStatus getStatus() {
		return this.status;
	}

	public void setStatus(FlightStatus status) {
		this.status = status;
	}

	@ManyToOne(fetch = LAZY)
	public Aircraft getAircraft() {
		return this.aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	@ManyToOne(fetch = LAZY, optional = false)
	public Airline getAirline() {
		return airline;
	}

}
