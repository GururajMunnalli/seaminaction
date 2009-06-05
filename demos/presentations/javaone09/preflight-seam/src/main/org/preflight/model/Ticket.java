package org.preflight.model;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "ticket", uniqueConstraints =
	@UniqueConstraint(columnNames = "eticket_number")
)
public class Ticket implements Serializable {

	private static final long serialVersionUID = -2099234157703859736L;

	private long id;
	private Reservation reservation;
	private String eTicketNumber;
	private Passenger passenger;
	private Set<BoardingPass> boardingPasses;

	public Ticket() {
		super();
	}

	@Id @GeneratedValue
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = LAZY, optional = false)
	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	@Column(name = "eticket_number", nullable = false)
	public String getETicketNumber() {
		return this.eTicketNumber;
	}

	public void setETicketNumber(String eTicketNumber) {
		this.eTicketNumber = eTicketNumber;
	}

	@ManyToOne(fetch = LAZY, optional = false)
	public Passenger getPassenger() {
		return this.passenger;
	}

	public void setPassenger(Passenger passenger) {
		this.passenger = passenger;
	}

	@OneToMany(cascade = ALL, mappedBy = "ticket")
	public Set<BoardingPass> getBoardingPasses() {
		return boardingPasses;
	}

	public void setBoardingPasses(Set<BoardingPass> boardingPasses) {
		this.boardingPasses = boardingPasses;
	}

}
