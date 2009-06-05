package org.preflight.criteria;

import java.io.Serializable;

public class ReservationIdentifier implements Serializable {

	private static final long serialVersionUID = -6820308329960898038L;

	private String reservationNumber;

	private String passengerLastName;

	private boolean checkInGroup = false;

	public String getReservationNumber() {
		return reservationNumber;
	}

	public void setReservationNumber(String reservationNumber) {
		this.reservationNumber = reservationNumber;
	}

	public String getPassengerLastName() {
		return passengerLastName;
	}

	public void setPassengerLastName(String passengerLastName) {
		this.passengerLastName = passengerLastName;
	}

	public boolean isCheckInGroup() {
		return checkInGroup;
	}

	public void setCheckInGroup(boolean checkInGroup) {
		this.checkInGroup = checkInGroup;
	}

}
