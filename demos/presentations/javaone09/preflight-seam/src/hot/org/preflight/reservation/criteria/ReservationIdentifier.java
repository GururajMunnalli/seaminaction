package org.preflight.reservation.criteria;

import java.io.Serializable;

import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

@Name("reservationIdentifier")
@Scope(ScopeType.CONVERSATION)
public class ReservationIdentifier implements Serializable {

	private static final long serialVersionUID = -6820308329960898038L;

	private String reservationNumber;
	
	private String passengerLastName;
	
	@NotNull
	@NotEmpty
	public String getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	@NotNull
	@NotEmpty
	public String getPassengerLastName() {
		return passengerLastName;
	}

	public void setPassengerLastName(String passengerLastName) {
		this.passengerLastName = passengerLastName;
	}
	
}
